package com.dianzhi.messengerclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent=new Intent();
        //绑定的都是服务端的
        intent.setPackage("com.dianzhi.messengerserve");
        intent.setAction("com.dianzhi.server");
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void sendToServe(View view) {
        //发送给服务端
        Message message=Message.obtain();
        //这里保存一个Messenger 服务端回复客户端需要用到的
        message.replyTo=messengerClient;
        Bundle bundle=new Bundle();
        bundle.putString("toServe","i am form client");
        message.setData(bundle);
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }
    //接收服务端的回复（在发送给服务端之后的操作）
    private Messenger messengerClient=new Messenger(new MessengerClientHandler());
    private class MessengerClientHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String toClient = data.getString("toClient");
            Log.e("sssss", "handleMessageClient: "+toClient);
        }
    }
    private Messenger messenger;
    private ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            messenger=new Messenger(iBinder);
            Log.e("sssss", "onServiceConnected: ");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.e("sssss", "onServiceDisconnected: ");

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
