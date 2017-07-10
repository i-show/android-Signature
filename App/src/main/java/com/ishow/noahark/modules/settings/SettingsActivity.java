/*
 * Copyright (C) 2016 The yuhaiyang Android Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ishow.noahark.modules.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.ishow.common.widget.TopBar;
import com.ishow.noahark.R;
import com.ishow.noahark.modules.base.AppBaseActivity;
import com.ishow.common.utils.AppUtils;
import com.ishow.common.widget.edittext.EditTextPro;
import com.ishow.noahark.modules.egg.EggActivity;

/**
 * Created by yuhaiyang on 2017/4/24.
 * 设置
 */

public class SettingsActivity extends AppBaseActivity implements
        TopBar.OnSecretListener,
        View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }


    @Override
    protected void initViews() {
        super.initViews();

        TopBar topBar = (TopBar) findViewById(R.id.top_bar);
        topBar.setOnSecretListener(this);

        EditTextPro version = (EditTextPro) findViewById(R.id.now_version);
        version.setInputText(AppUtils.getVersionName(this));
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onSecretClick(View v, int count) {
        if (count == 5) {
            Intent intent = new Intent(this, EggActivity.class);
            startActivity(intent);
        }
    }
}
