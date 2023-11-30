package ca.teambot.it.cave.examination.bot.ui;
//Adrian Portal Calcines n01489363 0CA
//Alfred Dowuona <student id> 0CA
//Ali Mohebi <student id> <section code>
//Hassan Noorani <student id> 0CB

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import ca.teambot.it.cave.examination.bot.FeedbackTimer;
import ca.teambot.it.cave.examination.bot.MainActivity;
import ca.teambot.it.cave.examination.bot.R;

public class FeedbackFragment extends Fragment {

    EditText firstName, phone, email, comment;
    Button button;
    RatingBar ratingBar;
    ProgressBar progressBar;
    TextView timeLeft;

    public FeedbackFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        button = view.findViewById(R.id.button);
        progressBar = view.findViewById(R.id.progressBar2);

        timeLeft = view.findViewById(R.id.textView12);

        firstName = view.findViewById(R.id.editTextText);
        phone = view.findViewById(R.id.editTextPhone);
        email = view.findViewById(R.id.editTextTextEmailAddress);
        comment = view.findViewById(R.id.editTextTextMultiLine);
        ratingBar = view.findViewById(R.id.ratingBar);

        button.setOnClickListener(view1 -> uploadFeedback());

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

    public void uploadFeedback()
    {
        FeedbackTimer feedbackTimer = new FeedbackTimer(requireContext());
        String pfirstName = firstName.getText().toString();
        String pphone = phone.getText().toString();
        String pemail = email.getText().toString();
        String pcomment = comment.getText().toString();
        String phoneModel = Build.MODEL;
        float pratingBar = ratingBar.getRating();

        boolean status = true;

        if (pfirstName.isEmpty())
        {
            firstName.setError(getString(R.string.this_field_cannot_be_empty));
            status = false;
        }
        else if ((pfirstName.length() < 3) || (!pfirstName.matches(getString(R.string.a_za_z))))
        {
            firstName.setError(getString(R.string.this_field_must_have_at_least_3_chars_and_no_numeric));
            status = false;
        }

        if (pphone.isEmpty())
        {
            phone.setError(getString(R.string.this_field_cannot_be_empty));
            status = false;
        }
        else if ((pphone.length() < 10) || !pphone.matches(getString(R.string._0_9_10)))
        {
            phone.setError(getString(R.string.this_field_must_have_exactly_10_numbers_and_no_alphabetic_characters));
            status = false;
        }

        if (pemail.isEmpty())
        {
            email.setError(getString(R.string.this_field_cannot_be_empty));
            status = false;
        }
        else if (!pemail.matches(getString(R.string.a_za_z0_9_a_za_z0_9_a_za_z_2)))
        {
            email.setError(getString(R.string.email_must_follow_correct_format));
            status = false;
        }

        if (!feedbackTimer.canSubmitFeedback())
        {
            status = false;
            timeLeft.setText("You can submit again in: " + feedbackTimer.getTimeLeft());
        }

        if (status)
        {
            progressBar.setVisibility(View.VISIBLE);
            Handler handler = new Handler();
            FBDatabase fbDatabase = new FBDatabase();

            String uniqueFeedbackId = fbDatabase.generateUniqueKey(getString(R.string.feedback));

            fbDatabase.addItem(getString(R.string.feedback), uniqueFeedbackId, getString(R.string.firstname), pfirstName);
            fbDatabase.addItem(getString(R.string.feedback), uniqueFeedbackId, getString(R.string.phone), pphone);
            fbDatabase.addItem(getString(R.string.feedback), uniqueFeedbackId, getString(R.string.email), pemail);
            fbDatabase.addItem(getString(R.string.feedback), uniqueFeedbackId, getString(R.string.comment), pcomment);
            fbDatabase.addItem(getString(R.string.feedback), uniqueFeedbackId, getString(R.string.phonemodel), phoneModel);
            fbDatabase.addItem(getString(R.string.feedback), uniqueFeedbackId, getString(R.string.ratingbar), String.valueOf(pratingBar));

            handler.postDelayed(this::alertDialog, 5000);

            firstName.setText("");
            phone.setText("");
            email.setText("");
            comment.setText("");
            ratingBar.setRating(0);

            progressBar.setVisibility(View.GONE);
            feedbackTimer.markFeedbackSubmitted();
            button.setBackgroundTintList((getResources().getColorStateList(R.color.grey)));
        }
    }

    public void alertDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setTitle(R.string.feedback_submission);
        builder.setMessage(R.string.your_feedback_was_successful);

        builder.setPositiveButton(R.string.ok, (dialog, id) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}