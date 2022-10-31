package com.br.didox.testes.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.didox.testes.modelViews.Home;

@RestController
public class HomeController {
    @GetMapping("/")
    public Home index(){
        return new Home();
    }
}
