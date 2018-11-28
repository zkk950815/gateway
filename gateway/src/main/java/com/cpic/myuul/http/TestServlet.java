package com.cpic.myuul.http;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TestServlet {

    private MyuulRunner runner = new MyuulRunner();
    @RequestMapping("/*")
    public void test(HttpServletRequest request, HttpServletResponse response) {
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
