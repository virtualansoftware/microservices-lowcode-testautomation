package io.virtualan.cucumblan.standard;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import java.io.File;

public class OCRReader { //} implements  StandardProcessing {


    public static void main(String[] args) {
        Tesseract tesseract = new Tesseract();
        try {

            tesseract.setDatapath("D:/Tess4J/tessdata");

            // the path of your tess data folder
            // inside the extracted file
            String text
                    = tesseract.doOCR(new File("D:\\virtualan-software-delivery\\2022\\feb-release\\microservices-lowcode-testautomation\\rest-post\\src\\test\\resources\\FEdex.png"));

            // path of your image file
            System.out.print(text);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
    }
}