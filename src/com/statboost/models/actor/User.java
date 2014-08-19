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

    private static SessionFactory userFactory = HibernateUtil.getUserSessionFactory();

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
     * @param fname - New User first name
     * @param lname - New User first name
     * @param role - Enum 'Admin', 'Employee', 'Customer'
     * @return boolean - true if success
     */

    public static boolean insert(String fname, String lname, String email, String password, String role) {
        Session session = userFactory.openSession();
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

    /**
     * This method updates the provided user object to the user database
     * @param user
     * @return boolean - true if success
     */
    public static boolean update(User user) {
        Session session = userFactory.openSession();
        Transaction tx = null;
        try{
            tx = session.beginTransaction();
            session.update(user);
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

    /**
     * This method returns the User object for a user exists with the provided email string
     * @param email
     * @return User
     */
    public static User find(String email) {
        User candidate = null;
        Session session = userFactory.openSession();
        Transaction tx = null;
        if (email !=null) {
            try {
                tx = session.beginTransaction();
                //query
                candidate = (User) session.createQuery("FROM User WHERE email='" + email + "'").uniqueResult();
                tx.commit();
            } catch (HibernateException e) {
                if (tx != null) tx.rollback();
                e.printStackTrace();
            } finally {
                session.close();
            }
        }
        return candidate;
    }


    /**
     * This method returns true if the provided email is an Admin user
     * @param email
     * @return  boolean - true if success
     */
    public static boolean isAdmin(String email) {
        boolean result = false;
        if (email != null) {
            User candidate = User.find(email);
            if (candidate != null && candidate.role.equals("Admin")) {
                result = true;
            }
        }
        return  result;
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
