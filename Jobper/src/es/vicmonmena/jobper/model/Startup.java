package es.vicmonmena.jobper.model;

/**
 * Representa una Startup
 * @author vicmonmena
 *
 */
public class Startup {

	/**
	 * Identificador único.
	 */
	private int id;
	/**
	 * Nombre de Startup.
	 */
	private String name;
	/**
	 * 
	 */
	private String logoURL;
	/**
	 * 
	 */
	private String productDescription;
	/**
	 * 
	 */
	private String companyURL;
	
	// Constructors
	
	public Startup() {
		// TODO Auto-generated constructor stub
	}

	// Getter y setter
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogoURL() {
		return logoURL;
	}

	public void setLogoURL(String logoURL) {
		this.logoURL = logoURL;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getCompanyURL() {
		return companyURL;
	}

	public void setCompanyURL(String companyURL) {
		this.companyURL = companyURL;
	}
}
