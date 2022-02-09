package com.springrest.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.elasticsearch.search.sort.SortOrder;

import javax.validation.constraints.Pattern;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchRequestDTO extends PageRequestDTO{
    private List<String> fields;
    private String searchTerm;
    private String sortBy;
    private SortOrder order;

    //@Pattern(message = "Invalid Timestamp, ")
    String startDate = "2022-01-01 00:00:00";
    //@Pattern(message = "Invalid Timestamp")
    String endDate = "2023-01-01 00:00:00";


}
