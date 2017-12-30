package com.bridgelabz.model;

import java.util.Date;
import java.util.LinkedHashSet;
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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author Deepak Prajapati
 *
 */
@Entity 
@Table(name="ToDo_Note")
public class Note {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO , generator="mygenerator")
	@GenericGenerator(name="mygenerator", strategy="native")
	private int id;
	private String title;
	private String description;
	private Date createdDate;
	private Date lastUpdate;
	private int user_id;
	private boolean trash;
	private boolean archive;
	private boolean pin;
	private Date reminder;
	@Lob
	@Column(columnDefinition="LONGBLOB")
	private String noteBackGround;
	private String noteColor;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name="collaborator", joinColumns=@JoinColumn(name="note_id"),inverseJoinColumns=@JoinColumn(name="user_id"))
	private Set<User> collaborator = new LinkedHashSet<User>();
	

	
	public Set<User> getCollaborator() {
		return collaborator;
	}
	public void setCollaborator(Set<User> collaborator) {
		this.collaborator = collaborator;
	}
	public String getNoteColor() {
		return noteColor;
	}
	public void setNoteColor(String noteColor) {
		this.noteColor = noteColor;
	}
	public Date getReminder() {
		return reminder;
	}
	public void setReminder(Date reminder) {
		this.reminder = reminder;
	}
	public String getNoteBackGround() {
		return noteBackGround;
	}
	public void setNoteBackGround(String noteBackGround) {
		this.noteBackGround = noteBackGround;
	}
	public boolean getPin() {
		return pin;
	}
	public void setPin(boolean pin) {
		this.pin = pin;
	}
	public boolean getArchive() {
		return archive;
	}
	public void setArchive(boolean archive) {
		this.archive = archive;
	}
	public boolean getTrash() {
		return trash;
	}
	public void setTrash(boolean trash) {
		this.trash = trash;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
