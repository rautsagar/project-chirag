package com.project.chirag;

import com.project.chirag.R;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends ParentActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_table);
        CheckUpdates task1=new CheckUpdates(this);
        task1.execute(null);
        FetchImages task2=new FetchImages(this);
        task2.execute(null);
    }
    
    
    public void notifSend(){
    	NotificationManager notificationManager = (NotificationManager) 
    			  getSystemService(NOTIFICATION_SERVICE); 
    	Notification updNotification=new Notification(R.drawable.ic_launcher,
    			  "A new notification", System.currentTimeMillis());
    	updNotification.flags |=Notification.FLAG_AUTO_CANCEL;
    	updNotification.number += 1;
    	Intent intent = new Intent(this, UpdateListActivity.class);
    	PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 0);
    	updNotification.setLatestEventInfo(this, "This is the title",
    	  "This is the text", activity);
    	notificationManager.notify(0, updNotification); 
    }

 
    
   
    	
    	
    }

