package com.xy.demo.network.params;

import android.util.Log;

import com.xy.demo.R;
import com.xy.demo.base.MyApplication;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 用于接口解密
 */
public class AESUtils {

    private static AESUtils aesUtils = null;

    // 偏移量iv
    private IvParameterSpec iv;
    // 指定工作模式
    private final String decodeToBytes = "AES/CBC/PKCS5Padding";
    // 密钥
    private SecretKeySpec secretKeySpec = null;

    public static AESUtils getInstance() {
        if (aesUtils == null) {
            synchronized (AESUtils.class) {
                if (aesUtils == null) {
                    aesUtils = new AESUtils();
                }
            }
        }
        return aesUtils;
    }

    public AESUtils() {
        try {
            secretKeySpec = new SecretKeySpec(MyApplication.instance.getResources().getString(R.string.AES_KEY).getBytes("ASCII"),
                    "AES");
            iv = new IvParameterSpec(MyApplication.instance.getResources().getString(R.string.AES_SECRET).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String decrypt(String base64) {
        byte[] toBytes = Base64Decoder.decodeToBytes(base64);
        return new String(decrypt(decodeToBytes, secretKeySpec, iv, toBytes));
    }

    private byte[] decrypt(String cmp, SecretKey sk, IvParameterSpec IV, byte[] bytes) {
        try {
            Cipher c = Cipher.getInstance(cmp);
            c.init(Cipher.DECRYPT_MODE, sk, IV);
            return c.doFinal(bytes);
        } catch (Throwable nsae) {
            Log.e("AESdemo", "no cipher getinstance support for " + cmp);
        }
        return null;
    }
}