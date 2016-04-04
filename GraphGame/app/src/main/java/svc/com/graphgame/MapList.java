package svc.com.graphgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import svc.com.graphgame.Maps.FirstMap;
import svc.com.graphgame.Maps.SecondMap;
import svc.com.graphgame.Maps.ThirdMap;

/*
* Created 3/10/2016 by Mac Mittereder
* This is the map list class
*/

public class MapList extends AppCompatActivity implements AdapterView.OnItemClickListener{

    /*
    * TODO: Create better description of class
    */

    //Create global variables and import classes
    ListView mapList;
    TextView title; //Not necessary since we're not changing it

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_list_layout); //Sets the view to the set layout

        //Initialize objects on screen by casting findViewById(R.id.(Whatever id is))
        mapList = (ListView) findViewById(R.id.lvMapList);
        title = (TextView) findViewById(R.id.tvPickMapTitle);

        //Create ArrayList for list of map names
        ArrayList<String> mapNames = new ArrayList<>();

        //Add names to ArrayList
        mapNames.add("First");
        mapNames.add("Second");
        mapNames.add("Third");

        /*
        * Setting up the look of the list with an array adapter
        * Passing in the context of the class using 'this'
        * Setting the look of the list as the simple_list_item1
        * Set the list to contain the values in mapNames
        */
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, mapNames);
        mapList.setAdapter(arrayAdapter);

        /*
        * Tell android that the items in the list are clickable
        * Use 'this' in the parameters to tell android that the item handler is in the class
        * Also must implement OnItemClickListener and methods
        */
        mapList.setOnItemClickListener(this);
    }

    //Class that assigns items in the list functions
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*
        * This is where item functions will be written using switch and case
        * Remember the list starts with 0 like an array
        */
        switch (position) {
            case 0:
                /*
                * Switching views using startActivity with a new Intent
                * Must pass the context of this view using this and activity you're going to using .class
                */
                startActivity(new Intent(this, FirstMap.class));
                break;
            case 1:
                startActivity(new Intent(this, SecondMap.class));
                break;
            case 2:
                startActivity(new Intent(this, ThirdMap.class));
                break;
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, StartScreen.class));
    }
}
