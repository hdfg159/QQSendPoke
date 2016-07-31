package hdfg159.qqsendpoke;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main extends Activity implements View.OnClickListener {
    private Button btnDisplayIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toggleDisplayIcon(isDisplayIcon());
        setContentView(R.layout.activity_main);
        initView();
    }

    private void toggleDisplayIcon(boolean displayIcon) {
        PackageManager packageManager = getPackageManager();
        int state = displayIcon ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        packageManager.setComponentEnabledSetting(getIconComponentName(), state,
                PackageManager.DONT_KILL_APP);
    }

    private void initView() {
        TextView mainContent = (TextView) findViewById(R.id.mainContent);
        Linkify.addLinks(mainContent, Linkify.ALL);
        btnDisplayIcon = (Button) findViewById(R.id.btndisplayicon);
        btnDisplayIcon.setOnClickListener(this);
        setBtnTitle();
    }

    private ComponentName getIconComponentName() {
        return new ComponentName(this, "hdfg159.qqsendpoke.Main-Alias");
    }

    private boolean isDisplayIcon() {
        return getPackageManager().getComponentEnabledSetting(getIconComponentName()) == PackageManager.COMPONENT_ENABLED_STATE_ENABLED;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btndisplayicon:
                toggleDisplayIcon(!isDisplayIcon());
                setBtnTitle();
                break;

            default:
                break;
        }
    }

    private void setBtnTitle() {
        if (isDisplayIcon()) {
            btnDisplayIcon.setText(R.string.noicon);
        } else {
            btnDisplayIcon.setText(R.string.displayicon);
        }
    }
}
