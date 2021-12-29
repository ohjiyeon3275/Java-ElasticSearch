package com.jiyeon.project.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import javax.persistence.Id;

@Getter
@Setter
@Document(indexName = "movies")
@Setting(settingPath = "static/es-mapping.json")
public class Movie {

    @Id
    private Long id;

    @Field(type = FieldType.Keyword)
    private String title;

    @Field(type= FieldType.Text)
    private Integer year;

}
