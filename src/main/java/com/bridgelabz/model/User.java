package com.bridgelabz.model;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity 
@Table(name="ToDo_User")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO , generator="mygenerator")
	@GenericGenerator(name="mygenerator", strategy="native")
	private int id;
	private String fName;
	private String lName;
	private String userName;
	private String password;
	private String contactNumber;
	private String address;
	private int isUserActive;
	@Lob
	@Column(columnDefinition="LONGBLOB")	
	private String profilePic;
	
	@ManyToMany
	@JsonIgnore
	@JoinTable(name = "collaborator", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "note_id"))
	private List<Note> collaborator = new LinkedList<>();
	
	@OneToMany(mappedBy = "user",fetch=FetchType.EAGER)
	private Set<Label> labels = new HashSet<Label>();
	
	public List<Note> getCollaborator() {
		return collaborator;
	}
	public void setCollaborator(List<Note> collaborator) {
		this.collaborator = collaborator;
	}
	public int getIsUserActive() {
		return isUserActive;
	}
	public void setIsUserActive(int isUserActive) {
		this.isUserActive = isUserActive;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getProfilePic() {
		return profilePic;
	}
	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		User user = (User) obj;
		if(user.getId()==id){
			return true;
		}
		return false;
	}
	
}
