package ca.teambot.it.cave.examination.bot;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
    ImageButton temperatureButton, humidityButton, caveButton;
    TextView temperature, humidity, objectDetection, caveIntegrity;
    private SensorDbHelper dbHelper;
    int caveInt = 4;

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
        temperatureButton = view.findViewById(R.id.imageButton7);
        temperature = view.findViewById(R.id.textView14);
        humidity = view.findViewById(R.id.textView16);
        humidityButton = view.findViewById(R.id.imageButton8);
        objectDetection = view.findViewById(R.id.textView18);
        caveIntegrity = view.findViewById(R.id.textView24);
        caveButton = view.findViewById(R.id.imageButton10);

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
        double humidityValue = 0;
        double temperatureValue = 0;

        dataReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                long delay = 0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final String key = snapshot.getKey();
                    final String timeValue = snapshot.child("Time").getValue(String.class);
                    final String objectDetected = snapshot.child("Spectrometer").getValue(String.class);
                    final String temperatureStr = snapshot.child("Temperature").getValue(String.class);
                    final String humidityStr = snapshot.child("Humidity").getValue(String.class);

                    saveSensorDataToLocalDatabase(timeValue, objectDetected, temperatureStr, humidityStr);

                    //assert objectDetected != null;
                    //double objectValue = Double.parseDouble(objectDetected);
                    assert temperatureStr != null;
                    assert humidityStr != null;
                    double humidityValue = Double.parseDouble(humidityStr);
                    double temperatureValue = Double.parseDouble(temperatureStr);


                    handler.postDelayed(() -> {
                        // Update your TextView with the retrieved value
                        humidity.setText(String.format(getString(R.string._1f), humidityValue) + getString(R.string.percentage));
                        temperature.setText(String.format(getString(R.string._1f), temperatureValue) + getString(R.string.c));
                        //objectDetection.setText(String.format(getString(R.string._1), objectValue));
                        caveIntegrity.setText(String.valueOf(caveInt));


                        if (temperatureValue > 20)
                        {
                            int color = Color.parseColor("#CE2029");
                            temperatureButton.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                        }
                        else if (temperatureValue < 5)
                        {
                            int color = Color.parseColor("#0096C7");
                            temperatureButton.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                        }
                        else
                        {
                            int color = Color.parseColor("#3E424B");
                            temperatureButton.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                        }

                        if (humidityValue > 70)
                        {
                            int color = Color.parseColor("#CE2029");
                            humidityButton.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                            caveInt = 0;
                        }
                        else
                        {
                            int color = Color.parseColor("#3E424B");
                            humidityButton.getBackground().setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                        }
                        if ((temperatureValue > 5 && temperatureValue < 20) && (humidityValue < 1))
                        {
                            caveInt = 4;
                        }
                        else if (temperatureValue > 20 && (humidityValue < 1))
                        {
                            caveInt = 3;
                        }
                        else if (temperatureValue < 5 && (humidityValue < 1))
                        {
                            caveInt = 2;
                        }
                        else
                        {
                            caveInt = 0;
                        }

                        int colorRed = Color.parseColor("#CE2029");
                        int colorOrange = Color.parseColor("#ff6600");
                        int colorLgreen = Color.parseColor("#d4ffb2");
                        int colorGreen = Color.parseColor("#45f248");
                        switch (caveInt)
                        {
                            case 0:
                                caveButton.getBackground().setColorFilter(colorRed, PorterDuff.Mode.SRC_ATOP);
                                break;
                            case 1:
                                caveButton.getBackground().setColorFilter(colorRed, PorterDuff.Mode.SRC_ATOP);
                                break;
                            case 2:
                                caveButton.getBackground().setColorFilter(colorOrange, PorterDuff.Mode.SRC_ATOP);
                                break;
                            case 3:
                                caveButton.getBackground().setColorFilter(colorGreen, PorterDuff.Mode.SRC_ATOP);
                                break;
                            case 4:
                                caveButton.getBackground().setColorFilter(colorGreen, PorterDuff.Mode.SRC_ATOP);
                                break;
                        }
                    }, delay);
                    delay += 3000; // 3000 milliseconds, or 3 seconds
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveSensorDataToLocalDatabase(String time, String objectDetected, String temperature, String humidity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SensorDataContract.SensorDataEntry.COLUMN_TIME, time);
        values.put(SensorDataContract.SensorDataEntry.COLUMN_OBJECT_DETECTION, objectDetected);
        values.put(SensorDataContract.SensorDataEntry.COLUMN_TEMPERATURE, temperature);
        values.put(SensorDataContract.SensorDataEntry.COLUMN_HUMIDITY, humidity);

        long newRowId = db.insert(SensorDataContract.SensorDataEntry.TABLE_NAME, null, values);
    }
}