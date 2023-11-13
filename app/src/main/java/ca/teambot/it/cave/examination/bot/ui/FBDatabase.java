package ca.teambot.it.cave.examination.bot.ui;
//Adrian Portal Calcines n01489363 0CA
//Alfred Dowuona <student id> 0CA
//Ali Mohebi <student id> <section code>
//Hassan Noorani n01485518 0CB

import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.Context;
import ca.teambot.it.cave.examination.bot.LoginActivity;
import ca.teambot.it.cave.examination.bot.MainActivity;
import ca.teambot.it.cave.examination.bot.R;


public class FBDatabase
{
    private DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    public FBDatabase() {
        // Initialize the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
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

    public void createUser(String email, String password, Context context)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                    {
                        Toast.makeText(context, context.getString(R.string.account_created), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(context, R.string.account_creation_failed, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void SignInUser(String email, String password, Context context, boolean rememberMe)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(context, context.getString(R.string.login_successful), Toast.LENGTH_SHORT).show();
                        if (rememberMe)
                        {
                            saveLoginState(context, true);
                        }
                        Intent intent = new Intent(context, MainActivity.class);
                        context.startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(context, context.getString(R.string.login_failed), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void saveLoginState(Context context, boolean isLoggedIn)
    {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.loginprefs), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.isloggedin), isLoggedIn);
        editor.apply();
    }
}
