package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.EmployeeMapper;
import org.javaboy.vhr.model.*;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.logging.SimpleFormatter;

@Service
public class EmployeeService {

    @Autowired
    EmployeeMapper employeeMapper;

    @Autowired
    MailSendLogService mailSendLogService;

    @Autowired
    RabbitTemplate rabbitTemplate;

    SimpleDateFormat yearFormat=new SimpleDateFormat("yyyy");
    SimpleDateFormat mouthFormat=new SimpleDateFormat("MM");
    DecimalFormat decimalFormat=new DecimalFormat("##.00");



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

    public RespBean updateEmp(Employee employee){
        if(employeeMapper.updateByPrimaryKeySelective(employee)==1){
            return RespBean.ok("更新成功");
        }

        return RespBean.error("更新失败");
    }

    public Integer maxWorkID(){
        return employeeMapper.maxWorkID();
    }

    public Integer addEmp(Employee employee){
        Date beginContract = employee.getBeginContract();
        Date endContract = employee.getEndContract();
        Double month=(Double.parseDouble(yearFormat.format(endContract))-Double.parseDouble(yearFormat.format(beginContract)))*12+(Double.parseDouble(mouthFormat.format(endContract))-Double.parseDouble(mouthFormat.format(beginContract)));
        employee.setContractTerm(Double.parseDouble(decimalFormat.format(month/12)));
        int result=employeeMapper.insertSelective(employee);
        if(result==1){
            Employee emp=employeeMapper.getEmployeeById(employee.getId());

            String msgId = UUID.randomUUID().toString();
            MailSendLog mailSendLog=new MailSendLog();
            mailSendLog.setMsgId(msgId);
            mailSendLog.setCreateTime(new Date());
            mailSendLog.setExchange(MailConstants.MAIL_EXCHANGE_NAME);
            mailSendLog.setRouteKey(MailConstants.MAIL_ROUTING_KEY_NAME);
            mailSendLog.setEmpId(emp.getId());
            mailSendLog.setTryTime(new Date(System.currentTimeMillis()+1000*60*MailConstants.MSG_TIMEOUT));
            mailSendLogService.insert(mailSendLog);

            rabbitTemplate.convertAndSend(MailConstants.MAIL_EXCHANGE_NAME,MailConstants.MAIL_ROUTING_KEY_NAME,emp,new CorrelationData(msgId));



        }
        return result;
    }

    public Integer deleteEmpById(Integer id){
        return employeeMapper.deleteByPrimaryKey(id);
    }

    public Integer addEmps(List<Employee> list){
        return employeeMapper.addEmps(list);
    }


}
