package com.moke.Demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.moke.Demo.Domain.PUser;
import com.moke.Demo.base.Result;

@Controller
@RequestMapping("/user")
public class UserController {

	
    @RequestMapping("/info")
    @ResponseBody
    public Result<PUser> info(Model model,PUser user) {
        return Result.success(user);
    }
    
}
