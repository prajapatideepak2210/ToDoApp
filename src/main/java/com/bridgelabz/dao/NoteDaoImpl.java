package com.bridgelabz.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.model.Label;
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
				e.printStackTrace();
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
	 * @return List
	 * 
	 * @Description It will return List of user if given user_id is available in database otherwise
	 *              return null.
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Note> getNoteById(int user_id) {
		Session session = sessionFactory.openSession();
		Query<Note> query = session.createQuery("from Note where user_id = :user_id");
		query.setParameter("user_id", user_id);
		List<Note> notes =  query.list();
		
		Criteria criteria = session.createCriteria(Note.class);
		criteria.createAlias("collaborator", "user");
		criteria.add(Restrictions.eq("user.id", user_id));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		List<Note> list = criteria.list();
		list.forEach(i->System.out.println(i.getId()));
		notes.addAll(list);
		
		session.close();
		return notes;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Note getNoteByNoteId(int id) {
		try {
			Session session = sessionFactory.openSession();
			Query query = session.createQuery("from Note where id = :id");
			query.setParameter("id", id);
			Note note = (Note) query.uniqueResult();
			session.close();
			return note;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public int createLabel(Label label) {
		int labelId=0;
		if(label!=null){
			try {
				Session session = sessionFactory.openSession();
				Transaction transaction = session.beginTransaction();
				labelId = (Integer) session.save(label);
				transaction.commit();
				session.close();
				return labelId;
			} catch (Exception e) {
				return labelId;
			}
		}
		else{
			return labelId;
		}
	}

	@Override
	public List<Label> getLabelByUserId(int user_id) {
		if(user_id!=0){
			try {
				Session session = sessionFactory.openSession();
				Query<?> query = session.createQuery("from Label where User_id = :user_id");
				query.setParameter("user_id", user_id);
				@SuppressWarnings("unchecked")
				List<Label> list = (List<Label>) query.list();
				return list;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		else{
			return null;
		}
		
	}

	@Override
	public int deleteLabel(int label_id) {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			Label label = (Label) session.load(Label.class, label_id);
			session.delete(label);
			transaction.commit();
			session.close();
			return label_id;
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public Label updateLabel(Label label) {
		try {
			Session session = sessionFactory.openSession();
			Transaction transaction = session.beginTransaction();
			session.update(label);
			transaction.commit();
			session.close();
			return label;
		} catch (Exception e) {
			return null;
		}
		
	}

	@Override
	public Label getLabelByLabelId(int label_id) {
		try {
			Session session = sessionFactory.openSession();
			Query query = session.createQuery("from Label where id = :id");
			query.setParameter("id", label_id);
			Label label = (Label) query.uniqueResult();
			session.close();
			return label;
		} catch (Exception e) {
			return null;
		}
	}
	
	
}
