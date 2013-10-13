package com.project.chirag.youtube.ui.phone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;

import com.project.chirag.R;
import com.project.chirag.youtube.domain.Library;
import com.project.chirag.youtube.domain.Video;
import com.project.chirag.youtube.service.task.GetYouTubeUserVideosTask;
import com.project.chirag.youtube.ui.widget.VideosListView;

/**
 * The Activity can retrieve Videos for a specific username from YouTube</br>
 * It then displays them into a list including the Thumbnail preview and the title</br>
 * There is a reference to each video on YouTube as well but this isn't used in this tutorial</br>
 * </br>
 * <b>Note<b/> orientation change isn't covered in this tutorial, you will want to override
 * onSaveInstanceState() and onRestoreInstanceState() when you come to this
 * </br>
 */
public class MainActivity extends Activity {
	
    // A reference to our list that will hold the video details
	private VideosListView listView;
	

	private static Library lib;
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy); 
       

        new GetYouTubeUserVideosTask(responseHandler, "projectchirag").run();
        
        listView = (VideosListView) findViewById(R.id.videosListView);
        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        	public void onItemClick(AdapterView<?> parent, View view,
        	          int position, long id) 
        	{
        		Video selectedVideo=lib.getVideos().get(position);
        		//String videoTitle=selectedVideo.getTitle();
        		String videoURL=selectedVideo.getUrl();
        		//String videoThumbURL=selectedVideo.getThumbUrl();
        		
        		//Toast.makeText(getBaseContext(), "Selected video URL: "+videoURL, Toast.LENGTH_LONG).show();
        		Uri uri=Uri.parse(videoURL);
        		String VIDEO_ID = uri.getQueryParameter("v");
        		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + VIDEO_ID));
        		startActivity(i);
        	}
		});
    }

    // This is the XML onClick listener to retreive a users video feed
//    public void getUserYouTubeFeed(){
//    	// We start a new task that does its work on its own thread
//    	// We pass in a handler that will be called when the task has finished
//    	// We also pass in the name of the user we are searching YouTube for
//    	
//    	new GetYouTubeUserVideosTask(responseHandler, "projectchirag").run();
//    	//new GetYouTubeUserVideosTask(responseHandler, "GoogleDevelopers").run();
//    }
   
    // This is the handler that receives the response when the YouTube task has finished
	 Handler responseHandler = new Handler() {
		public void handleMessage(Message msg) {
			populateListWithVideos(msg);
		};
	};

	/**
	 * This method retrieves the Library of videos from the task and passes them to our ListView
	 * @param msg
	 */
	private void populateListWithVideos(Message msg) {
		
		// Retreive the videos are task found from the data bundle sent back
		lib = (Library) msg.getData().get(GetYouTubeUserVideosTask.LIBRARY);
		// Because we have created a custom ListView we don't have to worry about setting the adapter in the activity
		// we can just call our custom method with the list of items we want to display
		listView.setVideos(lib.getVideos());
	}
	
	@SuppressLint("HandlerLeak")
	@Override
	protected void onStop() {
		// Make sure we null our handler when the activity has stopped
		// because who cares if we get a callback once the activity has stopped? not me!
		responseHandler = null;
		super.onStop();
	}
}