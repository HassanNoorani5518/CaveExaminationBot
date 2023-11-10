package ca.teambot.it.cave.examination.bot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import ca.teambot.it.cave.examination.bot.ui.FBDatabase;

public class LoginActivity extends AppCompatActivity
{
    EditText email, password;
    CheckBox rememberMe;
    Button submit, createAcc, forgotPass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.editTextTextEmailAddress);
        password = findViewById(R.id.editTextTextPassword);
        rememberMe = findViewById(R.id.checkBox);
        submit = findViewById(R.id.button2);
        createAcc = findViewById(R.id.create_account);
        forgotPass = findViewById(R.id.forgot_password);

        createAcc.setOnClickListener(view -> {
            Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(i);
            finish();
        });
        submit.setOnClickListener(view -> login());
    }

    public void login()
    {
        boolean status = true;

        String pemail = email.getText().toString();
        String ppassword = password.getText().toString();
        boolean prememberMe = rememberMe.isChecked();

        if (pemail.isEmpty())
        {
            email.setError("This field cannot be empty!");
            status = false;
        }
        else if (!pemail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
        {
            email.setError("Email must follow correct format!");
            status = false;
        }

        if (ppassword.isEmpty())
        {
            password.setError("This field cannot be empty!");
            status = false;
        }

        if (status)
        {
            FBDatabase fbDatabase = new FBDatabase();
            fbDatabase.SignInUser(pemail, ppassword, LoginActivity.this, prememberMe);

            finish();
        }
    }
}