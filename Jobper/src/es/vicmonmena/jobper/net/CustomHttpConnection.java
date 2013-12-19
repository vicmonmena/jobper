package es.vicmonmena.jobper.net;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.util.Base64;
import android.util.Log;

/**
 * 
 * @author vicmonmena
 *
 */
public class CustomHttpConnection {

	/**
    * TAG para mensajes de LOG.
    */
    private static final String TAG = "CustomHttpConnection";
    
    /**
     * @param uri
     * @return
     * @throws IOException 
     */
    public static InputStream customGetRequest(String uri) throws IOException {
    	
    	URL url = null;
    	InputStream is = null;

		//Construimos la URL y realizamos la llamada
		url = new URL(uri);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		is = connection.getInputStream();
		Log.e(TAG, "Response code " + connection.getResponseCode());
    	
    	return is;
    }
}