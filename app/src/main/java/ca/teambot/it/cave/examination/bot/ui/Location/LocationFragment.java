package ca.teambot.it.cave.examination.bot.ui.Location;
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
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import ca.teambot.it.cave.examination.bot.MainActivity;
import ca.teambot.it.cave.examination.bot.R;


public class LocationFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;

    public LocationFragment()
    {

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_location, container, false);


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment == null)
        {
            mapFragment = SupportMapFragment.newInstance();
            getChildFragmentManager().beginTransaction()
                    .replace(R.id.map, mapFragment)
                    .commit();
        }

        mapFragment.getMapAsync(this);

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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        LatLng ontario = new LatLng(51, 85);
        mMap.addMarker(new MarkerOptions().position(ontario).title(getString(R.string.ontario)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ontario));
    }
}