

public class User {

	private int id;
	private String first_name;
	private String last_name;
	private String email;
	private Long unique_person_number;
	private String country;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getUnique_person_number() {
		return unique_person_number;
	}
	public void setUnique_person_number(Long unique_person_number) {
		this.unique_person_number = unique_person_number;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public User(int id, String first_name, String last_name, String email, Long unique_person_number, String country) {
		super();
		this.id = id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.unique_person_number = unique_person_number;
		this.country = country;
	}
	
	public User(String first_name, String last_name, String email, Long unique_person_number, String country) {
		super();
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.unique_person_number = unique_person_number;
		this.country = country;
	}
	


	
}
