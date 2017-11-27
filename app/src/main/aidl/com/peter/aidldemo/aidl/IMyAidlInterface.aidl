// IMyAidlInterface.aidl
package com.peter.aidldemo.aidl;
import com.peter.aidldemo.aidl.IMyAidlInterfaceCallback;
// Declare any non-default types here with import statements

interface IMyAidlInterface {
    void play();
    void stop();
    void registerCallback(IMyAidlInterfaceCallback cb);
    void unregisterCallback(IMyAidlInterfaceCallback cb);

}
