package com.statboost.models.form;


/**
 * Created by Sam Kerr on 4/3/2014.
 */
public class Login {
    public String email;
    public String password;

    //do form error checking here
    /*
    public String validate() {
        User candidate = User.authenticate(email, password);
        if (candidate == null)
            return "Invalid email or password"; //Added to form's global error list
        return candidate.email;
    }
*/
}
