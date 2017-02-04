package hdfg159.qqsendpoke.hook;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import hdfg159.qqsendpoke.utilities.XSharedPreferencesUtil;

import static de.robv.android.xposed.XposedBridge.invokeOriginalMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Project:QQSendPoke
 * Package:hdfg159.qqsendpoke
 * Created by hdfg159 on 2016/7/24 18:32.
 */
public class PokeMsgHook implements IXposedHookLoadPackage {

    private static final String QQ_PACKAGE_NAME = "com.tencent.mobileqq";
    private static final String QQ_SEND_POKE_PACKAGENAME = "hdfg159.qqsendpoke";
    private static final String CLASSNAME_MAIN = "hdfg159.qqsendpoke.view.Main";
    private static final String CLASSNAME_POKE_ITEM_HELPER = "com.tencent.mobileqq.activity.aio.item.PokeItemHelper";
    private static final String CLASSNAME_QQ_APP_INTERFACE = "com.tencent.mobileqq.app.QQAppInterface";
    private static final String CLASSNAME_SESSION_INFO = "com.tencent.mobileqq.activity.aio.SessionInfo";
    private static final String CLASSNAME_CHAT_ACTIVITY_FACADE = "com.tencent.mobileqq.activity.ChatActivityFacade";
    private static final String METHOD_NAME_IS_ALLOW_POKE = "a";
    private static final String METHOD_NAME_POKE = "b";

    @Override
    public void handleLoadPackage(final LoadPackageParam loadPackageParam) throws Throwable {
        if (isQQSendPokePackage(loadPackageParam))
            findAndHookMethod(CLASSNAME_MAIN, loadPackageParam.classLoader, "isModuleActive", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(true);
                }
            });
        if (isQQPackage(loadPackageParam))
            findAndHookMethod(Application.class, "dispatchActivityResumed", Activity.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(final MethodHookParam param) throws Throwable {
                    sendPokeHook(loadPackageParam);
                }
            });
    }

    private void sendPokeHook(LoadPackageParam loadPackageParam) {
        unlockPokeTimes(loadPackageParam);
        pokeMoreTimes(loadPackageParam);
    }

    private void unlockPokeTimes(LoadPackageParam loadPackageParam) {
        findAndHookMethod(CLASSNAME_POKE_ITEM_HELPER, loadPackageParam.classLoader, METHOD_NAME_IS_ALLOW_POKE, CLASSNAME_QQ_APP_INTERFACE, CLASSNAME_SESSION_INFO, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                if (isEnableModule())
                    param.setResult(true);
            }
        });
    }

    private void pokeMoreTimes(final LoadPackageParam loadPackageParam) {
        findAndHookMethod(CLASSNAME_CHAT_ACTIVITY_FACADE, loadPackageParam.classLoader, METHOD_NAME_POKE, CLASSNAME_QQ_APP_INTERFACE, Context.class, CLASSNAME_SESSION_INFO, new XC_MethodReplacement() {

            @Override
            protected Object replaceHookedMethod(final MethodHookParam methodHookParam) throws Throwable {
                if (isEnableModule()) {
                    refreshSetting();
                    long pokeTimes = getPokeTimes();
                    ExecutorService singleThread = Executors.newSingleThreadExecutor();
                    for (long i = 0; i < pokeTimes; i++) {
                        singleThread.execute(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    invokeOriginalMethod(methodHookParam.method, null, methodHookParam.args);
                                    Thread.sleep(getMessageMillis());
                                } catch (Throwable t) {
                                    XposedBridge.log(t);
                                }
                            }
                        });
                    }
                } else {
                    invokeOriginalMethod(methodHookParam.method, null, methodHookParam.args);
                }
                return null;
            }
        });
    }

    private long getMessageMillis() {
        String time = XSharedPreferencesUtil.getString(XSharedPreferencesUtil.KEY_TIME_INTERVAL, "0");
        if (TextUtils.isEmpty(time))
            time = "0";
        Long second = Long.parseLong(time);
        return second * 1000;
    }

    private long getPokeTimes() {
        String time = XSharedPreferencesUtil.getString(XSharedPreferencesUtil.KEY_POKE_TIMES, "1");
        if (TextUtils.isEmpty(time))
            time = "1";
        return Long.parseLong(time);
    }

    private boolean isEnableModule() {
        refreshSetting();
        return XSharedPreferencesUtil.getBoolean(XSharedPreferencesUtil.KEY_IS_ENABLE, true);
    }

    private void refreshSetting() {
        XSharedPreferencesUtil.hasFileChangedAndReload();
    }

    private boolean isQQPackage(LoadPackageParam loadPackageParam) {
        return loadPackageParam.packageName.equals(QQ_PACKAGE_NAME);
    }

    private boolean isQQSendPokePackage(LoadPackageParam loadPackageParam) {
        return loadPackageParam.packageName.equals(QQ_SEND_POKE_PACKAGENAME);
    }
}
