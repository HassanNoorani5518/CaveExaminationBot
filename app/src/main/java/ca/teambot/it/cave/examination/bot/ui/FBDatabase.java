package ca.teambot.it.cave.examination.bot.ui;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FBDatabase
{
    private DatabaseReference databaseReference;

    public FBDatabase() {
        // Initialize the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public void addItem(String parentNode, String uniqueKey, String field, String value) {
        databaseReference.child(parentNode).child(uniqueKey).child(field).setValue(value);
    }

    public void deleteItem(String node, String key) {
        // Delete an item from the specified node
        databaseReference.child(node).child(key).removeValue();
    }

    public String generateUniqueKey(String parentNode) {
        return databaseReference.child(parentNode).push().getKey();
    }

    public void readData(String node, ValueEventListener listener) {
        // Read data from the specified node
        databaseReference.child(node).addListenerForSingleValueEvent(listener);
    }
}
