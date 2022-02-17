package com.example.album;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {


    ViewFlipper view;


    public void navigeurImage(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        view.addView(imageView);
        view.setFlipInterval(5000);
        view.setAutoStart(true);
        view.setInAnimation(this, android.R.anim.slide_in_left);
        view.setOutAnimation(this, android.R.anim.slide_out_right);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (ViewFlipper) findViewById(R.id.viewFlipper);

        int images[]  = {R.drawable.photo1,
                R.drawable.photo2,
                R.drawable.photo3,
                R.drawable.photo4,
                R.drawable.photo5,
                R.drawable.photo6,
                R.drawable.photo7};

        for(int image: images){
            navigeurImage(image);
        }
    }
}
