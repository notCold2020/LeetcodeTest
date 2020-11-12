package com.cxr.other.excetion;

import com.cxr.shiro.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class ExceptionController {

    @PostMapping("/h2")
    public String test(User user) {
        if (user.getUserName().equals("123")) {
            throw new ExcetionSelf("123", 666);
        }
        return "success";
    }

}
