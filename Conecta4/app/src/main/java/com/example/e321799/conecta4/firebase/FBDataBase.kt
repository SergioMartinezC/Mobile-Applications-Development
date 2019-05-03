package com.example.e321799.conecta4.firebase


import android.util.Log
import com.example.e321799.conecta4.model.Round
import com.example.e321799.conecta4.model.RoundRepository
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import es.uam.eps.multij.Tablero
import android.support.annotation.NonNull
import com.google.android.gms.tasks.OnCompleteListener
import kotlin.math.round


class FBDataBase: RoundRepository {
    private val DATABASENAME = "rounds"
    private val DEFAULT_PLAYER = "OPEN_ROUND"
    lateinit var db: DatabaseReference
    override fun open() {
        db = FirebaseDatabase.getInstance().reference.child(DATABASENAME)
//To change body of created functions use File | Settings | File Templates.
    }
    override fun close() {
        TODO("not implemented")
//To change body of created functions use File | Settings | File Templates.
    }
    override fun login(playername: String, password: String,
                       callback: RoundRepository.LoginRegisterCallback) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signInWithEmailAndPassword(playername, password).addOnCompleteListener()
        { it ->
            if (it.isSuccessful) {
                callback.onLogin(firebaseAuth.uid!!)
            } else {
                callback.onError("Error login as $playername")
            }
        }
//To change body of created functions use File | Settings | File Templates.
    }
    override fun register(playername: String, password: String,
                          callback: RoundRepository.LoginRegisterCallback) {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.createUserWithEmailAndPassword(playername, password).addOnCompleteListener()
        { it ->
            if (it.isSuccessful) {
                callback.onLogin(playername)
            } else {
                callback.onError("Error login as $playername")
            }
        }
//To change body of created functions use File | Settings | File Templates.
    }
    override fun getRounds(playeruuid: String, orderByField: String,
                           group: String, callback: RoundRepository.RoundsCallback) {
        db.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("DEBUG", p0.toString())
            }
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var rounds = listOf<Round>()
                for (postSnapshot in dataSnapshot.children) {
                    val round = postSnapshot.getValue(Round::class.java)!!
                    if (isOpenOrIamIn(round))
                        rounds += round
                }
                callback.onResponse(rounds)
            }
        })
//To change body of created functions use File | Settings | File Templates.
    }
    override fun addRound(round: Round, callback: RoundRepository.BooleanCallback) {
        var addRound = db.child(round.id).setValue(round)
        addRound.addOnCompleteListener { it ->
            if (it.isSuccessful) {
               callback.onResponse(true)
            } else {
               callback.onResponse(false)
            }
        }
//To change body of created functions use File | Settings | File Templates.
    }
    override fun updateRound(round: Round, callback: RoundRepository.BooleanCallback) {

        var updateRound = db.child(round.id).setValue(round)
        updateRound.addOnCompleteListener { it ->
            if (it.isSuccessful) {
                callback.onResponse(true)
            } else {
                callback.onResponse(false)
            }
        }
//To change body of created functions use File | Settings | File Templates.
    }

    override fun joinRound(round: Round, callback: RoundRepository.BooleanCallback) {
        val firebaseAuth = FirebaseAuth.getInstance()
        val email = firebaseAuth.currentUser?.email
        val uid = firebaseAuth.currentUser?.uid
        if (round.secondPlayerName == DEFAULT_PLAYER && round.firstPlayerName != email) {
            round.secondPlayerName = email!!
            round.secondPlayerUUID = uid!!

        }
    }

    fun startListeningChanges(callback: RoundRepository.RoundsCallback) {
        db = FirebaseDatabase.getInstance().getReference().child(DATABASENAME)
        db.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("DEBUG", p0.toString())
            }
            override fun onDataChange(p0: DataSnapshot) {
                var rounds = listOf<Round>()
                for (postSnapshot in p0.children) {
                    var round = postSnapshot.getValue(Round::class.java)!!
                    if (isOpenOrIamIn(round)) {
                        rounds += round
                    }

                }
                callback.onResponse(rounds)
            }
        })
    }

    fun startListeningBoardChanges(callback: RoundRepository.RoundsCallback) {
        db = FirebaseDatabase.getInstance().getReference().child(DATABASENAME)
        db.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                Log.d("DEBUG", p0.toString())
            }
            override fun onDataChange(p0: DataSnapshot) {
                var rounds = listOf<Round>()
                for (postSnapshot in p0.children)
                    rounds += postSnapshot.getValue(Round::class.java)!!
                callback.onResponse(rounds)
            }
        })
    }

    fun isOpenOrIamIn(round: Round) : Boolean {
        val firebaseAuth = FirebaseAuth.getInstance()
        if (round.board.estado != Tablero.EN_CURSO) {
            return false
        } else if ( round.firstPlayerName == firebaseAuth.currentUser?.email!! || round.secondPlayerName == firebaseAuth.currentUser?.email!!) {
            return true
        } else if ( round.firstPlayerName == DEFAULT_PLAYER ) {
            return true
        } else if ( round.secondPlayerName == DEFAULT_PLAYER ) {
            return true
        }
        return false
    }

}