package es.riberadeltajo.primaria_sanitycode.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse {

    @JsonProperty("generated_text")
    private String generatedText;

    public ApiResponse() {}

    public String getGeneratedText() { return generatedText; }
    public void setGeneratedText(String generatedText) { this.generatedText = generatedText; }
}