package com.statboost.controllers.admin;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Jessica on 10/18/14.
 */
@WebServlet("/admin/mediauploader")
public class MediaUploaderServlet extends HttpServlet {
    static Logger logger = Logger.getLogger(MediaUploaderServlet.class);
    public static final String SRV_MAP = "/admin/mediauploader";
    public static final String PARAM_IMAGE_TO_UPLOAD = "imageToUpload";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException  {
        //todo: will eventually load up the inventory stuff. but for now just forwards to the page
        request.getRequestDispatcher("/MediaUploader.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!ServletFileUpload.isMultipartContent(request))  {
            logger.error("The form must be type multipart/form-data");
        }

        //configure upload settings
        FileItemFactory fileItemFactory = new DiskFileItemFactory();
        ServletFileUpload servletFileUpload = new ServletFileUpload(fileItemFactory);
        List<FileItem> uploadFileItems;

        try  {
            uploadFileItems = servletFileUpload.parseRequest(request);
        } catch (FileUploadException e)  {
            logger.error("Could not handle the uploaded content.");
            return;
        }

        HashMap<String, String> normalFields = getNormalFields(uploadFileItems);
        HashMap<String, FileItem> uploadFields = getUploadedFields(uploadFileItems);

        if(uploadFields != null && uploadFields.size() > 1)  {
            throw new IllegalStateException("More than one upload detected.");
        }

        processUpload(request, uploadFields);


        request.getRequestDispatcher("/MediaUploader.jsp").forward(request, response);

    }

    private void processUpload(HttpServletRequest request, HashMap<String,FileItem> uploadedFiles)  {
        FileItem uploadFileItem = uploadedFiles.get(PARAM_IMAGE_TO_UPLOAD);

        if(uploadFileItem == null)  {
            return;
        }

        String originalFileName = uploadFileItem.getName();
        //todo; hard code for now, later use the uid of the inventory
        String filePart = "/" + 1 + originalFileName.substring(originalFileName.lastIndexOf("."));
        //todo: hard coded for now, later make variable.
        String uploadFilePath = "c:/Users/Jessica/IdeaProjects/statbooster2/web/images" + filePart;
        File uploadFile = new File(uploadFilePath);

        try  {
            uploadFileItem.write(uploadFile);
        }  catch(Exception e)  {
            logger.error("Could not write the file.", e);
            return;
        }

        //todo: set the magic card fields here that have to do with the image
        //todo: save magic card etc

    }

    //gets all of the other fields like text, checkbox, etc
    private HashMap<String, String> getNormalFields(List<FileItem> uploadItems) throws IOException  {
        HashMap<String, String> normalFields = new HashMap<>();

        for(FileItem currentItem : uploadItems)  {
            if(currentItem.isFormField())  {
                normalFields.put(currentItem.getFieldName(), currentItem.getString());
            }
        }

        return normalFields;
    }

    //gets all of the fields that are files ot upload
    private HashMap<String, FileItem> getUploadedFields(List<FileItem> uploadItems)  {
        HashMap<String, FileItem> normalFields = new HashMap<>();
        for (FileItem currentItem : uploadItems)  {
            if(!currentItem.isFormField())  {
                normalFields.put(currentItem.getFieldName(), currentItem);
            }
        }

        return normalFields;
    }
}
