package com.example.mrides;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class PreferencesTwo extends AppCompatActivity {
    ImageButton imageButton1, imageButton2, imageButton3, imageButton4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences_two);
        imageButton1 = (ImageButton) findViewById(R.id.img_dog);
        imageButton2 = (ImageButton) findViewById(R.id.img_smoke);
        imageButton3 = (ImageButton) findViewById(R.id.img_girl);
        imageButton4 = (ImageButton) findViewById(R.id.img_boy);

        imageButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton1.setImageResource(R.mipmap.dog_block);
            }
        });
        imageButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton2.setImageResource(R.mipmap.smoke_block);
            }
        });
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton3.setImageResource(R.mipmap.girl_block);
            }
        });
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageButton4.setImageResource(R.mipmap.boy_block);
            }
        });
    }


}
