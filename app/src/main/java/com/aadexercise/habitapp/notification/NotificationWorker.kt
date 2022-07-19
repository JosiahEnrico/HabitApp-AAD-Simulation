package com.aadexercise.habitapp.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.core.content.ContextCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.aadexercise.habitapp.ui.countdown.CountDownActivity
import com.aadexercise.habitapp.ui.detail.DetailHabitActivity
import com.aadexercise.habitapp.ui.list.HabitListActivity
import com.aadexercise.habitapp.utils.HABIT_ID
import com.aadexercise.habitapp.utils.HABIT_TITLE
import com.aadexercise.habitapp.utils.NOTIFICATION_CHANNEL_ID
import com.aadexercise.habitapp.R
import com.aadexercise.habitapp.utils.NOTIFICATION_CHANNEL_ID_NUMBER


class NotificationWorker(private val ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    private val habitId = inputData.getInt(HABIT_ID, 0)
    private val habitTitle = inputData.getString(HABIT_TITLE)

    override fun doWork(): Result {
        val prefManager = androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext)
        val shouldNotify = prefManager.getBoolean(applicationContext.getString(R.string.pref_key_notify), false)

        //TODO 12 : If notification preference on, show notification with pending intent
        if (shouldNotify){
            showAlarmNotification(context = ctx)
        }
        return Result.success()
    }

    private fun showAlarmNotification(context: Context) {
        val title = habitTitle
        val message = context.getString(R.string.notify_content)
        val notifIntent = Intent(context, HabitListActivity::class.java)

        val taskStackBuilder : android.app.TaskStackBuilder = android.app.TaskStackBuilder.create(context)
        taskStackBuilder.addParentStack(CountDownActivity::class.java)
        taskStackBuilder.addNextIntent(notifIntent)

        val pendingIntent: PendingIntent? = pendingIntent(habitId)
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val builder = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "notify-habit",
                NotificationManager.IMPORTANCE_DEFAULT
            )

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(NOTIFICATION_CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        builder.setAutoCancel(true)
        val notification = builder.build()
        notification.flags = Notification.FLAG_AUTO_CANCEL or Notification.FLAG_ONGOING_EVENT
        notificationManagerCompat.notify(NOTIFICATION_CHANNEL_ID_NUMBER, notification)
    }

    private fun pendingIntent(habitId: Int): PendingIntent? {
        val int = Intent(applicationContext, DetailHabitActivity::class.java).apply {
            putExtra(HABIT_ID, habitId)
        }
        return  TaskStackBuilder.create(applicationContext).run{
            addNextIntentWithParentStack(int)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }
    }
}
