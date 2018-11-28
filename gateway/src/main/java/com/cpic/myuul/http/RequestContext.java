package com.cpic.myuul.http;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;

public class RequestContext extends ConcurrentHashMap<String,Object> {

    protected static Class<? extends RequestContext> contextClass = RequestContext.class;

    protected static final ThreadLocal<? extends RequestContext> THREAD_LOCAL = new ThreadLocal<RequestContext>() {
        @Override
        protected RequestContext initialValue() {
            try {
                return contextClass.newInstance();
            } catch (Throwable e) {
                throw new RuntimeException();
            }
        }
    };

    public static RequestContext getCurrentContext() {
        return THREAD_LOCAL.get();
    }

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) get("request");
    }

    public void setRequest(HttpServletRequest request) {
        set("request",request);
    }

    public HttpServletResponse getResponse() {
        return (HttpServletResponse) get("response");
    }

    public void setResponse(HttpServletResponse response) {
        set("response",response);
    }

    public RequestEntity getRequestEntity() {
        return (RequestEntity) get("requestEntity");
    }

    public void setRequestEntity(RequestEntity requestEntity) {
        set("requestEntity",requestEntity);
    }

    public ResponseEntity getResponseEntity() {
        return (ResponseEntity) get("responseEntity");
    }

    public void setResponseEntity(ResponseEntity responseEntity) {
        set("responseEntity",responseEntity);
    }

    public void set(String key,Object value) {
        if(value!=null) put(key,value);
        else remove(key);
    }

    public void unset() {
        THREAD_LOCAL.remove();
    }

}
