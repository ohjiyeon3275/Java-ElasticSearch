package com.jiyeon.project.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.elasticsearch.search.sort.SortOrder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequestDto extends PageRequestDto{

    private List<String> fields;

    private String searchTerm;

    private String sort;

    // SortOrder is from es lib.
    private SortOrder order;

}
