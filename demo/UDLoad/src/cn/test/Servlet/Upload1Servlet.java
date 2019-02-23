package cn.test.Servlet;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class Upload1Servlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        try {
            List<FileItem> fileItems = sfu.parseRequest(request);
            FileItem fi1 = fileItems.get(0);
            FileItem fi2 = fileItems.get(1);
            System.out.println("普通表单项："+fi1.getFieldName()+"="+fi1.getString());
            System.out.println("文件表单项：");
            System.out.println("Content-Type:"+fi2.getContentType());
            System.out.println("size:"+fi2.getSize());
            System.out.println("filename:"+fi2.getName());

            File destFile = new File("F://截图.jpg");
            fi2.write(destFile);
        } catch (FileUploadException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
