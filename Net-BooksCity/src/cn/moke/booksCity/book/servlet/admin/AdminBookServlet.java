package cn.moke.booksCity.book.servlet.admin;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import cn.moke.booksCity.book.domain.Book;
import cn.moke.booksCity.book.service.BookService;
import cn.moke.booksCity.category.domain.Category;
import cn.moke.booksCity.category.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by MOKE on 2019/2/3.
 */
@WebServlet(name = "AdminBookServlet")
public class AdminBookServlet extends BaseServlet {
    private BookService bookService = new BookService();
    private CategoryService categoryService = new CategoryService();

    public String addPre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("categoryList",categoryService.findAll());
        return "f:/adminjsps/admin/book/add.jsp";
    }

    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("bookList",bookService.findAll());
        return "/adminjsps/admin/book/list.jsp";
    }

    public String load(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bid = request.getParameter("bid");
        request.setAttribute("categoryList",categoryService.findAll());
        request.setAttribute("book",bookService.load(bid));
        return "f:/adminjsps/admin/book/desc.jsp";
    }

    public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String bid = request.getParameter("bid");
        bookService.delete(bid);
        return findAll(request,response);
    }

    public String edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
        Category category = CommonUtils.toBean(request.getParameterMap(),Category.class);
        book.setCategory(category);
        bookService.edit(book);
        return findAll(request,response);
    }
}
