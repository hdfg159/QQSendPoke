package hdfg159.qqsendpoke.view;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import hdfg159.qqsendpoke.R;
import hdfg159.qqsendpoke.utilities.DialogUtil;
import hdfg159.qqsendpoke.utilities.ToastUtil;

public class Main extends Activity implements View.OnClickListener {
    private Button btnDisplayIcon;

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
        getSharedPreferences("SendPokeMain", Context.MODE_PRIVATE).edit().putBoolean("isDisplayIcon", displayIcon).commit();
    }

    private void initView() {
        TextView mainContent = (TextView) findViewById(R.id.mainContent);
        Linkify.addLinks(mainContent, Linkify.ALL);
        btnDisplayIcon = (Button) findViewById(R.id.btndisplayicon);
        btnDisplayIcon.setOnClickListener(this);
        Button btnAbout = (Button) findViewById(R.id.btnabout);
        btnAbout.setOnClickListener(this);
        ImageView payPicture = (ImageView) findViewById(R.id.imageView);
        payPicture.setOnClickListener(this);
        Button btnSetting = (Button) findViewById(R.id.btnsetting);
        btnSetting.setOnClickListener(this);
        setBtnTitle();
    }

    private ComponentName getIconComponentName() {
        return new ComponentName(this, "hdfg159.qqsendpoke.view.Main-Alias");
    }

    private boolean isDisplayIcon() {
        return getSharedPreferences("SendPokeMain", Context.MODE_PRIVATE).getBoolean("isDisplayIcon", true);
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
            case R.id.btnsetting:
                openSetting();
                break;
            default:
                break;
        }
    }

    private void openSetting() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    private void savePicture() {
        Bitmap alipayPicture = BitmapFactory.decodeResource(getResources(), R.drawable.wechatpay);
        MediaStore.Images.Media.insertImage(getContentResolver(), alipayPicture, "time:" + System.currentTimeMillis(), "time:" + System.currentTimeMillis());
        MediaScannerConnection.scanFile(this, new String[]{Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getPath(), Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getPath()}, null, null);
        DialogUtil.showAlertTwo(this, getString(R.string.thanks), getString(R.string.payPictureIsSave), "确定", null, getString(R.string.supportalipay), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://QR.ALIPAY.COM/FKX01070B8ASXMQNRFY528"));
                startActivity(intent);
            }
        });
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
