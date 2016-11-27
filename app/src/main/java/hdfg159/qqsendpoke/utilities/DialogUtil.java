package hdfg159.qqsendpoke.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.WindowManager;

/**
 * 对话框工具
 */
public class DialogUtil {
    /**
     * 显示一个对话框
     *
     * @param context                    上下文对象，最好给Activity，否则需要android.permission.SYSTEM_ALERT_WINDOW
     * @param title                      标题
     * @param message                    消息
     * @param confirmButton              确认按钮
     * @param confirmButtonClickListener 确认按钮点击监听器
     * @param centerButton               中间按钮
     * @param centerButtonClickListener  中间按钮点击监听器
     * @param cancelButton               取消按钮
     * @param cancelButtonClickListener  取消按钮点击监听器
     * @param onShowListener             显示监听器,当对话框调用show()方法的时候触发。
     * @param cancelable                 是否允许通过点击返回按钮或者点击对话框之外的位置关闭对话框
     * @param onCancelListener           取消监听器,当对话框调用dismiss()方法的时候触发。
     * @param onDismissListener          销毁监听器,当对话框调用cancel()方法的时候触发。
     * @return 对话框
     */
    public static AlertDialog showAlert(Context context, String title, String message, String confirmButton, DialogInterface.OnClickListener confirmButtonClickListener, String centerButton, DialogInterface.OnClickListener centerButtonClickListener, String cancelButton, DialogInterface.OnClickListener cancelButtonClickListener, DialogInterface.OnShowListener onShowListener, boolean cancelable, DialogInterface.OnCancelListener onCancelListener, DialogInterface.OnDismissListener onDismissListener) {
        AlertDialog.Builder promptBuilder = new AlertDialog.Builder(context);
        if (title != null) {
            promptBuilder.setTitle(title);
        }
        if (message != null) {
            promptBuilder.setMessage(message);
        }
        if (confirmButton != null) {
            promptBuilder.setPositiveButton(confirmButton,
                    confirmButtonClickListener);
        }
        if (centerButton != null) {
            promptBuilder.setNeutralButton(centerButton,
                    centerButtonClickListener);
        }
        if (cancelButton != null) {
            promptBuilder.setNegativeButton(cancelButton,
                    cancelButtonClickListener);
        }
        promptBuilder.setCancelable(cancelable);
        if (cancelable) {
            promptBuilder.setOnCancelListener(onCancelListener);
        }
        AlertDialog alertDialog = promptBuilder.create();
        if (!(context instanceof Activity)) {
            alertDialog.getWindow()
                    .setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        alertDialog.setOnDismissListener(onDismissListener);
        alertDialog.setOnShowListener(onShowListener);
        alertDialog.show();
        return alertDialog;
    }


    /**
     * 显示一个确认和取消按钮的对话框
     *
     * @param context                    上下文对象，最好给Activity，否则需要android.permission.SYSTEM_ALERT_WINDOW
     * @param title                      标题
     * @param message                    消息
     * @param confirmButton              确认按钮
     * @param confirmButtonClickListener 确认按钮点击监听器
     * @param cancelButton               取消按钮
     * @param cancelButtonClickListener  取消按钮点击监听器
     * @return 对话框
     */
    public static AlertDialog showAlertTwo(Context context, String title, String message, String confirmButton, DialogInterface.OnClickListener confirmButtonClickListener, String cancelButton, DialogInterface.OnClickListener cancelButtonClickListener) {
        return showAlert(context, title, message, confirmButton,
                confirmButtonClickListener, null, null, cancelButton,
                cancelButtonClickListener, null, true, null, null);
    }


    /**
     * 显示只有一个按钮的提示框
     *
     * @param context       上下文对象，最好给Activity，否则需要android.permission.SYSTEM_ALERT_WINDOW
     * @param message       提示的消息
     * @param confirmButton 确定按钮的名字
     */
    public static AlertDialog showPrompt(Context context, String title, String message, String confirmButton) {
        return showAlert(context, title, message, confirmButton, null, null, null, null, null, null, true, null, null);
    }

    /**
     * 显示有最左边和右边的按钮的提示框
     *
     * @param context       上下文对象，最好给Activity，否则需要android.permission.SYSTEM_ALERT_WINDOW
     * @param message       提示的消息
     * @param confirmButton 确定按钮的名字
     */
    public static AlertDialog showAlertlr(Context context, String title, String message, String confirmButton, DialogInterface.OnClickListener confirmButtonClickListener, String centerButton, DialogInterface.OnClickListener centerButtonClickListener) {
        return showAlert(context, title, message, confirmButton, confirmButtonClickListener, centerButton, centerButtonClickListener, null, null, null, false, null, null);
    }
}
