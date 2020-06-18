package org.javaboy.vhr.controller;

import org.javaboy.vhr.model.Department;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    public List<Department> getAllDepartments(){
        return departmentService.getAllDepartment();
    }

    @PostMapping("/")
    public RespBean addDepartment(@RequestBody Department department){
        departmentService.addDepartment(department);
        if(department.getResult()==1){
            return RespBean.ok("添加部门成功");
        }
        return RespBean.error("添加部门失败");

    }

    @DeleteMapping("/{id}")
    public RespBean deleteDep(@PathVariable Integer id){
       Department department=new Department();
       department.setId(id);
       departmentService.deleteDepartmentById(department);
       if(department.getResult()==-2){
           return RespBean.error("删除失败，有子部门");
       }else if(department.getResult()==-1){
           return RespBean.error("删除失败，有员工");
       }else if(department.getResult()==1){
           return RespBean.ok("删除成功");
       }
       return RespBean.error("删除失败");
    }
}
