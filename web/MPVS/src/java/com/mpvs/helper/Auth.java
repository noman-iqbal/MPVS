/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.helper;

import com.mpvs.db.Users;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author noman
 */
public class Auth {

    public static boolean isLogedIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(true);
        if (session.getAttribute("userInfo") == null) {
            return false;
        }
        return true;
    }

    public static boolean isAdmin(HttpServletRequest request) {
        Users userObj = (Users) request.getSession().getAttribute("userInfo");
        if (userObj != null && userObj.getType().equals("admin")) {
            return true;
        }
        return false;
    }

    public static boolean isSuperAdmin(HttpServletRequest request) {
        Users userObj = (Users) request.getSession().getAttribute("userInfo");
        if (userObj != null && userObj.getType().equals("super_admin")) {
            return true;
        }
        return false;
    }

    public static boolean isStudent(HttpServletRequest request) {
        Users userObj = (Users) request.getSession().getAttribute("userInfo");
        if (userObj != null && userObj.getType().equals("student")) {
            return true;
        }
        return false;

    }

    public static boolean isMember(HttpServletRequest request) {
        Users userObj = (Users) request.getSession().getAttribute("userInfo");
        if (userObj != null && userObj.getType().equals("member")) {
            return true;
        }
        return false;

    }

    public static Users getUser(HttpServletRequest request) {
        Users userObj = (Users) request.getSession().getAttribute("userInfo");
        return userObj;

    }
}
