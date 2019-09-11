package moke.demo.ssm.security.account;

import moke.demo.ssm.entity.Role;
import moke.demo.ssm.entity.User;
import moke.demo.ssm.service.RoleService;
import moke.demo.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class AccountDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userService.findByEmail(email);
        if(user == null){
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        List<Role> roles = roleService.findByUid(user.getId());
        user.setRoles(roles);

        return user;
    }
}
