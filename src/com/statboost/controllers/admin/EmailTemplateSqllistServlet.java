package com.statboost.controllers.admin;

import com.statboost.controllers.admin.EmailSqllistServlet;
import com.statboost.util.ServletUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.ResultSet;

@WebServlet("/admin/emailtemplatesqllist")
public class EmailTemplateSqllistServlet extends HttpServlet {
    public static final String ATTR_EMAILS_TEMPLATES = "emailTemplates";
    public static final String SRV_MAP = "/admin/emailtemplatesqllist";
    static Logger logger = Logger.getLogger(EmailSqllistServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String sql = "select * from stt_email_template";
        ResultSet emails = ServletUtil.getResultSetFromSql(sql);
        forwardToSqllist(request, response, emails);
    }

    private static void forwardToSqllist(HttpServletRequest request, HttpServletResponse response, ResultSet emailTemplates)
            throws IOException, ServletException {
        request.setAttribute(ATTR_EMAILS_TEMPLATES, emailTemplates);
        request.getRequestDispatcher("EmailTemplateSqllist.jsp").forward(request, response);
    }
}
