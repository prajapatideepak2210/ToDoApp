package com.bridgelabz.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.dao.NoteDao;
import com.bridgelabz.model.Note;
import com.bridgelabz.model.User;
import com.bridgelabz.token.TokenGenerator;

/**
 * @author Deepak Prajapati
 * @Description This class is a service provider which implements NoteService class.
 */
@Service
public class NoteServiceImpl implements NoteService{

	@Autowired
	NoteDao noteDao;
	
	/* (non-Javadoc)
	 * @see com.bridgelabz.services.NoteService#addNote(com.bridgelabz.model.Note)
	 */
	public int addNote(Note note, String token) {
		int id=TokenGenerator.verifyToken(token);
		if(id>0)
		{
			User user=new User();
			Date date = new Date();
			note.setCreatedDate(date);
			note.setLastUpdate(date);
			user.setId(id);
			note.setUser_id(id);
			return noteDao.addNote(note);
		}
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.services.NoteService#deleteNote(int)
	 */
	public int deleteNote(int noteId) {
		int checkNote= noteDao.deleteNote(noteId);
		return checkNote;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.services.NoteService#updateNote(com.bridgelabz.model.Note)
	 */
	public Note updateNote(Note note) {
		if(note!=null)
		{
			Date date=new Date();
			note.setLastUpdate(date);
			return noteDao.updateNote(note);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.services.NoteService#getNotes()
	 */
	public List<Note> getNotes() {
		return noteDao.getNotes();
	}

	/* (non-Javadoc)
	 * @see com.bridgelabz.services.NoteService#getNoteById(int)
	 */
	public Note getNoteById(int id) {
		return noteDao.getNoteById(id);
	}

}
