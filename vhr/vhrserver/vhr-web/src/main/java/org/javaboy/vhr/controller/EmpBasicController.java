package org.javaboy.vhr.controller;

import org.javaboy.vhr.model.*;
import org.javaboy.vhr.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/employee/basic")
public class EmpBasicController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    PoliticService politicService;

    @Autowired
    NationService nationService;

    @Autowired
    JobService jobService;

    @Autowired
    PositionService positionService;

    @Autowired
    DepartmentService departmentService;

    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1")Integer page, @RequestParam(defaultValue = "10")Integer size, Employee employee, Date[] biginDateScope){
        return employeeService.getEmployeeByPage(page,size,employee,biginDateScope);
    }

    @GetMapping("/politicsstatus")
    public List<Politicsstatus> getAllPoliticsstatus(){
        return politicService.getAllPoliticsstaus();
    }

    @GetMapping("/nations")
    public List<Nation> getAllNations(){
        return nationService.getAllNation();
    }

    @GetMapping("/joblevels")
    public List<JobLevel> getAllJobLevel(){
        return jobService.getAllJobLevel();
    }

    @GetMapping("/positions")
    public List<Position> getAllPosition(){
        return positionService.getAllPosition();
    }

    @GetMapping("/deps")
    public List<Department> getAllDepartment(){
        return departmentService.getAllDepartment();
    }



}
