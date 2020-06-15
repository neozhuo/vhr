package org.javaboy.vhr.controller;

import org.javaboy.vhr.model.Employee;
import org.javaboy.vhr.model.RespPageBean;
import org.javaboy.vhr.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/employee/basic")
public class EmpBasicController {

    @Autowired
    EmployeeService employeeService;

    @GetMapping("/")
    public RespPageBean getEmployeeByPage(@RequestParam(defaultValue = "1")Integer page, @RequestParam(defaultValue = "10")Integer size, Employee employee, Date[] biginDateScope){
        return employeeService.getEmployeeByPage(page,size,employee,biginDateScope);
    }
}
