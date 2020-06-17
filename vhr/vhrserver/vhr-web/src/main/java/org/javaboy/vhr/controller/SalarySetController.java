package org.javaboy.vhr.controller;

import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.model.RespPageBean;
import org.javaboy.vhr.model.Salary;
import org.javaboy.vhr.service.EmployeeService;
import org.javaboy.vhr.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/salary/sobcfg")
public class SalarySetController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    SalaryService salaryService;




    @GetMapping("/")
    public RespPageBean getEmployeeByPageWithSalary(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size){
        return employeeService.getEmployeeByPageWithSalary(page,size);
    }

    @GetMapping("/salaries")
    public List<Salary> getAllSalaries(){
        return salaryService.getAllSalaries();
    }

    @PutMapping("/")
    public RespBean updateEmployeeSalaryById(@RequestParam Integer eid,@RequestParam Integer sid){
        Integer result=employeeService.updateEmployeeSalaryById(eid,sid);
        if(result==1||result==2){
            return RespBean.ok("更新账套成功");
        }
        return RespBean.error("更新账套失败");
    }
}
