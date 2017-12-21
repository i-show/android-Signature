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

package com.ishow.noahark.modules.main;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;

import com.ishow.common.utils.image.ImageUtils;
import com.ishow.common.widget.YToast;
import com.ishow.common.widget.edittext.EditTextPro;
import com.ishow.noahark.R;
import com.ishow.noahark.entries.App;
import com.ishow.noahark.modules.base.AppBaseActivity;
import com.ishow.noahark.modules.settings.SettingsActivity;
import com.ishow.noahark.widget.select.SelectDialog;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppBaseActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";
    private long mLastTime;
    private App mSelectedApp;
    private EditTextPro mInputView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }

    @Override
    protected void initViews() {
        super.initViews();

        mInputView = (EditTextPro) findViewById(R.id.sign_input);
        mInputView.setRightImageClickListener(this);

        View submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {
        final long nowTime = System.currentTimeMillis();
        if (nowTime - mLastTime < 2000) {
            super.onBackPressed();
        } else {
            mLastTime = nowTime;
            YToast.show(this, R.string.click_again_to_exit);
        }
    }


    @Override
    public void onLeftClick(View v) {
        finish();
    }

    @Override
    public void onRightClick(View v) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                getSignature();
                break;
            case R.id.sign_input:
                selectApp();
                break;
            default:
                ViewParent parent = v.getParent();
                if (parent != null && (parent instanceof EditTextPro)) {
                    onClick((View) parent);
                }
                break;
        }
    }

    private void getSignature() {
        if (mSelectedApp == null) {
            dialog(R.string.please_selecte_app);
            return;
        }

        Intent intent = new Intent(this, AppDetailActivity.class);
        intent.putExtra(App.Key.TITLE, mSelectedApp.getTitle(this));
        intent.putExtra(App.Key.PACKAGE_NAME, mSelectedApp.getSubTitle(this));
        intent.putExtra(App.Key.ICON, ImageUtils.drawableToBytes(mSelectedApp.getImage(this)));
        startActivity(intent);
    }


    private void selectApp() {
        SelectDialog<App> dialog = new SelectDialog<>(this);
        dialog.setData(getAppList());
        dialog.setHasArrow(false);
        dialog.setOnSelectedListener(new SelectDialog.OnSelectedListener<App>() {
            @Override
            public void onSelected(App app) {
                mSelectedApp = app;
                mInputView.setInputText(mSelectedApp.getSubTitle(MainActivity.this));
            }
        });
        dialog.show();
    }

    private List<App> getAppList() {
        PackageManager pm = this.getPackageManager();
        // 查询所有已经安装的应用程序
        List<ApplicationInfo> listAppcations = pm.getInstalledApplications(PackageManager.MATCH_UNINSTALLED_PACKAGES);
        List<App> appList = new ArrayList<>();
        for (ApplicationInfo app : listAppcations) {
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                appList.add(getAppInfo(app));
            }
        }

        return appList;
    }

    private App getAppInfo(ApplicationInfo appInfo) {
        PackageManager pm = this.getPackageManager();
        App app = new App();
        app.setTitle((String) appInfo.loadLabel(pm));
        app.setIcon(appInfo.loadIcon(pm));
        app.setPackageName(appInfo.packageName);
        return app;
    }
}



