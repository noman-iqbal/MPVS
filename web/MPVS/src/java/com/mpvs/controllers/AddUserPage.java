/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.controllers;

import com.mpvs.db.ConnectionManager;
import com.mpvs.db.Programs;
import com.mpvs.db.Subjects;
import com.mpvs.db.Users;
import com.mpvs.db.UsersSubject;
import com.mpvs.helper.Auth;
import com.mpvs.helper.ViewDispatcher;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileItemFactory;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.hibernate.HibernateException;
import org.hibernate.PropertyValueException;
import org.hibernate.Session;

/**
 *
 * @author noman
 */
@WebServlet(name = "AddUserPage", urlPatterns = {"/AddAdmin.jsp", "/AddStudent.jsp", "/AddMember.jsp"})
public class AddUserPage extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!Auth.isLogedIn(request, response)) {
            response.sendRedirect("login.jsp");
            return;
        }
        try {
            Session hob = ConnectionManager.getSession();
            if (request.getRequestURI().endsWith("AddStudent.jsp")) {
                request.setAttribute("typeSt", "student");
            } else if (request.getRequestURI().endsWith("AddMember.jsp")) {
                request.setAttribute("typeSt", "member");
            } else if (request.getRequestURI().endsWith("AddAdmin.jsp")) {
                request.setAttribute("typeSt", "admin");
            }

            if (Auth.isMember(request)) {
                request.setAttribute("subList", getSubjets(Auth.getUser(request).getUsersSubjectCollection()));
            } else {
                request.setAttribute("subList", hob.getNamedQuery("Subjects.findAll").list());
            }
            request.setAttribute("programList", hob.getNamedQuery("Programs.findAll").list());
        } catch (Exception ex) {
            Logger.getLogger(AddUserPage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            request.setAttribute("viewName", "AddUserView.jsp");
            ViewDispatcher.dispatchMyView(request, response, this);
        }
    }

    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String sYear = null;
            String sMonth = null;
            boolean isMultipart = ServletFileUpload.isMultipartContent(request);
            Session hob = ConnectionManager.getSession();
            ArrayList<String> subjectList = new ArrayList<>();
            Users userObj = new Users();
            if (isMultipart) {
                FileItemFactory factory = new DiskFileItemFactory();
                ServletFileUpload upload = new ServletFileUpload(factory);
                List items = null;
//                try {
                    
//                    items = upload.parseRequest(request);
                    
//                } catch (FileUploadException ex) {
//                    Logger.getLogger(AddUserPage.class.getName()).log(Level.SEVERE, null, ex);
//                }
                Iterator it = items.iterator();
                for (Iterator it1 = items.iterator(); it1.hasNext();) {
                    FileItem item = (FileItem) it1.next();
                    String FeildName = item.getFieldName();
                    switch (FeildName) {
                        case "user_id":
                            try {
                                userObj.setId(Integer.parseInt(item.getString()));
                            } catch (Exception ex) {
                            }
                            break;
                        case "uniId":
                            userObj.setUserName(item.getString());
                            break;
                        case "fName":
                            userObj.setFirstName(item.getString());
                            break;
                        case "lName":
                            userObj.setLastName(item.getString());
                            break;
                        case "password":
                            userObj.setPassword(item.getString());
                            break;
                        case "email":
                            userObj.setEmail(item.getString());
                            break;
                        case "program":
                            if (!item.getString().isEmpty()) {
                                int id = Integer.parseInt(item.getString());
                                userObj.setProgram((Programs) hob.get(Programs.class, id));
                            }
                            break;
                        case "session_month":
                            sMonth = item.getString();
                            break;
                        case "session_year":
                            sYear = item.getString();
                            break;
                        case "section":
//                            userObj.setSection(item.getString());
                            break;
                        case "semester":
//                            userObj.setCurrentSemester(item.getString());
                            break;
                        case "subjectList":
                            subjectList.add(item.getString());
                            break;
                        case "type":
                            userObj.setType(item.getString());
                            break;
                        case "status":
                            userObj.setStatus("active");
                            break;
                        case "userType":
                            userObj.setType(item.getString());
                            break;
                        case "image":
                            String filePath = "img/profile_imgs/" + item.getName();
                            if (!item.isFormField() && !item.getName().trim().isEmpty()) {
                                String save_path = this.getServletContext().getRealPath("/") + "img" + File.separator + "profile_imgs" + File.separator + item.getName();
                                File savedFile = new File(save_path);
                                item.write(savedFile);
                            } else {
                                filePath = "img/user-identity-icon.png";
                            }
                            userObj.setProfilePicUrl(filePath);
                            break;
                    }
                }
            }


            userObj.setUpdatedAt(new Date());
            userObj.setCreatedAt(new Date());
            userObj.setStatus("active");
            userObj.setSession(sMonth + "-" + sYear);
            hob.beginTransaction();
            hob.saveOrUpdate(userObj);




            if (userObj.getType().contains("member") || userObj.getType().contains("student")) {
                Collection<UsersSubject> sbList = userObj.getUsersSubjectCollection();
                if (sbList != null) {
                    for (UsersSubject usersSubject : sbList) {
                        hob.delete(usersSubject);
                    }
                }
                for (String item : subjectList) {
                    UsersSubject uSub = new UsersSubject();
                    Subjects sub = (Subjects) hob.get(Subjects.class, Integer.parseInt(item));
                    if (sub != null) {
                        uSub.setSubjectId(sub);
                        uSub.setUsersStudentId(userObj);
                        hob.save(uSub);
                    }
                }
            }

            hob.getTransaction().commit();
            request.setAttribute("error", "Successfull.");

        } catch (PropertyValueException ex) {
            request.setAttribute("error", "Please change Your Uni Id.");
        } catch (HibernateException ex) {
            request.setAttribute("error", "Please change Your Uni Id.");
        } catch (Exception ex) {
            StringWriter errors = new StringWriter();
            ex.printStackTrace(new PrintWriter(errors));
            request.setAttribute("error", errors.toString());
        }
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private Object getSubjets(Collection<UsersSubject> usersSubjectCollection) {
        ArrayList<Subjects> temp = new ArrayList<>();
        for (UsersSubject usersSubject : usersSubjectCollection) {
            temp.add(usersSubject.getSubjectId());
        }
        return temp;
    }
}
