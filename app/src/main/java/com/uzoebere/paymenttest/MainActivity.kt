package com.uzoebere.paymenttest

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnPaystack.setOnClickListener{

            val intent = Intent(this, PaystackCheckout::class.java)
            startActivity(intent)

        }

        btnRave.setOnClickListener {
            val intent = Intent (this, RaveCheckout::class.java)
            startActivity(intent)
        }
    }
}
