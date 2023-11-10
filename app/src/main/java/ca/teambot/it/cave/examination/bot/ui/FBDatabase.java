package ca.teambot.it.cave.examination.bot.ui;

import android.content.Intent;
import android.widget.Toast;
import androidx.annotation.NonNull;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.Context;

import ca.teambot.it.cave.examination.bot.LoginActivity;
import ca.teambot.it.cave.examination.bot.MainActivity;
import ca.teambot.it.cave.examination.bot.RegisterActivity;

public class FBDatabase
{
    private DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    public FBDatabase() {
        // Initialize the database reference
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public void checkForLogin(Context context) {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
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
                .addOnCompleteListener(new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(context, "Account Created!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, LoginActivity.class);
                            context.startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(context, "Account Creation failed!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void SignInUser(String email, String password, Context context)
    {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(context, MainActivity.class);
                            context.startActivity(intent);
                        }
                        else
                        {
                            Toast.makeText(context, "Login Failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
