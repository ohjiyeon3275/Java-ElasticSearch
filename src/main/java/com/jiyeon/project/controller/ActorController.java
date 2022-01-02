package com.jiyeon.project.controller;

import com.jiyeon.project.domain.Actor;
import com.jiyeon.project.dto.SearchRequestDto;
import com.jiyeon.project.service.ActorService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/v1/actor")
@AllArgsConstructor
public class ActorController {

    private final ActorService actorService;

    @PutMapping("/save")
    public Boolean save(@RequestBody Actor actor){
       return actorService.save(actor);
    }


    @GetMapping("/{id}")
    public Actor findById(@PathVariable String id){
        return actorService.findById(id);
    }

    @PostMapping("/search")
    public List<Actor> search(@RequestBody SearchRequestDto searchRequestDto){
        return actorService.search(searchRequestDto);
    }

    @GetMapping("/search/{date}")
    public List<Actor> getAllActorBirthdaySince(@PathVariable
                                                @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        return actorService.getAllActorBirthdaySince(date);
    }

}
