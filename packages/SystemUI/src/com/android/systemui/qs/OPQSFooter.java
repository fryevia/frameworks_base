/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package com.android.systemui.qs;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.android.keyguard.CarrierText;
import com.android.systemui.Dependency;
import com.android.systemui.R;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.DataUsageView;
import com.android.systemui.statusbar.phone.SettingsButton;

public class OPQSFooter extends LinearLayout {

    protected View mEdit;
    protected TouchAnimator mFooterAnimator;
    protected TouchAnimator mCarrierTextAnimator;
    private FrameLayout mFooterActions;
    private SettingsButton mSettingsButton;
    private DataUsageView mDataUsageView;
    private CarrierText mCarrierText;
    private boolean mIsLandscape = false;
    private boolean mIsQQSPanel = false;

    public OPQSFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mEdit = findViewById(R.id.edit);
        mSettingsButton = findViewById(R.id.settings_button);
        mFooterActions = findViewById(R.id.op_qs_footer_actions);
        mCarrierText = findViewById(R.id.qs_carrier_text);
        mDataUsageView = findViewById(R.id.data_usage_view);
        mDataUsageView.setVisibility(View.GONE);
        mIsLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
        mFooterAnimator = createFooterAnimator();
        mCarrierTextAnimator = createCarrierTextAnimator();
    }

    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setOrientation(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE);
    }

    public void setExpansion(float headerExpansionFraction) {
        if (mFooterAnimator != null) {
            mFooterAnimator.setPosition(headerExpansionFraction);
        }
        if (mCarrierTextAnimator != null) {
            mCarrierTextAnimator.setPosition(headerExpansionFraction);
        }
    }

    public void setIsQQSPanel(boolean isQQS) {
        mIsQQSPanel = isQQS;
        setOrientation(mIsLandscape);
    }

    public void setExpanded(boolean expanded) {
        if (mDataUsageView != null) {
            mDataUsageView.setVisibility(expanded ? View.VISIBLE : View.GONE);
            if (expanded) {
                mDataUsageView.updateUsage();
                mDataUsageView.setOnClickListener(v -> startDataUsageActivity());
            }
        }
        if (mEdit != null) {
            mEdit.setVisibility(expanded ? View.VISIBLE : View.GONE);
        }
    }

    @Nullable
    private TouchAnimator createFooterAnimator() {
        TouchAnimator.Builder builder = new TouchAnimator.Builder()
                .addFloat(mEdit, "alpha", 0, 0, 1);
        if (mIsLandscape) {
            builder = builder.addFloat(mSettingsButton, "alpha", 0, 0, 1)
                    .setStartDelay(0.5f);
        }
        return builder.build();
    }

    @Nullable
    private TouchAnimator createCarrierTextAnimator() {
        TouchAnimator.Builder builder = new TouchAnimator.Builder();
        if (mIsLandscape) {
            builder = builder.addFloat(mDataUsageView, "alpha", 0, 0, 1)
                    .addFloat(mCarrierText, "alpha", 0, 0, 0)
                    .setStartDelay(0.5f);
        } else {
            builder = builder.addFloat(mDataUsageView, "alpha", 0, 0, 1)
                    .addFloat(mCarrierText, "alpha", 1, 0, 0);
        }
        return builder.build();
    }

    public View getSettingsButton() {
        return mSettingsButton;
    }

    public View getEditButton() {
        return mEdit;
    }

    public View getFooterActions() {
        return mFooterActions;
    }

    private void setOrientation(boolean isLandscape) {
        if (mIsLandscape != isLandscape) {
            mIsLandscape = isLandscape;
            mSettingsButton.setAlpha(1.0f);
            mFooterAnimator = createFooterAnimator();
            mCarrierTextAnimator = createCarrierTextAnimator();
        }
        mFooterActions.setVisibility(mIsLandscape && mIsQQSPanel ? View.GONE : View.VISIBLE);
    }

    private void startDataUsageActivity() {
        Intent intent = new Intent();
        intent.setClassName("com.android.settings",
                "com.android.settings.Settings$DataUsageSummaryActivity");
        Dependency.get(ActivityStarter.class).startActivity(intent, true /* dismissShade */);
    }
}
