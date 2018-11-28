package com.cpic.myuul.filter.route;

import com.cpic.myuul.filter.MyuulFilter;
import com.cpic.myuul.http.RequestContext;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class RoutingFilter implements MyuulFilter {
    public String filterType() {
        return "route";
    }

    public int filterOrder() {
        return 0;
    }

    public void run() {
        RequestContext context = RequestContext.getCurrentContext();
        RequestEntity requestEntity = context.getRequestEntity();
        RestTemplate template = new RestTemplate();
        ResponseEntity<byte[]> responseEntity = template.exchange(requestEntity, byte[].class);
        context.setResponseEntity(responseEntity);
    }
}
