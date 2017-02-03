package hdfg159.qqsendpoke.utilities;

import de.robv.android.xposed.XSharedPreferences;

public class XSharedPreferencesUtil {

    public static final String PACKAGENAME = "hdfg159.qqsendpoke";
    public static XSharedPreferences xSharedPreferences = new XSharedPreferences(PACKAGENAME);

    public static XSharedPreferences getXSharePreferences() {
        return xSharedPreferences;
    }

    public static void reload() {
        xSharedPreferences.reload();
    }

    public static boolean hasFileChanged() {
        return xSharedPreferences.hasFileChanged();
    }

    public static void hasFileChangedAndReload() {
        if (xSharedPreferences.hasFileChanged()) {
            reload();
        }
    }

    public static String getString(String key, String defValue) {
        return xSharedPreferences.getString(key, defValue);
    }

    public static boolean getBoolean(String key, boolean defValue) {
        return xSharedPreferences.getBoolean(key, defValue);
    }
}
