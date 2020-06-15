package org.javaboy.vhr.config;

import org.javaboy.vhr.model.Menu;
import org.javaboy.vhr.model.Role;
import org.javaboy.vhr.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    MenuService menuService;

    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        String requestUrl=((FilterInvocation)object).getRequestUrl();
        List<Menu> menus=menuService.getAllMenuWithRoles();
        for (Menu menu : menus) {
            if(menu.getUrl().equals(requestUrl)){
                List<Role> roles=menu.getRoles();
                String[] str=new String[roles.size()];
                for (int i = 0; i < roles.size(); i++) {
                    str[i]=roles.get(i).getName();
                }
                return SecurityConfig.createList(str);
            }
        }

        return SecurityConfig.createList("ROLE_LOGIN");

    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }
}
