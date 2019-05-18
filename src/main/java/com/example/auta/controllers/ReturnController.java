package com.example.auta.controllers;

import com.example.auta.models.classes.Return;
import com.example.auta.services.ReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("updateemployee/{uuid}")
    public boolean updateReturnEmployee(@PathVariable UUID uuid) {
        return returnService.updateReturnEmployee(uuid);
    }

    @PostMapping("updateextrapay/{uuid}")
    public boolean updateReturnExtraPayment(@PathVariable UUID uuid){
        return returnService.updateReturnExtraPayment(uuid);
    }
}
