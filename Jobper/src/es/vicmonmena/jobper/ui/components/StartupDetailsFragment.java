package es.vicmonmena.jobper.ui.components;

import android.app.Fragment;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import es.vicmonmena.jobper.Controller;
import es.vicmonmena.jobper.R;
import es.vicmonmena.jobper.model.Job;
import es.vicmonmena.jobper.util.Jobper;

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
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG,"onCreateView");
		View view = inflater.inflate(R.layout.startup_details, container, false);
		new JobAsyncTask().execute(getArguments().getString(StartupDetailsFragment.JOB_ID));
		return view;
	}
	
	/**
	 * Async task para obtener la información de la Startup relacionada con el Job
	 *
	 */
	private class JobAsyncTask extends AsyncTask<String, Void, Job> {
		
		@Override
		protected void onPreExecute() {
			//view.findViewById(R.id.loadingJobDetailsLayout).setVisibility(View.VISIBLE);
			//view.findViewById(R.id.startupJobDetailsLayout).setVisibility(View.GONE);
			super.onPreExecute();
		}
		
		@Override
		protected Job doInBackground(String... params) {
			
			Job job = null;
			if (params != null && params.length > 0) {
				job = Controller.getInstance().loadJob(params[0]);
			}
			return job;
		}
		
		@Override
		protected void onPostExecute(Job result) {
			
			if (result != null) {
				if (!TextUtils.isEmpty(result.getLocation())) {
					((TextView) getView().findViewById(R.id.jobLocationTxt)).setText(result.getLocation());
				}
				if (!TextUtils.isEmpty(result.getStartup().getName())) {
					((TextView) getView().findViewById(R.id.jobStartupTxt)).setText(result.getStartup().getName());
				}
				
				if (!TextUtils.isEmpty(result.getStartup().getProductDescription())) {
					((TextView) getView().findViewById(R.id.jobProductDescTxt)).setText(result.getStartup().getProductDescription());
				}
				
				if (!TextUtils.isEmpty(result.getStartup().getCompanyURL())) {
					getView().findViewById(R.id.startupLogoImg)
						.setTag((String)result.getStartup().getCompanyURL());
				}
				if (!TextUtils.isEmpty(result.getStartup().getLogoURL())) {
					new LoadImageTask().execute(result.getStartup().getLogoURL());
				}
				//view.findViewById(R.id.loadingJobDetailsLayout).setVisibility(View.GONE);
				//view.findViewById(R.id.startupJobDetailsLayout).setVisibility(View.VISIBLE);
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
        	
        	if (result != null) {
        		((ImageView) getView().findViewById(R.id.startupLogoImg))
        		.setImageDrawable(result);
        	}
        }
	}
}
