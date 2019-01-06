package fr.kalioz.eseo.icwebserver.servlets;

import fr.kalioz.eseo.icwebserver.apifetch.EasyHttpClient;
import fr.kalioz.eseo.icwebserver.apifetch.GouvCommunes;
import fr.kalioz.eseo.icwebserver.models.Ville;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class ContextListener implements ServletContextListener {


    private static final Logger logger = Logger.getLogger(EasyHttpClient.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (Ville.getAll().size() > 0) {
            logger.log(Level.FINE, "database already initiliazed - passing init");
        } else {
            logger.log(Level.FINE, "initializing the database... please wait");
            GouvCommunes.fetchAndSaveAllDepartements();
            logger.log(Level.FINE, "database initialized !");
        }
    }
}