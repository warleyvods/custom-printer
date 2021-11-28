package com.example.customprinter.controller;

import com.example.customprinter.domain.Printer;
import com.example.customprinter.service.CustomPrinterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CustomPrinterController {

    private final CustomPrinterService customPrinterService;

    public CustomPrinterController(CustomPrinterService customPrinterService) {
        this.customPrinterService = customPrinterService;
    }

    @GetMapping
    public List<String> listAllPrinters() {
        return customPrinterService.getPrinters();
    }

    @PostMapping
    public String printer(@RequestBody @Valid Printer printer) {
        customPrinterService.printText(printer);
        return "Text: " + printer.getText();
    }

}
