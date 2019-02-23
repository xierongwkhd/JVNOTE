package cn.moke.booksCity.category.servlet.admin;

import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
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
@WebServlet(name = "AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryService();

    public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("categoryList",categoryService.findAll());
        return "f:/adminjsps/admin/category/list.jsp";
    }

    public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Category category = CommonUtils.toBean(request.getParameterMap(),Category.class);
        category.setCid(CommonUtils.uuid());
        categoryService.add(category);
        return findAll(request,response);
    }

    public String delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        try {
            categoryService.delete(cid);
            return findAll(request,response);
        }catch (CategoryException e){
            request.setAttribute("msg",e.getMessage());
            return "f:/adminjsps/admin/msg.jsp";
        }
    }

    public String editPre(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        request.setAttribute("category",categoryService.load(cid));
        return "adminjsps/admin/category/mod.jsp";
    }

    public String edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Category category = CommonUtils.toBean(request.getParameterMap(),Category.class);
        categoryService.edit(category);
        return findAll(request,response);
    }
}
