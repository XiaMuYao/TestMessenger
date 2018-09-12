package cn.com.m2015.testmessenger;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * ================================================
 * 作    者：夏沐尧  Github地址：https://github.com/XiaMuYaoDQX
 * 版    本：1.0
 * 创建日期： 2018/9/12
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class MessengerService extends Service {

    //此Messenger将客户端发送的消息传递给 MessengerHandler
    private Messenger messenger = new Messenger(new MessengerHandler());

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 10086:
                    Log.i("swifter", "服务端收到消息：" + msg.getData().getString("msg"));
                    Messenger replyMessenger = msg.replyTo;
                    Message replyMessage = Message.obtain();
                    Bundle bundle = new Bundle();
                    bundle.putString("reply", "服务端已经收到消息");
                    replyMessage.what = 10010;
                    replyMessage.setData(bundle);
                    try {
                        replyMessenger.send(replyMessage);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent mIntent) {
        return messenger.getBinder();
    }
}
