package com.incendiary.ambulanceapp.features.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.incendiary.ambulanceapp.R;
import com.incendiary.ambulanceapp.data.model.fcm.FcmNotification;
import com.incendiary.ambulanceapp.data.model.fcm.NotificationTypes;
import com.incendiary.ambulanceapp.features.main.MainAct;
import com.incendiary.ambulanceapp.features.report.detail.ReportDetailActivity;
import com.incendiary.ambulanceapp.features.report.detail.ReportDetailProps;
import com.incendiary.androidcommon.android.ContextProvider;
import com.incendiary.androidcommon.android.text.Strings;
import com.incendiary.androidcommon.etc.Logger;

import java.util.Map;

public class OganMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        logMessage(remoteMessage);

        if (remoteMessage.getNotification() != null && !Strings.isEmpty(remoteMessage.getNotification().getBody())) {
            sendNotification(remoteMessage.getNotification().getBody());
        } else {
            showDataNotification(remoteMessage);
        }
    }

    private void logMessage(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            Logger.log(Log.DEBUG, "From: %s", remoteMessage.getFrom());
            Logger.log(Log.DEBUG, "Notification Message Body: %s" + remoteMessage.getNotification().getBody());
        } else {
            Logger.log(Log.DEBUG, "Message Data Received:" + remoteMessage.toString());
        }
    }

    private void showDataNotification(RemoteMessage remoteMessage) {
        Map<String, String> data = remoteMessage.getData();
        FcmNotification notification = FcmNotification.fromRemoteMessageData(data);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_push_notification)
                .setContentTitle(notification.getNotificationTitle())
                .setContentText(notification.getNotificationMessage())
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(createAppPendingIntent(notification));

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notification.getSafeNotificationId(), notificationBuilder.build());
    }

    private void sendNotification(String messageBody) {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_push_notification)
                .setContentTitle(ContextProvider.getString(R.string.app_name))
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(createAppPendingIntent(null));

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

    private PendingIntent createAppPendingIntent(FcmNotification fcmNotification) {
        Intent intent = createNotificationIntent(this, fcmNotification);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        if (fcmNotification != null) {
            intent.putExtra(FcmNotification.class.getSimpleName(), fcmNotification);
        }

        return PendingIntent.getActivity(this, 0 /* Request code */, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private static Intent createNotificationIntent(Context context, FcmNotification fcmNotification) {
        switch (fcmNotification.getNotificationType()) {
            case NotificationTypes.COMMENT:
                return ReportDetailActivity.getCommentIntent(context, new ReportDetailProps(fcmNotification.getExtraId()));
            case NotificationTypes.REPORT:
                return ReportDetailActivity.getIntent(context, new ReportDetailProps(fcmNotification.getExtraId()));
            default:
                return new Intent(context, MainAct.class);
        }
    }
}
