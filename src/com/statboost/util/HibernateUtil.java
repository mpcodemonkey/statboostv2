package com.statboost.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Created by Sam Kerr on 8/13/2014.
 */
public class HibernateUtil {


    private static  SessionFactory sessionFactory;

    static {
        try {
            System.err.println();
            Configuration configuration = new Configuration();
            configuration.configure(); //reads hibernate.cfg.xml
            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            sessionFactory =  configuration.buildSessionFactory(serviceRegistry);
        } catch (Throwable ex) {

            System.err.println("Initial SessionFactory creation failed." + ex);
            //throw new ExceptionInInitializerError(ex.getMessage());

        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}
