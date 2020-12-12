package com.cxr.other.filterTest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class fTest implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Filter:init");
        ServletContext servletContext = filterConfig.getServletContext();
        String name = filterConfig.getInitParameter("name");
        String filterName = filterConfig.getFilterName();
        filterConfig.getServletContext().setAttribute("初始化成功","初始化成功");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Filter:doFilter");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String name = request.getParameter("name");
        String name1 = request.getParameter("name");
        String name2 = request.getParameter("name");
        request.getSession().setAttribute("abc","aaaaa");
        request.setAttribute("s56","过滤成功");
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        System.out.println("destory");
    }
}
