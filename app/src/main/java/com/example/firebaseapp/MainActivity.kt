package com.example.firebaseapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {

    lateinit var providers: List<AuthUI.IdpConfig>
    val MY_REQUEST_CODE: Int = 1994

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        providers = listOf<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()

        )
        showSignInOptions()

        Sign_out.setOnClickListener{
            AuthUI.getInstance().signOut(this@MainActivity).addOnCompleteListener{
                Sign_out.isEnabled= false
                showSignInOptions()
            }
                .addOnFailureListener {
                        e-> Toast.makeText(this@MainActivity,e.message,Toast.LENGTH_SHORT).show()
                }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_REQUEST_CODE)
        {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(this, ""+user!!.email,Toast.LENGTH_SHORT).show()
                Sign_out.isEnabled = true

            }

            else{
                Toast.makeText(this, ""+response!!.error!!.message,Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showSignInOptions(){
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).build(),MY_REQUEST_CODE)
    }


}
/*class MainActivity : AppCompatActivity() {


    lateinit var providers: List<AuthUI.IdpConfig>
    val MY_REQUEST_CODE: Int = 1994

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        providers = listOf<AuthUI.IdpConfig>(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build()

        )
        showSignInOptions()

        Sign_out.setOnClickListener{
            AuthUI.getInstance().signOut(this@MainActivity).addOnCompleteListener{
                Sign_out.isEnabled= false
                showSignInOptions()
            }
                .addOnFailureListener {
                        e-> Toast.makeText(this@MainActivity,e.message,Toast.LENGTH_SHORT).show()
                }
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MY_REQUEST_CODE)
        {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                val user = FirebaseAuth.getInstance().currentUser
                Toast.makeText(this, ""+user!!.email,Toast.LENGTH_SHORT).show()
                Sign_out.isEnabled = true
                setContentView(R.layout.activity_profile)
            }

            else{
                Toast.makeText(this, ""+response!!.error!!.message,Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showSignInOptions(){
        startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().setAvailableProviders(providers).setTheme(R.style.AppTheme).build(),MY_REQUEST_CODE)
    }






    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



    }*/





}*/
