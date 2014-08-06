package com.statboost.models.form;

import models.actor.User;

/**
 * Created by Sam Kerr on 4/3/2014.
 */
public class Login {
    public String email;
    public String password;

    //do form error checking here
    public String validate() {
        if (User.authenticate(email, password) == null)
            return "Invalid email or password"; //Added to form's global error list
        return null;
    }

}
