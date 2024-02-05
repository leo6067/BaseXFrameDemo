package com.xy.demo.cast;

import static com.blankj.utilcode.util.ImageUtils.drawable2Bitmap;

import static okhttp3.internal.Util.closeQuietly;

import android.annotation.SuppressLint;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.text.TextUtils;


import com.xy.demo.R;
import com.xy.demo.base.Constants;
import com.xy.demo.base.MyApplication;
import com.xy.demo.network.Globals;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

import fi.iki.elonen.NanoHTTPD;


//本地搭建服务器

/**
 * 系统端口：0~1023，用于运行标准服务，例如 HTTP 服务（端口 80）、FTP 服务（端口 21）等。
 * 注册端口：1024~49151，用于运行注册的应用程序或服务，例如 DNS 服务（端口 53）、SMTP 服务（端口 25）等。
 * 动态/私有端口：49152~65535，可以被动态分配给任何应用程序或服务，通常用于客户端。 所以，如下代码，我们声明监听的端口是 49152 ，避免端口被占用
 * 请求携带中文，需要将请求头设置 UTF-8 字符集，防止中文乱码，如下代码27行进行相关处理；
 * 客户端发送POST请求，获取 POST，Body参数，需要 session.parseBody(new HashMap<String,String>())，如下代码40行处理，获取Body参数。
 * ————————————————  //192.168.9.235
 * <p>
 * FileDownloadUtils.getDefaultSaveRootPath() + File.separator + Constants.FILE_CACHE_APP;
 * <p>
 * 原文链接：https://blog.csdn.net/Crazy_Cw/article/details/131275226
 */

public class HttpService extends NanoHTTPD {


    public static final int DEFAULT_SERVER_PORT = Constants.NANO_SORT;
    public static final String TAG = HttpService.class.getSimpleName();


    private String pathDomain = "";
    private String fileType = "image/jpeg";   //是图片  或者 视频


    public HttpService() {
        super(DEFAULT_SERVER_PORT);
    }


    public void setFilePath(String filePath, String fileType) {
        pathDomain = filePath;
        this.fileType = fileType;

    }


    @Override
    public Response serve(IHTTPSession session)   {


        Globals.log("xxxx  getLocalIpStr(this) ----" + session.getUri());  //192.168.9.235

        if (TextUtils.isEmpty(pathDomain)) {
            return  NanoHTTPD.newFixedLengthResponse("");
        }



        Map<String, String> headers = session.getHeaders();
        Map<String, String> parms = session.getParms();
        Method method = session.getMethod();
        String uri = session.getUri();
        Map<String, String> files = new HashMap<>();





        File f = new File(uri);
        return serveFile(uri, headers, new File(pathDomain) ,fileType);



//            InputStream inputStream = null;
//            try {
//                inputStream = new FileInputStream(new File(pathDomain));
//                return NanoHTTPD.newChunkedResponse(Response.Status.OK, fileType, inputStream );
//            } catch (IOException e) {
//                Globals.log("xxxx  getLocalIpStr(this) -getMessage---" + e.getMessage());  //192.168
//                return  NanoHTTPD.newFixedLengthResponse("");
//            }




    }





