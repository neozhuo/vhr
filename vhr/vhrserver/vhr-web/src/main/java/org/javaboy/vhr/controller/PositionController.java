package org.javaboy.vhr.controller;

import org.javaboy.vhr.model.Position;
import org.javaboy.vhr.model.RespBean;
import org.javaboy.vhr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/basic/pos")
public class PositionController {

    @Autowired
    PositionService positionService;


    @GetMapping("/")
    public List<Position> getAllPositions(){
        return positionService.getAllPosition();
    }

    @PostMapping("/")
    public RespBean addPosition(@RequestBody Position position){
        if(positionService.addPosition(position)==1){
            return RespBean.ok("添加职位成功");
        }
        return RespBean.error("添加职位失败");
    }

    @PutMapping("/")
    public RespBean updatePosition(@RequestBody Position position){
        if(positionService.updatePosition(position)==1){
            return RespBean.ok("更新职位成功");
        }
        return RespBean.error("更新职位失败");
    }

    @DeleteMapping("/{id}")
    public RespBean deletePosition(@PathVariable Integer id ){
        if(positionService.deletePosition(id)==1){
            return RespBean.ok("删除职位成功");
        }
        return RespBean.error("删除职位失败");
    }

    @DeleteMapping("/")
    public RespBean deletePositions(Integer[]ids){
        if(positionService.deletePositions(ids)==ids.length){
            return RespBean.ok("批量删除职位成功");
        }
        return RespBean.error("批量删除职位失败");
    }
}
