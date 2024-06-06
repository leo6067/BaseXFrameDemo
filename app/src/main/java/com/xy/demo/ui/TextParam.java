package com.xy.demo.ui;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.xy.xframework.base.BaseSharePreference;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class TextParam {


    String[] ass = {"2", "5"};

//if (Build.VERSION.SDK_INT < 29) {
//        path = Environment.getExternalStorageDirectory() + "/" + 名称;
//    } else {
////10以后
//        path = activity.getExternalFilesDir("").getAbsolutePath() + 名称;
//    }

    /**
     *   将沙盒中的文件/data/data/包名/cache/test.txt 保存到外部存储区域 sdcard/test.txt
     */
//    public void handleWriteExternalStorage() {
//        File sandFile = new File(mFragment.getActivity().getCacheDir() + File.separator + "test.txt");
//        if(!sandFile.exists()) {
//            Toast.makeText(mFragment.getActivity(), "请先手动创建test.txt 并且保存到/data/data/包名/cache/下", Toast.LENGTH_LONG).show();
//            return;
//        }
//        ThreadManager.getInstence().postTask(new Runnable() {
//            @Override
//            public void run() {
//                Uri externalUri = null;
//                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
//                    File externalFile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//                    File destFile = new File(externalFile + File.separator + "test.txt");
//                    externalUri = Uri.fromFile(destFile);
//                } else {
//                    ContentResolver resolver = mFragment.getActivity().getContentResolver();
//                    ContentValues values = new ContentValues();
//                    values.put(MediaStore.Downloads.DISPLAY_NAME, "test.txt"); //名字
//                    values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);//保存路径
//                    Uri uri = MediaStore.Files.getContentUri("external");
//                    externalUri = resolver.insert(uri, values);
//                }
//
//                boolean ret = FileHelper.copySandFileToExternalUri(mFragment.getActivity(), sandFile.getAbsolutePath(), externalUri);
//                mFragment.getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(mFragment.getActivity(), "从沙盒" + sandFile.getAbsolutePath() + "中拷贝文件到外部存储"
//                                + Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath()
//                                + ", 结果= " + ret, Toast.LENGTH_LONG).show();
//                    }
//                });
//            }
//        });
//    }
//
//    /**
//     * 拷贝沙盒中的文件到外部存储区域
//     * @param filePath 沙盒文件路径
//     * @param  externalUri 外部存储文件的 uri
//     */
//    public static boolean copySandFileToExternalUri(Context context, String filePath, Uri externalUri) {
//        ContentResolver contentResolver = context.getContentResolver();
//        InputStream inputStream = null;
//        OutputStream outputStream = null;
//        boolean ret = false;
//        try {
//            outputStream = contentResolver.openOutputStream(externalUri);
//            File sandFile = new File(filePath);
//            if(sandFile.exists()) {
//                inputStream = new FileInputStream(sandFile);
//
//                int readCount = 0;
//                byte[] buffer = new byte[1024];
//                while ((readCount = inputStream.read(buffer)) != -1) {
//                    outputStream.write(buffer, 0 , readCount);
//                    outputStream.flush();
//                }
//            }
//            ret = true;
//        } catch (Exception e) {
//            Log.e(TAG, "copy SandFile To ExternalUri. e = " + e.toString());
//            ret = false;
//        } finally {
//            try {
//                if(outputStream != null) {
//                    outputStream.close();
//                }
//                if(inputStream != null) {
//                    inputStream.close();
//                }
//                Log.d(TAG, " input stream and output stream close successful.");
//            } catch (Exception e) {
//                e.printStackTrace();
//                Log.e(TAG, " input stream and output stream close fail. e = " + e.toString());
//            }
//            return ret;
//        }
//    }

}
