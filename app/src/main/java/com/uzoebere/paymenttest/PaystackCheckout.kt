package com.uzoebere.paymenttest

/*
*   Author: Umemba Uzoma
*   Date: 22nd AUG 2019
*   Version: 1.0
* */

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import co.paystack.android.PaystackSdk
import co.paystack.android.model.Card
import kotlinx.android.synthetic.main.activity_paystack_checkout.*
import co.paystack.android.Transaction
import co.paystack.android.Paystack
import co.paystack.android.model.Charge
import android.widget.Toast

class PaystackCheckout : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paystack_checkout)

        // Initialize Paystack
        PaystackSdk.initialize(applicationContext)

    //    val txtCardName = findViewById<EditText>(R.id.txtCardName)
        val txtCardNumber = findViewById<EditText>(R.id.txtCardNumber)
        val txtExpiryDate = findViewById<EditText>(R.id.txtExpiryDate)
        val txtExpiryYear = findViewById<EditText>(R.id.txtExpiryYear)
        val txtCvv = findViewById<EditText>(R.id.txtCvv)

        // validate the inputs


        btnPaystackPay.setOnClickListener {

            val cardNumber = txtCardNumber.text.toString()
            val expiryMonth = Integer.parseInt(txtExpiryDate.text.toString())
            val expiryYear = Integer.parseInt(txtExpiryYear.text.toString())
            val cvv = txtCvv.text.toString()

            val card = Card(cardNumber, expiryMonth, expiryYear, cvv)
            if (card.isValid) {
                // charge card
                performCharge(card)
            } else {
                return@setOnClickListener
            }
        }

    }

    private fun performCharge(card: Card) {
        //create a Charge object
        val charge = Charge()
        charge.card = card //sets the card to charge
        charge.email = "zomybee@gmail.com"
        charge.amount = 100



        PaystackSdk.chargeCard(this, charge, object : Paystack.TransactionCallback {

            override fun onSuccess(transaction: Transaction) {
                // This is called only after transaction is deemed successful.
                // Retrieve the transaction, and send its reference to your server
                // for verification.

                val paymentReference = transaction.reference
                Toast.makeText(this@PaystackCheckout, "Transaction Successful! payment reference: $paymentReference", Toast.LENGTH_LONG).show()

            }

            override fun beforeValidate(transaction: Transaction) {
                // This is called only before requesting OTP.
                // Save reference so you may send to server. If
                // error occurs with OTP, you should still verify on server.
            }

            override fun onError(error: Throwable, transaction: Transaction) {
                //handle error here
                Toast.makeText(this@PaystackCheckout, "Error in processing transaction", Toast.LENGTH_SHORT).show()
            }

        })
    }
}
