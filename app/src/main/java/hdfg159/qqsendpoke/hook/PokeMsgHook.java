package hdfg159.qqsendpoke.hook;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

/**
 * Project:QQSendPoke
 * Package:hdfg159.qqsendpoke
 * Created by hdfg159 on 2016/7/24 18:32.
 */
public class PokeMsgHook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(LoadPackageParam loadPackageParam) throws Throwable {
        new MyPackageHook(loadPackageParam).initAndHook();
        new QQSendPokeHook(loadPackageParam).initAndHook();
    }

}
