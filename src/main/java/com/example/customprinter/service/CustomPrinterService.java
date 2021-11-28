package com.example.customprinter.service;

import com.example.customprinter.domain.Printer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.PageRanges;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CustomPrinterService implements Printable {

    private PrintService[] printService;
    private String text;

    public java.util.List<String> getPrinters() {
        updatePrinters();
        PrintService[] ps = PrinterJob.lookupPrintServices();
        printService = PrinterJob.lookupPrintServices();
        List<String> list = new ArrayList<>();
        if (ps.length > 0) {
            for (int i = 0; i < ps.length; i++) {
                list.add("Name " + i + ": " + ps[i].getName());
            }
        }
        return list;
    }

    public void printText(Printer printer) {

        this.text = printer.getText();

        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(new PageRanges(1, 1));
        aset.add(new Copies(1));

        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(this);

        try {
            printJob.setPrintService(printService[printer.getPos()]);
            printJob.print(aset);
        } catch (PrinterException err) {
            err.printStackTrace();
        }
    }

    private static void updatePrinters() {
        Class<?>[] classes = PrintServiceLookup.class.getDeclaredClasses();
        for (Class<?> clazz : classes) {
            String str = "javax.print.PrintServiceLookup$Services";
            if (str.equals(clazz.getName())) {
                try {
                    Class<?> acClass = Class.forName("sun.awt.AppContext");
                    Object appContext = acClass.getMethod("getAppContext").invoke(null);
                    acClass.getMethod("remove", Object.class).invoke(appContext, clazz);
                } catch (Exception e) {
                    log.info("Erro ao limpar impressoras");
                }
                break;
            }
        }
    }

    public int print(Graphics g, PageFormat pf, int pageIndex) {
        Graphics2D g2 = (Graphics2D) g;
        g2.translate(pf.getImageableX(), pf.getImageableY());
        g.drawString(String.valueOf(this.text), 14, 14);
        return PAGE_EXISTS;
    }

}
