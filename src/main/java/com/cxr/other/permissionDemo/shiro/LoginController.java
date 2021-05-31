package com.cxr.other.permissionDemo.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @PostMapping("/login")
    @ResponseBody
    public String login(@RequestParam("username") String username,@RequestParam("username") String password, Model model) {
        //这玩意就是用来做登陆校验的
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        /*主体subject 需要个 token
        主体相当于当前用户
        subject->ShiroConfig(securityManager)->CustomRealm(extends AuthorizingRealm)
        用户       安全管理器                     操作数据库
        */
        Subject currentUser = SecurityUtils.getSubject();

        try {
            //主体提交登录请求到SecurityManager
            currentUser.login(token);
        } catch (IncorrectCredentialsException ice) {
            model.addAttribute("msg", "密码不正确");
        } catch (UnknownAccountException uae) {
            model.addAttribute("msg", "账号不存在");
        } catch (AuthenticationException ae) {
            model.addAttribute("msg", "状态不正常");
        }
        if (currentUser.isAuthenticated()) {
            System.out.println("认证成功");
            return "success,认证成功";
        } else {
            //注意！
            token.clear();
            return "login";
        }
    }
}
