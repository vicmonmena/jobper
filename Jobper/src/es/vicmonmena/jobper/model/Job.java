package es.vicmonmena.jobper.model;

/**
 * Representa una Startup
 * @author vicmonmena
 *
 */
public class Job {

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
	
	// Construtors
	
	public Job() {
		// TODO Auto-generated constructor stub
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
}
