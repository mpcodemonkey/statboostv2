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
    public static final String PARAM_TYPE = "type";
    public static final String PARAM_NAME = "name";
    public static final String ATTR_ERROR = "error";
    public static final String ATTR_INFO = "info";
    private String error = "";
    private String message = "";

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException  {
        //todo: add admin check
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


        String name = normalFields.get(PARAM_NAME);
        String type = normalFields.get(PARAM_TYPE);
        processUpload(request, uploadFields, type, name);


        request.setAttribute(ATTR_ERROR, error);
        request.setAttribute(ATTR_INFO, message);
        request.getRequestDispatcher("/MediaUploader.jsp").forward(request, response);
    }

    private void processUpload(HttpServletRequest request, HashMap<String,FileItem> uploadedFiles, String type, String name)  {
        FileItem uploadFileItem = uploadedFiles.get(PARAM_IMAGE_TO_UPLOAD);

        if(uploadFileItem == null)  {
            return;
        }

        String originalFileName = uploadFileItem.getName();
        if(originalFileName != null && !originalFileName.equals(""))  {
            String filePart = "/" + (name == null? originalFileName : (name + originalFileName.substring(originalFileName.lastIndexOf("."))));

            String magicPath = "/inventory/magic";
            String yugiohPath = "/inventory/yugioh";
            String otherPath = "/inventory/other";

            //default is website path
            String path = "/website";
            if(type != null)  {
                if(type.equals("magic"))  {
                    path = magicPath;
                } else if(type.equals("yugioh"))  {
                    path = yugiohPath;
                } else if(type.equals("other"))  {
                    path = otherPath;
                }
            }

            //used for local
           //String uploadFilePath = "c:/Users/Jessica/IdeaProjects/statbooster2/web/images" + path + filePart;

           //used for prod on digitalocean
           String uploadFilePath = "/home/images/inventory/other" + path + filePart;
            File uploadFile = new File(uploadFilePath);

            try  {
                uploadFileItem.write(uploadFile);
                message = "The file was uploaded successfully to " + uploadFilePath;
            }  catch(Exception e)  {
                logger.error("Could not write the file.", e);
                error = "The file could not be uploaded";
            }
        }
        error = "You must select an image to upload";
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

    //gets all of the fields that are files to upload
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
