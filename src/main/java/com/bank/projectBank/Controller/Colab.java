package com.bank.projectBank.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

public class Colab {
    @RestController
    @RequestMapping("/")
    public class CollaboratorsController {
        @GetMapping("/")
        public String index() {
            return "Project Bank: Adonay Rodrigues da Roch";
        }
    }
}
