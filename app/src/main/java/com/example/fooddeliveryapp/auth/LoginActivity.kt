package com.example.fooddeliveryapp.auth

import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.fooddeliveryapp.LoadingDialog
import com.example.fooddeliveryapp.MainActivity
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.databinding.ActivityLoginBinding
import com.example.fooddeliveryapp.info.LocationActivity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import org.checkerframework.checker.index.qual.GTENegativeOne
import java.util.concurrent.TimeUnit

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    val auth = FirebaseAuth.getInstance()
    private var verificationId : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        window.statusBarColor = Color.TRANSPARENT
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sendOtp = findViewById<Button>(R.id.send_otp)
        val verifyOtp = findViewById<Button>(R.id.verifyOtp)


        sendOtp.setOnClickListener {
            if(binding.userNumber.text!!.isEmpty()){
                Toast.makeText(this, "Please Enter your number!", Toast.LENGTH_LONG).show()
            }else{
                LoadingDialog.showDialog(this)
                sendOtp(binding.userNumber.text.toString())


            }
        }

        verifyOtp.setOnClickListener {
            if(binding.userOtp.text!!.isEmpty()){
                Toast.makeText(this, "Please Enter your number!", Toast.LENGTH_LONG).show()
            }else{
                verifyOtp(binding.userOtp.text.toString())
            }
        }





    }

    private fun sendOtp(number: String) {



        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signInWithPhoneAuthCredential(credential)
            }


            override fun onVerificationFailed(e: FirebaseException) {
                // Handle the failure here
            }







            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                this@LoginActivity.verificationId = verificationId

                LoadingDialog.hideDialog()
                binding.numberLayout.visibility = View.GONE
                binding.otpLayout.visibility = View.VISIBLE
            }
        }




        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber("+91$number") // corrected the line here
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(callbacks)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    private fun verifyOtp(otp : String){

        val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
        signInWithPhoneAuthCredential(credential)

    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                   // val user = task.result?.user

                    startActivity(Intent(this, LocationActivity::class.java))
                    finish()
                } else {

                    Toast.makeText(this, task.exception!!.message, Toast.LENGTH_LONG).show()

                }
            }
    }

}