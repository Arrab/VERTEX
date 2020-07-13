package com.example.mng.vertexdelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.mng.vertexdelivery.callback.IUserLoadCallback
import com.example.mng.vertexdelivery.common.Common
import com.example.mng.vertexdelivery.model.UserModel
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var txtUser:EditText
    private lateinit var txtPassword:EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var auth:FirebaseAuth
    private lateinit var userLoadCallbaclListener: IUserLoadCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        txtUser = findViewById(R.id.txtUser)
        txtPassword = findViewById(R.id.txtPassword)

        progressBar = findViewById(R.id.progressBar2)
        auth = FirebaseAuth.getInstance()

    }

    fun forgotPassword(view:View){
        startActivity(Intent(this, ForgotPasswordActivity::class.java))
    }

    fun register(view:View){
        startActivity(Intent(this,RegisterActivity::class.java))
    }

    fun login(view:View){
        //action()
        loginUser()
    }


    private fun loginUser(){
        val user:String = txtUser.text.toString()
        val password:String = txtPassword.text.toString()
        var usr_model: UserModel?= null


        if(!TextUtils.isEmpty(user) && !TextUtils.isEmpty(password)){
            progressBar.visibility = View.VISIBLE
            auth.signInWithEmailAndPassword(user,password).addOnCompleteListener(this){
                task ->
                if(task.isSuccessful){
                    Common.currentUser_id = auth.uid.toString()
                    action()
                } else {
                    Toast.makeText(this,"Access not allowed",Toast.LENGTH_LONG).show()
                    startActivity(Intent(this,LoginActivity::class.java))
                }
            }
        } else{
            Toast.makeText(this,"Enter you user data",Toast.LENGTH_LONG).show()
            return
        }
    }

    private fun action(){
        startActivity(Intent(this, HomeActivity::class.java))
    }
}