package com.statboost.models.actor;


import com.statboost.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.servlet.http.HttpSession;


/**
 * Created by Sam Kerr on 4/3/2014.
 */

@Entity
public class User {
    @Id
    private int usrUid;
    private String usrFirstName;
    private String usrLastName;
    private String usrEmail;
    private String usrPassword;
    private String usrRole;
    private String usrAddress1;
    private String usrAddress2;
    private String usrCity;
    private String usrState;
    private String usrZip;
    private String usrPhone;
    private Byte usrNewsletter;
    private Byte usrActive;
    private String usrDciNumber;

    private static SessionFactory userFactory = HibernateUtil.getUserSessionFactory();

    public User() {

    }

    /**
     * @param firstName
     * @param lastName
     * @param email     - user's email address
     * @param password  - user's hashed password
     * @param role      - Enum 'Admin', 'Employee', 'Customer'
     */
    public User(String firstName, String lastName, String email, String password, String role) {
        this.usrFirstName = firstName;
        this.usrLastName = lastName;
        this.usrEmail = email;
        this.usrPassword = password;
        this.usrRole = role;
    }

    /**
     * @param email    - User email
     * @param password - User password
     * @return - User matching the email and password
     */
    public static User authenticate(String email, String password) {
        User candidate = User.find(email);
        if (candidate != null && BCrypt.checkpw(password, candidate.getUsrPassword())) {
            return candidate;
        }
        return null;
    }


