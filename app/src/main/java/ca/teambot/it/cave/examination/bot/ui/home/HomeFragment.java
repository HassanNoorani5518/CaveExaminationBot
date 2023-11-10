package ca.teambot.it.cave.examination.bot.ui.home;
//Adrian Portal Calcines n01489363 0CA
//Alfred Dowuona <student id> 0CA
//Ali Mohebi <student id> <section code>
//Hassan Noorani <student id> 0CB

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import ca.teambot.it.cave.examination.bot.LoginActivity;
import ca.teambot.it.cave.examination.bot.MainActivity;
import ca.teambot.it.cave.examination.bot.R;

public class HomeFragment extends Fragment
{
    TextView textView;
    FirebaseAuth auth;
    FirebaseUser user;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        textView = view.findViewById(R.id.textView6);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null)
        {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            textView.setText(String.format("%s%s%s", getString(R.string.logged_in_as), getString(R.string.space), user.getEmail()));
        }

        ImageView fadingImage = view.findViewById(R.id.fade_cavebot);

        // Create an AlphaAnimation to fade the image in
        Animation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
        fadeInAnimation.setDuration(1000); // Adjust the duration as needed (in milliseconds)

        // Set an AnimationListener to control visibility when the animation ends
        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Make the image visible when the animation ends
                fadingImage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        // Start the animation
        fadingImage.startAnimation(fadeInAnimation);

        return view;
    }




    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Handle back button press for FragmentA
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                showExitConfirmationDialog();
                return true;
            }
            return false;
        });
    }

    private void showExitConfirmationDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle("Exit App")
                .setMessage("Are you sure you want to exit the app?")
                .setPositiveButton("Yes", (dialog, which) -> ((MainActivity) requireActivity()).exitApp())
                .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                .show();
    }

    public HomeFragment()
    {

    }




}