package com.example.mng.vertexdelivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.mng.vertexdelivery.common.Common
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity : AppCompatActivity() {

    private lateinit var txtName:EditText
    private lateinit var txtLastName:EditText
    private lateinit var txtEmail:EditText
    private lateinit var txtPassword:EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var dbReference: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        txtName = findViewById(R.id.txtName)
        txtLastName = findViewById(R.id.txtLastName)
        txtEmail = findViewById(R.id.txtEmail)
        txtPassword = findViewById(R.id.txtPassword)

        progressBar = findViewById(R.id.progressBar)

        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        dbReference = database.reference.child(Common.USER_REF)

    }

    fun register(view: View){
        createNewAccount()
    }


    private fun createNewAccount(){
        val name:String = txtName.text.toString()
        val lastName:String = txtLastName.text.toString()
        val email:String = txtEmail.text.toString()
        val password:String = txtPassword.text.toString()
        val category:String = Common.USER_CATEGORY

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(lastName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            progressBar.visibility = View.VISIBLE

            auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this) {
                    task ->

                    if(task.isComplete){
                        val user: FirebaseUser?= auth.currentUser
                        verifyEmail(user)

                        val userBD = user?.uid?.let { dbReference.child(it) }
                        userBD?.child("category")?.setValue(category)
                        userBD?.child("name")?.setValue(name)
                        userBD?.child("lastName")?.setValue(lastName)
                        userBD?.child("email")?.setValue(email)
                        userBD?.child("user_id")?.setValue(user?.uid)

                        action()
                    }
                }
        }
    }

    private fun action(){
        startActivity(Intent(this, LoginActivity::class.java))

    }

    private fun verifyEmail(user:FirebaseUser?){
        user?.sendEmailVerification()
            ?.addOnCompleteListener(this){
                task ->
                if (task.isComplete){
                    Toast.makeText(this,"Email sent",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(this,"Error on email verification",Toast.LENGTH_LONG).show()
                }
            }
    }
}