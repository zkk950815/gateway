package com.cpic.myuul.http;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(name = "myuul",urlPatterns = "/*")
public class MyuulServlet extends HttpServlet {

    private MyuulRunner runner = new MyuulRunner();
    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) {

        runner.init(request,response);

        try {
            runner.preRoute();
            runner.route();
            runner.postRoute();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
        }
    }
}
