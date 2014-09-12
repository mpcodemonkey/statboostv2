package com.statboost.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Created by Sam Kerr on 8/13/2014.
 */
public class HibernateUtil {

    /**
     * This class returns a session factory instance for opening a connection to the database
     */

    private static SessionFactory dbSessionFactory;

    public static SessionFactory getDatabaseSessionFactory() {
        if (dbSessionFactory == null) {
            Configuration configuration = new Configuration();
            configuration.configure("statboost.cfg.xml"); //reads statboost.cfg.xml
            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
            dbSessionFactory =  configuration.buildSessionFactory(serviceRegistry);
        }
        return dbSessionFactory;
    }


}
