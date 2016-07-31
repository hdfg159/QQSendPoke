package hdfg159.qqsendpoke;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Project:QQSendPoke
 * Package:hdfg159.qqsendpoke
 * Created by hdfg159 on 2016/7/24 18:32.
 */
public class PokeMsgHook implements IXposedHookLoadPackage {

    public static final String QQ_PACKAGENAME = "com.tencent.mobileqq";
    public static final String POKE_ITEM_HELPER = "com.tencent.mobileqq.activity.aio.item.PokeItemHelper";
    public static final String QQAPP_INTERFACE = "com.tencent.mobileqq.app.QQAppInterface";
    public static final String SESSION_INFO = "com.tencent.mobileqq.activity.aio.SessionInfo";

    @Override
    public void handleLoadPackage(final LoadPackageParam loadPackageParam) throws Throwable {
        if (isQQPackage(loadPackageParam)) {
            XposedBridge.log("[QQSendPoke]:模块加载");
            findAndHookMethod(POKE_ITEM_HELPER, loadPackageParam.classLoader, "a", QQAPP_INTERFACE, SESSION_INFO, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(true);
                }
            });
        }
    }

    private boolean isQQPackage(LoadPackageParam loadPackageParam) {
        return loadPackageParam.packageName.equals(QQ_PACKAGENAME);
    }

}
