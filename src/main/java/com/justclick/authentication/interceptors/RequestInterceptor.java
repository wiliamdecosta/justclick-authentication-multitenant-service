package com.justclick.authentication.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.justclick.authentication.entities.Tenant;
import com.justclick.authentication.repositories.TenantRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RequestInterceptor implements HandlerInterceptor {
	
	private final TenantRepository tenantRepo;

	@Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object object) throws Exception {
        
		String tenantID = getCurrentTenant(request);
		TenantContext.setCurrentTenant(tenantID);
        return true;
    }

    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        TenantContext.clear();
    }
    
    private String getCurrentTenant(HttpServletRequest request) {
    	
    	System.out.println("In preHandle we are Intercepting the Request");
        System.out.println("____________________________________________");
        String requestURI = request.getRequestURI();
        String xTenantID = request.getHeader("X-TenantID");
        
        if(xTenantID == null) {
        	xTenantID = "public";
        }else {
        	xTenantID = "/" + xTenantID.replace("/", "");
        	
        	System.err.println(xTenantID);
        	Tenant tenantEntity = tenantRepo.findByEndpoint(xTenantID);
        	System.err.println(tenantEntity);
        	
        	if(tenantEntity == null) {
        		xTenantID = "public";
        	}else {
        		//id_tenant is like this : 34a7f84f-fc9b-4276-baab-f8b547f36625
        		//schema name is like this : 34a7f84ffc9b4276baabf8b547f36625
        		xTenantID = tenantEntity.getId().toString().replace("-", "");
        	}
        }
        
        System.out.println("RequestURI::" + requestURI +" || Search for X-TenantID  :: " + xTenantID);
        System.out.println("____________________________________________");
        
        return xTenantID;
    }
    
}
