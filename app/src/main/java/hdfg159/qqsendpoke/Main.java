package hdfg159.qqsendpoke;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hdfg159.qqsendpoke.utilities.DialogUtil;
import hdfg159.qqsendpoke.utilities.SharedPreferencesUtil;
import hdfg159.qqsendpoke.utilities.ToastUtil;

public class Main extends Activity implements View.OnClickListener {
    private Button btnDisplayIcon, btnAbout;
    private ImageView payPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toggleDisplayIcon(isDisplayIcon());
        initView();
    }

    private void toggleDisplayIcon(boolean displayIcon) {
        writeInSP(displayIcon);
        PackageManager packageManager = getPackageManager();
        int state = displayIcon ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        packageManager.setComponentEnabledSetting(getIconComponentName(), state,
                PackageManager.DONT_KILL_APP);
    }

    private void writeInSP(boolean displayIcon) {
        SharedPreferencesUtil.putBoolean(this, "isDisplayIcon", displayIcon);
    }

    private void initView() {
        TextView mainContent = (TextView) findViewById(R.id.mainContent);
        Linkify.addLinks(mainContent, Linkify.ALL);
        btnDisplayIcon = (Button) findViewById(R.id.btndisplayicon);
        btnDisplayIcon.setOnClickListener(this);
        btnAbout = (Button) findViewById(R.id.btnabout);
        btnAbout.setOnClickListener(this);
        payPicture = (ImageView) findViewById(R.id.imageView);
        payPicture.setOnClickListener(this);
        setBtnTitle();
    }

    private ComponentName getIconComponentName() {
        return new ComponentName(this, "hdfg159.qqsendpoke.Main-Alias");
    }

    private boolean isDisplayIcon() {
        return SharedPreferencesUtil.getBoolean(this, "isDisplayIcon", true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btndisplayicon:
                toggleDisplayIcon(!isDisplayIcon());
                setBtnTitle();
                break;
            case R.id.btnabout:
                ToastUtil.showToast(this, R.string.aboutContent, Toast.LENGTH_LONG);
                break;
            case R.id.imageView:
                savePicture();
                break;
            default:
                break;
        }
    }

    private void savePicture() {
        Bitmap alipayPicture = BitmapFactory.decodeResource(getResources(), R.drawable.pay);
        MediaStore.Images.Media.insertImage(getContentResolver(), alipayPicture, "time:" + System.currentTimeMillis(), "time:" + System.currentTimeMillis());
        MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath(), Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath()}, null, null);
        DialogUtil.showPrompt(this, getString(R.string.thanks), getString(R.string.payPictureIsSave), "确定");
    }

    private void setBtnTitle() {
        if (isDisplayIcon()) {
            btnDisplayIcon.setText(R.string.noicon);
        } else {
            btnDisplayIcon.setText(R.string.displayicon);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.exit(0);
    }
}
