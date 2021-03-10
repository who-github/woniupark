package com.woniuxy.interceptor;

import com.alibaba.druid.support.json.JSONUtils;
import com.woniuxy.dto.Result;
import com.woniuxy.dto.StatusCode;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MyInterceptor implements HandlerInterceptor {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object loginUserId = request.getSession().getAttribute("loginUserId");
        if (ObjectUtils.isEmpty(loginUserId)) {
            response.getWriter().write(JSONUtils.toJSONString(new Result(false, StatusCode.ERROR, "no login")));
            return false;
        } else {
            String sessionId = request.getSession().getId();
            String redisSessionId = stringRedisTemplate.opsForValue().get("loginUser:" + loginUserId);
            if (sessionId.equals(redisSessionId)) {
                return true;
            } else {
                response.getWriter().write(JSONUtils.toJSONString(new Result(false, StatusCode.ERROR, "this user is loged")));
                return false;
            }
        }
    }
}
