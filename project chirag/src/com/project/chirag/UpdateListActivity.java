package com.project.chirag;

import com.project.chirag.R;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class UpdateListActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_list);
        
        
       ListView view1=(ListView)findViewById(R.id.updatelist);
        
        database db1=new database(this);
        Cursor cursor=db1.getCursor();
        String [] columns=new String[] {"id","info"};
        int[] to=new int[] {R.id.id_entry, R.id.info_entry};
        
        SimpleCursorAdapter dbAdapter=new SimpleCursorAdapter(this, R.layout.list_example_entry, cursor, columns, to);
        view1.setAdapter(dbAdapter);
        
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_update_list, menu);
        return true;
    }
    
//    public void notifSend(){
//    	NotificationManager notificationManager = (NotificationManager) 
//    			  getSystemService(NOTIFICATION_SERVICE); 
//    	Notification updNotification=new Notification(R.drawable.ic_launcher,
//    			  "A new notification", System.currentTimeMillis());
//    	updNotification.flags |=Notification.FLAG_AUTO_CANCEL;
//    	updNotification.number += 1;
//    	Intent intent = new Intent(this, UpdateListActivity.class);
//    	PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 0);
//    	updNotification.setLatestEventInfo(this, "This is the title",
//    	  "This is the text", activity);
//    	notificationManager.notify(0, updNotification); 
//    }
//    
//    public void serverPull() {
//		database db1 = new database(this);
//		String result = "";
//		InputStream is = new InputStream() {
//			@Override
//			public int read() throws IOException {
//				// TODO Auto-generated method stub
//				return 0;
//			}
//		};
//
//		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
//		
//		//send a id to the server.entries with id greater than this sent id will be fetched
//		int lastLocalId=db1.getLast();
//		if(lastLocalId>0){
//		nameValuePairs.add(new BasicNameValuePair("id", Integer.toString(lastLocalId)));
//		}else{nameValuePairs.add(new BasicNameValuePair("id", "0"));}
//
//		try {
//			HttpClient httpclient = new DefaultHttpClient();
//			HttpPost httppost = new HttpPost(
//					"http://www.teknack.in/sagar/fetch.php");
//			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
//			HttpResponse response = httpclient.execute(httppost);
//			HttpEntity entity = response.getEntity();
//			is = entity.getContent();
//		} catch (Exception ex) {
//			toast("Error in HTTP connection: " + ex.toString());
//		}
//
//		// Read the data into a string.
//		try {
//			BufferedReader reader = new BufferedReader(new InputStreamReader(
//					is, "iso-8859-1"), 8);
//			StringBuilder sb = new StringBuilder();
//			String line = null;
//			while ((line = reader.readLine()) != null)
//				sb.append(line + "\n");
//			is.close();
//
//			result = sb.toString();
//		} catch (Exception ex) {
//			toast("Error reading server response: " + ex.toString());
//		}
//
//		// Parse the JSON data.
//		try {
//			JSONArray jArray = new JSONArray(result);
//			boolean newUpdates=false;
//			
//			//check if new entries are present on the server
//			//JSONObject jsobjFirst = jArray.getJSONObject(0);
//			
//			//int lastRemoteId=jsobjFirst.getInt("id");
//			//if(lastRemoteId>lastLocalId)
//			//toast("New id"+lastRemoteId + "old id"+lastLocalId);
//			
//			for (int i = 0; i < jArray.length(); i++) {
//				JSONObject json_data = jArray.getJSONObject(i);
//				
//				if(json_data.getString("info").equalsIgnoreCase("no_new_updates")){
//					toast("No new updates");
//					break;
//				}else{
//				db1.insertData(json_data.getInt("id"),
//						json_data.getString("info"));
//				newUpdates=true;
//				// toast("id: " + json_data.getInt("id") + ", name: "
//				// + json_data.getString("info")
//				// );
//				}}
//			if(newUpdates){notifSend();}
//		} catch (JSONException ex) {
//			toast("Error parsing JSON data: " + ex.toString());
//		}
//
//	}
    
    public void toast(String msg) {

		Toast newToast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
		newToast.show();
	}
}
