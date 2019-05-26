package com.example.auta.controllers;

import com.example.auta.models.classes.Return;
import com.example.auta.services.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/return")
public class ReturnController {

    @Autowired
    private ReturnService returnService;

    @PostMapping("/add")
    public UUID addReturn(@RequestParam("employeeUUID") UUID employeeUUID,
                          @RequestParam("rentUUID") UUID rentUUID,
                          @RequestBody Return ret,
                          @RequestParam("branchUUID")UUID branchUUID){
        return returnService.addReturn(employeeUUID, rentUUID, ret,branchUUID);
    }
}
