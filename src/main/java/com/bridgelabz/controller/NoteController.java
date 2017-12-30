package com.bridgelabz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.Note;
import com.bridgelabz.model.Response;
import com.bridgelabz.model.User;
import com.bridgelabz.services.NoteService;
import com.bridgelabz.services.Service;
import com.bridgelabz.token.TokenGenerator;

@RestController
public class NoteController {

	@Autowired
	NoteService noteService;
	
	@Autowired
	Service userServiceImpl;

	@RequestMapping(value = "/addNote", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> addNote(@RequestBody Note note, HttpServletRequest request) {
		Response response = new Response();
		String token = request.getHeader("TokenAccess");
		
		if (note != null) {
			int noteId = noteService.addNote(note, token);
			if (noteId != 0) {
				response.setMessage("Note Successfully added.");
				return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
			} else {
				response.setMessage("Note is not added.");
				return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
			}
		}
		response.setMessage("Note is null, please fill the Note.");
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/deleteNote", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> deleteNote(HttpServletRequest request) {
		
		Response response = new Response();
		int noteId = noteService.deleteNote(request.getIntHeader("note_id"));
		if (noteId != 0) {
			response.setMessage("Note successfully deleted.");
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		}
		response.setMessage("Note is not deleted.");
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/updateNote", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> updateNote(@RequestBody Note note) {
		
		Response response = new Response();
		Note checkNote = noteService.updateNote(note);
		if (checkNote != null) {
			response.setMessage("Note Successfully Updated.");
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		}
		response.setMessage("Note is not updated.");
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getTrashedNote", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Note>> getAllNotes(HttpSession session) {
		int user_id=(int) session.getAttribute("user_id");
		List<Note> list = noteService.getTrashedNotes(user_id);
		if (list != null)
			return new ResponseEntity<List<Note>>(list, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<List<Note>>(list, HttpStatus.BAD_REQUEST);
	}

	
	@RequestMapping(value = "/getNoteByUserId", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Note>> getNoteByUserId(HttpSession session, HttpServletRequest request) {
		String token = request.getHeader("TokenAccess");
		int user_id=TokenGenerator.verifyToken(token);
		List<Note> list = noteService.getNoteById(user_id);
		if (list != null)
			return new ResponseEntity<List<Note>>(list, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<List<Note>>(list, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value="/collaborateUser", method = RequestMethod.POST)
	public ResponseEntity<Response> collaborateUser(@RequestBody Note note, HttpServletRequest servletRequest){
		Response response = new Response();
		User collabUser = userServiceImpl.getUserByEmail(servletRequest.getHeader("userNameForCollaborate"));
		Note oldNote = noteService.getNoteByNoteId(note.getId());
		if (collabUser!=null){
			oldNote.getCollaborator().add(collabUser);
			noteService.updateNote(oldNote);
			response.setMessage("Succefully successfully Added");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}else{
			response.setMessage("User Not available.");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);	
		}
	}
	
	@RequestMapping(value="/deleteCollabUser", method = RequestMethod.POST)
	public ResponseEntity<Response> deleteCollabUser(@RequestBody Note note, HttpServletRequest request, HttpSession session){
		Response response = new Response();
		Note oldnote =  noteService.getNoteByNoteId(note.getId());
		User collabUser = userServiceImpl.getUserByEmail(request.getHeader("userToDelete"));
		if(collabUser!=null){
			System.out.println("before remove "+oldnote.getCollaborator());
			oldnote.getCollaborator().remove(collabUser);
			noteService.updateNote(oldnote);
			System.out.println("after remove "+oldnote.getCollaborator());
		}else{
			response.setMessage("User Not found.");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);	
		}
		response.setMessage("Succefully removed");
		return new ResponseEntity<Response>(response, HttpStatus.OK);	
	}			
}
