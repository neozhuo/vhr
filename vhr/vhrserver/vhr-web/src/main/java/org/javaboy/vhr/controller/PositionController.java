package org.javaboy.vhr.controller;

import org.javaboy.vhr.model.Position;
import org.javaboy.vhr.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
