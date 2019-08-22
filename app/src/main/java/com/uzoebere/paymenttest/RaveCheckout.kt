package com.uzoebere.paymenttest

/*
*   Author: Umemba Uzoma
*   Date: 21st AUG 2019
*   Version: 1.0
* */

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import kotlinx.android.synthetic.main.activity_rave_checkout.*
import com.flutterwave.raveandroid.RavePayManager
import android.widget.Toast
import com.flutterwave.raveandroid.RavePayActivity
import com.flutterwave.raveandroid.RaveConstants
import android.content.Intent


class RaveCheckout : AppCompatActivity() {

    private val publicKey = "Enter your public key from your rave account"
    private val encryptionKey = "Enter your encryption key from your rave account"
    private val narration = "Payment Testing"
    private val country = "NG"
    private val currency = "NGN"
    private var txRef = "Testing Reference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rave_checkout)

        // Initialize the text fields
        val txtFName = findViewById<EditText>(R.id.txtFName)
        val txtLName = findViewById<EditText>(R.id.txtLName)
        val txtAmount = findViewById<EditText>(R.id.txtAmount)
        val txtEmail = findViewById<EditText>(R.id.txtEmail)

        val email = txtEmail.text.toString()
        val fName = txtFName.text.toString()
        val lName = txtLName.text.toString()

        btnRavePay.setOnClickListener {
            val amount = Integer.parseInt(txtAmount.text.toString())
            makePayment(amount, email, fName, lName)
        }
    }

    private fun makePayment(amount: Int, email: String, fName: String, lName: String) {

        // Create instance of RavePayManager

        RavePayManager(this)
            .setAmount(amount.toDouble())
            .setCountry(country)
            .setCurrency(currency)
            .setEmail(email)
            .setfName(fName)
            .setlName(lName)
            .setNarration(narration)
            .setPublicKey(publicKey)
            .setEncryptionKey(encryptionKey)
            .setTxRef(txRef)
            .acceptAccountPayments(true)
            .acceptCardPayments(true)
            .acceptMpesaPayments(false)
            .acceptGHMobileMoneyPayments(false)
            .onStagingEnv(true)                 // Change to false in a live environment
            .allowSaveCardFeature(true)
            .withTheme(R.style.DefaultTheme)
            .initialize()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == RaveConstants.RAVE_REQUEST_CODE && data != null) {

            val message = data.getStringExtra("response")
            if (resultCode == RavePayActivity.RESULT_SUCCESS) {
                Toast.makeText(this, "Payment Successful $message", Toast.LENGTH_SHORT).show()
            }
            else if (resultCode == RavePayActivity.RESULT_ERROR) {
                Toast.makeText(this, "ERROR $message", Toast.LENGTH_SHORT).show()
            }
            else if (resultCode == RavePayActivity.RESULT_CANCELLED) {
                Toast.makeText(this, "CANCELLED $message", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
