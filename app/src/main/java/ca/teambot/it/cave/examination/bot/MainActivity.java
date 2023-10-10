package ca.teambot.it.cave.examination.bot;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ca.teambot.it.cave.examination.bot.databinding.ActivityMainBinding;
import ca.teambot.it.cave.examination.bot.ui.dashboard.DashboardFragment;
import ca.teambot.it.cave.examination.bot.ui.home.HomeFragment;
import ca.teambot.it.cave.examination.bot.ui.notifications.NotificationsFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;
    private boolean backArrowPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navigation_home);
    }
    HomeFragment homeFragment = new HomeFragment();
    DashboardFragment dashboardFragment = new DashboardFragment();
    NotificationsFragment notificationsFragment = new NotificationsFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.navigation_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, homeFragment).commit();
                return true;
            case R.id.navigation_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, dashboardFragment).commit();
            case R.id.navigation_notifications:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, notificationsFragment).commit();
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        // Display a confirmation dialog
        new AlertDialog.Builder(this)
                .setTitle("Exit App")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // If the user confirms, finish the activity
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // If the user cancels, dismiss the dialog
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        // Set the flag to indicate back arrow press
        backArrowPressed = true;
        onBackPressed();
        return true;
    }

    private void showExitAlertDialog() {
        // Create an AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setTitle("Exit Confirmation");
        builder.setMessage("Do you want to exit the app?");

        // Add buttons to the AlertDialog
        builder.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Close the app
                finish();
            }
        });


        builder.setNegativeButton("Stay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // User chose to stay, reset the flag
                backArrowPressed = false;
            }
        });

        // Show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
