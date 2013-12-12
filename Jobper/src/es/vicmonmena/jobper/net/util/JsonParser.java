package es.vicmonmena.jobper.net.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.util.JsonReader;
import android.util.Log;

public class JsonParser {

	/** 
	 * TAG for log messages.
	 */
	private static final String TAG = "JsonParser";
	    
	public static void getStartups(InputStream is) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
		try {
			reader.beginArray();
			String id = "0";
			String name = "empty";
			while (reader.hasNext()) {
				reader.beginObject();
				while (reader.hasNext()) {
					String node = reader.nextName();
					if (node.equals("id")) {
						id = reader.nextString();
					} else if (node.equals("name")) {
						name = reader.nextString();
					}
				}
				reader.endObject();
				Log.i(TAG, "id: " + id + " - name: " + name);
			}
			reader.endArray();
		} catch (IOException e) {
			Log.i(TAG, "IOException parsing Json...");
		} catch (Exception e) {
			Log.i(TAG, "Exception parsing Json...");
		} finally {
			reader.close();
		}
		
	}
	
	public static void parseJobs(InputStream is) throws IOException {
		JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
		try {
			reader.beginArray();
			
			while (reader.hasNext()) {
				reader.beginObject();
				while (reader.hasNext()) {
					String node = reader.nextName();
					if (node.equals("id")) {
						reader.nextString();
					} else if (node.equals("title")) {
						reader.nextString();
					}  else if (node.equals("update_at")) {
						reader.nextString();
					} else if (node.equals("salary_min")) {
						
					} else if (node.equals("salary_max")) {
						
					}
					
					// location: tags -> LocationTag
				}
				reader.endObject();
			}
			reader.endArray();
		} catch (IOException e) {
			Log.i(TAG, "IOException parsing Json...");
		} catch (Exception e) {
			Log.i(TAG, "Exception parsing Json...");
		} finally {
			reader.close();
		}
		
	}
}
