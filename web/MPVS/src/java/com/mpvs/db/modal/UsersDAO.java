/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.db.modal;
import com.google.gson.JsonObject;
import com.mpvs.db.ConnectionManager;
import com.mpvs.db.Users;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
/**
 *
 * @author noman
 */
public class UsersDAO extends com.mpvs.db.Users {
    public static Users login(HttpServletRequest r) throws Exception {
       
        Session hibObject = ConnectionManager.getSession();
        Criteria crit = hibObject.createCriteria(Users.class);
        crit.add(Restrictions.like("userName", r.getParameter("userName")));
        List usersList = crit.list();
        if (usersList.size() == 1) {
            return (Users) usersList.get(0);
        } else {
            return null;
        }
    }

    public void saveOrUpdate() throws Exception {
        Session hibObj = ConnectionManager.getSession();
        Transaction tr = hibObj.beginTransaction();
//        Users obj=new Users();
//        obj.setFirstName(this.getFirstName());
        hibObj.save(this);
        tr.commit();
        
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
