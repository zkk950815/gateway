package com.cpic.myuul.filter;

public interface MyuulFilter {

    String filterType();

    int filterOrder();

    void run();
}
