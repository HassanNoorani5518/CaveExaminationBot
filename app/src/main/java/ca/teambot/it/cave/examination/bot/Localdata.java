package ca.teambot.it.cave.examination.bot;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class Localdata extends Fragment {

    private SensorDbHelper dbHelper;
    private TextView localDataTextView;
    private int currentIndex = 0;
    private Button button;
    private int localDataCount;

    public Localdata()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_localdata, container, false);

        dbHelper = new SensorDbHelper(getContext());
        localDataTextView = view.findViewById(R.id.textViewLocalData);
        button = view.findViewById(R.id.button4);

        localDataCount = getTotalDataCount();

        button.setOnClickListener(view1 -> {
            currentIndex = (currentIndex + 1) % localDataCount;

            displayLocalData(currentIndex);
        });
        return view;
    }

    private void displayLocalData(int index) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                SensorDataContract.SensorDataEntry.COLUMN_TIME,
                SensorDataContract.SensorDataEntry.COLUMN_CAVE_INTEGRITY,
                SensorDataContract.SensorDataEntry.COLUMN_TEMPERATURE,
                SensorDataContract.SensorDataEntry.COLUMN_HUMIDITY,
        };

        Cursor cursor = db.query(
                SensorDataContract.SensorDataEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        cursor.moveToPosition(index);

        String time = cursor.getString(cursor.getColumnIndexOrThrow(SensorDataContract.SensorDataEntry.COLUMN_TIME));
        String caveIntegrity = cursor.getString(cursor.getColumnIndexOrThrow(SensorDataContract.SensorDataEntry.COLUMN_CAVE_INTEGRITY));
        String temperature = cursor.getString(cursor.getColumnIndexOrThrow(SensorDataContract.SensorDataEntry.COLUMN_TEMPERATURE));
        String humidity = cursor.getString(cursor.getColumnIndexOrThrow(SensorDataContract.SensorDataEntry.COLUMN_HUMIDITY));

        // Update the TextView with the retrieved values
        localDataTextView.setText(String.format("Time: %s\nCave Integrity:%s\nTemperature:%s\nHumidity:%s\n", time, caveIntegrity, temperature, humidity));

        cursor.close();
    }

    private int getTotalDataCount() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int count = 0;

        try {
            String countQuery = "SELECT COUNT(*) FROM " + SensorDataContract.SensorDataEntry.TABLE_NAME;
            Cursor cursor = db.rawQuery(countQuery, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }

            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }
}