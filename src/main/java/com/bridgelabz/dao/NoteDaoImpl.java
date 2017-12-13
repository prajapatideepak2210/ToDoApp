package com.bridgelabz.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.model.Note;

/**
 * @author Deepak Prajapati
 * @Description This class is used to communicate with the Database.
 */
@Repository
public class NoteDaoImpl implements NoteDao {

	@Autowired
	SessionFactory sessionFactory;

	/**
	 * @param note
	 * @return boolean
	 * @Description This method is used to add the notes into the database. It
	 *              returns true if data is inserted, otherwise it returns
	 *              false.
	 */
	public int addNote(Note note) {
		int noteId = 0;
		if (note != null) {
			try {
				Session session = sessionFactory.openSession();
				Transaction transaction = session.beginTransaction();
				noteId = (Integer) session.save(note);
				transaction.commit();
				session.close();
				return noteId;
			} catch (Exception e) {
				return noteId;
			}
		}
		return noteId;
	}

	/**
	 * @param note
	 * @return boolean
	 * @Description this method is used to delete the note, it will return true
	 *              if note is deleted, otherwise returns false.
	 */
	public int deleteNote(int noteId) {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Note note = (Note) session.load(Note.class, noteId);
			session.delete(note);
			transaction.commit();
			session.close();
			return noteId;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * @param note
	 * @return boolean
	 * @Description This method is used to update the note, it returns true if
	 *              note is updated otherwise returns false.
	 */
	@SuppressWarnings("rawtypes")
	public Note updateNote(Note note) {
		try {
			int id = note.getId();
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();

			/* code for geting createdDate and setting createdDate into note */
			Session tempSession = sessionFactory.openSession();
			Query query = tempSession.createQuery("from Note where id = :id");
			query.setParameter("id", id);
			Note noteForGetCreatedDate = (Note) query.uniqueResult();
			Date createdDate = noteForGetCreatedDate.getCreatedDate();
			note.setCreatedDate(createdDate);
			int user_id=noteForGetCreatedDate.getUser_id();
			note.setUser_id(user_id);
			tempSession.close();

			session.update(note);
			transaction.commit();
			session.close();
			return note;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * @return List
	 * @Description this method is used to get the list of note from the
	 *              database.
	 */
	@SuppressWarnings("unchecked")
	public List<Note> getNotes() {
		Session session = sessionFactory.openSession();
		Query<Note> query = session.createQuery("from Note");
		List<Note> list = (List<Note>) query.list();
		session.close();
		return list;
	}

	/**
	 * @param id
	 * @return Note
	 * 
	 * @Description It will return User if note available in database otherwise
	 *              return null.
	 */
	@SuppressWarnings("rawtypes")
	public Note getNoteById(int id) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("from Note where id = :id");
		query.setParameter("id", id);
		Note note = (Note) query.uniqueResult();
		session.close();
		return note;
	}
}
