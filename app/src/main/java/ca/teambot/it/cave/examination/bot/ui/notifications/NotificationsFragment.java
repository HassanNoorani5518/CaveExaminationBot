package ca.teambot.it.cave.examination.bot.ui.notifications;
//Adrian Portal Calcines n01489363 0CA
//Alfred Dowuona <student id> 0CA
//Ali Mohebi <student id> <section code>
//Hassan Noorani <student id> 0CB


import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ca.teambot.it.cave.examination.bot.MainActivity;
import ca.teambot.it.cave.examination.bot.R;


public class NotificationsFragment extends Fragment {

    public NotificationsFragment()
    {

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

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
}