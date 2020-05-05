package com.example.firebaseapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class ProfileActivity : AppCompatActivity() {
    lateinit var editText: EditText
    lateinit var buttonSave: Button
    lateinit var button: Button
    lateinit var providers: List<AuthUI.IdpConfig>
    val MY_REQUEST_CODE: Int = 1994

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Sign_out.isEnabled = true
        setContentView(R.layout.activity_profile)
        providers = listOf<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()

        )
        editText = findViewById(R.id.data_input)
        buttonSave = findViewById(R.id.send_data)
        button = findViewById(R.id.Sign_out)

        buttonSave.setOnClickListener {
            saveData()
        }

        button.setOnClickListener{
            logout()
        }

        Sign_out.setOnClickListener{
            AuthUI.getInstance().signOut(this@ProfileActivity).addOnCompleteListener{
                Sign_out.isEnabled= false
                showSignInOptions()
            }
                .addOnFailureListener {
                        e-> Toast.makeText(this@ProfileActivity,e.message,Toast.LENGTH_SHORT).show()
                }
        }


    }

    private fun logout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                // ...
                AuthUI.getInstance().signOut(this@ProfileActivity).addOnCompleteListener{
                    Sign_out.isEnabled= false
                    showSignInOptions()
                }
                    .addOnFailureListener {
                            e-> Toast.makeText(this@ProfileActivity,e.message,Toast.LENGTH_SHORT).show()
                    }
            }
    }


    private fun saveData() {
        val name = editText.text.toString().trim()


        if (name.isEmpty()) {
            editText.error = "Please enter a data"
        }

        val ref = FirebaseDatabase.getInstance().getReference("data")
        val dataid = ref.push().key

        val data = Data(dataid, name)

        if (dataid != null) {
            ref.child(dataid).setValue(data).addOnCanceledListener {
                Toast.makeText(applicationContext, "Data Saved Successfully", Toast.LENGTH_LONG)
                    .show()

            }
        }


    }
    private fun showSignInOptions(){
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(),MY_REQUEST_CODE)
    }
}
