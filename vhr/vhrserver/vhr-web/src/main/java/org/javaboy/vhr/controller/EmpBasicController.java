package org.javaboy.vhr.controller;

import org.javaboy.vhr.model.*;
import org.javaboy.vhr.service.*;
import org.javaboy.vhr.util.POIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


    @PutMapping("/")
    public RespBean updateEmp(@RequestBody Employee employee){
        return employeeService.updateEmp(employee);
    }

    @GetMapping("/maxWorkID")
    public RespBean getMaxWorkId(){
        RespBean result=RespBean.build().setStatus(200)
                .setObj(String.format("%08d",employeeService.maxWorkID()+1));

        return result;
    }

    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee){
        if(employeeService.addEmp(employee)==1){
            return RespBean.ok("添加成功");
        }
        return RespBean.ok("添加失败");
    }


    @DeleteMapping("/{id}")
    public RespBean deleteEmp(@PathVariable Integer id){
        if(employeeService.deleteEmpById(id)==1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }


    @GetMapping("/export")
    public ResponseEntity<byte[]> export(){
        List<Employee> list=(List<Employee>) employeeService.getEmployeeByPage(null,null,new Employee(),null).getData();
        return POIUtils.employee2Excel(list);
    }

    @PostMapping("/import")
    public RespBean addEmps(MultipartFile file){
        List<Employee>list=POIUtils.excel2Employee(file,nationService.getAllNation(),politicService.getAllPoliticsstaus(),departmentService.getAllDepartment(),positionService.getAllPosition(),jobService.getAllJobLevel());
        if(employeeService.addEmps(list)==list.size()){
            return RespBean.ok("上传成功");
        }
        return RespBean.error("上传失败");
    }



}
