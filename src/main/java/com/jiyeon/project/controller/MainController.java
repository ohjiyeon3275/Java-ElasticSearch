package com.jiyeon.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class MainController {
    // issue test
    @GetMapping("/main")
    public ModelAndView main(@RequestParam Long id){

        return null;
    }
}
