package ru.paltyshev.bargen;

import com.itextpdf.text.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.PdfWriter;
import ru.paltyshev.bargen.gui.MainWindow;
import ru.paltyshev.bargen.gui.SaveDialog;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;


public class BarcodeGenerator {
    Barcode128 barcode128 = new Barcode128();
    PdfWriter wr;
    Image image;
    Document document;
    Paragraph paragraph;
    String fileName = "barcode.pdf";

    public BarcodeGenerator(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    MainWindow mainWindow;

    public void setFileName(int barcodeNumber) {
        this.fileName = String.format("AR-" + "%0" + 10 + "d", barcodeNumber);
    }

    public void setFileName(int firstBarcodeNumber, int lastBarcodeNumber) {
        this.fileName = String.format("AR-" + "%0" + 10 + "d", firstBarcodeNumber) + "-" + lastBarcodeNumber;
    }

    public void createSingleBarcode(int count) {
        createBarcode(count);
        Utils.updateCount(++count);
        mainWindow.updateCurentBarcodText();
    }

    public void createSomeBarcode(int currentNumber, int count) {
        prepareDoc();
        paragraph = new Paragraph();
        int columns = 1;
        for (int i = 0; i < count; i++) {
            createImage(currentNumber);
            paragraph.add(image);
            if (columns%4 == 0)
                addParagraph();
            columns++;
        }
        addParagraph();
        document.close();
        mainWindow.updateCurentBarcodText();
    }

    public void createRangeBarcode(int first, int last) {
        prepareDoc();
        paragraph = new Paragraph();
        int columns = 1;
        for (int i = first; i <= last; i++) {
            createImage(i);
            paragraph.add(image);
            if (columns%4 == 0)
                addParagraph();
            columns++;
        }
        addParagraph();
        document.close();
        Utils.updateCount(last+1);
        mainWindow.updateCurentBarcodText();
    }

    private void createImage(int barcodeNumber) {
        barcode128.setCode(String.format("AR-" + "%0" + 10 + "d", barcodeNumber));
        image = barcode128.createImageWithBarcode(wr.getDirectContent(), null, null);
        image.setAlignment(Image.TEXTWRAP);
        image.setIndentationRight(18f);
    }

    private void addParagraph() {
        try {
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        paragraph = new Paragraph();
    }

    private void createBarcode(int barcodeNumber) {
        prepareDoc();
        createImage(barcodeNumber);
        try {
            document.add(image);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        document.close();
    }

    private void prepareDoc() {
        try {
            document = new Document();
            wr = PdfWriter.getInstance(document, new FileOutputStream(new SaveDialog().open(fileName)));
            document.open();
        } catch (DocumentException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
