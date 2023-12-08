package ca.teambot.it.cave.examination.bot;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import ca.teambot.it.cave.examination.bot.ui.FBDatabase;


public class Datareading extends Fragment {
    FirebaseDatabase database;
    Button button;
    TextView data;
    private SensorDbHelper dbHelper;

    public Datareading() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_datareading, container, false);

        database = FirebaseDatabase.getInstance();
        dbHelper = new SensorDbHelper(getContext());

        button = view.findViewById(R.id.button3);
        data = view.findViewById(R.id.textView11);

        FBDatabase fbDatabase = new FBDatabase();

        button.setOnClickListener(view1 -> {
            for (int i = 0; i < 20; i++)
            {
                fbDatabase.populateData();
            }
        });
        displayData();

        return view;
    }

    public void displayData()
    {
        DatabaseReference dataReference = FirebaseDatabase.getInstance().getReference().child("SensorData");
        Handler handler = new Handler();


        dataReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long delay = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final String key = snapshot.getKey();
                    final String timeValue = snapshot.child("Time").getValue(String.class);
                    final String airStr = snapshot.child("Air Pressure").getValue(String.class);
                    final String caveStr = snapshot.child("Cave Integrity").getValue(String.class);
                    final String magStr = snapshot.child("Magnetic Field").getValue(String.class);
                    final String gasStr = snapshot.child("Gas Level").getValue(String.class);
                    final String temperatureStr = snapshot.child("Temperature").getValue(String.class);
                    final String humidityStr = snapshot.child("Humidity").getValue(String.class);

                    saveSensorDataToLocalDatabase(timeValue, airStr, caveStr, magStr, gasStr, temperatureStr, humidityStr);

                    assert airStr != null;
                    double airValue = Double.parseDouble(airStr);
                    assert caveStr != null;
                    double caveValue = Double.parseDouble(caveStr);
                    assert magStr != null;
                    double magValue = Double.parseDouble(magStr);
                    assert gasStr != null;
                    double gasValue = Double.parseDouble(gasStr);
                    assert temperatureStr != null;
                    double temperatureValue = Double.parseDouble(temperatureStr);
                    assert humidityStr != null;
                    double humidityValue = Double.parseDouble(humidityStr);

                    handler.postDelayed(() -> {
                        // Update your TextView with the retrieved values
                        data.setText(String.format(getString(R.string.s_s_air_pressure_s_temperature_s_humidity_s_s_s_magnetic_field_s_cave_integrity_s),
                                getString(R.string.time), timeValue, String.format(getString(R.string._3f), airValue), String.format(getString(R.string._3f), temperatureValue),
                                String.format(getString(R.string._3f), humidityValue), getString(R.string.gas_level), String.format(getString(R.string._3f), gasValue),
                                String.format(getString(R.string._3f), magValue), String.format(getString(R.string._3f), caveValue)));
                    }, delay);
                    delay += 3000; // 3000 milliseconds, or 3 seconds
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveSensorDataToLocalDatabase(String time, String airPressure, String caveIntegrity, String magneticField, String gasLevel, String temperature, String humidity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SensorDataContract.SensorDataEntry.COLUMN_TIME, time);
        values.put(SensorDataContract.SensorDataEntry.COLUMN_AIR_PRESSURE, airPressure);
        values.put(SensorDataContract.SensorDataEntry.COLUMN_CAVE_INTEGRITY, caveIntegrity);
        values.put(SensorDataContract.SensorDataEntry.COLUMN_MAGNETIC_FIELD, magneticField);
        values.put(SensorDataContract.SensorDataEntry.COLUMN_GAS_LEVEL, gasLevel);
        values.put(SensorDataContract.SensorDataEntry.COLUMN_TEMPERATURE, temperature);
        values.put(SensorDataContract.SensorDataEntry.COLUMN_HUMIDITY, humidity);

        long newRowId = db.insert(SensorDataContract.SensorDataEntry.TABLE_NAME, null, values);
    }
}