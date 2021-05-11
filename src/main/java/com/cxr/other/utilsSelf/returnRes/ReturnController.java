package com.cxr.other.utilsSelf.returnRes;

import com.cxr.other.demo.entriy.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController("/returnRes")
public class ReturnController {

    @RequestMapping("/test")
    private Object test(User user) {
        return null;
    }

}
