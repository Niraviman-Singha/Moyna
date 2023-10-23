package com.example.moyna

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.adapters.RadioGroupBindingAdapter
import com.example.moyna.databinding.ActivityVerificationBinding
import com.google.firebase.auth.FirebaseAuth

class VerificationActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityVerificationBinding.inflate(layoutInflater)
    }
    var auth:FirebaseAuth?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if (auth!!.currentUser!=null){
            startActivity(Intent(this@VerificationActivity,MainActivity::class.java))
            finish()
        }

        binding.phoneET.requestFocus()
        binding.continueBtn.setOnClickListener {
            var intent = Intent(this@VerificationActivity,OtpActivity::class.java)
            intent.putExtra("phoneNumber",binding.phoneET.text.toString())
            startActivity(intent)
        }
    }
}