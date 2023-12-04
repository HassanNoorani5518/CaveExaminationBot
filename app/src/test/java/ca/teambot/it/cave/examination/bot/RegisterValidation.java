package ca.teambot.it.cave.examination.bot;

import android.content.Context;

import ca.teambot.it.cave.examination.bot.R;

public class RegisterValidation
{
    private final Context context;

    public RegisterValidation(Context context)
    {
        this.context = context;
    }

    public boolean isValidName(String name)
    {
        return !name.isEmpty();
    }
    public boolean isValidEmail(String email)
    {
        return !email.isEmpty() && email.matches(context.getString(R.string.a_za_z0_9_a_za_z0_9_a_za_z_2));
    }
    public boolean isValidPassword(String password)
    {
        return !password.isEmpty() && password.matches(context.getString(R.string.a_z_a_z_d_a_za_z_d_6));
    }
    public boolean isValidPhone(String phone)
    {
        return phone.length() == 10 && phone.matches(context.getString(R.string._0_9_10));
    }
    public boolean isValidConfirmPassword(String confirmPassword, String password)
    {
        return password.matches(confirmPassword);
    }

    public boolean isValidRegistration(String name, String email, String password, String phone, String confirmPassword)
    {
        return isValidName(name) && isValidEmail(email) && isValidPassword(password) && isValidPhone(phone) && isValidConfirmPassword(password, confirmPassword);
    }
}

