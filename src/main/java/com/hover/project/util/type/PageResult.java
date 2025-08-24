package com.hover.project.util.type;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class PageResult<T> {

    private long size;
    private int totalPage;
    private int currentPage;

    @JsonAnyGetter
    private Map<String, T> dynamicField = new HashMap<>();

    public void setDynamicData(String fieldName, T value) {
        dynamicField.clear();
        dynamicField.put(fieldName, value);
    }

}
