package ca.teambot.it.cave.examination.bot.ui.notifications;
//Adrian Portal Calcines n01489363 0CA
//Alfred Dowuona <student id> 0CA
//Ali Mohebi <student id> <section code>
//Hassan Noorani n01485518 0CB


import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
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

import ca.teambot.it.cave.examination.bot.MainActivity;
import ca.teambot.it.cave.examination.bot.R;


public class NotificationsFragment extends Fragment {

    private static final String THEME_PREFERENCE_KEY = "theme_preference";
    private static final String LIGHT_THEME = "light";
    private static final String DARK_THEME = "dark";

    public NotificationsFragment()
    {

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        Button switchThemeButton = view.findViewById(R.id.switchThemeButton);

        switchThemeButton.setOnClickListener(v -> toggleTheme());

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