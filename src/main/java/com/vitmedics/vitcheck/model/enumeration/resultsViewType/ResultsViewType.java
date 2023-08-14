package com.vitmedics.vitcheck.model.enumeration.resultsViewType;

public enum ResultsViewType {
    TRAFFIC_LIGHT("TRAFFIC_LIGHT"),
    HEAT_SCALE("HEAT_SCALE");

    private String code;

    private ResultsViewType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
