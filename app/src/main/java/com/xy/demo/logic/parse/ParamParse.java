package com.xy.demo.logic.parse;

import com.xy.demo.model.IrModel;
import com.xy.demo.network.Globals;

import java.util.ArrayList;
import java.util.Arrays;

public class ParamParse {

    //RemoteCode   Frequency
    public static IrModel getIrCodeList(String remoteCode, int frequency) {
        IrModel irModel = new IrModel();
        if(!remoteCode.isEmpty()) {
            try {
                if(frequency > 0) {
                    ArrayList<String> arrayList0 = new ArrayList<String>(Arrays.asList(remoteCode.split(" ")));
                    int[] result = new int[arrayList0.size()];
                    int count = 0;
                    while(count < arrayList0.size()) {
                        result[count] = Integer.parseInt(((String)arrayList0.get(count)));
                        Globals.log("xxxxx指result00：", result[count]+ " ");
                        count++;
                    }
                    irModel.setIrCodeList(result);
                    irModel.setFrequency(frequency);
                }
                else {
                    ArrayList<String> arrayList1 = new ArrayList<String>(Arrays.asList(remoteCode.split(" ")));
                    arrayList1.remove(0);
                    int count = Integer.parseInt((String)arrayList1.remove(0),16);
                    count = (int)(1000000.0 / (((double)count) * 0.241246));
                    arrayList1.remove(0);
                    arrayList1.remove(0);
                    for(int i = 0; i < arrayList1.size();i++) {
                        arrayList1.set(i, Integer.toString(Integer.parseInt(((String)arrayList1.get(i)), 16)));
                    }
                    int len = arrayList1.size();
                    int[] result = new int[len];
                    int j = 0;
                    while(j < len) {
                        result[j] = Integer.parseInt(((String)arrayList1.get(j))) * 26;
                        Globals.log("xxxxx指result：", result[j]+ " ");
                        ++j;
                    }
                    Globals.log("xxxxx指result：", result[0]+ " ");
                    irModel.setIrCodeList(result);
                    irModel.setFrequency(count);
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
        return irModel;
    }


}
