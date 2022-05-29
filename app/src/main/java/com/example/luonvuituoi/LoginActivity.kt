package com.example.luonvuituoi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luonvuituoi.databinding.ActivityLoginBinding
import com.example.luonvuituoi.helper.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import java.util.*


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    //googleAuth
    lateinit var gso: GoogleSignInOptions
    lateinit var googleSignInClient: GoogleSignInClient
    val SIGN_IN_CODE = 100
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        PreferenceHelper.getSharedPreferences(this)
        initializeSignin()

        mAuth = FirebaseAuth.getInstance()
        val currentUserUid = mAuth.currentUser?.uid ?: "u5"
        val currentUser = mAuth.currentUser?:"ddd"
        Log.e("conghau", currentUser.toString())
//        binding.apply {
//            ibMobile.setOnClickListener {
//                startActivity(Intent(this@LoginActivity, PhoneNumberSignInActivity::class.java))
//            }
//        }

    }

    private fun initializeSignin() {
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        mAuth = FirebaseAuth.getInstance()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.ibGoogle.setOnClickListener {
            val intent: Intent = googleSignInClient.signInIntent
            startActivityForResult(intent, SIGN_IN_CODE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("code111",requestCode.toString() +" "+ SIGN_IN_CODE.toString())
        if (requestCode == SIGN_IN_CODE) {
            val task: Task<GoogleSignInAccount>? = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(task: Task<GoogleSignInAccount>?) {
        try {
            Log.e("task", task?.exception?.message.toString())
            if (task!!.isSuccessful) {
                val account = task.getResult(ApiException::class.java)
                PreferenceHelper.writeBooleanToPreference(KEY_LOGIN_WITH_OAUTH, true)
                updatePreference(account!!)
             //   Log.e("147852", account.id!!)
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("UserName", account.displayName)
                intent.putExtra("UserEmail", account.email)
                intent.putExtra("UserPhoto", account.photoUrl?.toString())
                intent.putExtra("uid", account.id)
//                Toast.makeText(this, "Welcome ${account.displayName}", Toast.LENGTH_SHORT)
//                    .show()
                saveUser(account)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(
                    this,
                    "Login Error " + task.exception?.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {

        }
    }

    private fun updatePreference(account: GoogleSignInAccount) {
        PreferenceHelper.writeBooleanToPreference(KEY_USER_LOGGED_IN, true)
        PreferenceHelper.writeStringToPreference(KEY_USER_GOOGLE_ID, account.id)
        PreferenceHelper.writeStringToPreference(KEY_DISPLAY_NAME, account.displayName)
        PreferenceHelper.writeStringToPreference(KEY_USER_GOOGLE_GMAIL, account.email)
    }

    private fun saveUser(account: GoogleSignInAccount) {

        val database = FirebaseDatabase.getInstance()

        val dbUsers = database.getReference("users").child(account.id!!)

        dbUsers.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    //even if user exit in database the FCM token will be different for each device
                    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = FirebaseDatabase.getInstance().getReference("users")
                                .child(account.id!!)
                            val token = Objects.requireNonNull(task.result)
                            user.child("token").setValue(token)
                        } else {
                            Log.d("TAG", "onComplete: " + task.exception!!.message)
                        }
                    }
                    Toast.makeText(
                        this@LoginActivity,
                        "Welcome Back ${account.displayName}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    return
                }
                if (!snapshot.exists()) {
                    FirebaseMessaging.getInstance().token.addOnCompleteListener {
                        if (it.isSuccessful) {
                            val token: String = Objects.requireNonNull<String>(it.result)
                            val username: String = ""
                            val user =
                                UserModel(
                                    account.email!!,
                                    account.displayName,
                                    username,
                                    account.photoUrl?.toString(),
                                    null,
                                    token
                                )

                            dbUsers.setValue(user)
                                .addOnCompleteListener { it_inside ->
                                    if (it_inside.isSuccessful) {
                                        Toast.makeText(
                                            this@LoginActivity,
                                            "token saved",
                                            Toast.LENGTH_SHORT
                                        )
                                            .show()
                                    }
                                }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }
}