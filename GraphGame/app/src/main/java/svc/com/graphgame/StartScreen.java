package svc.com.graphgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/*
* Created 3/10/2016 by Mac Mittereder
* This is the start screen class
*/

public class StartScreen extends AppCompatActivity implements View.OnClickListener{

    //Create global variables for objects on screen and import classes
    Button start;
    TextView title; //Not necessary since we're not changing it

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen_layout); //Sets the view to the set layout

        //Initialize objects on screen by casting findViewById(R.id.(Whatever id is))
        start = (Button) findViewById(R.id.btnStart);
        title = (TextView) findViewById(R.id.tvTitle);

        /*
        * Tell android that buttons are clickable
        * Use 'this' in the parameters to tell android that the button handler is in the class
        * Also must implement OnClickListener and methods
        */
        start.setOnClickListener(this);
    }

    //Class that assigns functions to buttons
    @Override
    public void onClick(View v) {
        //This is where button functions will be written using switch and case
        switch (v.getId()) {
            case R.id.btnStart:
                /*
                * Switching views using startActivity with a new Intent
                * Must pass the context of this view using this and activity you're going to using .class
                */
                startActivity(new Intent(this, MapList.class));
                break;
        }
    }
}
