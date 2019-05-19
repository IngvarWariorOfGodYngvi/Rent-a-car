package com.example.auta.controllers;

import com.example.auta.models.classes.Return;
import com.example.auta.services.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/Return")
public class ReturnController {

    @Autowired
    private ReturnService returnService;

    @PostMapping("/returnlist")
    public Map<UUID, Return> getReturns() {
        return returnService.getReturns();
    }

    @PostMapping("updateemployee/{uuid}/{empid}")
    public boolean updateReturnEmployee(@PathVariable UUID uuid,UUID empid) {
        return returnService.updateReturnEmployee(uuid,empid);
    }

    @PostMapping("updateextrapay/{uuid}")
    public boolean updateReturnExtraPayment(@PathVariable UUID uuid, @RequestBody Integer extraPay){
        return returnService.updateReturnExtraPayment(uuid,extraPay);
    }
    @GetMapping("/add")
    public UUID addReturn(@RequestParam("employeeUUID") UUID employeeUUID, @RequestParam("rentUUID")UUID rentUUID){
        return returnService.addReturn(employeeUUID,rentUUID);
    }
}
