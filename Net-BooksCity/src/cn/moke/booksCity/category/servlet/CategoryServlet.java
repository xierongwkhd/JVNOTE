package cn.moke.booksCity.category.servlet;

import cn.itcast.servlet.BaseServlet;
import cn.moke.booksCity.category.service.CategoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by MOKE on 2019/2/2.
 */
@WebServlet(name = "CategoryServlet")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryService();

    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {//查询所有分类
        request.setAttribute("categoryList",categoryService.findAll());
        return "f:/jsps/left.jsp";
    }
}
