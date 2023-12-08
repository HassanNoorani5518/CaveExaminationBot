package ca.teambot.it.cave.examination.bot.ui.home;
//Adrian Portal Calcines n01489363 0CA
//Alfred Dowuona <student id> 0CA
//Ali Mohebi <student id> <section code>
//Hassan Noorani n01485518 0CB


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ca.teambot.it.cave.examination.bot.Datareading;
import ca.teambot.it.cave.examination.bot.FeedbackTimer;
import ca.teambot.it.cave.examination.bot.LoginActivity;
import ca.teambot.it.cave.examination.bot.MainActivity;
import ca.teambot.it.cave.examination.bot.R;
import ca.teambot.it.cave.examination.bot.User;

public class HomeFragment extends Fragment
{
    private static final String PROFILE_PICTURE_PREFERENCE_KEY = "profile_picture_preference_user_";
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = mAuth.getCurrentUser();
    TextView textView;
    ImageView imageView;
    FirebaseAuth auth;
    FirebaseUser user;
    String userId = "";
    Datareading datareading = new Datareading();



    public HomeFragment()
    {

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        
        FloatingActionButton FAB = view.findViewById(R.id.fab);


        FAB.setOnClickListener(v -> ButtonNavigateDataReadingFragment());
        textView = view.findViewById(R.id.textView6);
        imageView = view.findViewById(R.id.imageView);

        if (currentUser != null) {
            userId = currentUser.getUid();
            Uri profilePictureUri = getProfilePictureUri(userId);
            loadProfilePicture(profilePictureUri);
        }

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null)
        {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            textView.setText(user.getEmail());
        }

        ImageView fadingImage = view.findViewById(R.id.fade_cavebot);

        Animation fadeInAnimation = new AlphaAnimation(0.0f, 1.0f);
        fadeInAnimation.setDuration(1000);
        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                fadingImage.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        fadingImage.startAnimation(fadeInAnimation);

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

    private Uri getProfilePictureUri(String userId) {
        SharedPreferences preferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        String uriString = preferences.getString(PROFILE_PICTURE_PREFERENCE_KEY + userId, null);
        return (uriString != null) ? Uri.parse(uriString) : null;
    }

    private void loadProfilePicture(Uri profilePictureUri) {
        if (profilePictureUri != null) {
            Glide.with(this)
                    .load(profilePictureUri)
                    .into(imageView);
        }
    }

    private void ButtonNavigateDataReadingFragment()
    {
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activity_main, datareading);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}