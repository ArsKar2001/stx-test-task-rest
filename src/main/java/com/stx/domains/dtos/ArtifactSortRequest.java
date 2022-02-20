package com.stx.domains.dtos;

public class ArtifactSortRequest {
    private String direction;
    private String[] fields;

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }
}
