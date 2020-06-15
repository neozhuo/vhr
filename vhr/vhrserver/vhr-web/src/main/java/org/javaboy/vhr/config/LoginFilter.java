package org.javaboy.vhr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.javaboy.vhr.model.Hr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    SessionRegistry sessionRegistry;

    private void checkCode(HttpServletResponse response,String code,String verify_code){
        if(code==null || verify_code==null || "".equals(code) || !verify_code.toLowerCase().equals(code.toLowerCase())){
            throw new AuthenticationServiceException("验证码错误");
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if(!request.getMethod().equals("POST")){
            throw new AuthenticationServiceException("方法错误"+request.getMethod());
        }
        String verify_code = (String) request.getSession().getAttribute("verify_code");
        if(request.getContentType().contains(MediaType.APPLICATION_JSON_VALUE)){
            Map<String,String> loginData= new HashMap<>();
            try {
                loginData = new ObjectMapper().readValue(request.getInputStream(), Map.class);
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                String code = loginData.get("code");
                checkCode(response,code,verify_code);
            }
            String username = loginData.get(getUsernameParameter());
            String password = loginData.get(getPasswordParameter());
            username=username.trim();

            Hr principal=new Hr();
            principal.setUsername(username);
            sessionRegistry.registerNewSession(request.getSession().getId(),principal);

            UsernamePasswordAuthenticationToken token=new UsernamePasswordAuthenticationToken(username,password);
            setDetails(request,token);
            return this.getAuthenticationManager().authenticate(token);
        }else {
            checkCode(response,request.getParameter("code"),verify_code);
            return super.attemptAuthentication(request,response);
        }
    }
}
