package com.project.chirag;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.ByteArrayBuffer;

import com.project.chirag.R;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class FetchImages extends AsyncTask<Void, Void, Boolean> {
    int status=0;
	Context parentContext;
	MainActivity parentAct;
    public FetchImages(MainActivity context) {
		parentContext=context;
		parentAct=context;
		// TODO Auto-generated constructor stub
	}
    
	@Override
	protected Boolean doInBackground(Void... params) {
		if(!parentAct.isInternetOn()){
			
        	return false;
        }
		
		try {
		prepareImages();
	} catch (ClientProtocolException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	}
		return true;
	}
	
	 protected void onPostExecute(Boolean result) {
		 
		 if(status==1){
			 toast("Done loading images from web");
		 }
			
	    }
	
	
	public void downloadFromUrl(String fileName) {

		try {
			System.gc();
			Runtime.getRuntime().gc();
			
			File root = Environment.getExternalStorageDirectory();
			String sdPath = parentContext.getString(R.string.sdcard_path);
			String downloadUrl = parentContext.getString(R.string.image_url);

			File dir = new File(root.getAbsolutePath() + sdPath);
			if (dir.exists() == false) {
				dir.mkdirs();
			}

			URL url = new URL(downloadUrl + "/" + fileName); // you can write here any link
			File file = new File(dir, fileName);
			
//			Toast.makeText(getBaseContext(), fileName, Toast.LENGTH_SHORT).show();

			long startTime = System.currentTimeMillis();
			Log.d("DownloadManager", "download begining");
			Log.d("DownloadManager", "download url:" + url);
			Log.d("DownloadManager", "downloaded file name:" + fileName);

			/* Open a connection to that URL. */
			URLConnection ucon = url.openConnection();

			/*
			 * Define InputStreams to read from the URLConnection.
			 */
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			/*
			 * Read bytes to the Buffer until there is nothing more to read(-1).
			 */
			ByteArrayBuffer baf = new ByteArrayBuffer(5000);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			/* Convert the Bytes read to a String. */
			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baf.toByteArray());
			fos.flush();
			fos.close();
			Log.d("DownloadManager",
					"download ready in"
							+ ((System.currentTimeMillis() - startTime) / 1000)
							+ " sec");

		} catch (IOException e) {
			Log.d("DownloadManager", "Error: " + e.getStackTrace());
		}

		// InputStream inputStream = null;
		//
		// //get the picture from location
		// picture = BitmapFactory.decodeFile("/sdcard/test.png");
		//
		// // CONVERT:
		// ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		// Boolean didItWork = picture.compress(Bitmap.CompressFormat.PNG, 50,
		// outStream);
		// picture.recycle();
		// if (didItWork = true) {
		// Log.d(TAG, "compression worked");
		// }
		// Log.d(TAG, "AFTER. Height: " + picture.getHeight() + " Width: "
		// + picture.getWidth());
		// final byte[] ba = outStream.toByteArray();
		// try {
		// outStream.close();
		// } catch (IOException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

	}
	
	
	public String[] getImageNames() throws ClientProtocolException, IOException {
		String result = "";
		String url = parentContext.getString(R.string.image_url);
		InputStream is = new InputStream() {
			@Override
			public int read() throws IOException {
				return 0;
			}
		};

		// Connect to the image URL
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(url);
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		is = entity.getContent();

		// Read data in the string
		BufferedReader reader = new BufferedReader(new InputStreamReader(is,
				"iso-8859-1"), 8);
		StringBuilder sb = new StringBuilder();
		String line = null;
		while ((line = reader.readLine()) != null) {
			
//			 Toast.makeText(getBaseContext(), " " + line,
//			 Toast.LENGTH_SHORT).show();
			if (line.contains(".jpg") || line.contains(".jpeg")
					|| line.contains(".png") || line.contains(".gif")) {
				// Toast.makeText(URLDemo.this, "in if block",
				// Toast.LENGTH_SHORT).show();

				if (sb.length() != 0)
					sb.append("|");

				sb.append(line.substring(line.indexOf("\"> ") + 3,
						line.indexOf("</")));
			}
		}
		is.close();

		result = sb.toString();
//		Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();
		
		String[] results = result.split("\\|");

		return results;
	}
	
	public void prepareImages() throws ClientProtocolException, IOException {
		String[] imgNames = getImageNames();

		for (int i = 0; i < imgNames.length; i++) {
			downloadFromUrl(imgNames[i]);
		}
		status=1;
	}
	
	public void toast(String msg) {

		Toast newToast = Toast.makeText(parentContext, msg, Toast.LENGTH_LONG);
		newToast.show();
	}

}
