package com.hardwork.fg607.relaxfinger.view;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.system.Os;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.hardwork.fg607.relaxfinger.R;
import com.hardwork.fg607.relaxfinger.service.FloatingBallService;
import com.hardwork.fg607.relaxfinger.utils.Config;
import com.hardwork.fg607.relaxfinger.utils.FloatingBallUtils;

import net.grandcentrix.tray.TrayAppPreferences;

public class BlankActivity extends Activity {

    private Intent mIntent = null;
    private boolean mIsNew = true;
    private TrayAppPreferences mPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);

        mPreferences = FloatingBallUtils.getMultiProcessPreferences();

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        layout.setClickable(true);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                closeMenu();

            }
        });



    }

    private void closeMenu() {

        if(mIntent == null){

            mIntent = new Intent();
            mIntent.putExtra("what", Config.CLOSE_MENU);
            mIntent.setClass(BlankActivity.this, FloatingBallService.class);
        }

        startService(mIntent);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(intent.getBooleanExtra("finish",false)){

            finish();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!mIsNew&&!mPreferences.getBoolean("addBackground",false)){

            finish();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        mIsNew = false;
    }

    @Override
    public void onBackPressed() {

        closeMenu();
    }
}
