package ca.teambot.it.cave.examination.bot;

import android.content.Context;

import androidx.core.widget.TextViewCompat;

import org.junit.Test;

import ca.teambot.it.cave.examination.bot.R;

public class LoginValidation
{
    private final Context context;
    public LoginValidation(Context context)
    {
        this.context = context;
    }
    @Test
    public boolean isValidEmail(String email) {
        return !email.isEmpty() && email.matches(context.getString(R.string.a_za_z0_9_a_za_z0_9_a_za_z_2));
    }

    @Test
    public boolean isValidPassword(String password) {
        return !password.isEmpty() && password.matches(context.getString(R.string.a_z_a_z_d_a_za_z_d_6));
    }

    @Test
    public boolean isValidLogin(String email, String password) {
        return isValidEmail(email) && isValidPassword(password);
    }
}
