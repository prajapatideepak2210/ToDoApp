package com.bridgelabz.dao;

import java.util.List;

import com.bridgelabz.model.Label;
import com.bridgelabz.model.Note;

public interface NoteDao {
	public int addNote(Note note);
	
	public int deleteNote(int noteId);
	
	public Note updateNote(Note note);
	
	public List<Note> getNotes();
	
	public List<Note> getNoteById(int id);

	public Note getNoteByNoteId(int id);
	
	int createLabel(Label label);

	public List<Label> getLabelByUserId(int user_id);

	public int deleteLabel(int label_id);

	public Label updateLabel(Label label);

	public Label getLabelByLabelId(int label_id);
}
