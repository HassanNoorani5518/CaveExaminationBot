package ca.teambot.it.cave.examination.bot;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.TimeUnit;

public class FeedbackTimer {
    private static final String PREF_NAME = "FeedbackTimerPrefs";
    private static final String LAST_SUBMISSION_TIME = "lastSubmissionTime";

    private static SharedPreferences preferences;

    public FeedbackTimer(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        }
    }

    public boolean canSubmitFeedback() {
        long lastSubmissionTime = preferences.getLong(LAST_SUBMISSION_TIME, 0);
        long currentTime = System.currentTimeMillis();

        // Calculate the difference between the current time and the last submission time
        long timeDifference = currentTime - lastSubmissionTime;

        // Check if 24 hours have passed since the last submission
        return timeDifference >= 24 * 60 * 60 * 1000;
    }

    public static String getTimeLeft() {
        long lastSubmissionTime = preferences.getLong(LAST_SUBMISSION_TIME, 0);
        long currentTime = System.currentTimeMillis();
        long submissionLimit = lastSubmissionTime + TimeUnit.HOURS.toMillis(24);

        // Calculate the difference between the current time and the last submission time
        long timeDifference = submissionLimit - currentTime;

        if (timeDifference <= 0) {
            return "00:00:00";
        }

        long hours = TimeUnit.MILLISECONDS.toHours(timeDifference);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifference) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeDifference) % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    public void markFeedbackSubmitted() {
        // Save the current time when feedback is submitted
        preferences.edit().putLong(LAST_SUBMISSION_TIME, System.currentTimeMillis()).apply();
    }
}
