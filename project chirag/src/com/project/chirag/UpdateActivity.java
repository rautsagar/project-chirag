package com.project.chirag;

import java.util.ArrayList;

import com.project.chirag.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends Activity {
	database db1;

	public void toast(String msg) {

		Toast newToast = Toast.makeText(this, msg, Toast.LENGTH_LONG);
		newToast.show();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update);

	}

	public void viewData(View v) {
		String opString;
		TextView num = (TextView) findViewById(R.id.numberField);
		TextView opField = (TextView) findViewById(R.id.outputField);
		opField.setText("");
		db1 = new database(this);

		int result = db1.getRows();
		num.setText(String.valueOf(result));

		ArrayList<chiragData> dbContent = db1.getData();
		for (chiragData c1 : dbContent) {

			opString = c1.getId() + "   " + c1.getData() + "\n";
			opField.append(opString);

		}
	}

//	public void serverPull(View v) {
//		db1 = new database(this);
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
//		nameValuePairs.add(new BasicNameValuePair("id", "0"));
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
//			for (int i = 0; i < jArray.length(); i++) {
//				JSONObject json_data = jArray.getJSONObject(i);
//				db1.insertData(json_data.getInt("id"),
//						json_data.getString("info"));
//				// toast("id: " + json_data.getInt("id") + ", name: "
//				// + json_data.getString("info")
//				// );
//			}
//		} catch (JSONException ex) {
//			toast("Error parsing JSON data: " + ex.toString());
//		}
//
//	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_update, menu);
		return true;
	}

}
