//package com.statboost.models.actor;
//
//import com.avaje.ebean.Ebean;
//import com.avaje.ebean.EbeanServer;
//import com.avaje.ebean.Model;
//
//import javax.persistence.Entity;
//import javax.persistence.Id;
//
///**
// * Created by Sam Kerr on 4/3/2014.
// */
//public class User{
//    public String email;
//    public String name;
//    public String password;
//    public String role;
//
//    /**
//     * @param name
//     * @param email
//     * @param password
//     */
//    public User(String name, String email, String password) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//    }
//
//    /**
//     * @param name - user's firstname_lastname
//     * @param email - user's email address
//     * @param password - user's hashed password
//     * @param role - Enum 'Admin', 'Employee', 'Customer'
//     */
//    public User(String name, String email, String password, String role) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.role = role;
//    }
//
//    /**
//     * @param email - User email
//     * @param password - User password
//     * @return - User matching the email and password
//     */
//    /*
//    public static User authenticate(String email, String password) {
//        EbeanServer userDB = Ebean.getServer("user");
//        User candidate = userDB.find(User.class).where().eq("email", email).findUnique();
//        if (candidate != null && BCrypt.checkpw(password, candidate.password))
//            return candidate;
//        else return null;
//    }
//*/
//    /**
//     * This method inserts a new user into the database
//     * @param email - New User email
//     * @param password - New User password
//     * @param name - New User name in the form of firstname_lastname
//     */
//    /*
//    public static boolean insert(String email, String password, String name) {
//        EbeanServer userDB = Ebean.getServer("user");
//        if (userDB.find(User.class).where().eq("email", email).findUnique() == null) {
//            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
//            User newUser = new User(name, email, hashed);
//            userDB.save(newUser);
//            return true;
//        }
//        return false;
//    }
//*/
////    /**
////     * This method inserts a new user into the database
////     * @param email - New User email
////     * @param password - New User password
////     * @param name - New User name in the form of firstname_lastname
////     * @param role - Enum 'Admin', 'Employee', 'Customer'
////     */
////    /*
////    public static boolean insert(String email, String password, String name, String role) {
////        EbeanServer userDB = Ebean.getServer("user");
////        if (userDB.find(User.class).where().eq("email", email).findUnique() == null) {
////            String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
////            User newUser = new User(name, email, hashed, role);
////            userDB.save(newUser);
////            return true;
////        }
////        return false;
////    }
////*/
////    public static User retrieveUser(String email) {
////        User candidate = null;
////        if (email != null) {
////            EbeanServer userDB = Ebean.getServer("user");
////            candidate = userDB.find(User.class).where().eq("email", email).findUnique();
////        }
////        return  candidate;
////    }
////
////
////    public static boolean isAdmin(String email) {
////        boolean result = false;
////        if (email != null) {
////            EbeanServer userDB = Ebean.getServer("user");
////            User candidate = userDB.find(User.class).where().eq("email", email).findUnique();
////            if (candidate != null && candidate.role.equals("Admin")) {
////                result = true;
////            }
////        }
////        return  result;
////    }
//
//    /**
//     * Static Finder used for querying
//     */
//    public static Finder<String,User> find = new Finder<>(String.class, User.class);
//}
