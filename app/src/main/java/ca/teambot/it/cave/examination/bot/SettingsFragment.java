package ca.teambot.it.cave.examination.bot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.widget.Button;
import android.widget.ImageView;

public class SettingsFragment extends Fragment
{
    private static final String THEME_PREFERENCE_KEY = "theme_preference";
    private static final String LIGHT_THEME = "light";
    private static final String DARK_THEME = "dark";

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView profileImageView;


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Button switchThemeButton = view.findViewById(R.id.switchThemeButton);

        switchThemeButton.setOnClickListener(v -> toggleTheme());

        profileImageView = view.findViewById(R.id.profileImageView);
        Button changeProfilePictureButton = view.findViewById(R.id.changeProfilePictureButton);

        changeProfilePictureButton.setOnClickListener(v -> openImagePicker());


        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                ((MainActivity) requireActivity()).showExitAlertDialog();
                return true;
            }
            return false;
        });

        return view;
    }

    private void toggleTheme() {
        // Get the current theme preference
        String currentTheme = getSavedTheme();

        // Toggle between light and dark themes
        String newTheme = (currentTheme.equals(DARK_THEME)) ? LIGHT_THEME : DARK_THEME;

        // Save the new theme preference
        saveThemePreference(newTheme);

        // Apply the new theme
        applyTheme(newTheme);
    }


    private String getSavedTheme() {
        SharedPreferences preferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        return preferences.getString(THEME_PREFERENCE_KEY, LIGHT_THEME);
    }



    private void saveThemePreference(String theme) {
        SharedPreferences preferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(THEME_PREFERENCE_KEY, theme);
        editor.apply();
    }



    private void applyTheme(String theme) {
        if (theme.equals(DARK_THEME)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        if (getActivity() != null) {
            getActivity().recreate();
        }
    }




    @Override
    public void onDetach() {
        super.onDetach();

        requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }



    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        // Lock the orientation to portrait when the device is rotated
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            requireActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            profileImageView.setImageURI(selectedImageUri);

            // ADD/UPDATE TO FIREBASE HERE
        }
    }



    public class NotificationUtils {

        public boolean hasNotificationPolicyAccess(Context context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                if (notificationManager != null) {
                    return notificationManager.isNotificationPolicyAccessGranted();
                }
            }
            return false;
        }
    }
}