package com.bridgelabz.services;

import java.util.List;

import com.bridgelabz.model.Note;

/**
 * @author Deepak Prajapati
 * @Description This Interface is a Service provider, which provides service like addition, deletion, updation etc.
 */
public interface NoteService {
	
	/**
	 * @param note
	 * @return boolean
	 * @Description This method is used to add the note, 
	 * it returns true if note added and returns false if note is not added.
	 */
	int addNote(Note note, String token);
	
	/**
	 * @param noteId
	 * @return boolean
	 * @Description This method is used to delete the note, 
	 * it returns true if note deleted and returns false if note is not deleted.
	 */
	int deleteNote(int noteId);
	
	/**
	 * @param note
	 * @return boolean
	 * @Description This method is used to update the note, 
	 * it returns true if note updated and returns false if note is not updated..
	 */
	Note updateNote(Note note);
	
	/**
	 * @return {@link List}
	 * @Description This method is used to getAll the note, 
	 * it returns List of Notes if note are available and returns null if note is not available.
	 */
	List<Note> getTrashedNotes(int user_id);
	
	/**
	 * @param user_id
	 * @return {@link Note}
	 * @Description This method is used to get the notes, 
	 * it returns list of notes if user_id is available otherwise returns null.
	 */
	List<Note> getNoteById(int user_id);
}
