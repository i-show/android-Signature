package com.ishow.noahark.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

/**
 * Created by yuhaiyang on 2017/6/9.
 * 获取签名的工具类
 */

public class SignatureUtils {

    /**
     * 获取签名信息
     */
    private static Signature[] getRawSignature(Context context, String packageName) {
        if ((packageName == null) || (packageName.length() == 0)) {
            throw new IllegalArgumentException("获取签名失败，包名为 null");
        }
        PackageManager localPackageManager = context.getPackageManager();
        PackageInfo localPackageInfo;
        try {
            localPackageInfo = localPackageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            if (localPackageInfo == null) {
                throw new IllegalArgumentException("信息为 null, 包名 = " + packageName);
            }
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
            throw new IllegalArgumentException("包名没有找到  ");
        }

        return localPackageInfo.signatures;
    }

    /**
     * 开始获得签名
     */
    public static String getSignature(Context context, String packageName) {
        try {
            Signature[] signatures = getRawSignature(context, packageName);
            if ((signatures == null) || (signatures.length == 0)) {
                return "获取签名失败";
            }
            return Md5.getMessageDigest(signatures[0].toByteArray());
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
