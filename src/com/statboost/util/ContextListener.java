package com.statboost.util;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Created by Sam Kerr on 10/9/2014.
 *
 * This class performs initialization code for the web application start and
 * destruction code for when the web application is shutdown or restarted
 */
@WebListener("application context listener")
public class ContextListener implements ServletContextListener {
    Logger logger = Logger.getLogger(ContextListener.class);
    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.setLevel(Level.DEBUG);

        //init database connection pool
        logger.debug("** Initializing Database Connections **");
        HibernateUtil.getDatabaseSessionFactory();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.setLevel(Level.DEBUG);

        //close database connection pool
        logger.debug("** Closing Database Connections **");
        HibernateUtil.closeSessionFactory();
    }
}
