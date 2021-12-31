package com.jiyeon.project.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Actor {

    private String id;

    private String name;

    private String age;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

}
