package com.stx.domains.dtos;

public class ArtifactSearchRequest {
    private String text;
    private String[] fields;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }
}
