package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.DepartmentMapper;
import org.javaboy.vhr.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    public List<Department> getAllDepartment(){
        return  departmentMapper.getAllDepartmentsWithOutChildren();
    }

    public Integer addDepartment(Department department){
        return departmentMapper.addDep(department);
    }

    public void deleteDepartmentById(Department department){
        departmentMapper.deleteDepById(department);
    }
}
