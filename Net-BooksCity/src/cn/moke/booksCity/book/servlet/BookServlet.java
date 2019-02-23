package cn.moke.booksCity.book.servlet;

import cn.itcast.servlet.BaseServlet;
import cn.moke.booksCity.book.service.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by MOKE on 2019/2/2.
 */
@WebServlet(name = "BookServlet")
public class BookServlet extends BaseServlet {
    private BookService bookService = new BookService();

    public String load(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//查询所有分类
        request.setAttribute("book",bookService.load(request.getParameter("bid")));
        return "f:/jsps/book/desc.jsp";
    }

    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//查询所有分类
        request.setAttribute("bookList",bookService.findAll());
        return "f:/jsps/book/list.jsp";
    }

    public String findByCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//按分类查询
        String cid = request.getParameter("cid");
        request.setAttribute("bookList",bookService.findByCategory(cid));
        return "f:/jsps/book/list.jsp";
    }
}
