package com.cxr.other.permissionDemo.byRBAC;

import com.cxr.other.demo.entriy.User;
import com.cxr.other.utilsSelf.ThreadLocalUtil;
import org.omg.PortableInterceptor.Interceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.messaging.handler.HandlerMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Set;

/**
 *
 */
public class SecurityInterceptor extends HandlerInterceptorAdapter {

    private HttpSession session;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 不拦截跨域请求相关
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 如果类或方法上没有加@RequiredPermission，直接放行
        if (isLoginFree(handler)) {
            return true;
        }

        // 登录校验，session里如果没有用户信息，就抛异常给globalExceptionHandler提示“需要登录”
        User user = handleLogin(request, response);
        /**
         * 存储用户信息：
         *  "thead_1(当前线程this)" :{
         *      "user_info(写死了)":{
         *           "id":123,
         *           "userName":"zhangsan" //这个ThreadLocal里面存储的是个map
         *      }
         *  }
         */
        ThreadLocalUtil.put(WebConstant.USER_INFO, user);

        // 权限校验，校验不通过就抛异常，交给全局异常处理
        checkPermission(user, handler);

        /**
         * 放行到Controller
         * 如果是 implements HandlerInterceptor 那么直接返回true即放行
         *
         * 有时候我们可能只需要实现三个回调方法中的某一个，如果实现HandlerInterceptor接口的话，三个方法必须实现，不管你需不需要，
         * 此时spring提供了一个HandlerInterceptorAdapter适配器（种适配器设计模式的实现），允许我们只实现需要的回调方法。
         * todo:适配器模式
         */

        return super.preHandle(request, response, handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 及时移除，避免ThreadLocal内存泄漏
        ThreadLocalUtil.remove(WebConstant.USER_INFO);
        super.afterCompletion(request, response, handler, ex);
    }

    /**
     * 接口是否免登录（支持Controller上添加@LoginRequired）
     *
     * @param handler
     * @return
     */
    private boolean isLoginFree(Object handler) {

        // 判断是否支持免登录
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;

            // 类上是否有@RequiresPermission
            Class<?> controllerClazz = handlerMethod.getBeanType();
            RequiresPermission requiresPermission = AnnotationUtils.findAnnotation(controllerClazz, RequiresPermission.class);

            return requiresPermission == null;
        }

        return true;
    }

    /**
     * 登录校验 就是看登录没登录的
     *
     * @param request
     * @param response
     * @return
     */
    private User handleLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        User currentUser = (User) session.getAttribute(WebConstant.CURRENT_USER_IN_SESSION);
        if (currentUser == null) {
            // 抛异常，请先登录
            throw new Exception("这里可以是个枚举");
        }
        return currentUser;
    }

    /**
     * 权限校验
     */
    private void checkPermission(User user, Object handler) throws Exception {
        // 如果类和当前方法上都没有加@RequiresPermission，说明不需要权限校验，直接放行
        if (isPermissionFree(handler)) {
            return;
        }

        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Class<?> controllerClazz = handlerMethod.getBeanType();

            // 代码走到这，已经很明确，当前方法需要权限才能访问，那么当前用户有没有权限呢？
            Set<String> userPermissionMethods = (Set<String>) session.getAttribute(WebConstant.USER_PERMISSIONS);//@PostMapping("/login")登录接口塞进去的
            String currentRequestMethod = controllerClazz.getName() + "#" + method.getName();
            /**
             * 这就是核心⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️⚠️：
             * 1.把存在session里面的用户的权限拿出来
             * 2.拼接当前访问的权限定类名#方法名
             * 3.判断1里面是不是包含2  包含 则通过
             *
             * 即：注解是个空注解，打上注解就说明 这个接口需要权限验证。
             * 用户的权限哪来的呢？通过t_user->t_user_role->t_role->t_role_permission->t_permission这样关联查询出来的
             *
             * t_role_permission就手动设置吧。。
             *
             * 那么还有个问题：
             * permission权限表里面的权限应该长什么样？也就是
             * session里面的权限是谁存进去的？换句话说
             * 数据库里面关于当前角色的权限是怎么存进去的呢 咱们暴露这个add接口咋写的！
             *
             */
            if (userPermissionMethods.contains(currentRequestMethod)) {
                return;
            }

            // 当前访问的方法需要权限，但是当前用户不具备该权限
            throw new Exception("这里可以是个枚举");
        }
    }

    /**
     * 是否需要权限校验
     *
     * @param handler
     * @return
     */
    private boolean isPermissionFree(Object handler) {
        // 判断是否需要权限认证
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Class<?> controllerClazz = handlerMethod.getBeanType();
            Method method = handlerMethod.getMethod();
            RequiresPermission controllerPermission = AnnotationUtils.getAnnotation(controllerClazz, RequiresPermission.class);
            RequiresPermission methodPermission = AnnotationUtils.getAnnotation(method, RequiresPermission.class);
            // 没有加@RequiresPermission，不需要权限认证
            return controllerPermission == null && methodPermission == null;
        }

        return true;
    }

}
