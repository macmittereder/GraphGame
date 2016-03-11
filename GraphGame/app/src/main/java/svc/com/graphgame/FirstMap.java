package svc.com.graphgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/*
* Created 3/10/2016 by Mac Mittereder
* This is the first map class
*/

public class FirstMap extends AppCompatActivity {

    //Create global variables for objects on screen and import classes
    TextView title; //Not necessary since we're not changing it

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_map_layout); //Sets the view to the set layout

        //Initialize objects on screen by casting findViewById(R.id.(Whatever id is))
        title = (TextView) findViewById(R.id.tvFirstMapTitle);

        //TODO: Add content
    }
}
