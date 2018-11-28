package com.cpic.myuul.filter.post;

import com.cpic.myuul.filter.MyuulFilter;
import com.cpic.myuul.http.RequestContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SendResponseFilter implements MyuulFilter {
    public String filterType() {
        return "post";
    }

    public int filterOrder() {
        return 1000;
    }

    public void run() {
        try {
            addResponseHeaders();
            writeResponse();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private void addResponseHeaders() {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletResponse response = context.getResponse();
        RequestEntity requestEntity = context.getRequestEntity();
        HttpHeaders headers = requestEntity.getHeaders();
        Set<Map.Entry<String, List<String>>> entries = headers.entrySet();
        for (Map.Entry<String, List<String>> entry : entries) {
            String headerName = entry.getKey();
            List<String> values = entry.getValue();
            values.forEach(value -> response.addHeader(headerName, value));
        }
    }

    private void writeResponse() throws Throwable {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletResponse response = context.getResponse();
        if (response.getCharacterEncoding() == null) {
            response.setCharacterEncoding("utf-8");
        }
        ResponseEntity responseEntity = context.getResponseEntity();
        if (responseEntity.hasBody()) {
            byte[] bytes = (byte[]) responseEntity.getBody();
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
        }
    }
}
