package org.example.interceptor;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.example.enums.BizCodeEnum;
import org.example.model.BaseUser;
import org.example.utils.CommonUtil;
import org.example.utils.JWTUtil;
import org.example.utils.JsonData;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;

/**
 * 拦截器
 */

@Slf4j
@Component
public class TokenCheckInterceptor implements HandlerInterceptor {

    //    public static ThreadLocal<LoginUser> tl = ThreadLocal.withInitial();
    //每一次拦截都会对ThreadLocal进行set操作，但是ThreadLocal已经做了一些优化，如果对象相同的话，就不会进行set
    public static final ThreadLocal<BaseUser> tl = new ThreadLocal<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("进入到拦截器了");
        String accessToken = request.getHeader("token");
        if (accessToken == null) {
            accessToken = request.getParameter("token");
        }

        if (StringUtils.isNoneBlank(accessToken)) {
            Claims claims = JWTUtil.checkJWT(accessToken);
            if (claims == null) {
                //未登录 就向前端发送未登录的响应，通过Response
                CommonUtil.sendJsonMessage(response, JsonData.buildResult(BizCodeEnum.ACCOUNT_UNLOGIN_ERROR));
                return false;
            }
            //成功的获取到了JWT的数据
            String userId = (String) claims.get("id");
            String mail = (String) claims.get("mail");
            String name = (String) claims.get("name");

            BaseUser loginUser = new BaseUser();
            loginUser.setId(userId);
            loginUser.setName(name);
            loginUser.setMail(mail);
            //request.setAttribute("loginUser",loginUser); //通过这种方式保存到下一个页面中
            //使用ThreadLocal保存登录用户信息
            tl.set(loginUser);

            return true;

        }
        CommonUtil.sendJsonMessage(response, JsonData.buildResult(BizCodeEnum.ACCOUNT_UNLOGIN_ERROR));
        return false;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 断开连接之后
        tl.remove();
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
