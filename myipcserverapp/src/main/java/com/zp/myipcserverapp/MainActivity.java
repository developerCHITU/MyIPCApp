package com.zp.myipcserverapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    private IPCMsg mMIPCMsg;


    //由AIDL文件生成的Java类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.send);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPICMsg();
            }
        });
    }

    private boolean isBind;
    /**
     * 发送进程间消息
     */
    private void sendPICMsg() {

        Log.i("-----connect bind----",isBind+"");
        //未绑定服务时重新绑定
        if(!isBind){
            attemptToConnectService();
            return;
        }
        if(mMIPCMsg==null)return;
        try {
            mMIPCMsg.setValue("收到消息。。。。");
        } catch (RemoteException e) {

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        attemptToConnectService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(mServiceConnection);
    }

    /**
     * 尝试连接服务
     */
    private void attemptToConnectService() {
        Intent intent = new Intent();
        intent.setAction("ipc");
        intent.setPackage("com.zp.myipcapp");
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mMIPCMsg = IPCMsg.Stub.asInterface(service);
            isBind = true;
            Log.i("-----connect-----","success");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
            Log.i("-----connect-----","fail");
        }
    };

}
