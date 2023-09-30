package com.capstone.datamate.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RequestInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception{
        String reqURI = request.getRequestURI();
        String tenantID = request.getHeader("X-TenantID");
        System.out.println("ReqURI:: " + reqURI + " Search fpr x-tenantId ::" + tenantID);
        if(tenantID == null){
            response.getWriter().write("tenant id not present in the request header");
            response.setStatus(400);
            return false;
        }
        TenantContext.setCurrentTenant(tenantID);
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)throws Exception{
        TenantContext.clear();
    }
}
