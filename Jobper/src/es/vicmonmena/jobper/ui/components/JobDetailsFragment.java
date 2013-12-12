package es.vicmonmena.jobper.ui.components;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import es.vicmonmena.jobper.R;

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
	
	public static final String JOB_TITLE = "job_title";
	
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
		View view = inflater.inflate(R.layout.activity_job_details, container, false);
		
		TextView jobTitle = (TextView) view.findViewById(R.id.textView1);
		jobTitle.setText(getArguments().getString(JOB_TITLE));
		
		return view;
	}
	
	
}
