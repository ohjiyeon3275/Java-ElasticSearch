package com.jiyeon.project.controller;

import com.jiyeon.project.service.IndexService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/index")
@AllArgsConstructor
public class IndexController {

    private final IndexService indexService;

    @PostMapping("/recreate")
    public void recreate(){
        // by adding isDeleted value, it will be controlled whether created or not.
        indexService.recreateIndices(true);
    }
}
