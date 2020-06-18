package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.MenuMapper;
import org.javaboy.vhr.mapper.MenuRoleMapper;
import org.javaboy.vhr.model.Hr;
import org.javaboy.vhr.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MenuService {

    @Autowired
    MenuMapper menuMapper;

    @Autowired
    MenuRoleMapper menuRoleMapper;

    public List<Menu> getAllMenuWithRoles(){
      return menuMapper.getAllMenusWithRole();
    }

    public List<Menu> getMenuByHrId(){
        return menuMapper.getMenusByHrId(((Hr)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
    }

    public List<Menu> getAllMenus(){
        return menuMapper.getAllMenus();
    }

    public List<Integer> getMidsByRid(Integer rid){
        return menuMapper.getMidsByRid(rid);
    }

    @Transactional
    public boolean updateMidsByRid(Integer rid,Integer[] mids){
        menuRoleMapper.deleteByRid(rid);
        if(mids==null||mids.length==0){
            return true;
        }
        Integer result = menuRoleMapper.insertRecord(rid, mids);
        return result==mids.length;
    }
}
