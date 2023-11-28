package ca.teambot.it.cave.examination.bot;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AlertsAdapter extends RecyclerView.Adapter<AlertsAdapter.AlertViewHolder> {

    private List<AlertModel> alertsList;

    public void setAlertsList(List<AlertModel> alertsList) {
        this.alertsList = alertsList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AlertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_alert, parent, false);
        return new AlertViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlertViewHolder holder, int position) {
        AlertModel alert = alertsList.get(position);
        holder.alertTypeTextView.setText(alert.getType());
        holder.alertMessageTextView.setText(alert.getMessage());
        // Bind other data as needed
    }

    @Override
    public int getItemCount() {
        return (alertsList == null) ? 0 : alertsList.size();
    }

    static class AlertViewHolder extends RecyclerView.ViewHolder {
        TextView alertTypeTextView;
        TextView alertMessageTextView;

        AlertViewHolder(@NonNull View itemView) {
            super(itemView);
            alertTypeTextView = itemView.findViewById(R.id.alertTypeTextView);
            alertMessageTextView = itemView.findViewById(R.id.alertMessageTextView);

        }
    }
}

