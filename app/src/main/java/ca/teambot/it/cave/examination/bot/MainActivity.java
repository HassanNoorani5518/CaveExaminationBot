package ca.teambot.it.cave.examination.bot;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import ca.teambot.it.cave.examination.bot.ui.AboutFragment;
import ca.teambot.it.cave.examination.bot.ui.FBDatabase;
import ca.teambot.it.cave.examination.bot.ui.FeedbackFragment;
import ca.teambot.it.cave.examination.bot.ui.Location.LocationFragment;
import ca.teambot.it.cave.examination.bot.ui.dashboard.DashboardFragment;
import ca.teambot.it.cave.examination.bot.ui.home.HomeFragment;
import ca.teambot.it.cave.examination.bot.ui.notifications.NotificationsFragment;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    private boolean backArrowPressed = false;
    private static final int INTERNET_PERMISSION_REQUEST_CODE = 1;


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
    LocationFragment locationFragment = new LocationFragment();

    FeedbackFragment feedbackFragment = new FeedbackFragment();
    AboutFragment aboutFragment = new AboutFragment();



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

            case R.id.navigation_location:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, locationFragment);
                return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        // Display a confirmation dialog
        super.onBackPressed();
        new AlertDialog.Builder(this)
                .setTitle("Exit App")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // If the user confirms, finish the activity
                    finish();
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // If the user cancels, dismiss the dialog
                    dialog.dismiss();
                })
                .setIcon(getDrawable(R.drawable.baseline_privacy_tip_24))
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
        builder.setPositiveButton("Exit", (dialog, which) -> {
            // Close the app
            finish();
        });


        builder.setNegativeButton("Stay", (dialog, which) -> {
            // User chose to stay, reset the flag
            backArrowPressed = false;
        });

        // Show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void exitApp() {

        finish();
        System.exit(0);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_implementation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.item1)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, aboutFragment).commit();
            return true;
        }
        else if (id == R.id.item2)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, feedbackFragment).commit();
            return true;
        }
        else if (id == R.id.item3)
        {
            FBDatabase fbDatabase = new FBDatabase();
            fbDatabase.saveLoginState(MainActivity.this, false);
            FirebaseAuth.getInstance().signOut();

            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        else if (id == R.id.item4)
        {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.INTERNET}, INTERNET_PERMISSION_REQUEST_CODE);
            }
            else
            {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.ca")));
            }
            return true;
        }
        else
        {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == INTERNET_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_main), "Permission granted.", Snackbar.LENGTH_SHORT);
                snackbar.show();
            } else {
                Snackbar snackbar = Snackbar.make(findViewById(R.id.activity_main), "Permission was not granted.", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        }
    }
}
