package com.bridgelabz.services;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.dao.NoteDao;
import com.bridgelabz.model.Label;
import com.bridgelabz.model.Note;
import com.bridgelabz.model.User;
import com.bridgelabz.token.TokenGenerator;

/**
 * @author Deepak Prajapati
 * @Description This class is a service provider which implements NoteService
 *              class.
 */
@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	NoteDao noteDao;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bridgelabz.services.NoteService#addNote(com.bridgelabz.model.Note)
	 */
	public int addNote(Note note, String token) {
		int id = TokenGenerator.verifyToken(token);
		if (id > 0) {
			User user = new User();
			Date date = new Date();
			note.setCreatedDate(date);
			note.setLastUpdate(date);
			user.setId(id);
			note.setUser_id(id);
			return noteDao.addNote(note);
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bridgelabz.services.NoteService#deleteNote(int)
	 */
	public int deleteNote(int noteId) {
		int checkNote = noteDao.deleteNote(noteId);
		return checkNote;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.bridgelabz.services.NoteService#updateNote(com.bridgelabz.model.Note)
	 */
	public Note updateNote(Note note) {
		if (note != null) {
			Date date = new Date();
			note.setLastUpdate(date);
			return noteDao.updateNote(note);
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bridgelabz.services.NoteService#getNotes()
	 */
	
	@SuppressWarnings("null")
	@Override
	public List<Note> getTrashedNotes(int user_id) {
		List<Note> list = noteDao.getNoteById(user_id);
		List<Note> listForReturn=null;
		Iterator<Note> iterator=list.iterator();
		while (iterator.hasNext()) {
			Note note = (Note) iterator.next();
			if(note.getTrash())
			{
				listForReturn.add(note);
			}
		}
		return listForReturn;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.bridgelabz.services.NoteService#getNoteById(int)
	 */
	public List<Note> getNoteById(int user_id) {
		
		return noteDao.getNoteById(user_id);
		
		/*List<Note> list = noteDao.getNoteById(user_id);
		List<Note> listForReturn=null;
		Iterator<Note> iterator=list.iterator();
		System.out.println(list.size());
		Note note ;
		while (iterator.hasNext()) {
			note = (Note) iterator.next();
			System.out.println(note.getTrash());
			if(note.getTrash()==false)
			{
				System.out.println(note.getTitle());
				listForReturn.add(note);
			}
		}
		return listForReturn;*/
		
	}

	@Override
	public Note getNoteByNoteId(int id) {
		
		return noteDao.getNoteByNoteId(id);
	}

	/*======================================= Label ========================================*/
	
	public int createLabel(String labelName, Note note, User user){
		int labelId = 0;
		if(labelName!=null && note!=null && user!=null){
			try {
				Label label = new Label();
				label.setLabelName(labelName);
				System.out.println("name added.");
				label.getNote().add(note);
				System.out.println("Note added.");
				label.setUser(user);
				System.out.println("name added.");
				labelId = noteDao.createLabel(label);
				return labelId;
			} catch (Exception e) {
				e.printStackTrace();
				return labelId;
			}
		}
		else{
			return labelId;
		}
	}

	@Override
	public int createLabel(Label label) {
		
		return noteDao.createLabel(label);
	}

	@Override
	public List<Label> getLabelByUserId(int user_id) {
		return noteDao.getLabelByUserId(user_id);
	}

	@Override
	public int deleteLabel(Label label) {
		int label_id = label.getId();
		
		return noteDao.deleteLabel(label_id);
	}

	@Override
	public Label updateLabel(Label label) {
		return noteDao.updateLabel(label);
	}

	@Override
	public Label getLabelByLabelId(int label_id) {
		
		return noteDao.getLabelByLabelId(label_id);
	}
	
}
