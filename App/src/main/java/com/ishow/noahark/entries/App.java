package com.ishow.noahark.entries;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by yuhaiyang on 2017/6/9.
 * APP的信息
 */

public class App implements ISelect {
    private String title;
    private String packageName;
    private Drawable icon;


    @Override
    public String getTitle(Context context) {
        return title;
    }

    @Override
    public String getSubTitle(Context context) {
        return packageName;
    }

    @Override
    public Drawable getImage(Context context) {
        return icon;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public static final class Key {
        public static final String TITLE = "key_app_title";
        public static final String PACKAGE_NAME = "key_app_package_name";
        public static final String ICON = "key_app_icon";
    }
}
