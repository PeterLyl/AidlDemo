package com.peter.aidldemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.peter.aidldemo.aidl.IMyAidlInterface;

/**
 * @author Peter
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button btnBindService;
    Button btnPlay;
    Button btnStop;
    IMyAidlInterface mMyAIDL;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMyAIDL = IMyAidlInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMyAIDL = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnBindService = (Button) findViewById(R.id.btn_bind_service);
        btnPlay = (Button) findViewById(R.id.btn_play);
        btnStop = (Button) findViewById(R.id.btn_stop);
        btnBindService.setOnClickListener(this);
        btnPlay.setOnClickListener(this);
        btnStop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_bind_service:
                Intent intent = new Intent(MainActivity.this, AIDLRemoteService.class);
                bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
                break;
            case R.id.btn_play:
                try {
                    Toast.makeText(this, "play", Toast.LENGTH_SHORT).show();
                    mMyAIDL.play();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.btn_stop:
                try {
                    Toast.makeText(this, "stop", Toast.LENGTH_SHORT).show();
                    mMyAIDL.stop();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }
}