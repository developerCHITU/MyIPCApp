package com.zp.myipcapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.zp.myipcserverapp.IPCMsg;

/**
 * created by zpp on 2018/4/13 11:29
 */
public class IPCService extends Service {

    private String mIPCContent;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIPCMsg;
    }

    private final IPCMsg.Stub mIPCMsg = new IPCMsg.Stub() {
        @Override
        public void setValue(String name) throws RemoteException {

            Log.i("-----connect msg-----",name);
            mIPCContent = name;
        }

        @Override
        public String getValue() throws RemoteException {

            return mIPCContent;
        }
    };
}