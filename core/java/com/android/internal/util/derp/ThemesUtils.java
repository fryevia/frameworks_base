/*
 * Copyright (C) 2014 The Android Open Source Project
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

package com.android.internal.util.derp;

import android.content.om.IOverlayManager;
import android.content.om.OverlayInfo;
import android.os.RemoteException;
import android.util.Log;

public class ThemesUtils {

public static final String TAG = "ThemesUtils";

    public static final String[] QS_TILE_THEMES = {
            "com.android.systemui.qstile.default",
            "com.bootleggers.qstile.trim",
            "com.bootleggers.qstile.dualtone",
            "com.bootleggers.qstile.dualtonetrim",
            "com.bootleggers.qstile.cookie",
            "com.bootleggers.qstile.deletround",
            "com.bootleggers.qstile.inktober",
            "com.bootleggers.qstile.shishunights",
            "com.bootleggers.qstile.monogradient",
            "com.bootleggers.qstile.wavey",
            "com.bootleggers.qstile.squaremedo",
            "com.bootleggers.qstile.ninja",
            "com.bootleggers.qstile.dottedcircle",
            "com.bootleggers.qstile.shishuink",
            "com.bootleggers.qstile.attemptmountain",
            "com.bootleggers.qstile.neonlike",
            "com.bootleggers.qstile.oos",
            "com.bootleggers.qstile.triangles",
            "com.bootleggers.qstile.divided",
            "com.bootleggers.qstile.cosmos",
            "com.bootleggers.qstile.gradient",
            "com.bootleggers.qstile.pureaccent"
    };

    public static final String[] STATUSBAR_HEIGHT = {
            "com.gnonymous.gvisualmod.sbh_m", // 1
            "com.gnonymous.gvisualmod.sbh_l", // 2
            "com.gnonymous.gvisualmod.sbh_xl", // 3
    };

    public static final String[] UI_RADIUS = {
            "com.gnonymous.gvisualmod.urm_r", // 1
            "com.gnonymous.gvisualmod.urm_m", // 2
            "com.gnonymous.gvisualmod.urm_l", // 3
    };

    public static final String NAVBAR_COLOR_PURP = "com.gnonymous.gvisualmod.pgm_purp";

    public static final String NAVBAR_COLOR_ORCD = "com.gnonymous.gvisualmod.pgm_orcd";

    public static final String NAVBAR_COLOR_OPRD = "com.gnonymous.gvisualmod.pgm_oprd";

    public static final String NAVBAR_COLOR_BLUE = "com.gnonymous.gvisualmod.pgm_blue";

    public static final String NAVBAR_COLOR_ROSE = "com.gnonymous.gvisualmod.pgm_rose";

    public static final String HEADER_LARGE = "com.android.theme.header.large";

    public static final String HEADER_XLARGE = "com.android.theme.header.xlarge";

    // Switch themes
    private static final String[] SWITCH_THEMES = {
        "com.android.system.switch.oneplus", // 0
        "com.android.system.switch.aosp", // 1
        "com.android.system.switch.narrow", // 2
        "com.android.system.switch.contained", // 3
        "com.android.system.switch.telegram", // 4
        "com.android.system.switch.md2", // 5
        "com.android.system.switch.retro", // 6
        "com.android.system.switch.oos", // 7
    };

    public static void updateSwitchStyle(IOverlayManager om, int userId, int switchStyle) {
        if (switchStyle == 1) {
            stockSwitchStyle(om, userId);
        } else {
            try {
                om.setEnabled(SWITCH_THEMES[switchStyle],
                        true, userId);
            } catch (RemoteException e) {
                Log.w(TAG, "Can't change switch theme", e);
            }
        }
    }

    public static void stockSwitchStyle(IOverlayManager om, int userId) {
        for (int i = 0; i < SWITCH_THEMES.length; i++) {
            String switchtheme = SWITCH_THEMES[i];
            try {
                om.setEnabled(switchtheme,
                        false /*disable*/, userId);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
