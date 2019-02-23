package cn.moke.booksCity.user.service;

import cn.moke.booksCity.user.dao.UserDao;
import cn.moke.booksCity.user.domain.User;


/**
 * Created by MOKE on 2019/2/2.
 * 业务层
 */
public class UserService {
    private UserDao userDao = new UserDao();

    public void regist(User form) throws UserException {//注册功能
        User user = userDao.findByUsername(form.getUsername());
        if(user!=null)throw new UserException("用户名已被注册");

        user = userDao.findByEmail(form.getEmail());
        if(user!=null)throw new UserException("Email已被注册");

        userDao.add(form);
    }

    public void active(String code) throws UserException {//激活功能
        User user = userDao.findByCode(code);
        if(user==null) throw new UserException("激活码无效");
        if(user.isState()) throw new UserException("您已经激活过了，不要重复激活");
        userDao.updateState(user.getUid(),true);
    }

    public User login(User form) throws UserException {//登陆功能
        User user = userDao.findByUsername(form.getUsername());
        if(user == null) throw new UserException("用户名不存在！");
        if(!user.getPassword().equals(form.getPassword())) throw new UserException("密码错误！");
        if(!user.isState()) throw new UserException("尚未激活");
        return user;
    }
}
