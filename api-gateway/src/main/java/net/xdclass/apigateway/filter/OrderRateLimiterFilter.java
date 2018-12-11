package net.xdclass.apigateway.filter;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.TimeUnit;

public class OrderRateLimiterFilter extends ZuulFilter {

    //谷歌出的开源令牌包，每秒产生100个令牌
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);

    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -4;
    }

    @Override
    public boolean shouldFilter() {
        //业务逻辑补全
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();

        //System.out.println(httpServletRequest.getRequestURI()); // /ps-gw/api/v1/product/list
        //System.out.println(httpServletRequest.getRequestURL()); // http://localhost:9000/ps-gw/api/v1/product/list

        if("/os-gw/api/v1/order/save".equalsIgnoreCase(httpServletRequest.getRequestURI())){
            return true;
        }

        return false;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = requestContext.getRequest();

        if(!RATE_LIMITER.tryAcquire(500, TimeUnit.MILLISECONDS)){
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
        }
        return null;
    }
}
