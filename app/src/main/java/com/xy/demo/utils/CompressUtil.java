package com.xy.demo.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfObject;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.parser.PdfImageObject;
import com.jeremyliao.liveeventbus.LiveEventBus;
import com.xy.demo.BuildConfig;
import com.xy.demo.base.Constants;
import com.xy.demo.base.MyApplication;
import com.xy.demo.model.CompressModel;
import com.xy.demo.network.Globals;
import com.xy.demo.ui.success.ImgToPdfActivity;
import com.xy.demo.ui.success.SuccessCompressActivity;
import com.xy.xframework.utils.FileUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;


public class CompressUtil {

    public static void compress(Activity activity, String inputPath, int level) {  //level  0-100 压缩范围
        try {
            PdfReader reader = new PdfReader(inputPath);
            int n = reader.getXrefSize();
            PdfObject object;
            PRStream stream;
            for (int i = 0; i < n; i++) {
                object = reader.getPdfObject(i);
                if (object == null || !object.isStream()) {
                    continue;
                }
                stream = (PRStream) object;
                //PdfObject pdfsubtype = stream.get(com.itextpdf.text.pdf.PdfName.SUBTYPE);
                //System.out.println(stream.type());
                if (!PdfName.IMAGE.equals(stream.getAsName(PdfName.SUBTYPE))) {
                    continue;
                }
                if (!PdfName.DCTDECODE.equals(stream.getAsName(PdfName.FILTER))) {
                    continue;
                }

                byte[] tempBytes = new PdfImageObject(stream).getImageAsBytes();
                Bitmap bitmap = BitmapFactory.decodeByteArray(tempBytes, 0, tempBytes.length);
                if (bitmap == null) {
                    return;
                }
                int width = bitmap.getWidth(); //int width = (int)(bi.getWidth() * FACTOR);

                int height = bitmap.getHeight(); //int height = (int)(bi.getHeight() * FACTOR);
                if (width <= 0 || height <= 0) {
                    continue;
                }
                Bitmap bitmap1 = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
                new Canvas(bitmap1).drawBitmap(bitmap, 0, 0, null);
                ByteArrayOutputStream imageBytes = new ByteArrayOutputStream();

                if (bitmap1.getAllocationByteCount() > 52800) {
                    bitmap1.compress(Bitmap.CompressFormat.JPEG, level, imageBytes);
                    stream.clear();
                    stream.setData(imageBytes.toByteArray(), false, PRStream.NO_COMPRESSION);
                    stream.put(PdfName.TYPE, PdfName.XOBJECT);
                    stream.put(PdfName.SUBTYPE, PdfName.IMAGE);
                    stream.put(PdfName.FILTER, PdfName.DCTDECODE);
                    stream.put(PdfName.WIDTH, new PdfNumber(width));
                    stream.put(PdfName.HEIGHT, new PdfNumber(height));
                    stream.put(PdfName.BITSPERCOMPONENT, new PdfNumber(8));
                    stream.put(PdfName.COLORSPACE, PdfName.DEVICERGB);
                } else {
                    bitmap.compress(Bitmap.CompressFormat.JPEG, level, imageBytes);
                }
            }
            reader.removeUnusedObjects();


            File inputFile = new File(inputPath);
            String fileName = inputFile.getName();
            String[] split = fileName.split("\\.");

            String outFileName = Constants.publicXXYDir + split[0] + Constants.COMPRESS_FORMAT;
            File outputFile = new File(outFileName);

            PdfStamper pdfStamper = new PdfStamper(reader, new FileOutputStream(outFileName));


            pdfStamper.setFullCompression();
            BaseFont baseFont = com.itextpdf.text.pdf.BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1252, BaseFont.NOT_EMBEDDED);
            baseFont.setCompressionLevel(9);
            pdfStamper.close();
            reader.close();

            LiveEventBus.get(Constants.EVENT_REFRESH_FILE).post(Constants.EVENT_REFRESH_FILE);

            CompressModel compressModel = new CompressModel();
            compressModel.setBeforePath(inputPath);
            compressModel.setAfterPath(outFileName);

            compressModel.setBeforeSize(FileUtils.getFileOrFilesSize(inputPath, 3) + "MB");
            compressModel.setAfterSize(FileUtils.getFileOrFilesSize(outFileName, 3) + "MB");
            compressModel.setFileName(split[0] + Constants.COMPRESS_FORMAT);

            compressModel.setCompressSize(FileUtils.FormatFileSize(inputFile.length() - outputFile.length(), 3) + "MB");

            Intent intent = new Intent();
            intent.putExtra("compressModel", compressModel);
            intent.setClass(activity, SuccessCompressActivity.class);
            activity.startActivity(intent);
            activity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //分享
    public static void shareFile(Activity activity, String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("application/pdf"); // Set MIME type accordingly
            Uri fileUri = FileProvider.getUriForFile(activity, BuildConfig.APPLICATION_ID + ".fileprovider", file);
            intent.putExtra(Intent.EXTRA_STREAM, fileUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

            List<ResolveInfo> resInfoList = activity.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                activity.grantUriPermission(packageName, fileUri, Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }
            Intent chooser = Intent.createChooser(intent, "Share File");
            activity.startActivity(chooser);

//            Intent chooser = Intent.createChooser(intent, "Share File");
//            if (intent.resolveActivity(activity.getPackageManager()) != null) {
//                activity.startActivity(chooser);
//            }
        }
    }


}
