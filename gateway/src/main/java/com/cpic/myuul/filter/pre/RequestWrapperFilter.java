package com.cpic.myuul.filter.pre;

import com.cpic.myuul.filter.MyuulFilter;
import com.cpic.myuul.http.RequestContext;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.List;

public class RequestWrapperFilter implements MyuulFilter {
    public String filterType() {
        return "pre";
    }

    public int filterOrder() {
        return -1;
    }

    public void run() {

        String rootUrl = "http://localhost:9090";
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String targetUrl = rootUrl + request.getRequestURI();
        RequestEntity<byte[]> requestEntity = null;
        try {
            requestEntity = createRequestEntity(request, targetUrl);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }

        context.setRequestEntity(requestEntity);

    }

    private RequestEntity<byte[]> createRequestEntity(HttpServletRequest request, String targetUrl) throws Throwable {
        String method = request.getMethod();
        HttpMethod httpMethod = HttpMethod.resolve(method);
        MultiValueMap<String, String> headers = createRequestHeader(request);
        byte[] body = createRequestBody(request);
        return new RequestEntity<byte[]>(body, headers, httpMethod, new URI(targetUrl));
    }

    private MultiValueMap<String, String> createRequestHeader(HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        List<String> headerNames = Collections.list(request.getHeaderNames());

        headerNames.forEach(headerName -> headers.add(headerName, request.getHeader(headerName)));
        return headers;
    }

    private byte[] createRequestBody(HttpServletRequest request) throws Throwable {
        InputStream inputStream = request.getInputStream();
        return StreamUtils.copyToByteArray(inputStream);
    }
}
