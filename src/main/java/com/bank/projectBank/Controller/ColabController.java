package com.bank.projectBank.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ColabController {
    @GetMapping("/")
    public String index() {
        return "Project Bank: Adonay Rodrigues da Rocha";
    }

}