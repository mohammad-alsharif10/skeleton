package com.skeleton.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
@AllArgsConstructor
@CrossOrigin(origins = ("http://localhost:4200"))
public class DemoController {


    @RequestMapping(path = "/demo", method = RequestMethod.GET)
    public String demo() {
        return "ok";
    }
}
