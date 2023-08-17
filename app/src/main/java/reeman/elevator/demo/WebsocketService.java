package reeman.elevator.demo;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.WebSocketListener;
import okhttp3.logging.HttpLoggingInterceptor;


public class WebsocketService extends Service {

    private final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();

    private final OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .callTimeout(20, TimeUnit.SECONDS)
            .pingInterval(40, TimeUnit.SECONDS)
            .addNetworkInterceptor(loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY))
            .build();

    public void connectWebSocket(String hostname, String token, WebSocketListener webSocketListener) {
        Request request = new Request.Builder()
                .url(hostname + "?accessToken=" + token)
                .header("Sec-WebSocket-Version", "13")
                .header("Sec-WebSocket-Protocol", "koneapi")
                .header("Upgrade", "websocket")
                .header("Connection", "Upgrade")
                .build();
        okHttpClient.newWebSocket(request, webSocketListener);
    }

    public void getToken(String username, String password, Callback callback) {
        getToken("grant_type=client_credentials&scope=application/inventory callgiving/* robotcall/*", username, password, callback);
    }

    public void getToken(String scope, String username, String password, Callback callback) {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(scope, mediaType);
        Request request = new Request.Builder()
                .url("https://dev.kone.com/api/v2/oauth2/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Authorization", Credentials.basic(username, password))
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    public void getListResources(String token, Callback callback) {
        Request request = new Request.Builder()
                .url("https://dev.kone.com/api/v2/application/self/resources")
                .addHeader("Authorization", "Bearer " + token)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String createNotificationChannel() {
        NotificationChannel notificationChannel = new NotificationChannel("reeman.service", "WebsocketService", NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.setLightColor(Color.BLUE);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        return "reeman.service";
    }

    private void startForeground() {
        String channelId = "";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = createNotificationChannel();
        }
        Notification notification = new NotificationCompat.Builder(this, channelId)
                .setOngoing(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setPriority(NotificationManager.IMPORTANCE_MIN)
                .setCategory(Notification.CATEGORY_SERVICE)
                .build();
        startForeground(1, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground();
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new WebsocketServiceBinder();
    }

    public class WebsocketServiceBinder extends Binder {
        public WebsocketService getService() {
            return WebsocketService.this;
        }
    }
}
