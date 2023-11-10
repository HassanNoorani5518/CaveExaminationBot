package ca.teambot.it.cave.examination.bot;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    private boolean checkLoginState()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("LoginPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (!checkLoginState())
        {
            new Handler().postDelayed(() -> {

                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }, 3000);
        }
        else
        {
            new Handler().postDelayed(() -> {

                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }, 3000);
        }


    }
}