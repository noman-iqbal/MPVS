/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mpvs.helper;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.mpvs.db.ExamContents;
import com.mpvs.db.Results;
import com.mpvs.db.ResultsContent;
import com.mpvs.db.ResultsStudent;
import java.io.FileOutputStream;

/**
 *
 * @author noman
 */
public class PDFCreater {

    
    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.NORMAL, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    private Results result;
    private String filePath;

    public PDFCreater() {
        result = null;
        filePath = "";
    }

    public PDFCreater(String outputFile, Results result) {
        try {
            this.result = result;
            this.filePath = outputFile;
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(filePath+".pdf"));
            document.open();
            addMetaData(document);
            addTitlePage(document);
            addContent(document);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void addMetaData(Document document) {
        document.addTitle("Result PDF");
        document.addSubject("Of exam id=" + result.getExamId().getId());
        document.addKeywords("Java, PDF, Result");
        document.addAuthor(result.getExamId().getUserCreaterId().getFirstName());
        document.addCreator(result.getExamId().getUserCreaterId().getFirstName());
    }

    private void addTitlePage(Document document)
            throws DocumentException {
        Paragraph preface = new Paragraph();
        addEmptyLine(preface, 1);
        preface.add(new Paragraph("Result of Exam", catFont));
        addEmptyLine(preface, 1);
        preface.add(new Paragraph(result.getExamId().getTitle(), smallBold));
        addEmptyLine(preface, 3);
        document.add(preface);

    }

    private void addContent(Document document) throws DocumentException {
        document.add(createTable());
    }

    private PdfPTable createTable()
            throws BadElementException {
        int i = 0;
        for (ExamContents examContents : result.getExamId().getExamContentsCollection()) {
            i++;
        }



        PdfPTable table = new PdfPTable(i+3);

        // t.setBorderColor(BaseColor.GRAY);
        // t.setPadding(4);
        // t.setSpacing(4);
        // t.setBorderWidth(1);

        PdfPCell c1 = new PdfPCell(new Phrase("University id"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Name"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        for (ExamContents examContents : result.getExamId().getExamContentsCollection()) {
            c1 = new PdfPCell(new Phrase(examContents.getName() + "/" + examContents.getPercentage()));
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(c1);
        }
        c1 = new PdfPCell(new Phrase("Status"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        table.setHeaderRows(1);

        for (ResultsStudent resultsStudent : result.getResultsStudentCollection()) {
            table.addCell(resultsStudent.getStudentId().getUserName());
            table.addCell(resultsStudent.getStudentId().getFirstName() + " " + resultsStudent.getStudentId().getLastName());
            for (ResultsContent resultsContent : resultsStudent.getResultsContentCollection()) {
                table.addCell(resultsContent.getMarks() + "");
            }
            table.addCell(resultsStudent.getStatus());

        }
        return table;

    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
}
