package cn.test.Servlet;

import cn.itcast.commons.CommonUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by MOKE on 2019/1/31.
 * 目录哈希打散
 */
@WebServlet(name = "Upload2Servlet")
public class Upload2Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        DiskFileItemFactory factory = new DiskFileItemFactory(20*1024, new File("F:/temp"));//创建工厂，并设置缓存大小和临时目录
        ServletFileUpload sfu = new ServletFileUpload(factory);
        //sfu.setFileSizeMax(100 * 1024);//限制上传文件为100KB
        try {
            List<FileItem> list = sfu.parseRequest(request);
            FileItem fi = list.get(1);

            String root = this.getServletContext().getRealPath("/WEB-INF/files/");
            String filename = fi.getName();

            int index = filename.lastIndexOf("\\");
            if(index!=-1){
                filename = filename.substring(index+1);
            }
            String savename = CommonUtils.uuid()+"_"+filename;
            int hCode = filename.hashCode();//获取哈希值
            String hex = Integer.toHexString(hCode);//转换为16进制
            File dirFile = new File(root,hex.charAt(0)+"/"+hex.charAt(1));//
            dirFile.mkdirs();//创建目录
            File destFile = new File(dirFile,savename);
            fi.write(destFile);

        } catch (FileUploadException e) {
            if(e instanceof FileUploadBase.FileSizeLimitExceededException){
                request.setAttribute("msg","你上传的文件超过了100KB");
                request.getRequestDispatcher("/index2.jsp").forward(request,response);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
