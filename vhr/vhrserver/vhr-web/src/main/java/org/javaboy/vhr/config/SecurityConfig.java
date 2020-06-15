package org.javaboy.vhr.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.formula.functions.T;
import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.HrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    CustomSecurityMetadataSource customSecurityMetadataSource;

    @Autowired
    CustomAeccessManager customAeccessManager;

    @Autowired
    HrService hrService;

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(hrService);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/js/**","/css/**","/img/**","/fonts/**","/verifyCode","/favicon.ico","/index.html");
    }

    @Bean
   SessionRegistryImpl sessionRegistry(){
        return new SessionRegistryImpl();
   }

   @Bean
   LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter=new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler(((request, response, authentication) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer = response.getWriter();
            Hr hr = (Hr) authentication.getPrincipal();
            hr.setPassword(null);
            RespBean ok=RespBean.ok("登录成功",hr);
            String s=new ObjectMapper().writeValueAsString(ok);
            writer.write(s);
            writer.flush();
            writer.close();
        }));
        loginFilter.setAuthenticationFailureHandler(((request, response, exception) -> {
            response.setContentType("application/json;charset=utf-8");
            PrintWriter writer=response.getWriter();
            RespBean error=RespBean.error(exception.getMessage());
            if(exception instanceof LockedException){
                error.setMsg("账户锁定");
            }else if(exception instanceof CredentialsExpiredException){
                error.setMsg("密码过期");
            }else if(exception instanceof AccountExpiredException){
                error.setMsg("账户过期");
            }else if(exception instanceof DisabledException){
                error.setMsg("账户禁用");
            }else if(exception instanceof  BadCredentialsException){
                error.setMsg("用户名或者密码输入错误");
            }
            writer.write(new ObjectMapper().writeValueAsString(error));
            writer.flush();
            writer.close();
        }));
        loginFilter.setAuthenticationManager(authenticationManagerBean());
        loginFilter.setFilterProcessesUrl("/doLogin");
        ConcurrentSessionControlAuthenticationStrategy sessionStrategyy=new ConcurrentSessionControlAuthenticationStrategy(sessionRegistry());
        sessionStrategyy.setMaximumSessions(1);
        loginFilter.setSessionAuthenticationStrategy(sessionStrategyy);
        return loginFilter;
   }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setAccessDecisionManager(customAeccessManager);
                        object.setSecurityMetadataSource(customSecurityMetadataSource);
                        return object;
                    }
                })
                .and()
                .logout()
                .logoutSuccessHandler(((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    RespBean res=RespBean.ok("注销成功");
                    String s = new ObjectMapper().writeValueAsString(res);
                    out.write(s);
                    out.flush();
                    out.close();
                }))
                .permitAll()
                .and()
                .csrf().disable().exceptionHandling()
                .authenticationEntryPoint(((request, response, authException) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(401);
                    PrintWriter out = response.getWriter();
                    RespBean res=RespBean.error("请求失败");
                    if (authException instanceof InsufficientAuthenticationException) {
                        res.setMsg("请求失败，请联系管理员!");
                    }
                    out.write(new ObjectMapper().writeValueAsString(res));
                    out.flush();
                    out.close();

                }));
        http.addFilterAt(loginFilter(),UsernamePasswordAuthenticationFilter.class);
        http.addFilterAt(new ConcurrentSessionFilter(sessionRegistry(),event -> {
            HttpServletResponse response = event.getResponse();
            response.setContentType("application/json;utf-8");
            response.setStatus(401);
            PrintWriter out = response.getWriter();
            out.write(new ObjectMapper().writeValueAsString(RespBean.error("已在另一客户端登录")));
            out.flush();
            out.close();
        }),ConcurrentSessionFilter.class);

    }
}
