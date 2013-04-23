/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.db;

import com.google.android.gcm.server.Sender;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;


/**
 *
 * @author noman
 */
public class ConnectionManager {
      private static SessionFactory session;

    public static SessionFactory getConnection() {
        session = new AnnotationConfiguration()
                .configure() // configures settings from hibernate.cfg.xml
                .buildSessionFactory();
        return session;
    }

    public static Session getSession()throws Exception{
        if (session == null) {
            session = getConnection();
        }
        return session.openSession();
    }

    public static void shutDown() {
        if (session == null) {
            session = getConnection();
        }
        session.close();
    }
      public static Sender getSender() {
        return new Sender("AIzaSyBGDQ0lMQb-oOvZ1OlfUA7q2bBGjIA-VVg");
    }
    
}
