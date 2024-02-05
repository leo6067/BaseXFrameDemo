package com.xy.demo;

import android.util.Log;

import com.xy.xframework.base.BaseSharePreference;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class TextParam {


     String[]  ass  = {"2","5"};

    public void param(){

        BaseSharePreference.Companion.getInstance().putString("xxxx","ooooo");
    }
}
