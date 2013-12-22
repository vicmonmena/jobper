package es.vicmonmena.jobper.ui.components;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import es.vicmonmena.jobper.Controller;
import es.vicmonmena.jobper.R;
import es.vicmonmena.jobper.model.Startup;

/**
 * 
 * @author vicmonmena
 *
 */
public class StartupDetailsFragment extends Fragment {

	/**
	 * TAG para mensajes de LOG.
	 */
	private static final String TAG = "StartupDetailsFragment";
	/**
	 * Cadena key de paso de dato entre activities.
	 */
	public static final String JOB_ID = "es.vicmonmena.jobper.model.job.id";
	/**
	 * 
	 */
	private StartupDetailsAsyncTask startupTask;
	/**
	 * 
	 */
	private LoadImageTask loadImageTask;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.startup_details, container, false);
		startupTask = new StartupDetailsAsyncTask();
		startupTask.execute(getArguments().getString(StartupDetailsFragment.JOB_ID));
		return view;
	}
	
	/**
	 * Async task para obtener la información de la Startup relacionada con el Job
	 *
	 */
	private class StartupDetailsAsyncTask extends AsyncTask<String, Void, Startup> {
		
		@Override
		protected Startup doInBackground(String... params) {
			
			Startup startup = null;
			if (params != null && params.length > 0) {
				startup = Controller.getInstance().loadStartup(this, params[0]);
			}
			return startup;
		}
		
		@Override
		protected void onPostExecute(Startup result) {
			
			if (!isCancelled() && result != null) {
				if (!TextUtils.isEmpty(result.getName())) {
					((TextView) getView().findViewById(R.id.jobStartupTxt)).setText(result.getName());
				}
				
				if (!TextUtils.isEmpty(result.getProductDescription())) {
					((TextView) getView().findViewById(R.id.jobProductDescTxt)).setText(result.getProductDescription());
				}
				
				if (!TextUtils.isEmpty(result.getCompanyURL())) {
					getView().findViewById(R.id.startupLogoImg)
						.setTag((String)result.getCompanyURL());
				}
				if (!TextUtils.isEmpty(result.getLogoURL())) {
					loadImageTask = new LoadImageTask();
					loadImageTask.execute(result.getLogoURL());
				}
			}
		}
	}
	
	/**
     * Carga una imagen en 2º plano.
     */
    private class LoadImageTask extends AsyncTask<String, Void, Drawable> {
    	
        @Override
        protected Drawable doInBackground(String... params) {
        	Drawable image = null;
        	String imageURI = params[0];
        	
			if (!TextUtils.isEmpty(imageURI)) {
            	image = Controller.getInstance().loadImage(imageURI);
			}
            return image;
        }
        
        @Override
        protected void onPostExecute(Drawable result) {
        	if (!isCancelled() && result != null) {
        		((ImageView) getView().findViewById(R.id.startupLogoImg))
        		.setImageDrawable(result);
        	}
        }
	}
    
    @Override
    public void onDestroy() {
    	cancelAllTask();
    	super.onDestroy();
    }
    
    @Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		cancelAllTask();
	}
    
    private void cancelAllTask() {
    	if (startupTask != null) {
			startupTask.cancel(true);
		}
		if (loadImageTask != null) {
			loadImageTask.cancel(true);
		}
    }
}
