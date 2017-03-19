package com.birthright.util;


import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by birthright on 07.05.16.
 */
public class CreatePDF {

    private static final Font TIME_ROMAN = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font TIME_ROMAN_SMALL = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

    public static Document createPDF(String file, java.util.List<String> autoBrandList) {

        Document document = null;
        try {
            document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            addMetaData(document);

            addTitlePage(document);

            createTable(document, autoBrandList);

            document.close();

        } catch (FileNotFoundException | DocumentException e) {

            e.printStackTrace();
        }
        return document;

    }

    private static void addMetaData(Document document) {
        document.addTitle("Generate PDF report");
        document.addSubject("Generate PDF report");
        document.addAuthor("Java Honk");
        document.addCreator("Java Honk");
    }

    private static void addTitlePage(Document document)
            throws DocumentException {

        Paragraph preface = new Paragraph();
        creteEmptyLine(preface, 1);
        preface.add(new Paragraph("Price List", TIME_ROMAN));

        creteEmptyLine(preface, 1);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy");
        preface.add(new Paragraph("Price created on "
                + simpleDateFormat.format(new Date()), TIME_ROMAN_SMALL));
        document.add(preface);

    }

    private static void creteEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private static void createTable(Document document, java.util.List<String> autoBrandList) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        creteEmptyLine(paragraph, 2);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(2);

        PdfPCell c1 = new PdfPCell(new Phrase("Brand model"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Price"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

//        for (AutoBrand autoBrand : autoBrandList) {
//            java.util.List<AutoModel> autoModelList = autoBrand.getAutoModels();
//            for (AutoModel anAutoModelList : autoModelList) {
//                table.setWidthPercentage(100);
//                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
//                table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
//                table.addCell(autoBrand.getName() + " " + anAutoModelList.getName());
//                if (anAutoModelList.getPrice() != null) {
//                    table.addCell(anAutoModelList.getPrice().getPrice() + "");
//                } else {
//                    table.addCell("-");
//                }
//
//            }
//        }
//        for (int i = 0; i < length; i++) {
//            table.setWidthPercentage(100);
//            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
//            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_MIDDLE);
//            table.addCell("Java");
//            table.addCell("Honk");
//        }

        document.add(table);
    }

}
