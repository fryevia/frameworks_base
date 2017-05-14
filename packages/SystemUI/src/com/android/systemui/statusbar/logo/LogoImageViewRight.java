/*
 * Copyright (C) 2018 crDroid Android Project
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
 * limitations under the License.
 */

package com.android.systemui.statusbar.logo;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.provider.Settings;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.android.systemui.R;
import com.android.systemui.Dependency;
import com.android.systemui.plugins.DarkIconDispatcher;

public class LogoImageViewRight extends ImageView {

    private Context mContext;

    private boolean mAttached;
    private boolean mDerpLogo;
    private int mDerpLogoPosition;
    private int mDerpLogoStyle;
    private int mTintColor = Color.WHITE;
    private final Handler mHandler = new Handler();
    private ContentResolver mContentResolver;

    private class SettingsObserver extends ContentObserver {
        SettingsObserver(Handler handler) {
            super(handler);
        }

        void observe() {
            ContentResolver resolver = mContext.getContentResolver();
            resolver.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.STATUS_BAR_LOGO), false, this);
            resolver.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.STATUS_BAR_LOGO_POSITION), false, this);
            resolver.registerContentObserver(Settings.System.getUriFor(
                    Settings.System.STATUS_BAR_LOGO_STYLE), false, this);
        }

        @Override
        public void onChange(boolean selfChange) {
            updateSettings();
        }
    }

    private SettingsObserver mSettingsObserver = new SettingsObserver(mHandler);

    public LogoImageViewRight(Context context) {
        this(context, null);
    }

    public LogoImageViewRight(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LogoImageViewRight(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        final Resources resources = getResources();
        mContext = context;
        mSettingsObserver.observe();
        updateSettings();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mAttached) {
            return;
        }
        mAttached = true;
        Dependency.get(DarkIconDispatcher.class).addDarkReceiver(this);
        updateSettings();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (!mAttached) {
            return;
        }
        mAttached = false;
        Dependency.get(DarkIconDispatcher.class).removeDarkReceiver(this);
    }

    public void onDarkChanged(Rect area, float darkIntensity, int tint) {
        mTintColor = DarkIconDispatcher.getTint(area, this, tint);
        if (mDerpLogo && mDerpLogoPosition == 1) {
            updateDerpLogo();
        }
    }

    public void updateDerpLogo() {
        Drawable drawable = null;

        if (!mDerpLogo || mDerpLogoPosition == 0) {
            setImageDrawable(null);
            setVisibility(View.GONE);
            return;
        } else {
            setVisibility(View.VISIBLE);
        }

        if (mDerpLogoStyle == 0) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_derp_logo);
        } else if (mDerpLogoStyle == 1) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_android_logo);
        } else if (mDerpLogoStyle == 2) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_apple_logo);
        } else if (mDerpLogoStyle == 3) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_beats);
        } else if (mDerpLogoStyle == 4) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_biohazard);
        } else if (mDerpLogoStyle == 5) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_blackberry);
        } else if (mDerpLogoStyle == 6) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_blogger);
        } else if (mDerpLogoStyle == 7) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_bomb);
        } else if (mDerpLogoStyle == 8) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_brain);
        } else if (mDerpLogoStyle == 9) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_cake);
        } else if (mDerpLogoStyle == 10) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_cannabis);
        } else if (mDerpLogoStyle == 11) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_death_star);
        } else if (mDerpLogoStyle == 12) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_emoticon);
        } else if (mDerpLogoStyle == 13) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_emoticon_cool);
        } else if (mDerpLogoStyle == 14) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_emoticon_dead);
        } else if (mDerpLogoStyle == 15) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_emoticon_devil);
        } else if (mDerpLogoStyle == 16) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_emoticon_happy);
        } else if (mDerpLogoStyle == 17) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_emoticon_neutral);
        } else if (mDerpLogoStyle == 18) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_emoticon_poop);
        } else if (mDerpLogoStyle == 19) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_emoticon_sad);
        } else if (mDerpLogoStyle == 20) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_emoticon_tongue);
        } else if (mDerpLogoStyle == 21) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_fire);
        } else if (mDerpLogoStyle == 22) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_flask);
        } else if (mDerpLogoStyle == 23) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_gender_female);
        } else if (mDerpLogoStyle == 24) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_gender_male);
        } else if (mDerpLogoStyle == 25) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_gender_male_female);
        } else if (mDerpLogoStyle == 26) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_ghost);
        } else if (mDerpLogoStyle == 27) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_google);
        } else if (mDerpLogoStyle == 28) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_guitar_acoustic);
        } else if (mDerpLogoStyle == 29) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_guitar_electric);
        } else if (mDerpLogoStyle == 30) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_heart);
        } else if (mDerpLogoStyle == 31) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_human_female);
        } else if (mDerpLogoStyle == 32) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_human_male);
        } else if (mDerpLogoStyle == 33) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_human_male_female);
        } else if (mDerpLogoStyle == 34) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_incognito);
        } else if (mDerpLogoStyle == 35) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_ios_logo);
        } else if (mDerpLogoStyle == 36) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_linux);
        } else if (mDerpLogoStyle == 37) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_lock);
        } else if (mDerpLogoStyle == 38) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_music);
        } else if (mDerpLogoStyle == 39) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_ninja);
        } else if (mDerpLogoStyle == 40) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_pac_man);
        } else if (mDerpLogoStyle == 41) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_peace);
        } else if (mDerpLogoStyle == 42) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_robot);
        } else if (mDerpLogoStyle == 43) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_skull);
        } else if (mDerpLogoStyle == 44) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_smoking);
        } else if (mDerpLogoStyle == 45) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_wallet);
        } else if (mDerpLogoStyle == 46) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_windows);
        } else if (mDerpLogoStyle == 47) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_xbox);
        } else if (mDerpLogoStyle == 48) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_xbox_controller);
        } else if (mDerpLogoStyle == 49) {
            drawable = mContext.getResources().getDrawable(R.drawable.ic_yin_yang);
        }

        setImageDrawable(null);

        clearColorFilter();

        drawable.setTint(mTintColor);
        setImageDrawable(drawable);
    }

    public void updateSettings() {
        ContentResolver resolver = mContext.getContentResolver();
        mDerpLogo = Settings.System.getInt(resolver,
                Settings.System.STATUS_BAR_LOGO, 0) == 1;
        mDerpLogoPosition = Settings.System.getInt(resolver,
                Settings.System.STATUS_BAR_LOGO_POSITION, 0);
        mDerpLogoStyle = Settings.System.getInt(resolver,
                Settings.System.STATUS_BAR_LOGO_STYLE, 0);
        updateDerpLogo();
    }
}
