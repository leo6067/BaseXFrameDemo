package com.xy.demo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDPage;
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream;
import com.tom_roush.pdfbox.pdmodel.common.PDRectangle;
import com.tom_roush.pdfbox.pdmodel.font.PDType1Font;
import com.tom_roush.pdfbox.pdmodel.graphics.image.LosslessFactory;
import com.tom_roush.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.tom_roush.pdfbox.android.PDFBoxResourceLoader;
import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDPage;
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream;
import com.tom_roush.pdfbox.pdmodel.common.PDRectangle;
import com.tom_roush.pdfbox.pdmodel.font.PDType1Font;
import com.tom_roush.pdfbox.pdmodel.graphics.image.LosslessFactory;
import com.tom_roush.pdfbox.pdmodel.graphics.image.PDImageXObject;
import com.xy.demo.base.Constants;
import com.xy.demo.model.ImagePdfParam;
import com.xy.demo.ui.success.ImgToPdfActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageToPdfConverter {

    public void createPdf(Activity activity, ImagePdfParam imagePdfParam) {
        // 输出PDF文件路径
        String filePath = Constants.publicXXYDir + imagePdfParam.name + ".pdf";
        PDRectangle pageSize = PDRectangle.A0;  // 设置页面尺寸，可以是 A0, A1, A2, A3, A4, A5 等
        boolean isLandscape = imagePdfParam.direction == 2; // 是否为横向
        Rectangle rectangle = PageSize.A4;// 设置页面尺寸，可以是 A0, A1, A2, A3, A4, A5 等
        switch (imagePdfParam.pageSize) {
            case 0:
                rectangle = PageSize.A0;
                break;
            case 1:
                rectangle = PageSize.A1;
                break;
            case 2:
                rectangle = PageSize.A2;
                break;
            case 3:
                rectangle = PageSize.A3;
                break;
            case 4:
                rectangle = PageSize.A4;
                break;
            case 5:
                rectangle = PageSize.A5;
                break;
            case 6:
                rectangle = PageSize.LETTER;
                break;
            case 7:
                rectangle = PageSize.LEGAL;
                break;
        }

        try {
            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            Background event = new Background();
            writer.setPageEvent(event);
            document.open();
            for (int i = 0; i < imagePdfParam.imgPathList.size(); i++) {
                Image img = Image.getInstance(imagePdfParam.imgPathList.get(i));
                boolean autoRotate = imagePdfParam.direction == 2;
                boolean imageIsLandscape = img.getWidth() > img.getHeight();
                if (autoRotate && imageIsLandscape) {
                    rectangle = new Rectangle(rectangle.getHeight(), rectangle.getWidth());
                }
                document.setPageSize(rectangle);
                document.newPage();
                float newWidth = 0;
                float newHeight = 0;
                if (img.getWidth() > rectangle.getWidth()) {
                    newWidth = (float) (rectangle.getWidth() * 0.9);
                } else {
                    if (img.getWidth() < rectangle.getWidth() && img.getWidth() > (rectangle.getWidth() - 20)) {
                        newWidth = (float) (img.getWidth() * 0.9);
                    } else {
                        newWidth = img.getWidth();
                    }
                }
                if (img.getHeight() > rectangle.getHeight()) {
                    newHeight = (float) (rectangle.getHeight() * 0.9);
                } else {
                    if (img.getHeight() < rectangle.getHeight() && img.getHeight() > (rectangle.getHeight() - 20)) {
                        newHeight = (float) (img.getHeight() * 0.9);
                    } else {
                        newHeight = img.getHeight();
                    }
                }
                img.scaleAbsolute(newWidth, newHeight);
                img.setAbsolutePosition((rectangle.getWidth() - img.getScaledWidth()) / 2, (rectangle.getHeight() - img.getScaledHeight()) / 2);
                addPageNumber(rectangle, writer, i + 1, imagePdfParam.imgPathList.size());
                document.add(img);
            }
            document.close();
//            File pdfFile = new File(filePath);
//            document.save(pdfFile);
//            document.close();
            imagePdfParam.path = filePath;
            Intent intent = new Intent();
            intent.putExtra("imagePdfParam", imagePdfParam);
            intent.setClass(activity, ImgToPdfActivity.class);
            activity.startActivity(intent);
            activity.finish();
//            mView.showPdfSavePath(path);
        } catch (IOException e) {
            e.printStackTrace();
//            mView.showPdfSaveError();
        } catch (DocumentException e) {
            e.printStackTrace();
//            mView.showPdfSaveError();
        }
    }

    private void addPageNumber(Rectangle rectangle, PdfWriter pdfWriter, int index, int total) {  //页码 角标
        PdfContentByte pdfContentByte = pdfWriter.getDirectContent();
        float f = (rectangle.getRight() + rectangle.getLeft()) / 2.0f;
        float f1 = rectangle.getBottom() + 20.0f;
        String pageNumberStyle = "pg_num_style_x_of_n";
        ColumnText.showTextAligned(pdfContentByte, 6, getPhrase( pageNumberStyle, index, total), f, f1, 0.0f);
    }

    private Phrase getPhrase( String s, int index, int total) {
        Chunk chunk = new Chunk(String.format("%d / %d", index, total));
        chunk.setFont(new Font(Font.FontFamily.HELVETICA, 30)); // 设置字体大小和字体系列
        Phrase phrase = new Phrase();
        phrase.add(chunk); // 将Chunk添加到Phrase中
//        if (!s.equals("pg_num_style_page_x_of_n")) {
//            return s.equals("pg_num_style_x_of_n") ? phrase : new Phrase(String.format("%d", index));
//        }
        return phrase;
    }


    public class Background extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            //color
            PdfContentByte canvas = writer.getDirectContentUnder();
            Rectangle rect = document.getPageSize();
            canvas.setColorFill(BaseColor.WHITE);
            canvas.rectangle(rect.getLeft(), rect.getBottom(), rect.getWidth(), rect.getHeight());
            canvas.fill();

            //border
            PdfContentByte canvasBorder = writer.getDirectContent();
            Rectangle rectBorder = document.getPageSize();
            rectBorder.setBorder(Rectangle.BOX);
            rectBorder.setBorderWidth(15);
            rectBorder.setBorderColor(BaseColor.WHITE);
            rectBorder.setUseVariableBorders(true);
            canvasBorder.rectangle(rectBorder);
        }
    }


    public void convertImagesToPdf(Activity activity, ImagePdfParam imagePdfParam) throws IOException {
        // 输出PDF文件路径
        String filePath = Constants.publicXXYDir + imagePdfParam.name + ".pdf";
        PDRectangle pageSize = PDRectangle.A0;  // 设置页面尺寸，可以是 A0, A1, A2, A3, A4, A5 等
        boolean isLandscape = imagePdfParam.direction == 2; // 是否为横向
        switch (imagePdfParam.pageSize) {
            case 0:
                pageSize = PDRectangle.A0;
                break;
            case 1:
                pageSize = PDRectangle.A1;
                break;
            case 2:
                pageSize = PDRectangle.A2;
                break;
            case 3:
                pageSize = PDRectangle.A3;
                break;
            case 4:
                pageSize = PDRectangle.A4;
                break;
            case 5:
                pageSize = PDRectangle.A5;
                break;
            case 6:
                pageSize = PDRectangle.LETTER;
                break;
            case 7:
                pageSize = PDRectangle.LEGAL;
                break;
        }


        // Create a new document
        PDDocument document = new PDDocument();
        int imageNumber = 1;
//        for (String imagePath : imagePdfParam.imgPathList) {
//            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
//            // Create a new page with specified size
//            PDRectangle adjustedPageSize = isLandscape ? new PDRectangle(pageSize.getHeight(), pageSize.getWidth()) : pageSize;
//            PDPage page = new PDPage(adjustedPageSize);
//            document.addPage(page);
//            // Create image object from bitmap
//            PDImageXObject pdImage = LosslessFactory.createFromImage(document, bitmap);
//
//            // Create a content stream to draw on the page
//            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND,true,true);
//
//            // Calculate positions to center the image on the page
//            float imageWidth = pdImage.getWidth();
//            float imageHeight = pdImage.getHeight();
//            float pageWidth = adjustedPageSize.getWidth();
//            float pageHeight = adjustedPageSize.getHeight();
//            float xPosition = (pageWidth - imageWidth) / 2;
//            float yPosition = (pageHeight - imageHeight) / 2;
//
//            // Draw the image at the calculated position
//            contentStream.drawImage(pdImage, xPosition, yPosition);
//
//            // Add text in the top-left corner
//            contentStream.beginText();
//            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
//            contentStream.newLineAtOffset(10, pageHeight - 20); // Position text 10 points from left, and slightly below top
//            contentStream.showText("Image " + imageNumber);
//            contentStream.endText();
//
//            // Close the content stream
//            contentStream.close();
//            imageNumber++;
//        }


        for (String imagePath : imagePdfParam.imgPathList) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);

            // Create a new page with specified size
            PDRectangle adjustedPageSize = isLandscape ? new PDRectangle(pageSize.getHeight(), pageSize.getWidth()) : pageSize;
            PDPage page = new PDPage(adjustedPageSize);
            document.addPage(page);

            // Create image object from bitmap
            PDImageXObject pdImage = LosslessFactory.createFromImage(document, bitmap);

            float pageWidth = page.getMediaBox().getWidth();
            float pageHeight = page.getMediaBox().getHeight();

            // Create a content stream to draw on the page
            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Calculate positions to center the image on the page
            float imageAspectRatio = (float) pdImage.getWidth() / (float) pdImage.getHeight();
            float pageAspectRatio = pageWidth / pageHeight;

            float scaleFactor = 1.0f;
            if (imageAspectRatio > pageAspectRatio) {
                scaleFactor = pageWidth / pdImage.getWidth();
            } else {
                scaleFactor = pageHeight / pdImage.getHeight();
            }

            float xPos = (pageWidth - (pdImage.getWidth() * scaleFactor)) / 2;
            float yPos = (pageHeight - (pdImage.getHeight() * scaleFactor)) / 2;

            // Draw the image at the calculated position
//            contentStream.drawImage(pdImage, xPos, yPos,pdImage.getWidth() * scaleFactor,pdImage.getHeight() * scaleFactor);
            contentStream.drawImage(pdImage, 0, 0, pageWidth, pageHeight);
            // Add text in the top-left corner
//            contentStream.beginText();
//            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
//            contentStream.newLineAtOffset(10, pageHeight - 20); // Position text 10 points from left, and slightly below top
//            contentStream.showText("Image " + imageNumber);
//            contentStream.endText();
            contentStream.close();
            imageNumber++;
        }


        // Save the document to a file
        File pdfFile = new File(filePath);
        document.save(pdfFile);
        document.close();
        imagePdfParam.path = filePath;
        Intent intent = new Intent();
        intent.putExtra("imagePdfParam", imagePdfParam);
        intent.setClass(activity, ImgToPdfActivity.class);
        activity.startActivity(intent);
        activity.finish();
    }

//    private int convertDpToPx(int dp) {
//        return Math.round(dp * (context.getResources().getDisplayMetrics().xdpi / android.util.DisplayMetrics.DENSITY_DEFAULT));
//    }
}

