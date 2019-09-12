package moke.demo.ssm.security.phone;

import moke.demo.ssm.entity.Role;
import moke.demo.ssm.entity.User;
import moke.demo.ssm.service.RoleService;
import moke.demo.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public class PhoneUserDetailsService implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    public UserDetails loadUserByUsername(String phone) throws PhoneNotFoundException {
        User user = userService.findByPhone(phone);
        if(user == null){
            throw new PhoneNotFoundException("手机号码错误");
        }
        List<Role> roles = roleService.findByUid(user.getId());
        user.setRoles(roles);
        return user;
    }
}