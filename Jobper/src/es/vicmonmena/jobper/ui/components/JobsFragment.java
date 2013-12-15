package es.vicmonmena.jobper.ui.components;

import java.util.List;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import es.vicmonmena.jobper.Controller;
import es.vicmonmena.jobper.R;
import es.vicmonmena.jobper.model.Job;
import es.vicmonmena.jobper.ui.DetailsActivity;

/**
 * 
 * @author vicmonmena
 *
 */
public class JobsFragment extends ListFragment {
	
	/**
	 * TAG para mensajes de LOG.
	 */
	private static final String TAG = "JobsFragment";
	/**
	 * A true indica que trabajamos con un tablet.
	 */
	private boolean singleColumn = false;
	/**
	 * Adapter para el listado de Jobs
	 */
	private JobAdapter adapter;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Log.i(TAG,"onActivityCreated");
		View jobDetailsFragment = getActivity()
			.findViewById(R.id.job_details_fragment);
		
		if (jobDetailsFragment == null) {
			singleColumn = true;
		}

		new JobsAsyncTask().execute();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		String jobId = adapter.getItem(position).getJobId();

		if (singleColumn){
			Intent intent = new Intent(getActivity(), DetailsActivity.class);
			intent.putExtra(JobDetailsFragment.JOB_ID, jobId);
			startActivity(intent);
		} else {
			FragmentManager fm = getFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();

			Bundle args = new Bundle();
			args.putCharSequence(JobDetailsFragment.JOB_ID, jobId);
			
			JobDetailsFragment jobDetails = new JobDetailsFragment();
			jobDetails.setArguments(args);
			
			transaction.replace(R.id.job_details_fragment, jobDetails);
			transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			transaction.commit();
		}
	}
	
	/**
	 * 
	 * @author vicmonmena
	 *
	 */
	private class JobsAsyncTask extends AsyncTask<Void, Void, List<Job>> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}
		
		@Override
		protected List<Job> doInBackground(Void... params) {
			return Controller.getInstance().loadJobs();
		}
		
		@Override
		protected void onPostExecute(List<Job> result) {
			adapter = new JobAdapter(getActivity(), 
				android.R.layout.simple_list_item_1, R.layout.job_item, result);
			setListAdapter(adapter);
			super.onPostExecute(result);
		}
	}
}