package es.vicmonmena.jobper.ui.components;

import java.util.List;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import es.vicmonmena.jobper.Controller;
import es.vicmonmena.jobper.R;
import es.vicmonmena.jobper.model.Job;
import es.vicmonmena.jobper.ui.DetailsActivity;
import es.vicmonmena.jobper.ui.components.widget.JobAdapter;

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
	 * Cadena key de paso de dato entre activities.
	 */
	public static final String FAVORITE_JOB_LIST = "es.vicmonmena.jobper.model.job.favorite_job_list";
	/**
	 * A true indica que trabajamos con un tablet.
	 */
	private boolean singleColumn = false;
	/**
	 * Adapter para el listado de Jobs
	 */
	private JobAdapter adapter;
	/**
	 * 
	 */
	private JobsAsyncTask jobsTask;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		View jobDetailsFragment = getActivity()
			.findViewById(R.id.job_details_fragment);
		
		if (jobDetailsFragment == null) {
			singleColumn = true;
		}
		
		jobsTask = new JobsAsyncTask();
		jobsTask.execute();
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Job selectedJob = adapter.getItem(position);
		if (singleColumn){
			Intent intent = new Intent(getActivity(), DetailsActivity.class);
			intent.putExtra(JobDetailsFragment.JOB, selectedJob);
			startActivity(intent);
		} else {
			FragmentManager fm = getFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();

			Bundle args = new Bundle();
			args.putParcelable(JobDetailsFragment.JOB, selectedJob);
			
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
			return Controller.getInstance().loadJobs(this);
		}
		
		@Override
		protected void onPostExecute(List<Job> result) {
			adapter = new JobAdapter(getActivity(), 
				android.R.layout.simple_list_item_1, R.layout.job_row_item, result);
			setListAdapter(adapter);
			super.onPostExecute(result);
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		
		jobsTask.cancel(true);
	}
}