package svc.com.graphgame.GameFiles;

/*
* Created 3/20/2016 by Mac Mittereder
* This is the game rules class
*/

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import svc.com.graphgame.MapList;
import svc.com.graphgame.Maps.FirstMap;
import svc.com.graphgame.Maps.SecondMap;
import svc.com.graphgame.Maps.ThirdMap;

public class GameRules extends View{

    /*
    * TODO: Add rectangle to either restart game or select another map (probably just select another map)
    * TODO: Add functionality to rectangle to go to the MapList.class via touch_down
    * TODO: Can draw text to show how many turns have happened, etc.
    * TODO: Create better description of class
    */

    //Create global variables and import classes
    PebbleNode lastNodeSelected = null, lastNodeMovedFrom, lastNodeMovedTo, goalNode;
    ArrayList<ConnectNodes> listOfConnectedNodes;
    ArrayList<PebbleNode> listOfPebbleNodes;
    boolean attackersTurn = true;
    int screenX, screenY, mapNum;
    Paint paint;
    Rect resetRect, resetMapRect;

    //Default initialization
    public GameRules(Context context, int mapNum) {
        super(context);
        this.mapNum = mapNum;
        listOfConnectedNodes = new ArrayList<>();
        listOfPebbleNodes = new ArrayList<>();
        paint = new Paint();
    }

    //This method gets executed after it's called to draw the item to your 'canvas' or layout
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //Displays to the screen either attacker or defender's turn
        paint.setTextSize(75f);
        paint.setColor(Color.rgb(171, 32, 159));
        String turnText;
        if (checkLose())
            turnText = "";
        else
            turnText = attackersTurn ? "Attacker's Turn" : "Defender's Turn";
        canvas.drawText(turnText, (screenX / 2) - 250, (screenY * 7 /8), paint);

        //Checks if the goal node has a pebble on it
        if (goalNode.getPebbles() > 0){
            paint.setColor(Color.rgb(227, 227, 227));
            canvas.drawRect(0, 0, screenX, screenY, paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(120f);
            canvas.drawText("Attacker Wins!", (screenX / 4) , (screenY / 2), paint);

            paint.setColor(Color.rgb(171, 32, 159));
            paint.setTextSize(60f);
            resetRect = new Rect(screenX / 4, (screenY/2)+200, screenX-360 , (screenY/2)+400);
            canvas.drawRect(resetRect, paint);
            resetMapRect = new Rect(screenX/4, (screenY/2) + 500, screenX-360, (screenY/2)+700);
            canvas.drawRect(resetMapRect, paint);
            paint.setColor(Color.BLACK);
            canvas.drawText("Replay", resetRect.centerX() - 100, resetRect.centerY(), paint);
            canvas.drawText("Choose Map", resetMapRect.centerX() - 175, resetMapRect.centerY(), paint);
        }
        //Checks if there are more than 1 pebble on a node at any time
        if(checkLose()) {
            paint.setColor(Color.rgb(227, 227, 227));
            canvas.drawRect(0, 0, screenX, screenY, paint);
            paint.setColor(Color.BLACK);
            paint.setTextSize(120f);
            canvas.drawText("Defender Wins!", (screenX / 4), (screenY / 2), paint);

            paint.setColor(Color.rgb(171, 32, 159));
            paint.setTextSize(60f);
            resetRect = new Rect(screenX / 4, (screenY/2)+200, screenX-360 , (screenY/2)+400);
            canvas.drawRect(resetRect, paint);
            resetMapRect = new Rect(screenX/4, (screenY/2) + 500, screenX-360, (screenY/2)+700);
            canvas.drawRect(resetMapRect, paint);
            paint.setColor(Color.BLACK);
            canvas.drawText("Replay", resetRect.centerX() - 100, resetRect.centerY(), paint);
            canvas.drawText("Choose Map", resetMapRect.centerX() - 175, resetMapRect.centerY(), paint);
        }
    }

    //Adder method
    public void addConnectedNodes(ConnectNodes connectedNodes) {
        listOfConnectedNodes.add(connectedNodes);
    }

    //Adder method
    public void addPebbleNodes(PebbleNode pebbleNode) {
        listOfPebbleNodes.add(pebbleNode);
    }

    //Checks if the pebbles are allowed to move via list of connected nodes
    //If node1 from connected nodes is either the start or destination and node 2 from
    //connected nodes is either the start or destination then the move is valid so return true
    //otherwise return false
    public boolean checkPebbleMove(PebbleNode start, PebbleNode destination) {
        if (start.getPebbles() < 2 || start == goalNode)
            return false;
        for (ConnectNodes cntNode: listOfConnectedNodes)
            if ( (cntNode.node1 == start || cntNode.node1 == destination) &&
                    (cntNode.node2 == start || cntNode.node2 == destination))
                return true;
        return false;
    }

    //Checks if the defender tries to reverse the attacker's last move. If the start of the
    //defender's move is the destination the the attacker's last move, and the destination is the
    //start of the attacker's last move, this is illegal. Returns true if the move is illegal.
    public boolean checkIllegalDefenderMove(PebbleNode start, PebbleNode destination) {
        if (start == lastNodeMovedTo && destination == lastNodeMovedFrom && !(attackersTurn)) {
            Toast.makeText(getContext(), "Attackers Move Can't be Undone", Toast.LENGTH_SHORT).show();
            return true;
        }
        invalidate();
        attackersTurn = !attackersTurn;
        return false;
    }

    //For each PebbleNode in the list of nodes check if any of them have 2 or more pebbles return false (attacker hasn't lost)
    //if none of them have 2 or more pebbles then returns true (attacker has lost)
    public boolean checkLose() {
        for (PebbleNode node: listOfPebbleNodes)
            if (node.getPebbles() >= 2)
                return false;
        return true;
    }

    //This is the touch handling class
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int touchX = (int) event.getX();
        int touchY = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (checkLose() || goalNode.getPebbles() > 0) {
                    if (resetRect.contains(touchX, touchY)) {
                        switch (mapNum) {
                            case 1:
                                getContext().startActivity(new Intent(getContext(), FirstMap.class));
                                break;
                            case 2:
                                getContext().startActivity(new Intent(getContext(), SecondMap.class));
                                break;
                            case 3:
                                getContext().startActivity(new Intent(getContext(), ThirdMap.class));
                                break;
                        }
                    }
                    if (resetMapRect.contains(touchX, touchY))
                        getContext().startActivity(new Intent(getContext(), MapList.class));
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    //Setter method
    public void setScreenSize(int x, int y) {
        screenX = x;
        screenY = y;
    }

    //Getter method
    public ArrayList<ConnectNodes> getListOfConnectedNodes() {
        return listOfConnectedNodes;
    }

    //Getter method
    public ArrayList<PebbleNode> getListOfPebbleNodes() {
        return listOfPebbleNodes;
    }

    //Setter method
    public void setGoalNode(PebbleNode goalNode) {
        this.goalNode = goalNode;
    }

    //Getter method
    public PebbleNode getLastNode() {
        return lastNodeSelected;
    }

    //Setter methods
    public void setLastNode(PebbleNode nodeSelected) {
        lastNodeSelected = nodeSelected;
    }

    public void setLastMoveFrom(PebbleNode node) { lastNodeMovedFrom = node; }

    public void setLastMoveTo(PebbleNode node) { lastNodeMovedTo = node; }
}