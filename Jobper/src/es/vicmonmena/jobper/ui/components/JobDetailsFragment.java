package es.vicmonmena.jobper.ui.components;

import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import es.vicmonmena.jobper.Controller;
import es.vicmonmena.jobper.R;
import es.vicmonmena.jobper.model.Job;

/**
 * 
 * @author vicmonmena
 *
 */
public class JobDetailsFragment extends Fragment {

	/**
	 * TAG para mensajes de LOG.
	 */
	private static final String TAG = "JobDetailsFragment";
	/**
	 * Cadena key de paso de dato entre activities.
	 */
	public static final String JOB_ID = "es.vicmonmena.jobper.model.job.id";
	/**
	 * 
	 */
	private View view;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG,"onActivityCreated");
		
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i(TAG,"onCreateView");
		view = inflater.inflate(R.layout.activity_job_details, container, false);
		new JobAsyncTask().execute(getArguments().getString(JOB_ID));
		return view;
	}
	
	private class JobAsyncTask extends AsyncTask<String, Void, Job> {
		
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
			((TextView) view.findViewById(R.id.jtitle)).setText(result.getTitle());
			((TextView) view.findViewById(R.id.jpublished)).setText(result.getUpdateAt());
			((TextView) view.findViewById(R.id.jsalarymin)).setText(result.getSalaryMin());
			((TextView) view.findViewById(R.id.jsalarymax)).setText(result.getSalaryMax());
			super.onPostExecute(result);
		}
	}
}