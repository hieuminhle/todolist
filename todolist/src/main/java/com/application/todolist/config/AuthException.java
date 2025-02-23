package com.application.todolist.config;

import com.google.gson.Gson;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AuthException {
    @ExceptionHandler(value = UnauthorizedException.class)
    public void AuthErrorHandler(HttpServletResponse res, Exception e) throws IOException {
        makeResponse(res,"auth error");
    }

    @ExceptionHandler(value = UnknownAccountException.class)
    public void UnKnowAccountErrorHandler(HttpServletResponse res, Exception e) throws IOException {
        makeResponse(res,"unknown user");
    }

    @ExceptionHandler(value = IncorrectCredentialsException.class)
    public void IncorrectCredentialErrorHandler(HttpServletResponse res, Exception e) throws IOException {
        makeResponse(res,"incorrect credential");
    }

    @ExceptionHandler(value = AuthorizationException.class)
    public void AuthorizationErrorHandler(HttpServletResponse res, Exception e) throws IOException {
       makeResponse(res,"authorization required");
    }

    private void makeResponse(HttpServletResponse res, String msg) throws IOException {
        res.setContentType("application/json; charset=utf-8");
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("msg", msg);
        res.getWriter().write(new Gson().toJson(result));
    }
}
