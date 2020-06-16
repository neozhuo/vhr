package org.javaboy.vhr.service;


import org.javaboy.vhr.mapper.MailSendLogMapper;
import org.javaboy.vhr.model.MailSendLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailSendLogService {

    @Autowired
    MailSendLogMapper mailSendLogMapper;

    public Integer insert(MailSendLog mailSendLog){
        return mailSendLogMapper.insert(mailSendLog);
    }
}
