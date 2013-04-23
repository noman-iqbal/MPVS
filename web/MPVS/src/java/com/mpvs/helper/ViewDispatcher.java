/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.helper;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Ali Mujtaba
 */
public class ViewDispatcher {

    public static void dispatchMyView(HttpServletRequest request, HttpServletResponse response, ServletConfig config) throws ServletException, IOException {
        String viewName = null;
        if (request.getAttribute("viewName") == null) {
            request.setAttribute("viewName", config.getServletName().toLowerCase() + "View.jsp");
         
        }
        viewName = (String) request.getAttribute("viewName");

        String templateName = null;
        if (request.getAttribute("templateName") == null) {
            request.setAttribute("templateName", "template.jsp");
        }
        templateName = (String) request.getAttribute("templateName");

        request.setAttribute("jsppage", viewName);
        request.getRequestDispatcher(templateName).forward(request, response);
    }

    public static String getPagination(HttpServletRequest request, int maxPerPage, long totalCount) {
        String html = "<ul>";
        if (totalCount > maxPerPage) {
            int curr_page = (request.getParameter("page_no") != null) ? Integer.parseInt(request.getParameter("page_no")) : 1;
            String uri = request.getRequestURI() + "?" + ((request.getQueryString() == null) ? "" : request.getQueryString());
            if (!uri.contains("page_no")) {
                if (request.getQueryString() != null) {
                    uri += "&";
                }
                uri += "page_no=1";
            }

            int qou = (int) Math.floor((float) curr_page / 5);
            int start = (qou * 5);
            if (start == 0) {
                start = 1;
            }
            int last = (int) Math.ceil((float) totalCount / maxPerPage);// + 1;
            
            if (curr_page != 1) {
                html += "<li><a href='" + uri.replaceAll("page_no=(\\d*)", "page_no=" + 1) + "'>First</a></li>";
                html += "<li><a href='" + uri.replaceAll("page_no=(\\d*)", "page_no=" + (curr_page - 1)) + "'>Previous</a></li>";
            }

            int i = start;
            while (i != ((qou * 5) + 6) && i != last + 1) {
                html += "<li><a href='" + uri.replaceAll("page_no=(\\d+)", "page_no=" + i) + "' " + ((curr_page == i) ? "class='selected'" : "") + " >" + i + "</a></li>";
                i++;
            }

            if (curr_page != last) {
                html += "<li><a href='" + uri.replaceAll("page_no=(\\d*)", "page_no=" + (curr_page + 1)) + "'>Next</a></li>";
                html += "<li><a href='" + uri.replaceAll("page_no=(\\d*)", "page_no=" + last) + "'>Last</a></li>";
            }

        }
        html += "</ul>";

        return html;
    }
}
