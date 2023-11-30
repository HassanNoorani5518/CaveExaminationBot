package ca.teambot.it.cave.examination.bot;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.TimeUnit;

public class FeedbackTimer {
    private static final String PREF_NAME_PREFIX = "FeedbackTimerPrefs_";

    private static final String LAST_SUBMISSION_TIME = "lastSubmissionTime";

    private final SharedPreferences preferences;

    public FeedbackTimer(Context context, User user) {
        preferences = context.getSharedPreferences(PREF_NAME_PREFIX + user.getUserId(), Context.MODE_PRIVATE);
    }

    public boolean canSubmitFeedback() {
        long lastSubmissionTime = preferences.getLong(LAST_SUBMISSION_TIME, 0);
        long currentTime = System.currentTimeMillis();
        long timeDifference = currentTime - lastSubmissionTime;

        return timeDifference >= TimeUnit.HOURS.toMillis(24);
    }

    public String getTimeLeft() {
        long lastSubmissionTime = preferences.getLong(LAST_SUBMISSION_TIME, 0);
        long currentTime = System.currentTimeMillis();
        long submissionLimit = lastSubmissionTime + TimeUnit.HOURS.toMillis(24);
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
        preferences.edit().putLong(LAST_SUBMISSION_TIME, System.currentTimeMillis()).apply();
    }
}
