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
    String nodePath = "/Controls/Direction";

    public void moveForward()
    {

        Map<String, Object> updates = new HashMap<>();
        updates.put("Direction", currValue);

        databaseReference.child(nodePath).updateChildren(updates)
                .addOnSuccessListener(aVoid -> {
                    // Update successful
                    // Handle success here
                });

    }
    public void moveLeft()
    {

    }
    public void moveBack()
    {

    }
    public void moveRight()
    {

    }
}
