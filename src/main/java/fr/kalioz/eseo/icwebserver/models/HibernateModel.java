package fr.kalioz.eseo.icwebserver.models;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.logging.Level;
import java.util.logging.Logger;

public class HibernateModel {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static Logger logger = Logger.getLogger(HibernateModel.class.getName());

    private HibernateModel() {
    }

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the ServiceRegistry from hibernate.cfg.xml
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();

            // Create a metadata sources using the specified service registry.
            return new MetadataSources(serviceRegistry).buildMetadata().buildSessionFactory();
        } catch (Exception ex) {
            logger.log(Level.FINE, "Initial SessionFactory creation failed. {0}", ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

}
