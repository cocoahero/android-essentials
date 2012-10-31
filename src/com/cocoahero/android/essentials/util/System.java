package com.cocoahero.android.essentials.util;

import android.os.Build;

public class System {

    public static boolean isVersion(int version) {
        return Build.VERSION.SDK_INT >= version;
    }

    public static boolean isEclair() {
        return isVersion(Build.VERSION_CODES.ECLAIR);
    }

    public static boolean isFroyo() {
        return isVersion(Build.VERSION_CODES.FROYO);
    }
    
    public static boolean isGingerbread() {
        return isVersion(Build.VERSION_CODES.GINGERBREAD);
    }
    
    public static boolean isHoneycomb() {
        return isVersion(Build.VERSION_CODES.HONEYCOMB);
    }
    
    public static boolean isIceCreamSandwich() {
        return isVersion(Build.VERSION_CODES.ICE_CREAM_SANDWICH);
    }
    
    public static boolean isJellyBean() {
        return isVersion(Build.VERSION_CODES.JELLY_BEAN);
    }
}
