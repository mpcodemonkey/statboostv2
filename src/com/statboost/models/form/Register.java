package com.statboost.models.form;

/**
 * Created by Sam Kerr on 4/9/2014.
 */
public class Register {
    public String email;
    public String password;
    public String passwordConf;
    public String firstname;
    public String lastname;

    //do form error checking here
    public String validate() {
        if (email.equals("") || password.equals("") || firstname.equals("") || lastname.equals(""))
            return "Fields cannot be left blank.";
        if (!password.equals(passwordConf))
            return "Passwords do not match.";
        return null;
    }
}
