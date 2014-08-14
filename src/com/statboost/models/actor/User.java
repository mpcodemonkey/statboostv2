package com.statboost.models.actor;


import com.statboost.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.Id;


/**
 * Created by Sam Kerr on 4/3/2014.
 */


public class User {
    @Id
    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;

    private static SessionFactory factory = HibernateUtil.getSessionFactory();

    public User() {

    }

    /**
     * @param firstName
     * @param lastName
     * @param email - user's email address
     * @param password - user's hashed password
     * @param role - Enum 'Admin', 'Employee', 'Customer'
     */
    public User(String firstName, String lastName, String email, String password, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    /**
     * @param email - User email
     * @param password - User password
     * @return - User matching the email and password
     */

    public static User authenticate(String email, String password) {
        User candidate = User.find(email);
        if (candidate != null && BCrypt.checkpw(password, candidate.getPassword())) {
            return candidate;
        }
        return null;
    }

    /**
     * This method inserts a new user into the database
     * @param email - New User email
     * @param password - New User password
     * @param name - New User name in the form of firstname_lastname
     */

    public static boolean insert(String email, String password, String name) {
     /*   EbeanServer userDB = Ebean.getServer("default");
        if (userDB.find(User.class).where().eq("email", email).findUnique() == null) {
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            User newUser = new User(name, email, hashed);
            userDB.save(newUser);
            return true;
        }
        return false;*/return false;
    }

    /**
     * This method inserts a new user into the database
     * @param email - New User email
     * @param password - New User password
     * @param fname - New User first name
     * @param lname - New User first name
     * @param role - Enum 'Admin', 'Employee', 'Customer'
     */

    public static boolean insert(String fname, String lname, String email, String password, String role) {
       /* EbeanServer userDB = Ebean.getServer("user");
        if (userDB.find(User.class).where().eq("email", email).findUnique() == null) {
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            User newUser = new User(name, email, hashed, role);
            userDB.save(newUser);
            return true;
        }
        return false;*/
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            User newUser = new User(fname, lname, email, hashed, role);
            session.save(newUser);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }  finally {
            session.close();
        }
        return false;
    }

    public static User retrieveUser(String email) {
      /*  User candidate = null;
        if (email != null) {
            EbeanServer userDB = Ebean.getServer("user");
            candidate = userDB.find(User.class).where().eq("email", email).findUnique();
        }
        return  candidate;*/return null;
    }

    public static User find(String email) {
        User candidate = null;
        Session session = factory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();

            candidate = (User) session.createQuery("FROM User WHERE email='" + email + "'").uniqueResult();

            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        }  finally {
            session.close();
        }

        return candidate;
    }


    public static boolean isAdmin(String email) {
     /*   boolean result = false;
        if (email != null) {
            EbeanServer userDB = Ebean.getServer("user");
            User candidate = userDB.find(User.class).where().eq("email", email).findUnique();
            if (candidate != null && candidate.role.equals("Admin")) {
                result = true;
            }
        }
        return  result;*/return false;
    }


    /**
     * Getters and Setters
     */
    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
