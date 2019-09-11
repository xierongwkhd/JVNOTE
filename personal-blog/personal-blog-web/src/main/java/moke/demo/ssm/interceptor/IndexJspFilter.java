package moke.demo.ssm.interceptor;

import moke.demo.ssm.common.PageHelper;
import moke.demo.ssm.controller.BaseController;
import moke.demo.ssm.dao.UserContentMapper;
import moke.demo.ssm.entity.User;
import moke.demo.ssm.entity.UserContent;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.*;
import java.io.IOException;
import java.util.List;

/**
 * Created by MOKE on 2019/2/17.
 */
public class IndexJspFilter extends BaseController implements Filter{
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("===========自定义过滤器==========");
        ServletContext context = request.getServletContext();
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
        UserContentMapper userContentMapper = ctx.getBean(UserContentMapper.class);
        PageHelper.startPage(null, null);//开始分页
        List<UserContent> list = userContentMapper.findByJoin(null);
        PageHelper.Page endPage = PageHelper.endPage();//分页结束
        request.setAttribute("page", endPage );
        User user = getCurrentUser();
        request.setAttribute("user",user);
        chain.doFilter(request, response);
    }

    public void destroy() {

    }
}
