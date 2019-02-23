package cn.moke.booksCity.user.servlet;

import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;
import cn.moke.booksCity.cart.domain.Cart;
import cn.moke.booksCity.user.domain.User;
import cn.moke.booksCity.user.service.UserException;
import cn.moke.booksCity.user.service.UserService;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by MOKE on 2019/2/2.
 */
public class UserServlet extends BaseServlet {
    private UserService userService = new UserService();

    public String quit(HttpServletRequest request, HttpServletResponse response){//退出登录
        request.getSession().invalidate();//将session销毁
        return "r:/index.jsp";
    }

    public String login(HttpServletRequest request, HttpServletResponse response){//登陆
        User form = CommonUtils.toBean(request.getParameterMap(),User.class);
        try {
            User user = userService.login(form);
            request.getSession().setAttribute("session_user",user);

            request.getSession().setAttribute("cart",new Cart());//创建一个购物车

            return "r:/index.jsp";
        } catch (UserException e) {
            request.setAttribute("msg",e.getMessage());
            request.setAttribute("form",form);
            return "f:/jsps/user/login.jsp";
        }

    }
    public String active(HttpServletRequest request, HttpServletResponse response){//邮箱激活
        String code = request.getParameter("code");
        try {
            userService.active(code);
            request.setAttribute("msg","恭喜你激活成功了！请登陆！");
        } catch (UserException e) {
            request.setAttribute("msg",e.getMessage());
        }
        return "f:/jsps/msg.jsp";
    }

    public String regist(HttpServletRequest request, HttpServletResponse response) throws IOException {//注册
        User form = CommonUtils.toBean(request.getParameterMap(),User.class);
        form.setUid(CommonUtils.uuid());
        form.setCode(CommonUtils.uuid()+CommonUtils.uuid());

        Map<String,String> errors = new HashMap<String,String>();
        String username = form.getUsername();
        if(username == null || username.trim().isEmpty()){
            errors.put("username","用户名不能为空！");
        }else if(username.length()<3||username.length()>10){
            errors.put("username","用户名长度必须在3~10之间");
        }
        String password = form.getPassword();
        if(password == null || password.trim().isEmpty()){
            errors.put("password","密码不能为空！");
        }else if(password.length()<3||password.length()>10){
            errors.put("password","密码长度必须在3~10之间");
        }
        String email = form.getEmail();
        if(email == null || email.trim().isEmpty()){
            errors.put("email","Email不能为空！");
        }else if(!email.matches("\\w+@\\w+\\.\\w+")){
            errors.put("email","Email格式不正确！");
        }

        if(errors.size()>0){
            request.setAttribute("errors",errors);
            request.setAttribute("form",form);
            return "f:/jsps/user/regist.jsp";
        }

        try {
            userService.regist(form);
        } catch (UserException e) {
            request.setAttribute("msg",e.getMessage());
            request.setAttribute("form",form);
            return "f:/jsps/msg.jsp";
        }
        //发邮件
        Properties pros = new Properties();
        pros.load(this.getClass().getClassLoader().getResourceAsStream("email_template.properties"));
        String host = pros.getProperty("host");
        String uname = pros.getProperty("uname");
        String pwd = pros.getProperty("pwd");
        String from = pros.getProperty("from");
        String to = form.getEmail();
        String subject = pros.getProperty("subject");
        String content = pros.getProperty("content");
        content = MessageFormat.format(content,form.getCode());//替换配置文件中的占位符

        Session session = MailUtils.createSession(host,uname,pwd);
        Mail mail = new Mail(from,to,subject,content);
        try {
            MailUtils.send(session,mail);
        } catch (MessagingException e) {
            System.out.println(e.toString());
        }
        request.setAttribute("msg","恭喜注册成功！请到邮箱激活");
        return  "f:/jsps/msg.jsp";
    }

}
