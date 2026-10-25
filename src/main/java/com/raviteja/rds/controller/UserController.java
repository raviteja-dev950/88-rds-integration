package com.raviteja.rds.controller;

import com.raviteja.rds.entity.User;
import com.raviteja.rds.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService service;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @GetMapping("/")
    public Map<String, Object> home() {
        Map<String, Object> map = new HashMap<>();
        map.put("status", "Live on 【entity-AWS¦canonical_name=AWS】 RDS - MySQL Ready");
        map.put("project", "88-rds-integration");
        map.put("tier", "9 - DevOps + Cloud - RDS Integration");
        map.put("database", dbUrl.contains("rds.amazonaws.com") || dbUrl.contains("mysql") ? "【entity-AWS¦canonical_name=AWS】 RDS MySQL" : "H2 In-Memory (RDS-Ready)");
        map.put("dbUrl", dbUrl);
        map.put("endpoints", new String[]{
            "GET /", "GET /health", "GET /users", "POST /users", "GET /users/{id}", "DELETE /users/{id}"
        });
        map.put("totalUsers", service.count());
        map.put("owner", "RaviTeja - 88/100");
        return map;
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "UP", "database", "Connected", "project", "88-rds-integration");
    }

    @GetMapping("/users")
    public List<User> getAll() {
        return service.getAll();
    }

    @PostMapping("/users")
    public User create(@RequestBody User user) {
        return service.save(user);
    }

    @GetMapping("/users/{id}")
    public User getOne(@PathVariable Long id) {
        return service.getById(id);
    }

    @DeleteMapping("/users/{id}")
    public Map<String, String> delete(@PathVariable Long id) {
        service.delete(id);
        return Map.of("message", "Deleted user id " + id, "status", "success");
    }
}