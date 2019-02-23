package cn.moke.booksCity.user.filter;

import cn.moke.booksCity.user.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by MOKE on 2019/2/4.
 */
@WebFilter(filterName = "LoginFilter")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request  = (HttpServletRequest)req;
        User user = (User)request.getSession().getAttribute("session_user");
        if(user!=null){
            chain.doFilter(req, resp);
        }else {
            request.setAttribute("msg","你还未登陆！");
            request.getRequestDispatcher("jsps/user/login.jsp").forward(request,resp);
        }
    }
    public void init(FilterConfig config) throws ServletException {
    }
}
