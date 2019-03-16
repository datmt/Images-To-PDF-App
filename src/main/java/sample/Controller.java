package sample;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class Controller {


    @FXML
    TextField pdfPathTF;

    @FXML
    BorderPane rootPane;

    List<File> imagesFile;


    public void selectImages()
    {
        FileChooser imagesChooser = new FileChooser();

        imagesChooser.setTitle("Please select your images");

        imagesFile = imagesChooser.showOpenMultipleDialog(rootPane.getScene().getWindow());

        if (imagesFile == null)
        {
            System.out.println("Images are null");
            return;
        }

        for (File f : imagesFile)
            System.out.println(f.getAbsolutePath());



    }

    public void choosePDFPath()
    {
        //select the file path
        //show the file path in the text field
        FileChooser pdfChooser = new FileChooser();
        pdfChooser.setTitle("Select the file name and path to save to");
        File pdfFile = pdfChooser.showSaveDialog(rootPane.getScene().getWindow());

        if (pdfFile == null)
        {
            System.out.println("pdf file is null");
            return;
        }

        pdfPathTF.setText(pdfFile.getAbsolutePath());

    }

    public void converToPDF()
    {
        if (imagesFile == null || pdfPathTF.getText().equals(""))
        {
            System.out.println("Invalid input");
            return;
        }
        PDFMaker maker = new PDFMaker(pdfPathTF.getText(), imagesFile);

        try {
            maker.createPDFFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
