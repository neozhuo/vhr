package org.javaboy.vhr.controller;

import org.javaboy.vhr.model.JobLevel;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/")
    public RespBean addJobLevel(@RequestBody JobLevel jobLevel){
        if(jobService.addJobLevel(jobLevel)==1){
            return RespBean.ok("添加职称成功");
        }
        return RespBean.error("添加职称失败");
    }

    @PutMapping("/")
    public RespBean updateJobLevel(@RequestBody JobLevel jobLevel){
        if(jobService.updateJobLevel(jobLevel)==1){
            return RespBean.ok("更新职称成功");
        }
        return RespBean.error("更新职称失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deleteJobLevelById(@PathVariable Integer id){
        if(jobService.deleteJobLevel(id)==1){
            return RespBean.ok("删除成功");
        }
        return RespBean.error("删除失败");
    }

    @DeleteMapping("/")
    public RespBean deleteJobLevelByIds(Integer[] ids){
        if(jobService.deleteJobLevels(ids)==ids.length){
            return RespBean.ok("批量删除成功");
        }
        return RespBean.error("批量删除失败");
    }


}
