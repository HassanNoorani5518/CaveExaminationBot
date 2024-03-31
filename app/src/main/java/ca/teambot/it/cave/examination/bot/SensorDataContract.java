package ca.teambot.it.cave.examination.bot;

import android.provider.BaseColumns;

public final class SensorDataContract {

    private SensorDataContract() {}

    public static class SensorDataEntry implements BaseColumns {
        public static final String TABLE_NAME = "sensor_data";
        public static final String COLUMN_TIME = "time";
        public static final String COLUMN_OBJECT_DETECTION = "object_detection";
        public static final String COLUMN_CAVE_INTEGRITY = "air_pressure";
        public static final String COLUMN_TEMPERATURE = "air_pressure";
        public static final String COLUMN_HUMIDITY = "air_pressure";

        public static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + SensorDataEntry.TABLE_NAME + " (" +
                        SensorDataEntry._ID + " INTEGER PRIMARY KEY," +
                        SensorDataEntry.COLUMN_TIME + " TEXT," +
                        SensorDataEntry.COLUMN_CAVE_INTEGRITY + " TEXT" +
                        SensorDataEntry.COLUMN_OBJECT_DETECTION + " TEXT" +
                        SensorDataEntry.COLUMN_TEMPERATURE + " TEXT" +
                        SensorDataEntry.COLUMN_HUMIDITY + " TEXT" +
                        ")";

        public static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + SensorDataEntry.TABLE_NAME;
    }
}
