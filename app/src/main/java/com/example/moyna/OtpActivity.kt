package com.example.moyna

import android.app.ProgressDialog
import android.content.Intent
import android.credentials.Credential
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.compose.material.ProgressIndicatorDefaults
import com.example.moyna.databinding.ActivityOtpBinding
import com.example.moyna.databinding.ActivityVerificationBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class OtpActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityOtpBinding.inflate(layoutInflater)
    }
    var verificationId:String ?= null
    var auth:FirebaseAuth ?= null
    var dialog:ProgressDialog ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dialog = ProgressDialog(this@OtpActivity)
        dialog!!.setMessage("Sending OTP ....")
        dialog!!.setCancelable(false)
        dialog!!.show()

        auth = FirebaseAuth.getInstance()

        val phoneNumber = intent.getStringExtra("phoneNumber")

        binding.phoneET.text = "Verify $phoneNumber"

        val option = PhoneAuthOptions.newBuilder(auth!!)
            .setPhoneNumber(phoneNumber!!)
            .setTimeout(60L,TimeUnit.SECONDS)
            .setActivity(this@OtpActivity)
            .setCallbacks(object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                    TODO("Not yet implemented")
                }

                override fun onVerificationFailed(p0: FirebaseException) {
                    TODO("Not yet implemented")
                }

                override fun onCodeSent(
                    verifId: String,
                    forceResendingToken: PhoneAuthProvider.ForceResendingToken
                ) {
                    super.onCodeSent(verifId, forceResendingToken)
                    dialog!!.dismiss()
                    verificationId = verifId
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
                    binding.otpView.requestFocus()
                }

            }).build()

        PhoneAuthProvider.verifyPhoneNumber(option)
        binding.otpView.setOtpCompletionListener {
            val credential = PhoneAuthProvider.getCredential(verificationId!!,it)
            auth!!.signInWithCredential(credential)
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        startActivity(Intent(this@OtpActivity, SetupProfileActivity::class.java))
                        finish()
                    }
                    else{
                        Toast.makeText(this@OtpActivity,"Failed!",Toast.LENGTH_SHORT).show()
                    }
                }

        }
    }
}