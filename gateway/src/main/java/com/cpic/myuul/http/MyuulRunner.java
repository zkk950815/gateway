package com.cpic.myuul.http;

import com.cpic.myuul.filter.MyuulFilter;
import com.cpic.myuul.filter.post.SendResponseFilter;
import com.cpic.myuul.filter.pre.RequestWrapperFilter;
import com.cpic.myuul.filter.route.RoutingFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class MyuulRunner {

    private ConcurrentHashMap<String, List<MyuulFilter>> filtersByType = new ConcurrentHashMap<String, List<MyuulFilter>>() {{
        put("pre", new ArrayList<MyuulFilter>() {{
            add(new RequestWrapperFilter());
        }});
        put("route", new ArrayList<MyuulFilter>() {{
            add(new RoutingFilter());
        }});
        put("post", new ArrayList<MyuulFilter>() {{
            add(new SendResponseFilter());
        }});
    }};

    public void init(HttpServletRequest request, HttpServletResponse response) {
        RequestContext context = RequestContext.getCurrentContext();
        context.setRequest(request);
        context.setResponse(response);
    }

    public void preRoute() throws Throwable {
        runFilter("pre");
    }

    public void route() throws Throwable {
        runFilter("route");
    }

    public void postRoute() throws Throwable {
        runFilter("post");
    }

    private void runFilter(String type) {
        List<MyuulFilter> filters = filtersByType.get(type);
        if (filters != null) {
            filters.forEach(filter -> filter.run());
        }
    }
}
