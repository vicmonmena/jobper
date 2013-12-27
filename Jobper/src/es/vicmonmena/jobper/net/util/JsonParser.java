package es.vicmonmena.jobper.net.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import es.vicmonmena.jobper.Controller;
import es.vicmonmena.jobper.model.Job;
import es.vicmonmena.jobper.model.Startup;

/**
 * 
 * @author vicmonmena
 *
 */
public class JsonParser {

	/** 
	 * TAG for log messages.
	 */
	private static final String TAG = "JsonParser";
	
	/**
	 * Obtiene un listado de Jobs a partir de una fuente con formato Json.
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static List<Job> parseJobs(AsyncTask task, InputStream is) throws IOException {
		List<Job> jobs = null;
		JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
		try {
			reader.beginObject();
			String nodeRoot = reader.nextName();
			
			if (nodeRoot.equals("jobs")) {
				reader.beginArray();
				
				jobs = new ArrayList<Job>();
				
				while (reader.hasNext()) {
					
					if (task.isCancelled()){
						break;
					}
					
					reader.beginObject();
					Job job = null;
					
					job = new Job();
					while (reader.hasNext()) {
						String node = reader.nextName();
						if (reader.peek() == JsonToken.NULL) {
							reader.skipValue();
						} else {
							if (node.equals("id")) {
								job.setJobId(reader.nextString());
								Log.i(TAG, "parseJob " + job.getJobId());
							} else if (node.equals("title")) {
								job.setTitle(reader.nextString());
							}  else if (node.equals("updated_at")) {
								job.setUpdateAt(reader.nextString());
							} else if (node.equals("salary_min")) {
								job.setSalaryMin(reader.nextString());
							} else if (node.equals("salary_max")) {
								job.setSalaryMax(reader.nextString());
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
								reader.skipValue();
							}
						}
					}
					jobs.add(job);
					reader.endObject();
				}
				reader.endArray();
			}
		} catch (IOException e) {
			Log.i(TAG, "IOException parsing Json (parseJobs)");
		} catch (Exception e) {
			Log.i(TAG, "Exception parsing Json (parseJobs)");
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
		JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
		Job job = null;
		try {
			while (reader.hasNext()) {
				reader.beginObject();
				job = new Job();
				while (reader.hasNext()) {
					String node = reader.nextName();
					if (reader.peek() == JsonToken.NULL) {
						reader.skipValue();
					} else {
						if (node.equals("id")) {
							job.setJobId(reader.nextString());
							Log.i(TAG, "parseJob " + job.getJobId());
						} else if (node.equals("title")) {
							job.setTitle(reader.nextString());
						}  else if (node.equals("updated_at")) {
							job.setUpdateAt(reader.nextString());
						} else if (node.equals("salary_min")) {
							job.setSalaryMin(reader.nextString());
						} else if (node.equals("salary_max")) {
							job.setSalaryMax(reader.nextString());
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
							reader.skipValue();
						}
					}
				}
				reader.endObject();
			}
		} catch (IOException e) {
			Log.i(TAG, "IOException parsing Json (parseJob)");
		} catch (Exception e) {
			Log.i(TAG, "Exception parsing Json (parseJob)");
		} finally {
			reader.close();
		}
		return job;
	}
	
	/**
	 * Obtiene información de una Starup a partir de una fuente con formato Json.
	 * @param is
	 * @return
	 * @throws IOException
	 */
	public static Startup parseStartup(AsyncTask task, InputStream is) throws IOException {
		Startup startup = null;
		JsonReader reader = new JsonReader(new InputStreamReader(is, "UTF-8"));
		try {
			reader.beginObject();
			while (reader.hasNext()) {
				
				if (task.isCancelled()){
					break;
				}
				
				String node = reader.nextName();
				if (node.equals("startup")) {
					reader.beginObject();
					startup = new Startup();
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
					reader.endObject();
				} else {
					reader.skipValue();
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
		return startup;
	}
}