package org.javaboy.vhr.service;

import org.javaboy.vhr.mapper.PoliticsstatusMapper;
import org.javaboy.vhr.model.Politicsstatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PoliticService {

    @Autowired
    PoliticsstatusMapper politicsstatusMapper;


    public List<Politicsstatus> getAllPoliticsstaus(){
        return politicsstatusMapper.getAllPoliticsstatus();
    }
}
