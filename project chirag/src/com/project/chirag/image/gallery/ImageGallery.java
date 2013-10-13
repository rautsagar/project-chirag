package com.project.chirag.image.gallery;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.chirag.R;

public class ImageGallery extends Activity {

	public TextView tv;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_gallery);

		// try {
		// prepareImages();
		// } catch (ClientProtocolException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		final ImageView imgView = (ImageView) findViewById(R.id.imageview1);
		tv = (TextView) findViewById(R.id.textView1);
		tv.setText(" ");
		final Gallery ga = (Gallery) findViewById(R.id.gallery1);
		ga.setAdapter(new ImageAdapter(this));
		ga.setBackgroundColor(Color.LTGRAY);

		ga.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View v,
					int position, long id) {
				// Toast.makeText(getBaseContext(), "Selected : " + position,
				// Toast.LENGTH_SHORT).show();
				System.gc();
				Runtime.getRuntime().gc();

				imgView.setImageURI(Uri.parse(((ImageAdapter) ga.getAdapter())
						.getImageUri(position)));
				imgView.setAdjustViewBounds(false);
				imgView.setPadding(10, 10, 10, 10);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
	}

	public String[] listFilesFromDirectory() {

		String path = getString(R.string.sdcard_path);

		File f = new File(Environment.getExternalStorageDirectory().getPath()
				+ path);
		File[] files = f.listFiles();
		List<String> fileList = new ArrayList<String>();
		fileList.clear();

		if (files.length != 0) {
			for (File file : files) {
				fileList.add(file.getPath());
				// Toast.makeText(getBaseContext(), file.getPath(),
				// Toast.LENGTH_SHORT)
				// .show();
			}
		}

		return fileList.toArray(new String[fileList.size()]);

	}

	// public void downloadFromUrl(String fileName) {
	//
	// try {
	// System.gc();
	// Runtime.getRuntime().gc();
	//
	// File root = Environment.getExternalStorageDirectory();
	// String sdPath = getString(R.string.sdcard_path);
	// String downloadUrl = getString(R.string.image_url);
	//
	// File dir = new File(root.getAbsolutePath() + sdPath);
	// if (dir.exists() == false) {
	// dir.mkdirs();
	// }
	//
	// URL url = new URL(downloadUrl + "/" + fileName); // you can write here
	// any link
	// File file = new File(dir, fileName);
	//
	// // Toast.makeText(getBaseContext(), fileName, Toast.LENGTH_SHORT).show();
	//
	// long startTime = System.currentTimeMillis();
	// Log.d("DownloadManager", "download begining");
	// Log.d("DownloadManager", "download url:" + url);
	// Log.d("DownloadManager", "downloaded file name:" + fileName);
	//
	// /* Open a connection to that URL. */
	// URLConnection ucon = url.openConnection();
	//
	// /*
	// * Define InputStreams to read from the URLConnection.
	// */
	// InputStream is = ucon.getInputStream();
	// BufferedInputStream bis = new BufferedInputStream(is);
	//
	// /*
	// * Read bytes to the Buffer until there is nothing more to read(-1).
	// */
	// ByteArrayBuffer baf = new ByteArrayBuffer(5000);
	// int current = 0;
	// while ((current = bis.read()) != -1) {
	// baf.append((byte) current);
	// }
	//
	// /* Convert the Bytes read to a String. */
	// FileOutputStream fos = new FileOutputStream(file);
	// fos.write(baf.toByteArray());
	// fos.flush();
	// fos.close();
	// Log.d("DownloadManager",
	// "download ready in"
	// + ((System.currentTimeMillis() - startTime) / 1000)
	// + " sec");
	//
	// } catch (IOException e) {
	// Log.d("DownloadManager", "Error: " + e.getStackTrace());
	// }
	//
	// }

	// public String[] getImageNames() throws ClientProtocolException,
	// IOException {
	// String result = "";
	// String url = getString(R.string.image_url);
	// InputStream is = new InputStream() {
	// @Override
	// public int read() throws IOException {
	// return 0;
	// }
	// };
	//
	// // Connect to the image URL
	// HttpClient httpclient = new DefaultHttpClient();
	// HttpPost httppost = new HttpPost(url);
	// HttpResponse response = httpclient.execute(httppost);
	// HttpEntity entity = response.getEntity();
	// is = entity.getContent();
	//
	// // Read data in the string
	// BufferedReader reader = new BufferedReader(new InputStreamReader(is,
	// "iso-8859-1"), 8);
	// StringBuilder sb = new StringBuilder();
	// String line = null;
	// while ((line = reader.readLine()) != null) {
	//
	// // Toast.makeText(getBaseContext(), " " + line,
	// // Toast.LENGTH_SHORT).show();
	// if (line.contains(".jpg") || line.contains(".jpeg")
	// || line.contains(".png") || line.contains(".gif")) {
	// // Toast.makeText(URLDemo.this, "in if block",
	// // Toast.LENGTH_SHORT).show();
	//
	// if (sb.length() != 0)
	// sb.append("|");
	//
	// sb.append(line.substring(line.indexOf("\"> ") + 3,
	// line.indexOf("</")));
	// }
	// }
	// is.close();
	//
	// result = sb.toString();
	// // Toast.makeText(getBaseContext(), result, Toast.LENGTH_SHORT).show();
	//
	// String[] results = result.split("\\|");
	//
	// return results;
	// }

	// public void prepareImages() throws ClientProtocolException, IOException {
	// String[] imgNames = getImageNames();
	//
	// for (int i = 0; i < imgNames.length; i++) {
	// downloadFromUrl(imgNames[i]);
	// }
	// }

	public class ImageAdapter extends BaseAdapter {
		/** The parent context */
		private Context myContext;

		private String[] myImageIds = listFilesFromDirectory();

		/** Simple Constructor saving the 'parent' context. */
		public ImageAdapter(Context c) {
			this.myContext = c;

		}

		// inherited abstract methods - must be implemented
		// Returns count of images, and individual IDs
		public int getCount() {
			return this.myImageIds.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public String getImageUri(int position) {
			return myImageIds[position];
		}

		// Returns a new ImageView to be displayed,
		public View getView(int position, View convertView, ViewGroup parent) {

			File root = Environment.getExternalStorageDirectory();
			String sdPath = getString(R.string.sdcard_path);
			
			File dir = new File(root.getAbsolutePath() + sdPath);
			if (dir.exists() == false) {
				dir.mkdirs();
				Toast.makeText(getBaseContext(), "No connection to Internet", Toast.LENGTH_SHORT).show();
				return null;
			}
			
			System.gc();
			Runtime.getRuntime().gc();
			
			// Get a View to display image data
			ImageView iv = new ImageView(this.myContext);
			iv.setImageURI(Uri.parse(myImageIds[position]));

			// Image should be scaled somehow
			iv.setScaleType(ImageView.ScaleType.FIT_XY);

			// Set the Width & Height of the individual images
			iv.setLayoutParams(new Gallery.LayoutParams(200, 150));

			iv.setBackgroundColor(Color.GRAY);
			iv.setPadding(10, 10, 10, 10);

			return iv;
		}
	}
}
