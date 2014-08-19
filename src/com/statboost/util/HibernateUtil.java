package com.statboost.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Created by Sam Kerr on 8/13/2014.
 */
public class HibernateUtil {


    private static  SessionFactory userSessionFactory;
    private static  SessionFactory mtgSessionFactory;

    static {
        try {

            Configuration userConfiguration = new Configuration();
            userConfiguration.configure("userData.cfg.xml"); //reads userData.cfg.xml
            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(userConfiguration.getProperties()).buildServiceRegistry();
            userSessionFactory =  userConfiguration.buildSessionFactory(serviceRegistry);

            Configuration mtgConfiguration = new Configuration();
            mtgConfiguration.configure("mtgData.cfg.xml"); //reads userData.cfg.xml
            ServiceRegistry serviceRegistry2 = new ServiceRegistryBuilder().applySettings(mtgConfiguration.getProperties()).buildServiceRegistry();
            mtgSessionFactory =  mtgConfiguration.buildSessionFactory(serviceRegistry2);
        } catch (Throwable ex) {

            System.err.println("Initial SessionFactory creation failed." + ex);
            //throw new ExceptionInInitializerError(ex.getMessage());

        }
    }

    public static SessionFactory getUserSessionFactory() {
        return userSessionFactory;
    }
    public static SessionFactory getMTGSessionFactory() {
        return mtgSessionFactory;
    }

}
