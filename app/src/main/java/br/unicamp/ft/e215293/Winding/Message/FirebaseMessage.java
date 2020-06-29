package br.unicamp.ft.e215293.Winding.Message;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessage extends FirebaseMessagingService {
    public FirebaseMessage() {
    }

    @Override
    public void onNewToken(String s){
        super.onNewToken(s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage){
        System.out.println("SERVICE --> Mensagem chegou");

        if (remoteMessage.getNotification() != null){
            System.out.println("SERVICE -->"+remoteMessage.getNotification().getBody());
        }

        if (remoteMessage.getData().size() > 0){
            System.out.println("SERVICE --> DADOS: "+remoteMessage.getData());
        }
        mySendBroadcast();
    }

    private void mySendBroadcast(){
        /* Enviando um Broadcast Message */
        Intent intent = new Intent();
        intent.setAction("REDIRECTING");
        sendBroadcast(intent);
    }
}
