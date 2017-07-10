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

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ishow.common.adapter.ListAdapter;
import com.ishow.common.utils.image.loader.ImageLoader;
import com.ishow.noahark.R;
import com.ishow.noahark.entries.ISelect;


class SelectAdapter<T extends ISelect> extends ListAdapter<T, SelectAdapter.ViewHolder> {
    private int mGravity;

    SelectAdapter(Context context) {
        this(context, Gravity.START | Gravity.CENTER_VERTICAL);
    }


    SelectAdapter(Context context, int gravity) {
        super(context);
        mGravity = gravity;
    }

    private boolean hasArrow = true;

    void setHasArrow(boolean hasArrow) {
        this.hasArrow = hasArrow;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int type) {
        View item = mLayoutInflater.inflate(R.layout.item_dialog_selet, parent, false);
        return new ViewHolder(item, type);
    }

    @Override
    public void onBindViewHolder(ViewGroup parent, SelectAdapter.ViewHolder holder, int position, int type) {
        ISelect entry = getItem(position);

        holder.image.setImageDrawable(entry.getImage(mContext));

        holder.title.setText(entry.getTitle(mContext));
        if (TextUtils.isEmpty(entry.getSubTitle(mContext))) {
            holder.subTitle.setVisibility(View.GONE);
        } else {
            holder.subTitle.setVisibility(View.VISIBLE);
        }
        holder.subTitle.setText(entry.getSubTitle(mContext));
        holder.arrow.setVisibility(hasArrow ? View.VISIBLE : View.GONE);
    }

    class ViewHolder extends ListAdapter.Holder {
        TextView title;
        TextView subTitle;
        ImageView arrow;
        ImageView image;

        ViewHolder(View item, int type) {
            super(item, type);
            title = (TextView) item.findViewById(R.id.title);
            title.setGravity(mGravity);
            subTitle = (TextView) item.findViewById(R.id.sub_title);
            subTitle.setGravity(mGravity);
            image = (ImageView) item.findViewById(R.id.image);
            arrow = (ImageView) item.findViewById(R.id.arrow);
        }
    }
}