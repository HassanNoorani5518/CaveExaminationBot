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

import ca.teambot.it.cave.examination.bot.AlertsAdapter;
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
    }

    public NotificationsFragment()
    {

    }



}