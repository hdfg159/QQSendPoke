package hdfg159.qqsendpoke.hook;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

/**
 * Project:QQSendPoke
 * Package:hdfg159.qqsendpoke.hook
 * Created by hdfg159 on 2017/2/6 23:14.
 */
class MyPackageHook {
    private static final String METHOD_NAME_IS_MODULE_ACTIVE = "isModuleActive";
    private final XC_LoadPackage.LoadPackageParam loadPackageParam;
    private static final String QQ_SEND_POKE_PACKAGENAME = "hdfg159.qqsendpoke";
    private static final String CLASSNAME_MAIN = "hdfg159.qqsendpoke.view.Main";

    public MyPackageHook(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        this.loadPackageParam = loadPackageParam;
    }

    public void initAndHook() {
        if (isQQSendPokePackage(loadPackageParam)) {
            findAndHookMethod(CLASSNAME_MAIN, loadPackageParam.classLoader, METHOD_NAME_IS_MODULE_ACTIVE, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    param.setResult(true);
                }
            });
        }
    }

    private boolean isQQSendPokePackage(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        return loadPackageParam.packageName.equals(QQ_SEND_POKE_PACKAGENAME);
    }
}
