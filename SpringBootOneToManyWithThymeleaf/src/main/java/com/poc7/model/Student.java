package com.poc7.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "Students")

public class Student extends AuditModel{

	
	@Id
	@Column(name = "id" )
	
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;

	@Column(name = "First_Name")
	@NotNull
	private String firstName;
	
	
	@Column(name = "Last_Name")
	@NotNull
	private String lastName;
	
	@Column(name = "EmailId" ,unique = true)
	@NotNull
	
	private String emailId;
	
	@Column(name = "Contact_number", unique = true)
	@NotNull
	
	private String contact;
	
	@Column(name = "Photo")
	@NotNull
	private String photo;
	
	@Column(name = "Skills")
	@NotNull
	private String skills;
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public Student(Integer id, @NotNull String firstName, @NotNull String lastName, @NotNull String emailId,
			@NotNull String contact, @NotNull String photo, @NotNull String skills) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailId = emailId;
		this.contact = contact;
		this.photo = photo;
		this.skills = skills;
	}

	public Student() {
		
	}
	
	@Transient
    public String getPhotoImagePath() {
        if (photo == null || id == null) return null;
         
        return "/students-photos/" + id + "/" + photo;
    }

}
