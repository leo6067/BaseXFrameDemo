package com.xy.demo.logic;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

import com.xy.xframework.utils.ToastUtils;

//https://blog.csdn.net/u010127332/article/details/98968350?spm=1001.2101.3001.6650.3&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-3-98968350-blog-117286742.235%5Ev40%5Epc_relevant_rights_sort&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7ECTRLIST%7ERate-3-98968350-blog-117286742.235%5Ev40%5Epc_relevant_rights_sort&utm_relevant_index=6
@TargetApi(Build.VERSION_CODES.KITKAT)
public class ConsumerIrManagerApi {

    private static ConsumerIrManagerApi instance;
    private static android.hardware.ConsumerIrManager service;

    private ConsumerIrManagerApi(Context context) {
        //Android4.4才开始支持红外功能
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            // 获取系统的红外遥控服务
            service = (android.hardware.ConsumerIrManager) context.getApplicationContext().getSystemService(Context.CONSUMER_IR_SERVICE);
        }
    }

    public static ConsumerIrManagerApi getConsumerIrManager(Context context) {
        if (instance == null) {
            instance = new ConsumerIrManagerApi(context);
        }
        return instance;
    }

    /**
     * 手机是否有红外功能
     *
     * @return
     */
    public boolean hasIrEmitter() {
        //android4.4及以上版本&有红外功能
        if (service != null) {
            return service.hasIrEmitter();
        }
        //android4.4以下及4.4以上没红外功能
        return false;
    }

    /**
     * 发射红外信号
     *
     * @param carrierFrequency 红外频率
     * @param pattern
     */
    public void transmit(int carrierFrequency, int[] pattern) {
        if (service != null) {
            service.transmit(carrierFrequency, pattern);
        } else {
            ToastUtils.showShort("不支持红外功能");
        }

    }

    /**
     * 获取可支持的红外信号频率
     *
     * @return
     */
    public android.hardware.ConsumerIrManager.CarrierFrequencyRange[] getCarrierFrequencies() {
        if (service != null) {
            return service.getCarrierFrequencies();
        }
        return null;
    }
}

