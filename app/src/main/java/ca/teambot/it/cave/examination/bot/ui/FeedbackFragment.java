package ca.teambot.it.cave.examination.bot.ui;

import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;


import ca.teambot.it.cave.examination.bot.R;

public class FeedbackFragment extends Fragment {

    EditText firstName, phone, email, comment;
    Button button;
    RatingBar ratingBar;

    public FeedbackFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_feedback, container, false);

        button = view.findViewById(R.id.button);

        firstName = view.findViewById(R.id.editTextText);
        phone = view.findViewById(R.id.editTextPhone);
        email = view.findViewById(R.id.editTextTextEmailAddress);
        comment = view.findViewById(R.id.editTextTextMultiLine);
        ratingBar = view.findViewById(R.id.ratingBar);

        button.setOnClickListener(view1 -> uploadFeedback());
        return view;
    }

    public void uploadFeedback()
    {
        String pfirstName = firstName.getText().toString();
        String pphone = phone.getText().toString();
        String pemail = email.getText().toString();
        String pcomment = comment.getText().toString();
        String phoneModel = Build.MODEL;
        int pratingBar = ratingBar.getNumStars();

        boolean status = true;

        if (pfirstName.isEmpty())
        {
            firstName.setError("This field cannot be empty!");
            status = false;
        }
        else if ((pfirstName.length() < 3) || (!pfirstName.matches("[a-zA-Z]+")))
        {
            firstName.setError("\"This field must have at least 3 chars and no numeric\"");
            status = false;
        }

        if (pphone.isEmpty())
        {
            phone.setError("This field cannot be empty!");
            status = false;
        }
        else if ((pphone.length() < 10) || !pphone.matches("^[0-9]{10}$"))
        {
            phone.setError("This field must have exactly 10 numbers and no alphabetic characters");
            status = false;
        }

        if (pemail.isEmpty())
        {
            email.setError("This field cannot be empty!");
            status = false;
        }
        else if (!pemail.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
        {
            email.setError("Email must follow correct format!");
            status = false;
        }

        if (status)
        {
            Document feedbackDocument = new Document();
            feedbackDocument.put("firstName", pfirstName);
            feedbackDocument.put("phone", pphone);
            feedbackDocument.put("email", pemail);
            feedbackDocument.put("comment", pcomment);
            feedbackDocument.put("phoneModel", phoneModel);
            feedbackDocument.put("ratingBar", pratingBar);

            // Call a method in DatabaseConnect to add the document to DynamoDB
            DatabaseConnect databaseConnect = new DatabaseConnect();
            databaseConnect.addItemToTable(feedbackDocument);
        }
    }
}