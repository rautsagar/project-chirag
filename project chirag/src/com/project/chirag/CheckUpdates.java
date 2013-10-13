package com.project.chirag;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class CheckUpdates extends AsyncTask<String,Void,Boolean>{
	
	private final static String URL = "http://sagarraut.apiary.io/chirag";
	private static final String TAG_NEWS = "news";
	
	int status;
	JSONException exResult;
	Context parentContext;
	MainActivity parentAct;
	public CheckUpdates(MainActivity context) {
		parentContext=context;
		parentAct=context;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Boolean doInBackground(String... arg0) {
       
		//just make a toast if no connection detected
		if(!parentAct.isInternetOn()){
        	status=5;
        	return false;
        }
		database db1 = new database(parentContext);
		
		

		
		JsonParser jParser = new JsonParser();
		JSONObject json = jParser.getJSONFromUrl(URL);
		
		// Parse the JSON data.
		try {
			JSONArray jArray = json.getJSONArray(TAG_NEWS);
			boolean newUpdates=false;
			
			//check if new entries are present on the server
			//JSONObject jsobjFirst = jArray.getJSONObject(0);
			
			//int lastRemoteId=jsobjFirst.getInt("id");
			//if(lastRemoteId>lastLocalId)
			//toast("New id"+lastRemoteId + "old id"+lastLocalId);
			
			for (int i = 0; i < jArray.length(); i++) {
				JSONObject json_data = jArray.getJSONObject(i);
				
				if(json_data.getString("info").equalsIgnoreCase("no_new_updates")){
					status=0;
					break;
				}else{
				db1.insertData(json_data.getInt("id"),
						json_data.getString("info"));
				newUpdates=true;
				// toast("id: " + json_data.getInt("id") + ", name: "
				// + json_data.getString("info")
				// );
				}}
			if(newUpdates){status=1;}
			return newUpdates;
		} catch (JSONException ex) {
			ex=exResult;
			status=4;
		}

	
		// TODO Auto-generated method stub
		return null;
	}
	@Override
    protected void onPostExecute(Boolean result) {
		//if(result){parentAct.notifSend();}
		switch (status) {
		case 0:
			toast("No new updates");
			break;
			
		case 1:	
			parentAct.notifSend();
			break;
		case 3:
			toast("Error reading server response:"+exResult);
			break;
		case 4:
			toast("Error parsing JSON data:"+exResult);
			break;
		case 5:
			toast("No connection to the Internet");
			break;
		default:
			break;
		}
    }
	public void toast(String msg) {

		Toast newToast = Toast.makeText(parentContext, msg, Toast.LENGTH_LONG);
		newToast.show();
	}
	


	

}
