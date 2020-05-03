package com.song.example.study.common.widget


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_SECRET
import com.song.example.study.R

/**
 * @author: Listensong
 * @time 19-10-22 下午7:40
 * @desc com.song.example.study.common.widget.NotificationUtil
 * Refer: https://www.jianshu.com/p/cb8426620e74
 */
class NotificationUtil {
    private object Holder {
        val HOLDER_INSTANCE = NotificationUtil()
    }

    companion object {
        @JvmStatic
        fun getInstance() = Holder.HOLDER_INSTANCE

        //理论上可定制许多不同的通知渠道和描述
        private const val channelId = "channel_id"
        private const val channelName = "channel_name"
        private const val channelDesc = "channel_description"
    }

    private var notificationManager: NotificationManager? = null

    private fun createNotificationManager(context: Context): NotificationManager? {
        if (notificationManager == null) {
            notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
        return notificationManager
    }

    private fun createChannelIfNeed(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                    channelName,
                    NotificationManager.IMPORTANCE_DEFAULT)
                    .apply {
                        canBypassDnd() //是否绕过勿扰模式
                        enableLights(false)
                        lockscreenVisibility = VISIBILITY_SECRET//锁屏可见
                        lightColor = Color.RED//
                        canShowBadge()//显示桌面launcher的消息图标
                        enableVibration(false)
                        audioAttributes
                        group
                        setBypassDnd(true)
                        vibrationPattern = longArrayOf(100, 100, 100)
                        shouldShowLights()
                    }
            createNotificationManager(context)
                    ?.createNotificationChannel(channel)
        }
    }

    private fun generateBuilder(context: Context,
                                title: String,
                                content: String,
                                intent: PendingIntent? = null): NotificationCompat.Builder {
        createChannelIfNeed(context)
        return NotificationCompat
                .Builder(context, channelId)
                .apply {
                    setContentTitle(title)
                    setContentText(content)
                    setSmallIcon(R.mipmap.ic_logo_wanandroid)
                    setAutoCancel(true)
                    //如果设置成true，左右滑动的时候就不会被删除了
                    //setOngoing(true)
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                        priority = NotificationCompat.PRIORITY_DEFAULT
                    } else {
                        setOnlyAlertOnce(true)
                    }
                    if (intent != null) {
                        setContentIntent(intent)
                    }
                }
    }

    private fun generateProgressBuilder(context: Context,
                                        title: String,
                                        content: String,
                                        progress: Int,
                                        intent: PendingIntent? = null
    ): NotificationCompat.Builder {
        createChannelIfNeed(context)
        return generateBuilder(context, title, content, intent).apply {
            //setLargeIcon(BitmapFactory.decodeResource())
            if (progress > 0 || progress < 100) {
                //indeterminate表示是否有进度， false表示进度，true表示流动
                setProgress(100, progress, false)
            } else {
                setProgress(0, 0, false)
                setContentText("Done")
            }
            setAutoCancel(true)
            //通知产生的时间
            setWhen(System.currentTimeMillis())
            //设置是否显示时间计时
            setUsesChronometer(true)
            //是否显示当前里头，仅在4.2以上生效，与setUsesChronometer显示有冲突
            setShowWhen(true)
            //如果设置成true，左右滑动的时候就不会被删除了
            setOngoing(true)
        }
    }

    fun sendNotification(context: Context,
                         notifyId: Int,
                         title: String,
                         content: String,
                         intent: PendingIntent? = null) {
        createNotificationManager(context)?.notify(
                notifyId,
                generateBuilder(context, title, content, intent).build()
        )
    }

    fun sendProgressNotification(context: Context,
                                 title: String,
                                 content: String,
                                 progress: Int,
                                 intent: PendingIntent? = null) {
        createNotificationManager(context)?.notify(
                0,
                generateProgressBuilder(context, title, content, progress, intent).build()
        )

    }

    fun cancelAll() {
        notificationManager?.cancelAll()
    }
}