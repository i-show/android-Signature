package com.ishow.noahark.modules.main;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.ishow.common.utils.AppUtils;
import com.ishow.common.utils.image.ImageUtils;
import com.ishow.common.widget.TopBar;
import com.ishow.common.widget.YToast;
import com.ishow.common.widget.edittext.EditTextPro;
import com.ishow.noahark.R;
import com.ishow.noahark.entries.App;
import com.ishow.noahark.modules.base.AppBaseActivity;
import com.ishow.noahark.utils.SignatureUtils;

/**
 * Created by yuhaiyang on 2017/6/11.
 * App详情
 */

public class AppDetailActivity extends AppBaseActivity implements View.OnClickListener {
    private static final String TAG = "SIGN";
    private App mApp;
    private EditTextPro mSignView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_detail);
    }

    @Override
    protected void initNecessaryData() {
        super.initNecessaryData();
        Intent intent = getIntent();
        String title = intent.getStringExtra(App.Key.TITLE);
        String packageName = intent.getStringExtra(App.Key.PACKAGE_NAME);
        byte[] iconBytes = intent.getByteArrayExtra(App.Key.ICON);

        if (TextUtils.isEmpty(title) || TextUtils.isEmpty(packageName) || iconBytes == null || iconBytes.length == 0) {
            dialog(R.string.no_app, true);
            return;
        }

        mApp = new App();
        mApp.setTitle(title);
        mApp.setPackageName(packageName);
        mApp.setIcon(ImageUtils.byteToDrawable(iconBytes));
    }

    @Override
    protected void initViews() {
        super.initViews();

        if (mApp == null) {
            return;
        }

        TopBar topBar = (TopBar) findViewById(R.id.top_bar);
        topBar.setText(mApp.getTitle(this));

        ImageView icon = (ImageView) findViewById(R.id.icon);
        icon.setImageDrawable(mApp.getImage(this));

        TextView packageName = (TextView) findViewById(R.id.app_package_name);
        packageName.setText(mApp.getSubTitle(this));

        TextView version = (TextView) findViewById(R.id.app_version);
        version.setText(AppUtils.getVersionName(this, mApp.getSubTitle(this)));

        mSignView = (EditTextPro) findViewById(R.id.sign);
        mSignView.setInputText(SignatureUtils.getSignature(this, mApp.getSubTitle(this)));
        mSignView.setRightTextClickListener(this);

        View submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign:
                ClipData clip = ClipData.newPlainText("sign", mSignView.getInputText());
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                cm.setPrimaryClip(clip);
                YToast.show(this, R.string.sing_copy_success);
                break;
            case R.id.submit:
                StringBuilder builder = new StringBuilder();
                builder.append("***************  ");
                builder.append(mApp.getTitle(this));
                builder.append("的包名");
                builder.append("  ***************\n");
                builder.append(mApp.getSubTitle(this));
                builder.append("\n***************  ");
                builder.append(mApp.getTitle(this));
                builder.append("的签名");
                builder.append("  ***************\n");
                builder.append(mSignView.getInputText());
                String rusult = builder.toString();
                Log.i(TAG, rusult);
                break;
            default:
                ViewParent parent = v.getParent();
                if (parent != null && (parent instanceof EditTextPro)) {
                    onClick((View) parent);
                }
                break;
        }
    }
}
