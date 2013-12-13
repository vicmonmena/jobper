package es.vicmonmena.jobper.net.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.util.JsonReader;
import android.util.Log;
import es.vicmonmena.jobper.model.Job;

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
	
	public static List<Job> parseJobs(InputStream is) throws IOException {
		List<Job> jobs = null;
		JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
		try {
			reader.beginObject();
			String nodeRoot = reader.nextName();
			
			if (nodeRoot.equals("jobs")) {
				reader.beginArray();
				
				jobs = new ArrayList<Job>();
				
				while (reader.hasNext()) {
					reader.beginObject();
					while (reader.hasNext()) {
						Job job = new Job();
						String node = reader.nextName();
						if (node.equals("id")) {
							job.setJobId(reader.nextString());
						} else if (node.equals("title")) {
							job.setTitle(reader.nextString());
						}  else if (node.equals("update_at")) {
							job.setUpdateAt(reader.nextString());
						} else if (node.equals("salary_min")) {
							job.setSalaryMin(reader.nextString());
						} else if (node.equals("salary_max")) {
							job.setSalaryMax(reader.nextString());
						}
						
						// location: tags -> LocationTag
						
						jobs.add(job);
					}
					reader.endObject();
				}
				reader.endArray();
			}
		} catch (IOException e) {
			Log.i(TAG, "IOException parsing Json...");
		} catch (Exception e) {
			Log.i(TAG, "Exception parsing Json...");
		} finally {
			reader.close();
		}
		return jobs;
	}
}
