package com.example.auta.services;

import com.example.auta.models.classes.Rent;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class RentService {
    public Map<UUID, Rent> getRents() {
        return null;
    }

    public boolean updateRentComment(UUID uuid) {
        return false;
    }

    public boolean updateRentEmployee(UUID uuid) {
        return false;
    }
}
