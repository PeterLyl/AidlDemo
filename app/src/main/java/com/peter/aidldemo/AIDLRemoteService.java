package com.peter.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

import com.peter.aidldemo.aidl.IMyAidlInterface;
import com.peter.aidldemo.aidl.IMyAidlInterfaceCallback;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * @author Peter
 */
public class AIDLRemoteService extends Service {
    private MediaPlayer mediaPlayer;
    final RemoteCallbackList<IMyAidlInterfaceCallback> mCallbacks = new RemoteCallbackList<>();


    public AIDLRemoteService() {
    }

    private final IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {

        @Override
        public void play() throws RemoteException {
            try {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    callback(0);
                } else {
                    callback(1);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void stop() throws RemoteException {
            try {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    callback(2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void registerCallback(IMyAidlInterfaceCallback cb) throws RemoteException {
            if (cb != null) {
                mCallbacks.register(cb);
            }
        }

        @Override
        public void unregisterCallback(IMyAidlInterfaceCallback cb) throws RemoteException {
            if (cb != null) {
                mCallbacks.unregister(cb);
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            try {
                FileDescriptor fileDescriptor = getResources().openRawResourceFd(R.raw.music).getFileDescriptor();
                mediaPlayer.setDataSource(fileDescriptor);
                mediaPlayer.setLooping(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return mBinder;
    }

    void callback(int val) {
        final int N = mCallbacks.beginBroadcast();
        for (int i = 0; i < N; i++) {
            try {
                mCallbacks.getBroadcastItem(i).actionPerformed(val);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        mCallbacks.finishBroadcast();
    }
}