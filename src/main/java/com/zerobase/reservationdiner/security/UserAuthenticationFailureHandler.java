package com.zerobase.reservationdiner.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
public class UserAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        log.info("login fail handler");
        String errormessage;

        if(exception instanceof BadCredentialsException||exception instanceof InternalAuthenticationServiceException){
            errormessage="아이디또는 비밀번호가 맞지 않습니다";
        }else if(exception instanceof UsernameNotFoundException){
            errormessage="존재하지 않는 아이디입니다";
        }else{
            errormessage="로그인이 되지 않습니다. 관리자에게 문의해주세요";
        }

        log.info("login fail cause = {}",errormessage);
        log.error("login error ={}",exception.getMessage());
        errormessage= URLEncoder.encode(errormessage,"UTF-8");
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(errormessage);
    }
}
