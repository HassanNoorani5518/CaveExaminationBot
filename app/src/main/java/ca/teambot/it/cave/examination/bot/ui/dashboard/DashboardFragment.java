package ca.teambot.it.cave.examination.bot.ui.dashboard;
//Adrian Portal Calcines n01489363 0CA
//Alfred Dowuona <student id> 0CA
//Ali Mohebi <student id> <section code>
//Hassan Noorani <student id> 0CB

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import ca.teambot.it.cave.examination.bot.R;
import ca.teambot.it.cave.examination.bot.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment
{
    public DashboardFragment()
    {

    }
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);
        return view;
    }

}