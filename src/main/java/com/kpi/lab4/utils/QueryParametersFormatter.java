package com.kpi.lab4.utils;

import java.util.HashMap;
import java.util.Map;

public class QueryParametersFormatter {
    private Map<String, String[]> parameters;

    public QueryParametersFormatter() {
        this.parameters = new HashMap<>();
    }

    public void setParameters(Map<String, String[]> parameters) {
        this.parameters = new HashMap<>();
        parameters.forEach((name, values) -> this.parameters.put(name, values));
    }

    public void setParam(String name, String value) {
        parameters.put(name, new String[]{ value });
    }

    public String getFormattedQuery() {
        StringBuilder queryBuilder = new StringBuilder("?");
        this.parameters.forEach((name, values) -> {
            for (String value: values) {
                queryBuilder.append(name).append("=").append(value).append("&");
            }
        });
        queryBuilder.deleteCharAt(queryBuilder.length() - 1);
        return queryBuilder.toString();
    }

    public String getFormattedQuery(String name, String value) {
        this.setParam(name, value);
        return getFormattedQuery();
    }
}
