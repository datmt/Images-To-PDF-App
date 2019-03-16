package sample;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.image.JPEGFactory;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class PDFMaker {

    private PDDocument document;
    private String pdfFilePath;
    private List<File> imagesFiles;

    public PDFMaker(String pdfFilePath, List<File> imagesFiles)
    {
        this.pdfFilePath = pdfFilePath;
        this.imagesFiles = imagesFiles;
        document = new PDDocument();
    }

    private PDPage createPageFromImage(File imageFile)
    {
        try
        {
            PDPage page = new PDPage();
            PDPageContentStream contentStream = new PDPageContentStream(this.document, page);


            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();

            PDImageXObject image = JPEGFactory.createFromStream(this.document, (new FileInputStream(imageFile)));

            int imageHeight = image.getHeight();
            int imageWidth = image.getWidth();

            float inPageImageWidth = 0f;
            float inPageImageHeight = 0f;
            if (imageHeight <= imageWidth)
            {
                inPageImageWidth = pageWidth;
                inPageImageHeight = (imageHeight * inPageImageWidth)/imageWidth;
            } else
            {
                //imageHeight > imageWidth
                if (pageHeight * imageWidth/imageHeight > pageWidth)
                {
                    inPageImageWidth = pageWidth;
                    inPageImageWidth = pageWidth * imageHeight / imageWidth;
                } else
                {
                    inPageImageHeight = pageHeight;
                    inPageImageWidth = pageHeight * imageWidth / imageHeight;
                }
            }

            contentStream.drawImage(image, 0, pageHeight - inPageImageHeight, inPageImageWidth, inPageImageHeight);



            System.out.println("image draw");
            contentStream.close();
            return page;
        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
        return null;
    }

    public void createPDFFile() throws IOException
    {
        for (File f : imagesFiles)
        {
            PDPage page = createPageFromImage(f);
            if (page!=null)
                document.addPage(page);
        }

        document.save(this.pdfFilePath);
        document.close();

        System.out.println("Document created");
    }

}
