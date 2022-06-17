package com.example.kerklyv5.pago;

import static javax.mail.internet.HeaderTokenizer.Token.EOF;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.kerklyv5.R;
import com.stripe.android.PaymentConfiguration;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentMethod;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.payments.paymentlauncher.PaymentLauncher;
import com.stripe.android.payments.paymentlauncher.PaymentResult;

public class oxxo extends AppCompatActivity {
    private String paymentIntentClientSecret;
    private PaymentLauncher paymentLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oxxo);

    }


}