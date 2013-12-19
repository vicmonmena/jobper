package es.vicmonmena.jobper.net.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.text.TextUtils;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import es.vicmonmena.jobper.Controller;
import es.vicmonmena.jobper.model.Job;
import es.vicmonmena.jobper.model.Startup;

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
	
	/**
	 * Obtiene un listado de Jobs a partir de una fuente con formato Json.
	 * @param is
	 * @return
	 * @throws IOException
	 */
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
					Job job = null;
					try {
						job = new Job();
						while (reader.hasNext()) {
							String node = reader.nextName();
							//if (reader.peek() == JsonToken.NULL) {
								//reader.skipValue();
							//} else {
								if (node.equals("id")) {
									job.setJobId(reader.nextString());
								} else if (node.equals("title")) {
									job.setTitle(reader.nextString());
								}  else if (node.equals("updated_at")) {
									job.setUpdateAt(Controller
										.getDateFormat(reader.nextString()));
								} else if (node.equals("salary_min")) {
									job.setSalaryMin(reader.nextString());
								} else if (node.equals("salary_max")) {
									job.setSalaryMax(reader.nextString());
								} else {
									reader.skipValue();
								}
							//}
						}
						jobs.add(job);
						reader.endObject();
					} catch (Exception e) {
						Log.i(TAG, "Exception parsing job " + job.getId());
					}
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
	
	/**
	 * Obtiene un Job a partir de una fuente con formato Json.
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static Job parseJob(InputStream is) throws IOException {
		Job job = null;
		JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
		try {
			reader.beginObject();
			job = new Job();
			while (reader.hasNext()) {
				String node = reader.nextName();
				if (node.equals("startup")) {
					reader.beginObject();
					Startup startup = new Startup();
					while (reader.hasNext()) {
						node = reader.nextName();
						if (reader.peek() == JsonToken.NULL) {
							reader.skipValue();
						} else {
							if (node.equals("name")) {
								startup.setName(reader.nextString());
							} else if (node.equals("logo_url")) {
								startup.setLogoURL(reader.nextString());
							}  else if (node.equals("product_desc")) {
								startup.setProductDescription(reader.nextString());
							} else if (node.equals("company_url")) {
									startup.setCompanyURL(reader.nextString());
							} else {
								reader.skipValue();
							}
						}
					}
					job.setStartup(startup);
					reader.endObject();
				} else if (node.equals("tags")) {
					reader.beginArray();
					while (reader.hasNext()) {
						boolean isLocationTagType = false;
						reader.beginObject();
						while (reader.hasNext()) {
							node = reader.nextName();
							if (node.equals("tag_type")) {
								if (reader.nextString().equals("LocationTag")) {
									isLocationTagType = true;
								}
							} else if (isLocationTagType && node.equals("display_name")) {
								job.setLocation(reader.nextString());
								isLocationTagType = false;
							} else {
								reader.skipValue();
							}
						}
						reader.endObject();
					}
					reader.endArray();
				} else {
					reader.nextString();
				}
			}
			reader.endObject();
		} catch (IOException e) {
			Log.i(TAG, "IOException parsing Json...");
		} catch (Exception e) {
			Log.i(TAG, "Exception parsing Json...");
		} finally {
			reader.close();
		}
		return job;
	}
}