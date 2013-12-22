package es.vicmonmena.jobper.ui.components.widget;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import es.vicmonmena.jobper.Controller;
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
		
		View rowView = convertView;
		ViewHolder holder = new ViewHolder();
		
		if (rowView == null) {
			rowView = mInflater.inflate(R.layout.job_row_item, parent, false);
			holder.jobTitleTextView = (TextView) rowView.findViewById(R.id.jobTitleTextView);
			holder.jobDateTextView = (TextView) rowView.findViewById(R.id.jobDateTextView);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		Log.e("JobAdapter", jobs.get(position).getTitle());
		holder.jobTitleTextView.setText(jobs.get(position).getTitle());
		String date = Controller.getDateFormat(jobs.get(position).getUpdateAt());
		holder.jobDateTextView.setText(date);
		
		return rowView;
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
