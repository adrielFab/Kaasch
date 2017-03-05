/*
* Class PreferencesTwoActivity
*
* 03/04/17
*/
package com.example.mrides.Activity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mrides.BackgroundWork;
import com.example.mrides.R;

public class PreferencesTwoActivity extends AppCompatActivity {

    private ImageView imageDog, imageSmoke, imageBoy, imageGirl;
    private TextView textDog, textSmoke, textBoy, textGirl, textTitle;
    private int [] preferenceChoice = {1, 1, 1, 1};
    private Typeface tf1;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences_two);

        tf1 = Typeface.createFromAsset(getAssets(), "Ubuntu-L.ttf");

        textTitle = (TextView) findViewById(R.id.textTitle);
        textTitle.setTypeface(tf1);

        textDog = (TextView) findViewById(R.id.text_dog);
        imageDog = (ImageView) findViewById(R.id.img_dog);
        textDog.setTypeface(tf1);

        textSmoke = (TextView) findViewById(R.id.text_smoke);
        imageSmoke = (ImageView) findViewById(R.id.img_smoke);
        textSmoke.setTypeface(tf1);

        textGirl = (TextView) findViewById(R.id.text_girl);
        imageGirl = (ImageView) findViewById(R.id.img_girl);
        textGirl.setTypeface(tf1);

        textBoy = (TextView) findViewById(R.id.text_boy);
        imageBoy = (ImageView) findViewById(R.id.img_boy);
        textBoy.setTypeface(tf1);

        imageDog.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(preferenceChoice[0] == 1){

                    startColorAnimation(imageDog, true);
                    startTransitionAnimation(true, imageDog, textDog);
                    preferenceChoice[0] = 0;
                }
                else{

                    startColorAnimation(imageDog, false);
                    startTransitionAnimation(false, imageDog, textDog);
                    preferenceChoice[0] = 1;
                }
            }
        });

        imageSmoke.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(preferenceChoice[1] == 1){

                    startColorAnimation(imageSmoke, true);
                    startTransitionAnimation(true, imageSmoke, textSmoke);
                    preferenceChoice[1] = 0;
                }
                else{

                    startColorAnimation(imageSmoke, false);
                    startTransitionAnimation(false, imageSmoke, textSmoke);
                    preferenceChoice[1] = 1;
                }
            }
        });

        imageGirl.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(preferenceChoice[2] == 1){

                    startColorAnimation(imageGirl, true);
                    startTransitionAnimation(true, imageGirl, textGirl);
                    preferenceChoice[2] = 0;
                }
                else{

                    startColorAnimation(imageGirl, false);
                    startTransitionAnimation(false, imageGirl, textGirl);
                    preferenceChoice[2] = 1;
                }
            }
        });

        imageBoy.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                if(preferenceChoice[3] == 1){

                    startColorAnimation(imageBoy, true);
                    startTransitionAnimation(true, imageBoy, textBoy);
                    preferenceChoice[3] = 0;
                }
                else{

                    startColorAnimation(imageBoy, false);
                    startTransitionAnimation(false, imageBoy, textBoy);
                    preferenceChoice[3] = 1;
                }
            }
        });

        btn =(Button)findViewById(R.id.submitBtn);
        btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                nextButton();
            }
        });

    }

    public void nextButton(){

        boolean[] conv_data={true,true,true,true};
        for(int i: preferenceChoice){

            conv_data[i] = ((preferenceChoice[i] == 1) ? true:false);
        }
        //TO DO:Obtain current user email and add to conv_data as String.

        BackgroundWork backgroundWork = new BackgroundWork(this);
        backgroundWork.execute(conv_data);

    }

    public void startTransitionAnimation(boolean transition, ImageView imageView, TextView textView){

        String text = textView.getText().toString();

        if(transition == true){

            imageView.animate().translationXBy(850f).setDuration(400);
            textView.setAlpha(0f);
            textView.animate().translationXBy(-430f).setDuration(50);
            switch(text){

                case "I do not mind pets" :
                    textView.setText("I do mind \r\npets");
                    break;
                case "I do not mind smoking" :
                    textView.setText("I do mind \r\nsmoking");
                    break;
                case "I do not mind women" :
                    textView.setText("I do mind \r\nwomen");
                    break;
                case "I do not mind men" :
                    textView.setText("I do mind \r\nmen");
                default: break;
            }
            textView.animate().alpha(1f).setDuration(400);

        } else {

            imageView.animate().translationXBy(-850f).setDuration(400);
            textView.setAlpha(0f);
            textView.animate().translationXBy(430f).setDuration(50);
            switch(text){

                case "I do mind \r\npets" :
                    textView.setText("I do not mind pets");
                    break;
                case "I do mind \r\nsmoking" :
                    textView.setText("I do not mind smoking");
                    break;
                case "I do mind \r\nwomen" :
                    textView.setText("I do not mind women");
                    break;
                case "I do mind \r\nmen" :
                    textView.setText("I do not mind men");
                    break;
                default: break;
            }
            textView.animate().alpha(1f).setDuration(400);
        }

    }

    public void startColorAnimation(View v, boolean transition){
        int colorStart = v.getSolidColor();
        int colorEnd = 0;
        if(transition == true){

            colorEnd = 0xFFFF6666;
        } else {

            colorEnd = 0xFF00FF00;
        }

        ValueAnimator colorAnim = ObjectAnimator.ofInt(v, "backgroundColor", colorStart, colorEnd);
        colorAnim.setDuration(400);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();
    }
}
