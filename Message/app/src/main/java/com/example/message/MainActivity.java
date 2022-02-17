package com.example.message;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.regex.Pattern;


public class MainActivity extends AppCompatActivity {


    final int SEND_SMS_PERMISSION_REQUEST_CODE = 1;
    EditText numero, message;
    Button envoyer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        numero =  findViewById(R.id.numero);
        message = (EditText) findViewById(R.id.message);
        envoyer = (Button) findViewById(R.id.envoyer);

    }


    public void envoyer(View v){

        String numeroPhone = numero.getText().toString();
        String mess = message.getText().toString();

        if(numeroPhone == null || numeroPhone.length() ==0 || mess == null || mess.length() ==0 || Pattern.compile("^(\\+2126|06)\\d{8}$").matcher(numeroPhone).find() == false){
            Toast.makeText(this, "Numéro non valide ou message vide ", Toast.LENGTH_SHORT).show();

            return;
        }

        if(checkPermission(Manifest.permission.SEND_SMS)){
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(numeroPhone, null, mess, null, null);
            Toast.makeText(this, "Message envoyé", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Message non envoyé", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

}
