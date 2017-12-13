package com.bridgelabz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.bridgelabz.services.NoteService;

@RestController
public class NoteController {

	@Autowired
	NoteService noteService;

	@RequestMapping(value = "/addNote", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> addNote(@RequestBody Note note, HttpServletRequest request) {
		Response response = new Response();
		String token = request.getHeader("TokenAccess");
		if (note != null) {
			int noteId=noteService.addNote(note, token);
			if (noteId!=0) {
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

	@RequestMapping(value = "/deleteNote/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> deleteNote(@PathVariable int id) {
		Response response = new Response();
		int noteId=noteService.deleteNote(id);
		if (noteId!=0) {
			response.setMessage("Note successfully deleted.");
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		}
		response.setMessage("Note is not deleted.");
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);

	}

	@RequestMapping(value = "/updateNote/{id}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> updateNote(@RequestBody Note note, @PathVariable int id) {
		Response response = new Response();
		note.setId(id);
		Note checkNote=noteService.updateNote(note);
		if (checkNote!=null) {
			response.setMessage("Note Successfully Updated.");
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		}
		response.setMessage("Note is not updated.");
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getAllNotes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Note>> getAllNotes() {
		List<Note> list = noteService.getNotes();
		System.out.println(list);
		if (list != null)
			return new ResponseEntity<List<Note>>(list, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<List<Note>>(list, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/getNote/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Note> getNoteById(@PathVariable int id) {
		Note note = noteService.getNoteById(id);
		if (note != null)
			return new ResponseEntity<Note>(note, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<Note>(note, HttpStatus.BAD_REQUEST);
	}

}
