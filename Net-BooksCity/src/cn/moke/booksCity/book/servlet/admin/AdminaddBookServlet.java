package cn.moke.booksCity.book.servlet.admin;

import cn.itcast.commons.CommonUtils;
import cn.moke.booksCity.book.domain.Book;
import cn.moke.booksCity.book.service.BookService;
import cn.moke.booksCity.category.domain.Category;
import cn.moke.booksCity.category.service.CategoryService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MOKE on 2019/2/3.
 * 上传专用servlet，不能继承BaseServlet?
 */
@WebServlet(name = "AdminaddBookServlet")
public class AdminaddBookServlet extends HttpServlet {
    private BookService bookService = new BookService();
    private CategoryService categoryService = new CategoryService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");

        DiskFileItemFactory factory = new DiskFileItemFactory(20 * 1024,new File("f:/temp"));
        ServletFileUpload sfu = new ServletFileUpload(factory);
        sfu.setFileSizeMax(20 * 1024);//设置单个文件大小为15kb
        try {
            List<FileItem> fileItemList = sfu.parseRequest(request);
            Map<String,String> map = new HashMap<String,String>();
            for(FileItem fileItem : fileItemList) {
                if(fileItem.isFormField()) {
                    map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
                }
            }

            Book book = CommonUtils.toBean(map, Book.class);
            book.setBid(CommonUtils.uuid());
            Category category = CommonUtils.toBean(map, Category.class);
            book.setCategory(category);

            String savepath = this.getServletContext().getRealPath("/book_img");
            String filename = CommonUtils.uuid() + "_" + fileItemList.get(1).getName();

            File destFile = new File(savepath, filename);
            fileItemList.get(1).write(destFile);
            book.setImage("book_img/" + filename);

            //校验文件的扩展名
            if(filename.toLowerCase().endsWith("jpg")){
                request.setAttribute("msg","你上传的文件不是JPG类型");
                request.setAttribute("categoryList", categoryService.findAll());
                request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request,response);
            }

            bookService.add(book);

            //校验图片的尺寸
            Image image = new ImageIcon(destFile.getAbsolutePath()).getImage();
            if(image.getWidth(null)>200||image.getHeight(null)>200){
                destFile.delete();
                request.setAttribute("msg", "您上传的图片尺寸超出了200 * 200！");
                request.setAttribute("categoryList", categoryService.findAll());
                request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
                        .forward(request, response);
                return;
            }

            request.getRequestDispatcher("/AdminBookServlet?method=findAll")
                    .forward(request, response);
        }  catch (Exception e) {
            if(e instanceof FileUploadBase.FileSizeLimitExceededException){
                request.setAttribute("msg","你上传的文件超出了15KB");
                request.setAttribute("categoryList", categoryService.findAll());
                request.getRequestDispatcher("/adminjsps/admin/book/add.jsp").forward(request,response);
            }
        }

    }

}
