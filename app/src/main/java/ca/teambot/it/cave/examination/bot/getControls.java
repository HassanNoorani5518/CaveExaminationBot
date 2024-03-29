package ca.teambot.it.cave.examination.bot;

import java.util.HashMap;
import java.util.Map;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.teambot.it.cave.examination.bot.ui.FBDatabase;

public class getControls {

    private DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    public getControls() {
        // Initialize the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }
    int currValue = 0; // = get from xml or wtv.
    String nodePath = "/Controls";

    Map<String, Object> updates = new HashMap<>();




    public void moveForward()
    {
        updates.clear();
        currValue = 1;
        updates.put("Direction", currValue);

        databaseReference.child(nodePath).updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                });

    }

    public void moveForwardRight(){

        updates.clear();
        currValue = 2;
        updates.put("Direction", currValue);

        databaseReference.child(nodePath).updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                });
    }

    public void moveRight()
    {
        updates.clear();
        currValue = 3;
        updates.put("Direction", currValue);

        databaseReference.child(nodePath).updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                });

    }


    public void moveBackRight()
    {
        updates.clear();
        currValue = 4;
        updates.put("Direction", currValue);

        databaseReference.child(nodePath).updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                });

    }


    public void moveBack()
    {

        updates.clear();
        currValue = 5;
        updates.put("Direction", currValue);

        databaseReference.child(nodePath).updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                });

    }

    public void moveBackLeft()
    {

        updates.clear();
        currValue = 6;
        updates.put("Direction", currValue);

        databaseReference.child(nodePath).updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                });

    }

    public void moveLeft()
    {

        updates.clear();
        currValue = 7;
        updates.put("Direction", currValue);

        databaseReference.child(nodePath).updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                });

    }

    public void moveForwardLeft()
    {

        updates.clear();
        currValue = 8;
        updates.put("Direction", currValue);

        databaseReference.child(nodePath).updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                });

    }


    public void stop()
    {

        updates.clear();
        currValue = 0;
        updates.put("Direction", currValue);

        databaseReference.child(nodePath).updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                });

    }
}
