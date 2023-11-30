package ca.teambot.it.cave.examination.bot;
//Adrian Portal Calcines n01489363 0CA
//Alfred Dowuona <student id> 0CA
//Ali Mohebi <student id> <section code>
//Hassan Noorani <student id> 0CB


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Objects;

import ca.teambot.it.cave.examination.bot.ui.FBDatabase;

public class LoginActivity extends AppCompatActivity
{
    GoogleSignInClient oneTapClient;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    EditText email, password;
    CheckBox rememberMe;
    Button submit, createAcc, forgotPass;
    ImageButton googleButton;
    int RC_SIGN_IN = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build();
        oneTapClient = GoogleSignIn.getClient(this, gso);

        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        rememberMe = findViewById(R.id.checkBox);
        submit = findViewById(R.id.button2);
        createAcc = findViewById(R.id.create_account);
        forgotPass = findViewById(R.id.forgot_password);
        googleButton = findViewById(R.id.imageButton);



        googleButton.setOnClickListener(view -> googleSignIn());

        createAcc.setOnClickListener(view -> {
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);
            finish();
        });
        submit.setOnClickListener(view -> login());
    }

    public void googleSignIn()
    {
        Intent intent = oneTapClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==RC_SIGN_IN)
        {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuth(account.getIdToken());
            }
            catch (Exception e)
            {
                Toast.makeText(this, getString(R.string.error) + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void firebaseAuth(String idToken)
    {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(task -> {
            if (task.isSuccessful())
            {
                FirebaseUser user = mAuth.getCurrentUser();

                HashMap<String, Object> map = new HashMap<>();
                assert user != null;
                map.put(getString(R.string.id), user.getUid());
                map.put(getString(R.string.name), user.getDisplayName());
                map.put(getString(R.string.profile), Objects.requireNonNull(user.getPhotoUrl()).toString());

                database.getReference().child(getString(R.string.users)).child(user.getUid()).setValue(map);

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(LoginActivity.this, getString(R.string.could_not_sign_you_in), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void login()
    {
        boolean status = true;

        String pemail = email.getText().toString();
        String ppassword = password.getText().toString();
        boolean prememberMe = rememberMe.isChecked();

        if (pemail.isEmpty())
        {
            email.setError(getString(R.string.this_field_cannot_be_empty));
            status = false;
        }
        else if (!pemail.matches(getString(R.string.a_za_z0_9_a_za_z0_9_a_za_z_2)))
        {
            email.setError(getString(R.string.email_must_follow_correct_format));
            status = false;
        }

        if (ppassword.isEmpty())
        {
            password.setError(getString(R.string.this_field_cannot_be_empty));
            status = false;
        }
        else if (!ppassword.matches(getString(R.string.a_z_a_z_d_a_za_z_d_6)))
        {
            password.setError(getString(R.string.password_must_follow_correct_format));
            status = false;
        }

        if (status)
        {
            FBDatabase fbDatabase = new FBDatabase();
            fbDatabase.SignInUser(pemail, ppassword, LoginActivity.this, prememberMe);
        }
    }
}