package com.ishow.noahark.entries;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by Bright.Yu on 2016/12/29.
 * 选中的ISelect
 */

public interface ISelect {
    String getTitle(Context context);

    String getSubTitle(Context context);

    Drawable getImage(Context context);
}
