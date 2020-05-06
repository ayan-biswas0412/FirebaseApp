package com.example.firebaseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    lateinit var editText: EditText
    lateinit var editTextIncome: EditText
    lateinit var buttonSave: Button
    lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       /* Sign_out.isEnabled = true*/
        setContentView(R.layout.activity_profile)

        editText = findViewById(R.id.data_input)
        editTextIncome = findViewById(R.id.data_income)


        buttonSave = findViewById(R.id.send_data2)
        button = findViewById(R.id.Sign_out)

        buttonSave.setOnClickListener {
            saveData()
        }

        button.setOnClickListener{
            logout()
        }

        /*Sign_out.setOnClickListener{
            AuthUI.getInstance().signOut(this@ProfileActivity).addOnCompleteListener{
                Sign_out.isEnabled= false

            }
                .addOnFailureListener {
                        e-> Toast.makeText(this@ProfileActivity,e.message,Toast.LENGTH_SHORT).show()
                }
        }*/


    }

    private fun logout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // ...
                AuthUI.getInstance().signOut(this@ProfileActivity).addOnCompleteListener{
                    /*Sign_out.isEnabled= false*/
                    var startIntent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(startIntent)
                    finish()
                }
                    .addOnFailureListener {
                            e-> Toast.makeText(this@ProfileActivity,e.message,Toast.LENGTH_SHORT).show()
                    }
            }
    }


    private fun saveData() {

        val name = editText.text.toString().trim()
        var income = editTextIncome.text.toString().trim()

        if (name.isEmpty()) {
            editText.error = "Please enter a data"
        }

        val ref = FirebaseDatabase.getInstance().getReference("data")
        val dataId = ref.push().key
        val user = FirebaseAuth.getInstance().currentUser
        val useruid = user?.uid
        val data = Data(name, income = income)

        if (dataId != null) {

            if (useruid != null) {
                ref.child(useruid).setValue(data).addOnCompleteListener{
                    Toast.makeText(applicationContext, "Data Saved Successfully", Toast.LENGTH_SHORT)
                        .show()


                }
            }
        }


    }

}
