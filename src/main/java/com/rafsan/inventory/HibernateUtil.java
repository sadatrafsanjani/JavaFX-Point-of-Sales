package com.rafsan.inventory;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    public static boolean setSessionFactory() {
        try {
            sessionFactory = new Configuration()
                    .configure()
                    .buildSessionFactory();
        } catch (HibernateException ex) {
            return false;
            
        }
        
        return true;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
