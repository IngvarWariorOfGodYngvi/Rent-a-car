package com.example.auta.services;

import com.example.auta.models.classes.Return;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class ReturnService {
    public Map<UUID, Return> getReturns() {
        return null;
    }

    public boolean updateReturnEmployee(UUID uuid) {
        return false;
    }

    public boolean updateReturnExtraPayment(UUID uuid) {
        return false;
    }
}
