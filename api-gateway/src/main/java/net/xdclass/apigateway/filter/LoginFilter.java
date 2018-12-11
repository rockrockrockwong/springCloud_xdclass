package net.xdclass.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;


/**
 * 登录过滤器
 */
@Component("LoginFilter")
public class LoginFilter extends ZuulFilter {

    /**
     * 前置过滤器
     * @return
     */
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    /**
     * 有多个过滤器的时候，通过filterOrder决定执行先后顺序
     * order越小，优先级越高
     * @return
     */
    @Override
    public int filterOrder() {
        return 4;
    }

    /**
     * 是否生效
     * @return
     */
    @Override
    public boolean shouldFilter() {
        //业务逻辑补全
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();

        System.out.println(httpServletRequest.getRequestURI()); // /ps-gw/api/v1/product/list
        System.out.println(httpServletRequest.getRequestURL()); // http://localhost:9000/ps-gw/api/v1/product/list

        if("/ps-gw/api/v1/product/list".equalsIgnoreCase(httpServletRequest.getRequestURI())){
            return false;
        }

        return true;
    }

    /**
     *
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("Intcepted!");

        //JMT
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();

        //token对象
        String token = httpServletRequest.getHeader("token");
        if(StringUtils.isEmpty(token)){
            token = httpServletRequest.getParameter("token");
        }

        //进行业务逻辑运算
        if(StringUtils.isEmpty(token)){
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.FORBIDDEN.value());
        }

        return null;
    }
}
