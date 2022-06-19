package com.example.luonvuituoi

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Base64.encode
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.luonvuituoi.databinding.ActivityLoginBinding
import com.example.luonvuituoi.helper.*
import com.facebook.*
import com.facebook.appevents.AppEventsLogger
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.zxing.qrcode.encoder.Encoder.encode
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*


class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    //googleAuth
    lateinit var gso: GoogleSignInOptions
    lateinit var googleSignInClient: GoogleSignInClient
    val SIGN_IN_CODE = 100
    var mAuth: FirebaseAuth? = null
    var callbackManager : CallbackManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FacebookSdk.sdkInitialize(getApplicationContext())
        AppEventsLogger.activateApp(this);
        PreferenceHelper.getSharedPreferences(this)
        initializeSignin()
        mAuth = FirebaseAuth.getInstance()
        callbackManager = CallbackManager.Factory.create()

        binding.ibFacebook.setOnClickListener {
            Log.e("test","123")
            binding.loginButton.performClick()
            SigninwithFB()
        }

    }
    private fun SigninwithFB() {
        Log.e("test","456")
        binding.loginButton.setReadPermissions("email", "public_profile", "user_birthday", "user_friends")
        binding.loginButton.registerCallback(callbackManager,object :FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                handleFBAccessToken(result!!.accessToken)
            }

            override fun onCancel() {
                Log.e("123","cancel")
            }

            override fun onError(error: FacebookException?) {
                error?.message?.let { Log.e("123", it) }
            }

        })
    }

    private fun handleFBAccessToken(accessToken: AccessToken?) {
        val credential = FacebookAuthProvider.getCredential(accessToken!!.token)
        mAuth!!.signInWithCredential(credential)
            .addOnFailureListener {e->
                Toast.makeText(this,e.message,Toast.LENGTH_SHORT).show()
            }
            .addOnSuccessListener {result ->

                val intent = Intent(this, MainActivity::class.java)
                updatePreferenceFB(result.user?.uid.toString(),result.user?.displayName.toString(),result.user?.email.toString())
                PreferenceHelper.writeBooleanToPreference(USER_FACEBOOK_LOGIN, true)
                intent.putExtra("UserName", result.user?.displayName)
                intent.putExtra("UserEmail", result.user?.email)
                intent.putExtra("UserPhoto", result.user?.photoUrl.toString())
                intent.putExtra("uid", result.user?.uid)
              Toast.makeText(this, "Welcome ${result.user?.displayName}", Toast.LENGTH_SHORT).show()
                saveUserFB(result.user?.uid.toString(),result.user?.displayName.toString(),result.user?.email.toString(),result.user?.photoUrl.toString())
                startActivity(intent)
                finish()
               // Toast.makeText(this,email,Toast.LENGTH_SHORT).show()
            }
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
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
       // Log.e("code111",requestCode.toString() +" "+ SIGN_IN_CODE.toString())
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

    private fun updatePreferenceFB(uid : String , name : String, email :String) {
        PreferenceHelper.writeBooleanToPreference(KEY_USER_LOGGED_IN, true)
        PreferenceHelper.writeStringToPreference(KEY_USER_GOOGLE_ID, uid)
        PreferenceHelper.writeStringToPreference(KEY_DISPLAY_NAME, name)
        PreferenceHelper.writeStringToPreference(KEY_USER_GOOGLE_GMAIL, email)
    }
    private fun updatePreference(account: GoogleSignInAccount) {
        PreferenceHelper.writeBooleanToPreference(KEY_USER_LOGGED_IN, true)
        PreferenceHelper.writeStringToPreference(KEY_USER_GOOGLE_ID, account.id)
        PreferenceHelper.writeStringToPreference(KEY_DISPLAY_NAME, account.displayName)
        PreferenceHelper.writeStringToPreference(KEY_USER_GOOGLE_GMAIL, account.email)
    }
    private fun saveUserFB(uid : String , name : String, email :String,url : String)
    {

        val database = FirebaseDatabase.getInstance()

        val dbUsers = database.getReference("users").child(uid)

        dbUsers.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    //even if user exit in database the FCM token will be different for each device
                    FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = FirebaseDatabase.getInstance().getReference("users")
                                .child(uid)
                            val token = Objects.requireNonNull(task.result)
                            user.child("token").setValue(token)
                        } else {
                            Log.d("TAG", "onComplete: " + task.exception!!.message)
                        }
                    }
                    Toast.makeText(
                        this@LoginActivity,
                        "Welcome Back ${name}",
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
                                    email,
                                    name,
                                    username,
                                    url,
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