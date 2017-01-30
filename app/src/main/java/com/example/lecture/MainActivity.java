package com.example.lecture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button mButton;
    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButton = (Button) findViewById(R.id.button);
        mEditText = (EditText) findViewById(R.id.editText);

        mButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String name = mEditText.getText().toString();
                change(name);
            }
        });
    }

    private void change(String name){
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }
}
