package io.virtualan.cucumblan.standard;

import io.restassured.response.ValidatableResponse;
import io.virtualan.cucumblan.standard.StandardProcessing;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.json.JSONArray;
import org.json.JSONObject;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;

public class PDFConverter implements StandardProcessing {

    @Override
    public String getType() {
        return "PDF_TEMPLATE";
    }

    @Override
    public String preRequestProcessing(String jsonObject) {
        return null;
    }

    @Override
    public String postResponseProcessing(ValidatableResponse jsonObject) {
        JSONObject labelObj = new JSONObject();
        try {
            PDDocument document = PDDocument.load(jsonObject.extract().asByteArray());
            AccessPermission ap = document.getCurrentAccessPermission();
            Rectangle fromRect = new Rectangle(510, 10, 250, 140);
            JSONArray from = getContent(document, "from", fromRect);
            Rectangle toRect = new Rectangle(510, 100, 250, 250);
            JSONArray to = getContent(document, "to", toRect);
            labelObj.put("from", from);
            labelObj.put("to", to);
        } catch (IOException ioException) {
        }
        System.out.println(labelObj.toString());
        return labelObj.toString();
    }

    private JSONArray getContent(PDDocument document, String label, Rectangle rect) throws IOException {
        PDFTextStripperByArea stripper = new PDFTextStripperByArea();
        stripper.setSortByPosition(true);
        stripper.addRegion(label, rect);
        PDPage firstPage = document.getPage(0);
        stripper.extractRegions( firstPage );
        String row = stripper.getTextForRegion(label);
        String[] rows = row.replace("\r","").split("\n");
        JSONArray array = new JSONArray();
        Arrays.stream(rows).forEach(x -> array.put(x));
        return array;
    }

    @Override
    public String actualResponseProcessing(String jsonObject) {
        return jsonObject;
    }
}