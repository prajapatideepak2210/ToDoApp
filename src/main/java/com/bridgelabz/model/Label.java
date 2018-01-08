package com.bridgelabz.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Label {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO , generator="mygenerator")
	@GenericGenerator(name="mygenerator", strategy="native")
	private int id;
	
	@ManyToMany(mappedBy="labels")
	@JsonIgnore
	private Set<Note> note = new HashSet<>();                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name="user_id")
	private User user;
	
	private String labelName;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Set<Note> getNote() {
		return note;
	}

	public void setNote(Set<Note> note) {
		this.note = note;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
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
		
		Label label = (Label) obj;
		if (label.getId()==id){
			return true;
		}
		return false;
	}
	
	
	
}