    private Response serveFile(String uri, Map<String, String> header, File file,String mimeType) {
        Response res;
        String mime = getMimeTypeForFile(uri);
        try {
            // Calculate etag
            String etag = Integer.toHexString((file.getAbsolutePath() +
                    file.lastModified() + "" + file.length()).hashCode());

            // Support (simple) skipping:
            long startFrom = 0;
            long endAt = -1;
            String range = header.get("range");
            if (range != null) {
                if (range.startsWith("bytes=")) {
                    range = range.substring("bytes=".length());
                    int minus = range.indexOf('-');
                    try {
                        if (minus > 0) {
                            startFrom = Long.parseLong(range.substring(0, minus));
                            endAt = Long.parseLong(range.substring(minus + 1));
                        }
                    } catch (NumberFormatException ignored) {
                    }
                }
            }

            // Change return code and add Content-Range header when skipping is requested
            long fileLen = file.length();
            if (range != null && startFrom >= 0) {
                if (startFrom >= fileLen) {
                    res = createResponse(Response.Status.RANGE_NOT_SATISFIABLE, MIME_PLAINTEXT, "");
                    res.addHeader("Content-Range", "bytes 0-0/" + fileLen);
                    res.addHeader("ETag", etag);
                } else {
                    if (endAt < 0) {
                        endAt = fileLen - 1;
                    }
                    long newLen = endAt - startFrom + 1;
                    if (newLen < 0) {
                        newLen = 0;
                    }

                    final long dataLen = newLen;
                    FileInputStream fis = new FileInputStream(file) {
                        @Override
                        public int available() throws IOException {
                            return (int) dataLen;
                        }
                    };
                    fis.skip(startFrom);

                    res = createResponse(Response.Status.PARTIAL_CONTENT, mime, fis);
                    res.addHeader("Content-Length", "" + dataLen);
                    res.addHeader("Content-Range", "bytes " + startFrom + "-" +
                            endAt + "/" + fileLen);
                    res.addHeader("ETag", etag);
                }
            } else {
                if (etag.equals(header.get("if-none-match")))
                    res = createResponse(Response.Status.NOT_MODIFIED, mime, "");
                else {
                    res = createResponse(Response.Status.OK, mime, new FileInputStream(file));
                    res.addHeader("Content-Length", "" + fileLen);
                    res.addHeader("ETag", etag);
                }
            }
        } catch (IOException ioe) {
            res = getResponse("Forbidden: Reading file failed");
        }

        return (res == null) ? getResponse("Error 404: File not found") : res;
    }

    // Announce that the file server accepts partial content requests
    private Response createResponse(Response.Status status, String mimeType, InputStream message) throws IOException {
        Response res = NanoHTTPD.newFixedLengthResponse(status, mimeType, message, message.available());
        res.addHeader("Accept-Ranges", "bytes");
        return res;
    }

    // Announce that the file server accepts partial content requests
    private Response createResponse(Response.Status status, String mimeType, String message) {
        Response res = NanoHTTPD.newFixedLengthResponse(status, mimeType, message);
        res.addHeader("Accept-Ranges", "bytes");
        return res;
    }

    private Response getResponse(String message) {
        return createResponse(Response.Status.OK, "text/plain", message);
    }







    public Response responseRootPage(IHTTPSession session) {
        File file = new File(pathDomain);
        if (!file.exists()) {
            return response404(session, pathDomain);
        }
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html><html><body>");
        builder.append("<video ");
//        builder.append("width="+getQuotaStr(String.valueOf(mVideoWidth))+" ");
//        builder.append("height="+getQuotaStr(String.valueOf(mVideoHeight))+" ");
        builder.append("controls>");
        builder.append("<source src=" + getQuotaStr(pathDomain) + " ");
        builder.append("type=" + getQuotaStr("video/mp4") + ">");
        builder.append("Your browser doestn't support HTML5");
        builder.append("</video>");
        builder.append("</body></html>\n");
        return NanoHTTPD.newFixedLengthResponse(builder.toString());
    }


    public Response response404(IHTTPSession session, String url) {
        StringBuilder builder = new StringBuilder();
        builder.append("<!DOCTYPE html><html><body>");
        builder.append("Sorry, Can't Found " + url + " !");
        builder.append("</body></html>\n");
        return NanoHTTPD.newFixedLengthResponse(builder.toString());


    }


    protected String getQuotaStr(String text) {
        return "\"" + text + "\"";
    }


    public String getIndex(String filename) {
        InputStream inputStream = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        StringBuffer sb = new StringBuffer();
        try {
//            inputStream = asset.open(filename);
            inputStream = new BufferedInputStream(new FileInputStream(new File(filename)));
            isr = new InputStreamReader(inputStream);
            br = new BufferedReader(isr);
            sb.append(br.readLine());
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append("\n" + line);
            }

            br.close();
            isr.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (isr != null) {
                    isr.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return sb.toString();
    }

    public static byte[] readFile(File file) {
        RandomAccessFile rf = null;
        byte[] data = null;
        try {
            rf = new RandomAccessFile(file, "r");
            data = new byte[(int) rf.length()];
            rf.readFully(data);
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            closeQuietly(rf);
        }
        return data;
    }


}