/**
 * Copyright (C) 2016 The yuhaiyang Android Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ishow.noahark.widget.select;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.ishow.common.utils.ScreenUtils;
import com.ishow.common.utils.log.L;
import com.ishow.noahark.R;
import com.ishow.noahark.entries.ISelect;

import java.util.List;

/**
 * Created by yuhaiyang on 2016/10/31.
 * 选择城市的dialog
 */

public class SelectDialog<T extends ISelect> extends Dialog implements
        AdapterView.OnItemClickListener {
    private static final String TAG = "SelectDialog";
    private SelectAdapter<T> mAdapter;
    private OnSelectedListener mSelectedListener;

    public SelectDialog(Context context) {
        super(context, R.style.AppDialog_Bottom_Transparent);
        mAdapter = new SelectAdapter<>(getContext());
        setCancelable(true);
    }

    public SelectDialog(Context context, int gravity) {
        super(context, R.style.AppDialog_Bottom_Transparent);
        mAdapter = new SelectAdapter<>(context, gravity);
        setCancelable(true);
    }

    public void setHasArrow(boolean hasArrow) {
        mAdapter.setHasArrow(hasArrow);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common_select);

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(mAdapter);
        list.setOnItemClickListener(this);
    }

    public void setData(List<T> data) {
        mAdapter.setData(data);
    }

    @Override
    public void show() {
        super.show();
        Window window = getWindow();
        if (window == null) {
            L.i(TAG, "show: window is null");
            return;
        }
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = ScreenUtils.getScreenSize()[0];
        lp.gravity = Gravity.BOTTOM;
        window.setAttributes(lp);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        T item = mAdapter.getItem(position);
        notifySelectCityChanged(item);
        dismiss();
    }


    private void notifySelectCityChanged(T selected) {
        if (mSelectedListener != null) {
            mSelectedListener.onSelected(selected);
        }
    }

    public void setOnSelectedListener(OnSelectedListener<T> listener) {
        mSelectedListener = listener;
    }

    public interface OnSelectedListener<T extends ISelect> {
        void onSelected(T t);
    }
}
