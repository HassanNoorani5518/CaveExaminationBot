package ca.teambot.it.cave.examination.bot;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class User
{
    private String userId;

    public User(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }
}
