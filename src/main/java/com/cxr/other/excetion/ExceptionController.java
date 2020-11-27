package com.cxr.other.excetion;

import com.cxr.shiro.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ExceptionController {

    @PostMapping("/h2")
    public String test(User user) {
        if (user.getUserName().equals("123")) {
            //如果这里用try catch捕获 那么将不会执行全局异常处理 只执行try catch的逻辑
            throw new ExcetionSelf("123", 666);
        }
        return "success";
    }

}
