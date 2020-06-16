package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.EmployeeMapper;
import org.javaboy.vhr.model.Employee;
import org.javaboy.vhr.model.Politicsstatus;
import org.javaboy.vhr.model.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;



    public RespPageBean getEmployeeByPage(Integer page, Integer size, Employee employee, Date[] biginDateScoupe){
        if(page!=null&&size!=null){
            page=(page-1)*size;
        }
        List<Employee> data = employeeMapper.getEmployeeByPage(page, size, employee, biginDateScoupe);
        Long total = employeeMapper.getTotal(employee, biginDateScoupe);
        RespPageBean rpb=new RespPageBean();
        rpb.setData(data);
        rpb.setTotal(total);
        return rpb;
    }

}
