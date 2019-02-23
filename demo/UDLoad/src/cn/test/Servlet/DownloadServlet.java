package cn.test.Servlet;

import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by MOKE on 2019/1/31.
 */
@WebServlet(name = "DownloadServlet")
public class DownloadServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename =  "F:/test.mp3";
        String contentType = this.getServletContext().getMimeType(filename);
        String contentDisposition = "attachment;filename=test.mp3";
        FileInputStream input  = new FileInputStream(filename);
        response.setHeader("Content-Type",contentType);
        response.setHeader("Content-Disposition",contentDisposition);
        ServletOutputStream output = response.getOutputStream();
        IOUtils.copy(input,output);
        input.close();
    }
}
