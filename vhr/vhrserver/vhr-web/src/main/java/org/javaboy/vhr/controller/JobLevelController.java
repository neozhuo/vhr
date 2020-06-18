package org.javaboy.vhr.controller;

import org.javaboy.vhr.model.JobLevel;
import org.javaboy.vhr.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/basic/joblevel")
public class JobLevelController {

    @Autowired
    JobService jobService;

    @GetMapping("/")
    public List<JobLevel> getAllJobLevels(){
        return jobService.getAllJobLevel();
    }
}
