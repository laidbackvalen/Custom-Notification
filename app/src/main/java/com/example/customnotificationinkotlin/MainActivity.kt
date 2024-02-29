package com.example.customnotificationinkotlin

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat

class MainActivity : AppCompatActivity() {
    //A class that represents how a persistent notification is to be presented to the user using the NotificationManager.
    companion object {
        const val CHANNEL_ID : String = "My Channel" //there can be many channels, you can specify a specific channel id to work with
        const val REQUEST_CODE : Int = 100 //for pending intent
        const val NOTIFIACTION_ID = 100 //there can be many channels, you can specify a specific channel id to work with
    }
    lateinit var notification: Notification
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Code To Convert drawable png to bitmap
        var drawable: Drawable? = ResourcesCompat.getDrawable(resources, R.drawable.ic_launcher, null)
        var bitmapDrawable = drawable as BitmapDrawable
        var largeIcon: Bitmap = bitmapDrawable.bitmap

        //https://www.youtube.com/watch?v=Pen_en0zhIY     //to know more

        var notificationManager : NotificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        // NotificationManager is a class in the Android SDK that provides functionality to manage notifications shown to the user.
        // It allows you to create, update, and cancel notifications that appear in the notification drawer or as pop-up alerts.

        var iNotify = Intent(applicationContext, MainActivity::class.java)
        iNotify.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) //This flag is used to start an activity in a new task. If the activity is already running in another task, it will be moved to the front.

        var pendingIntent : PendingIntent = PendingIntent.getActivities(this, REQUEST_CODE,
            arrayOf(iNotify), PendingIntent.FLAG_IMMUTABLE) //For example, if you want to create a notification that opens an activity when clicked, you would create a PendingIntent that opens the activity and attach it to the notification.
       //A PendingIntent in Android is a token that you can give to another application (like NotificationManager, AlarmManager, etc.)
        //which allows this application to execute a predefined piece of code on your behalf at a later time, even if your application is not running.

        var bigpicturestyle = Notification.BigPictureStyle()
            //it is a Helper class for generating large-format notifications that include a large image attachment.
            .bigPicture(drawable.bitmap)
            .bigPicture(largeIcon)
            .setBigContentTitle("A message From tine")
            .setSummaryText("Image Message")

        //INBOX STYLE
        var notificationAsInboxStyle = Notification.InboxStyle()   //multiple  lines upto 7
            .addLine("A")
            .addLine("B")
            .addLine("C")
            .addLine("D")
            .addLine("E")
            .addLine("F")
            .addLine("G")
            .addLine("H")
            .addLine("I")
            .addLine("J")
            .addLine("K")
            .addLine("L")
            .setBigContentTitle("Full Message")
            .setSummaryText("Messages by Valen")

        if(android.os.Build.VERSION.SDK_INT>= android.os.Build.VERSION_CODES.O) { //notification channels were introduced starting from Android 8.0 (API level 26)
            notification = Notification.Builder(this)
                //The Notification.Builder has been added to make it easier to construct Notifications.
                //the NotificationBuilder is a class used to construct and customize notifications that are displayed to users.
                // The NotificationBuilder is typically used in conjunction with the NotificationManager to create and manage notifications within an Android application.
                .setLargeIcon(Bitmap.createBitmap(largeIcon))
                .setSmallIcon(R.drawable.n)
                .setContentText("Hello People")
                .setContentIntent(pendingIntent)  //Pending Intent
                .setSubText("Message from Valen")
                .setStyle(bigpicturestyle) //switch style value use  - notificationAsInboxStyle
                //.setOngoing(true)  // this will not let to delete or remove the notification, for example USB Debugging connected will not be remove until you remove the usb cable
                .setChannelId(CHANNEL_ID)
                // A "channel ID" in notifications typically refers to a unique identifier associated with a communication channel or endpoint through which notifications are sent or received.
                //it's important to note that starting from Android 8.0 (API level 26), notification channels were introduced.
                //Notification channels allow developers to categorize notifications and give users more control over which types of notifications they want to receive and how they are presented.
                //When creating notifications using the NotificationBuilder, you need to specify a notification channel for the notification to be associated with.
                // This ensures that the notification is properly categorized and follows the user's notification preferences.
                .build()

            notificationManager.createNotificationChannel(NotificationChannel(CHANNEL_ID, "Valen's notification channel", NotificationManager.IMPORTANCE_HIGH))  // NotificationManager.IMPORTANCE_HIGH is Priority or importance
        }
        else{
            notification = Notification.Builder(this)
                .setLargeIcon(Bitmap.createBitmap(largeIcon))
                .setSmallIcon(R.drawable.n)
                .setContentText("New Message")
                .setSubText("message from Valen")
//                .setOngoing(true)
                .setStyle(bigpicturestyle)
                .build()
        }

        notificationManager.notify(NOTIFIACTION_ID, notification)  //CALL THIS METHOD WHERE YOU WANT THE NOTIFICATION TO BE APPEAR AT< IN MY CASE IT IS CALLED ON ONCREATE()
        //To get different notifications form different channels make sure to specify the id related to that channel
        //if you provide different channel id's with each notification you send you will get those notifications in different instance of notification bar

        //FOR EXAMPLE -
        //if mansi send you a message that you receive on a channel_id1 and after that charlie send you a message
        //your notification bar will only show charlie's message as the message will get updated as the notification are being passed on the same channel
        //in case you want to see their notification in different notification sections you have to provide each one of them with a specific channel id

        //if you want to see a mansi's message on a different notification section, provide a channel id to her chat. whenever a message gets from her side
        //you will get a different notification section associated with her channel id
        //all the the messages coming from that channel id will be updated and displayed on that channel id only

        //same you can do with charlie to  get his messages on a different section with channel_id2

    }

    //WHAT IS CHANNEL?
//A "channel ID" in notifications typically refers to a unique identifier
// associated with a communication channel or endpoint through which notifications are sent or received.
//
//For instance, in messaging platforms like Slack or Microsoft Teams, each channel within a workspace has its own unique identifier.
// When sending notifications programmatically, developers often need to specify the channel ID to direct the notification to the appropriate destination.
//
//Similarly, in other notification systems, such as email services or push notification services for mobile apps,
// there may be identifiers associated with specific channels or endpoints to ensure that notifications reach the intended recipients.
//
//The channel ID helps identify where the notification should be delivered and is essential for proper routing and delivery of messages within a system.


    //FLAGS IN INTENT
//  Flags provide additional instructions or behavior modifiers to the Intent, influencing how it is processed by the system.
//  They help in defining the behavior of the Intent when it is delivered or executed.
}