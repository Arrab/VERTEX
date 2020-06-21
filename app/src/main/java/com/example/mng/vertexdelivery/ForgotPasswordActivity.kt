package com.example.mng.vertexdelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var txtEmail:EditText
    private lateinit var auth:FirebaseAuth
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        txtEmail = findViewById(R.id.txtEmail)
        auth = FirebaseAuth.getInstance()
        progressBar = findViewById(R.id.progressBar3)
    }

    fun send(view:View){
        val email:String =txtEmail.text.toString()

        if(!TextUtils.isEmpty(email)){
            auth.sendPasswordResetEmail(email).addOnCompleteListener(this){
                task ->
                if(task.isSuccessful){
                    progressBar.visibility = View.VISIBLE
                    Toast.makeText(this,"Check your mail box", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, LoginActivity::class.java))

                }else {
                    Toast.makeText(this,"Email not found", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}