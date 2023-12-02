package ca.teambot.it.cave.examination.bot.ui.notifications;
//Adrian Portal Calcines n01489363 0CA
//Alfred Dowuona <student id> 0CA
//Ali Mohebi <student id> <section code>
//Hassan Noorani n01485518 0CB

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ca.teambot.it.cave.examination.bot.AlertsAdapter;
import ca.teambot.it.cave.examination.bot.AlertsNotification;
import ca.teambot.it.cave.examination.bot.R;


public class NotificationsFragment extends Fragment {

    private RecyclerView alertsRecyclerView;
    private AlertsAdapter alertsAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        alertsRecyclerView = view.findViewById(R.id.alertsRecyclerView);
        alertsAdapter = new AlertsAdapter();
        alertsRecyclerView.setAdapter(alertsAdapter);

        retrieveAlerts();

        return view;
    }

    private void retrieveAlerts() {

        List<AlertsNotification> alertsList = new ArrayList<>();

        alertsList.add(new AlertsNotification("Sensor Error", "Sensor XYZ is malfunctioning."));
        alertsList.add(new AlertsNotification("Movement Issue", "Unable to move forward."));
        alertsList.add(new AlertsNotification("Connection Lost", "Connection to the main server lost."));
        alertsList.add(new AlertsNotification("Low Battery", "Battery level is below 20%."));

        alertsAdapter.setAlertsList(alertsList);

    }



    public NotificationsFragment()
    {

    }



}