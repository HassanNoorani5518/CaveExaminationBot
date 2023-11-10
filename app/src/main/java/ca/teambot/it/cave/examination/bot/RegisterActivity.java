package ca.teambot.it.cave.examination.bot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import ca.teambot.it.cave.examination.bot.ui.FBDatabase;

public class RegisterActivity extends AppCompatActivity
{
    EditText password, confirmPassword, email, name, phone;
    Button register, backtologin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        password = findViewById(R.id.editTextTextPassword);
        confirmPassword = findViewById(R.id.editTextTextConfirmPassword);
        email = findViewById(R.id.editTextTextEmailAddress);
        name = findViewById(R.id.editTextTextName);
        phone = findViewById(R.id.editTextTextPhoneNumber);
        register = findViewById(R.id.button2);
        progressBar = findViewById(R.id.progressBar);
        backtologin = findViewById(R.id.Backbutton);

        backtologin.setOnClickListener(view -> {
            Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(i);
            finish();
        });

        register.setOnClickListener(view -> {
            progressBar.setVisibility(View.VISIBLE);
            String pemail = email.getText().toString();
            String ppassword = password.getText().toString();
            String pname = name.getText().toString();
            String pphone = phone.getText().toString();
            String pconfirmPass = confirmPassword.getText().toString();

            boolean status = true;

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

            if (pphone.isEmpty())
            {
                phone.setError("This field cannot be empty!");
                status = false;
            }
            else if ((pphone.length() < 10) || !pphone.matches("^[0-9]{10}$"))
            {
                phone.setError("This field must have exactly 10 numbers and no alphabetic characters");
                status = false;
            }

            if (ppassword.isEmpty())
            {
                password.setError("This field cannot be empty!");
                status = false;
            }
            if (pconfirmPass.isEmpty())
            {
                confirmPassword.setError("This field cannot be empty!");
                status = false;
            }
            if (!pconfirmPass.equals(ppassword))
            {
                confirmPassword.setError("The passwords do not match!");
                status = false;
            }

            if (pname.isEmpty())
            {
                name.setError("This field cannot be empty!");
                status = false;
            }

            if (status)
            {
                FBDatabase fbDatabase = new FBDatabase();

                String uniqueUserId = fbDatabase.generateUniqueKey("User");
                fbDatabase.addItem("Feedback", uniqueUserId, "Name", pname);
                fbDatabase.addItem("Feedback", uniqueUserId, "Phone", pphone);
                fbDatabase.addItem("Feedback", uniqueUserId, "Email", pemail);
                fbDatabase.createUser(pemail, ppassword, RegisterActivity.this);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}