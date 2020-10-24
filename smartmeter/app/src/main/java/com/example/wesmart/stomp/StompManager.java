package com.example.wesmart.stomp;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;
import ua.naiksoftware.stomp.dto.LifecycleEvent;
import ua.naiksoftware.stomp.dto.StompMessage;

public class StompManager {

    private static final String LOG_TAG = "StompManager";

    private CompositeDisposable compositeDisposable;
    private ServiceHandler serviceHandler;
    private StompClient stompClient;

    public StompManager(String url) {
        compositeDisposable = new CompositeDisposable();
        HandlerThread handlerThread = new HandlerThread("ConnectionService.HandlerThread");
        handlerThread.start();
        serviceHandler = new ServiceHandler(handlerThread.getLooper());

        stompClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, url);
        Disposable disposable = stompClient.lifecycle()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(new Consumer<LifecycleEvent>() {
                    @Override
                    public void accept(LifecycleEvent lifecycleEvent) throws Exception {
                        switch (lifecycleEvent.getType()) {
                            case OPENED:
                                Log.d(LOG_TAG, "################ ONLINE " + isConnected());
                                break;
                            case ERROR:
                                Log.d(LOG_TAG, "################ ERROR");
                                try {
                                    Thread.sleep(10000);
                                } catch (InterruptedException e) {
                                    Log.d(LOG_TAG, "Thread.sleep() exception");
                                }
                                connect();
                                break;
                            case CLOSED:
                                Log.d(LOG_TAG, "################ OFFLINE");
                                break;
                        }
                    }
                });
        compositeDisposable.add(disposable);
    }

    public boolean isConnected() {
        return stompClient.isConnected();
    }

    public void connect() {
        Log.d(LOG_TAG, "Connecting...");
        serviceHandler.post(new Runnable() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "Connecting in other thread...");
                stompClient.connect();
            }
        });
    }

    public void disconnect() {
        stompClient.disconnect();
    }

    public void send(String mapping, String msg) {
        Log.d(LOG_TAG, "Sending message...");
        stompClient.send(mapping, msg).subscribe();
    }

    public void subscribeTopic(String topic, Consumer<StompMessage> onNext, Consumer<Throwable> onError) {
        Disposable disposable = stompClient.topic(topic)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .subscribe(onNext, onError);
        compositeDisposable.add(disposable);
    }

    private static class ServiceHandler extends Handler {
        public ServiceHandler(Looper looper) {
            super(looper);
        }
    }
}
