package com.cmplete.reggiedemo.filter;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.cmplete.reggiedemo.common.BaseContext;
import com.cmplete.reggiedemo.common.R;
import com.cmplete.reggiedemo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.handler.WebRequestHandlerInterceptorAdapter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/*")
@Slf4j
/*采用过滤器*/
public class LoginCheckFilter implements HandlerInterceptor,Filter {
    public static final AntPathMatcher PATH_MATCHER= new AntPathMatcher();
    /*登入拦截*/
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest=(HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse=(HttpServletResponse) servletResponse;
        String requestURI=httpServletRequest.getRequestURI();
        /*放行的目录*/
        String[] uris=new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login"
        };
        log.info("拦截到请求:{}",httpServletRequest.getRequestURI());
        /*判断请求是否需要处理*/
        Boolean check = check(uris, requestURI);
        if (check){

            log.info("本次{}不需要处理",requestURI);
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        Long employee =(Long) httpServletRequest.getSession().getAttribute("employee");
        log.info("获取的id为{}",employee);
        System.out.println("id:"+employee);
        if (employee!=null){
            long id = Thread.currentThread().getId();
            log.info("线程id：{}",id);
            log.info("用户已登入,用户id{}",employee);
            BaseContext.serCurrentId(employee);
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        if (httpServletRequest.getSession().getAttribute("user")!=null){
            User user =(User) httpServletRequest.getSession().getAttribute("user");
            log.info("用户id为:{}",user.getId());

            BaseContext.serCurrentId(user.getId());
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return;
        }
        log.info("用户未登入");
        httpServletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;
    }
    public Boolean check(String[] uris,String requestURI){
        for (String uri:uris){
            boolean match = PATH_MATCHER.match(uri, requestURI);
            if (match){
                return true;
            }
        }
        return false;
    }
//    未被实现的方法 可能是因为限制的太死了
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (request.getSession().getAttribute("employee")!=null){
//            log.info("登入成功");
//            return true;
//        }else {
//            log.info("登入失败");
//            response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
//            return false;
//        }
//    }
}
