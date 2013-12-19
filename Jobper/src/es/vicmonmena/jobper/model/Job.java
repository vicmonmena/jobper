package es.vicmonmena.jobper.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Representa una Startup
 * @author vicmonmena
 *
 */
public class Job implements Parcelable {

	/**
	 * 
	 */
	private int id;
	/**
	 * 
	 */
	private String jobId;
	/**
	 * 
	 */
	private String title;
	/**
	 * 
	 */
	private String updateAt;
	/**
	 * 
	 */
	private String salaryMin;
	/**
	 * 
	 */
	private String salaryMax;
	/**
	 * 
	 */
	private String location;
	/**
	 * Startup asociada al Job.
	 */
	private Startup startup;
	/**
	 * Marcado como favorito
	 */
	private boolean favorite;
	
	// Construtors
	
	public Job() {
		// TODO Auto-generated constructor stub
	}

	public Job(Parcel in) {
		readFromParcel(in);
	}
	
	// Getter y settet
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUpdateAt() {
		return updateAt;
	}

	public void setUpdateAt(String updateAt) {
		this.updateAt = updateAt;
	}

	public String getSalaryMin() {
		return salaryMin;
	}

	public void setSalaryMin(String salaryMin) {
		this.salaryMin = salaryMin;
	}

	public String getSalaryMax() {
		return salaryMax;
	}

	public void setSalaryMax(String salaryMax) {
		this.salaryMax = salaryMax;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Startup getStartup() {
		return startup;
	}
	
	public void setStartup(Startup startup) {
		this.startup = startup;
	}
	
	public boolean isFavorite() {
		return favorite;
	}
	
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	
	// Parcelable methods
	
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(jobId);
		dest.writeString(title);
		dest.writeString(updateAt);
		dest.writeString(salaryMin);
		dest.writeString(salaryMax);
		dest.writeByte((byte) (favorite ? 1 : 0));
	}
	
	private void readFromParcel(Parcel in) {
		id = in.readInt();
		jobId = in.readString();
		title = in.readString();
		updateAt = in.readString();
	    salaryMin = in.readString();
	    salaryMax = in.readString();
	    favorite = in.readByte() != 0;
    }
	
	public static final Parcelable.Creator<Job> CREATOR = 
		new Parcelable.Creator<Job>() {
        public Job createFromParcel(Parcel in) {
            return new Job(in);
        }
 
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };

}
