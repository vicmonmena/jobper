package es.vicmonmena.jobper.ui.components;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import es.vicmonmena.jobper.R;
import es.vicmonmena.jobper.model.Job;

/**
 * 
 * @author vicmonmena
 *
 */
public class JobAdapter extends ArrayAdapter<Job>{
	
	private List<Job> jobs;
	private LayoutInflater mInflater;
	
	public JobAdapter(Context context, int resource, int textViewResourceId,
			List<Job> jobs) {
		super(context, resource, textViewResourceId, jobs);
		this.jobs = jobs;
		mInflater = LayoutInflater.from(context);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = null;
		ViewHolder holder = null;
		
		if (convertView == null) {
			view = mInflater.inflate(R.layout.job_row_item, parent, false);
			holder = new ViewHolder();
			holder.jobTitleTextView = (TextView) view.findViewById(R.id.jobTitleTextView);
			holder.jobDateTextView = (TextView) view.findViewById(R.id.jobDateTextView);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.jobTitleTextView.setText(jobs.get(position).getTitle());
		holder.jobDateTextView.setText(jobs.get(position).getUpdateAt());
		
		return view;
	}

	static class ViewHolder {
		TextView jobTitleTextView;
		TextView jobDateTextView;
	}
	
	@Override
	public int getCount() {
		return jobs.size();
	}
	
	@Override
	public Job getItem(int position) {
		return jobs.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return Long.parseLong(jobs.get(position).getJobId());
	}
}
