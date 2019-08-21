package com.uzoebere.paymenttest

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

    val publicKey = "FLWPUBK-d7ad5c1b41799bf2d88624cf42792048-X"
    val encryptionKey = "bca888ac5b406cbce7d88284"
    val narration = "Payment Testing"
    val country = "NG"
    val currency = "NGN"
    var txRef = "Testing Reference"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rave_checkout)

        // Initialize the text fields
        val txtFName = findViewById<EditText>(R.id.txtFName)
        val txtLName = findViewById<EditText>(R.id.txtLName)
        val txtAmount = findViewById<EditText>(R.id.txtAmount)
        val txtEmail = findViewById<EditText>(R.id.txtEmail)


        btnRavePay.setOnClickListener {
            val amount = Integer.parseInt(txtAmount.text.toString())
            makePayment(amount)
        }
    }

    fun makePayment(amount: Int) {

        // Create instance of RavePayManager

        val email = txtEmail.text.toString()
        val fName = txtFName.text.toString()
        val lName = txtLName.text.toString()

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
            .onStagingEnv(false)
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
