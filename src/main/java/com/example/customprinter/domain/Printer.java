package com.example.customprinter.domain;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class Printer {

    @NotBlank
    private String text;

    @NotNull
    private int pos;

}
