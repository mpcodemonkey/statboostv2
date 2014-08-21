package com.statboost.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Created by Sam Kerr on 8/13/2014.
 */
public class HibernateUtil {


    private static SessionFactory userSessionFactory;
    private static SessionFactory mtgSessionFactory;

    public static SessionFactory getUserSessionFactory() {
        if (userSessionFactory == null) {
            Configuration userConfiguration = new Configuration();
            userConfiguration.configure("userData.cfg.xml"); //reads userData.cfg.xml
            ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(userConfiguration.getProperties()).buildServiceRegistry();
            userSessionFactory =  userConfiguration.buildSessionFactory(serviceRegistry);
        }
        return userSessionFactory;
    }


    public static SessionFactory getMTGSessionFactory() {
        if (mtgSessionFactory == null) {
            Configuration mtgConfiguration = new Configuration();
            mtgConfiguration.configure("mtgData.cfg.xml"); //reads mtgData.cfg.xml
            ServiceRegistry serviceRegistry2 = new ServiceRegistryBuilder().applySettings(mtgConfiguration.getProperties()).buildServiceRegistry();
            mtgSessionFactory =  mtgConfiguration.buildSessionFactory(serviceRegistry2);
        }
        return mtgSessionFactory;
    }

}
