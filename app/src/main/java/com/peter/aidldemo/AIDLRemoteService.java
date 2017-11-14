package com.peter.aidldemo;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.RemoteException;

import com.peter.aidldemo.aidl.IMyAidlInterface;

import java.io.FileDescriptor;
import java.io.IOException;

/**
 * @author Peter
 */
public class AIDLRemoteService extends Service {
    private MediaPlayer mediaPlayer;

    public AIDLRemoteService() {
    }

    private final IMyAidlInterface.Stub mBinder = new IMyAidlInterface.Stub() {

        @Override
        public void play() throws RemoteException {
            try {
                if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.prepare();
                    mediaPlayer.start();
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
                }
            } catch (Exception e) {
                e.printStackTrace();
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
}
