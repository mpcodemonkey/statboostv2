package com.statboost.controllers.admin;

import com.statboost.util.ServletUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/webpageeditor")
public class WebpageEditorServlet {
    public static final String SRV_MAP = "/webpageeditor";
    public static final String PARAM_WEBPAGE_UID = "webpageUid";
    public static final String PARAM_NAME = "name";
    public static final String ATTR_WEBPAGE = "webpage";
    public static final String ATTR_ERRORS = "errors";
    public static final String ATTR_INFO = "info";
    public static final String PARAM_CODE = "code";
    //doesn't follow standard so that it works with tinymce
    public static final String PARAM_BODY = "area";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Webpage webpage = null;
        if(request.getParameter(PARAM_WEBPAGE_UID) == null || request.getParameter(PARAM_WEBPAGE_UID).equals(""))  {
            webpage = new Webpage();
        } else  {
            webpage = (Webpage) ServletUtil.getObjectFromSql("select * from stt_webpage where " +
                    "wbp_uid = " + request.getParameter(PARAM_WEBPAGE_UID));
        }

        forwardToEditor(request, response, webpage, null, "");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Webpage webpage = null;
        if(request.getParameter(PARAM_WEBPAGE_UID) == null || request.getParameter(PARAM_WEBPAGE_UID).equals(""))  {
            webpage = new Webpage();
        } else  {
            webpage = (Webpage) ServletUtil.getObjectFromSql("select * from stt_webpage where " +
                    "wbp_uid = " + request.getParameter(PARAM_WEBPAGE_UID));
        }

        webpage.setBody(request.getParameter(PARAM_BODY));
        webpage.setCode(request.getParameter(PARAM_CODE));
        webpage.setName(request.getParameter(PARAM_NAME));

        ArrayList<String> errors = new ArrayList<String>();
        if(webpage.getBody() == null || webpage.getBody().trim().equals(""))  {
            errors.add("You must enter content of the webpage.");
        }

        if(webpage.getName() == null || webpage.getName().trim().equals(""))  {
            errors.add("You must enter a name for the webpage.");
        }

        if(errors.size() == 0)  {
            //no errors so save the object and forward back to the editor with a save message
            ServletUtil.saveObject(webpage);
            forwardToEditor(request, response, webpage, errors, "The record was save successfully.");
        }  else  {
            forwardToEditor(request, response, webpage, errors, "");
        }
    }


    private static void forwardToEditor(HttpServletRequest request, HttpServletResponse response, Webpage webpage,
                                        ArrayList<String> errors, String info)
            throws IOException, ServletException {
        request.setAttribute(ATTR_WEBPAGE, webpage);
        request.setAttribute(ATTR_ERRORS, errors);
        request.setAttribute(ATTR_INFO, info);
        request.getRequestDispatcher("WebpageEditor.jsp").forward(request, response);
    }

    public static String getEditUrl(int webpageUid)  {
        return SRV_MAP + "?" + PARAM_WEBPAGE_UID + "=" + webpageUid;
    }
}