    /**
     * This method inserts a new user into the database
     *
     * @param email    - New User email
     * @param password - New User password
     * @param fname    - New User first name
     * @param lname    - New User first name
     * @param role     - Enum 'Admin', 'Employee', 'Customer'
     * @return boolean - true if success
     */
    public static boolean insert(String fname, String lname, String email, String password, String role) {
        Session session = userFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
            User newUser = new User(fname, lname, email, hashed, role);
            session.save(newUser);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    /**
     * This method updates the provided user object to the user database
     *
     * @param user
     * @return boolean - true if success
     */
    public static boolean update(User user) {
        Session session = userFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(user);
            tx.commit();
            return true;
        } catch (HibernateException e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return false;
    }

    /**
     * This method returns the User object for a user exists with the provided email string
     *
     * @param email
     * @return User
     */
    public static User find(String email) {
        User candidate = null;
        Session session = userFactory.openSession();
        Transaction tx = null;
        if (email != null) {
            try {
                tx = session.beginTransaction();
                //query
                candidate = (User) session.createQuery("FROM User WHERE usrEmail='" + email + "'").uniqueResult();
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
     * Check that the user is logged in and check that the user is an Admin, either through session attribute or db check last resort
     *
     * @param session
     * @return - true if Admin is legit
     */
    public static boolean isAdmin(HttpSession session) {
        //check that the user is logged in and check that the user is an admin either through session attribute or db check
        if (session != null && session.getAttribute("email") != null &&
                (session.getAttribute("admin").equals("true") || User.isAdmin((String) session.getAttribute("email")))) {
            return true;
        }
        return false;
    }


    /**
     * This method returns true if the provided email is an Admin user
     *
     * @param email
     * @return boolean - true if success
     */
    public static boolean isAdmin(String email) {
        boolean result = false;
        if (email != null) {
            User candidate = User.find(email);
            if (candidate != null && candidate.usrRole.equals("Admin")) {
                result = true;
            }
        }
        return result;
    }


    /**
     * Getters and Setters
     */
    public int getUsrUid() {
        return usrUid;
    }

    public void setUsrUid(int usrUid) {
        this.usrUid = usrUid;
    }

    public String getUsrFirstName() {
        return usrFirstName;
    }

    public void setUsrFirstName(String usrFirstName) {
        this.usrFirstName = usrFirstName;
    }

    public String getUsrLastName() {
        return usrLastName;
    }

    public void setUsrLastName(String usrLastName) {
        this.usrLastName = usrLastName;
    }

    public String getUsrEmail() {
        return usrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        this.usrEmail = usrEmail;
    }

    public String getUsrPassword() {
        return usrPassword;
    }

    public void setUsrPassword(String usrPassword) {
        this.usrPassword = usrPassword;
    }

    public String getUsrRole() {
        return usrRole;
    }

    public void setUsrRole(String usrRole) {
        this.usrRole = usrRole;
    }

    public String getUsrAddress1() {
        return usrAddress1;
    }

    public void setUsrAddress1(String usrAddress1) {
        this.usrAddress1 = usrAddress1;
    }

    public String getUsrAddress2() {
        return usrAddress2;
    }

    public void setUsrAddress2(String usrAddress2) {
        this.usrAddress2 = usrAddress2;
    }

    public String getUsrCity() {
        return usrCity;
    }

    public void setUsrCity(String usrCity) {
        this.usrCity = usrCity;
    }

    public String getUsrState() {
        return usrState;
    }

    public void setUsrState(String usrState) {
        this.usrState = usrState;
    }

    public String getUsrZip() {
        return usrZip;
    }

    public void setUsrZip(String usrZip) {
        this.usrZip = usrZip;
    }

    public String getUsrPhone() {
        return usrPhone;
    }

    public void setUsrPhone(String usrPhone) {
        this.usrPhone = usrPhone;
    }

    public Byte getUsrNewsletter() {
        return usrNewsletter;
    }

    public void setUsrNewsletter(Byte usrNewsletter) {
        this.usrNewsletter = usrNewsletter;
    }

    public Byte getUsrActive() {
        return usrActive;
    }

    public void setUsrActive(Byte usrActive) {
        this.usrActive = usrActive;
    }

    public String getUsrDciNumber() {
        return usrDciNumber;
    }

    public void setUsrDciNumber(String usrDciNumber) {
        this.usrDciNumber = usrDciNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User that = (User) o;

        if (usrUid != that.usrUid) return false;
        if (usrActive != null ? !usrActive.equals(that.usrActive) : that.usrActive != null) return false;
        if (usrAddress1 != null ? !usrAddress1.equals(that.usrAddress1) : that.usrAddress1 != null) return false;
        if (usrAddress2 != null ? !usrAddress2.equals(that.usrAddress2) : that.usrAddress2 != null) return false;
        if (usrCity != null ? !usrCity.equals(that.usrCity) : that.usrCity != null) return false;
        if (usrDciNumber != null ? !usrDciNumber.equals(that.usrDciNumber) : that.usrDciNumber != null) return false;
        if (usrEmail != null ? !usrEmail.equals(that.usrEmail) : that.usrEmail != null) return false;
        if (usrFirstName != null ? !usrFirstName.equals(that.usrFirstName) : that.usrFirstName != null) return false;
        if (usrLastName != null ? !usrLastName.equals(that.usrLastName) : that.usrLastName != null) return false;
        if (usrNewsletter != null ? !usrNewsletter.equals(that.usrNewsletter) : that.usrNewsletter != null)
            return false;
        if (usrPassword != null ? !usrPassword.equals(that.usrPassword) : that.usrPassword != null) return false;
        if (usrPhone != null ? !usrPhone.equals(that.usrPhone) : that.usrPhone != null) return false;
        if (usrRole != null ? !usrRole.equals(that.usrRole) : that.usrRole != null) return false;
        if (usrState != null ? !usrState.equals(that.usrState) : that.usrState != null) return false;
        if (usrZip != null ? !usrZip.equals(that.usrZip) : that.usrZip != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = usrUid;
        result = 31 * result + (usrFirstName != null ? usrFirstName.hashCode() : 0);
        result = 31 * result + (usrLastName != null ? usrLastName.hashCode() : 0);
        result = 31 * result + (usrEmail != null ? usrEmail.hashCode() : 0);
        result = 31 * result + (usrPassword != null ? usrPassword.hashCode() : 0);
        result = 31 * result + (usrRole != null ? usrRole.hashCode() : 0);
        result = 31 * result + (usrAddress1 != null ? usrAddress1.hashCode() : 0);
        result = 31 * result + (usrAddress2 != null ? usrAddress2.hashCode() : 0);
        result = 31 * result + (usrCity != null ? usrCity.hashCode() : 0);
        result = 31 * result + (usrState != null ? usrState.hashCode() : 0);
        result = 31 * result + (usrZip != null ? usrZip.hashCode() : 0);
        result = 31 * result + (usrPhone != null ? usrPhone.hashCode() : 0);
        result = 31 * result + (usrNewsletter != null ? usrNewsletter.hashCode() : 0);
        result = 31 * result + (usrActive != null ? usrActive.hashCode() : 0);
        result = 31 * result + (usrDciNumber != null ? usrDciNumber.hashCode() : 0);
        return result;
    }

}