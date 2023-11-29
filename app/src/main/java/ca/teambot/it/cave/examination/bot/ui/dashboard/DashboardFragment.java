package ca.teambot.it.cave.examination.bot.ui.dashboard;
//Adrian Portal Calcines n01489363 0CA
//Alfred Dowuona <student id> 0CA
//Ali Mohebi <student id> <section code>
//Hassan Noorani <student id> 0CB


import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ca.teambot.it.cave.examination.bot.Datareading;
import ca.teambot.it.cave.examination.bot.MainActivity;
import ca.teambot.it.cave.examination.bot.R;
import ca.teambot.it.cave.examination.bot.Robotcontrol;
import ca.teambot.it.cave.examination.bot.SettingsFragment;

public class DashboardFragment extends Fragment
{
    Button settings, dataRead, robotControl, random;
    SettingsFragment settingsFragment = new SettingsFragment();
    Datareading datareading = new Datareading();
    Robotcontrol robotcontrol = new Robotcontrol();

    public DashboardFragment()
    {

    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        settings = view.findViewById(R.id.btnSettings);
        robotControl = view.findViewById(R.id.btnRobotControl);
        dataRead = view.findViewById(R.id.btnDataReading);
        random = view.findViewById(R.id.btnRandom);

        // Set click listeners for your buttons
        settings.setOnClickListener(v -> navigateToSettingsFragment());
        robotControl.setOnClickListener(v -> navigateToRobotControlFragment());
        dataRead.setOnClickListener(v -> navigateToDataReadingFragment());
        random.setOnClickListener(v -> navigateToRandomFragment());

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

    private void navigateToSettingsFragment()
    {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main, settingsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void navigateToRobotControlFragment()
    {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main, robotcontrol);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void navigateToDataReadingFragment()
    {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main, datareading);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void navigateToRandomFragment()
    {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main, settingsFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}