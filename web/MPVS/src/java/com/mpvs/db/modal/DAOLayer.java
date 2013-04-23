/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.db.modal;

import com.mpvs.db.ConnectionManager;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author noman
 */
public class DAOLayer {

    public void saveOrUpdate() throws HibernateException, Exception {
        Session hibObj = ConnectionManager.getSession();
        Transaction tr = hibObj.getTransaction();
        tr.begin();
        hibObj.saveOrUpdate(this);
        tr.commit();
    }

    public void delet() throws HibernateException, Exception {
        Session hibObj = ConnectionManager.getSession();
        Transaction tr = hibObj.getTransaction();
        tr.begin();
        hibObj.delete(this);
        tr.commit();
        hibObj.flush();
    }
}
