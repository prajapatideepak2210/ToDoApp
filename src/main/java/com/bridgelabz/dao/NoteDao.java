package com.bridgelabz.dao;

import java.util.List;

import com.bridgelabz.model.Note;

public interface NoteDao {
	public int addNote(Note note);
	
	public int deleteNote(int noteId);
	
	public Note updateNote(Note note);
	
	public List<Note> getNotes();
	
	public Note getNoteById(int id);
}
