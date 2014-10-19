package com.statboost.controllers.admin;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Jessica on 10/18/14.
 */
@WebServlet("/admin/mediaUploader")
public class MediaUploaderServlet {
    public static final String SRV_MAP = "/admin/mediaUploader";
    public static final String PARAM_IMAGE_TO_UPLOAD = "imageToUpload";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
