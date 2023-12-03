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

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import ca.teambot.it.cave.examination.bot.AlertsAdapter;
import ca.teambot.it.cave.examination.bot.AlertsNotification;
import ca.teambot.it.cave.examination.bot.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.ValueEventListener;


public class NotificationsFragment extends Fragment {

    private RecyclerView alertsRecyclerView;
    private AlertsAdapter alertsAdapter;

    private DatabaseReference databaseReference;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        alertsRecyclerView = view.findViewById(R.id.alertsRecyclerView);
        alertsAdapter = new AlertsAdapter(requireContext());
        alertsRecyclerView.setAdapter(alertsAdapter);
        databaseReference = FirebaseDatabase.getInstance().getReference("Errors");


        retrieveAlerts();

        return view;
    }

    private void retrieveAlerts() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<AlertsNotification> alertsList = new ArrayList<>();

                for (DataSnapshot errorSnapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot typeSnapshot : errorSnapshot.getChildren()) {
                        String type = typeSnapshot.getKey();
                        String message = typeSnapshot.getValue(String.class);

                        alertsList.add(new AlertsNotification(type, message));
                    }
                }

                alertsAdapter.setAlertsList(alertsList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public NotificationsFragment() {
        // Default constructor
    }
}