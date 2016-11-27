package hdfg159.qqsendpoke.hook;

import android.app.Activity;
import android.app.Application;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Project:QQSendPoke
 * Package:hdfg159.qqsendpoke
 * Created by hdfg159 on 2016/7/24 18:32.
 */
public class PokeMsgHook implements IXposedHookLoadPackage {

    private static final String QQ_PACKAGE_NAME = "com.tencent.mobileqq";
    private static final String POKE_ITEM_HELPER = "com.tencent.mobileqq.activity.aio.item.PokeItemHelper";
    private static final String METHOD_NAME_IS_ALLOW_POKE = "a";
    private static final String PARAM_QQ_APP_INTERFACE = "com.tencent.mobileqq.app.QQAppInterface";
    private static final String PARAM_SESSION_INFO = "com.tencent.mobileqq.activity.aio.SessionInfo";

    @Override
    public void handleLoadPackage(final LoadPackageParam loadPackageParam) throws Throwable {
        if (isQQPackage(loadPackageParam)) {
            findAndHookMethod(Application.class, "dispatchActivityResumed", Activity.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    findAndHookMethod(POKE_ITEM_HELPER, loadPackageParam.classLoader, METHOD_NAME_IS_ALLOW_POKE, PARAM_QQ_APP_INTERFACE, PARAM_SESSION_INFO, new XC_MethodHook() {
                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            param.setResult(true);
                        }
                    });
                }
            });
        }
    }

    private boolean isQQPackage(LoadPackageParam loadPackageParam) {
        return loadPackageParam.packageName.equals(QQ_PACKAGE_NAME);
    }

}
