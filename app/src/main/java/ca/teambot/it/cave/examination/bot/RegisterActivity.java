package ca.teambot.it.cave.examination.bot;
//Adrian Portal Calcines n01489363 0CA
//Alfred Dowuona <student id> 0CA
//Ali Mohebi <student id> <section code>
//Hassan Noorani <student id> 0CB

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

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
                email.setError(getString(R.string.this_field_cannot_be_empty));
                status = false;
            }
            else if (!pemail.matches(getString(R.string.a_za_z0_9_a_za_z0_9_a_za_z_2)))
            {
                email.setError(getString(R.string.email_must_follow_correct_format));
                status = false;
            }

            if (pphone.isEmpty())
            {
                phone.setError(getString(R.string.this_field_cannot_be_empty));
                status = false;
            }
            else if ((pphone.length() < 10) || !pphone.matches(getString(R.string._0_9_10)))
            {
                phone.setError(getString(R.string.this_field_must_have_exactly_10_numbers_and_no_alphabetic_characters));
                status = false;
            }

            if (ppassword.isEmpty())
            {
                password.setError(getString(R.string.this_field_cannot_be_empty));
                status = false;
            }
            if (pconfirmPass.isEmpty())
            {
                confirmPassword.setError(getString(R.string.this_field_cannot_be_empty));
                status = false;
            }
            if (!pconfirmPass.equals(ppassword))
            {
                confirmPassword.setError(getString(R.string.the_passwords_do_not_match));
                status = false;
            }

            if (pname.isEmpty())
            {
                name.setError(getString(R.string.this_field_cannot_be_empty));
                status = false;
            }

            if (status)
            {
                FBDatabase fbDatabase = new FBDatabase();

                String uniqueUserId = fbDatabase.generateUniqueKey(getString(R.string.user));
                fbDatabase.addItem(getString(R.string.user), uniqueUserId, getString(R.string.name), pname);
                fbDatabase.addItem(getString(R.string.user), uniqueUserId, getString(R.string.phone), pphone);
                fbDatabase.addItem(getString(R.string.user), uniqueUserId, getString(R.string.email), pemail);
                fbDatabase.createUser(pemail, ppassword, RegisterActivity.this);
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}