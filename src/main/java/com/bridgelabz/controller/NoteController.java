package com.bridgelabz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.Label;
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
	public ResponseEntity<List<Note>> getTrashedNotes(HttpSession session) {
		int user_id=(int) session.getAttribute("user_id");
		List<Note> list = noteService.getTrashedNotes(user_id);
		if (list != null)
			return new ResponseEntity<List<Note>>(list, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<List<Note>>(list, HttpStatus.BAD_REQUEST);
	}

	
	@RequestMapping(value = "/getAllNotes", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getAllNotes(HttpSession session, HttpServletRequest request) {
		String token = request.getHeader("TokenAccess");
		
		int user_id=TokenGenerator.verifyToken(token);
		User user = userServiceImpl.getUserById(user_id);
		List<Note> notes = null;
		if (user != null){
			notes = noteService.getNoteById(user.getId());
			return ResponseEntity.ok(notes);
		}else{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User Not logged In");
		}
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
			oldnote.getCollaborator().remove(collabUser);
			noteService.updateNote(oldnote);
			response.setMessage("Succefully removed");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}else{
			response.setMessage("User Not found.");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);	
		}
			
	}
	
	/*=====================================Label=======================================*/
	
	/*@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<Response> addNoteInLabel(@RequestBody Note note, HttpServletRequest request){
		String labelName = request.getHeader("labelName");
		System.out.println("Label Name : "+labelName);
		Response responseMessage = new Response();
		User user = userServiceImpl.getUserById(note.getUser_id());
		int labelId = noteService.createLabel(labelName, note, user);
		if(labelId>0)
		{
			responseMessage.setMessage("Label successfully created.");
			return new ResponseEntity<Response>(responseMessage, HttpStatus.ACCEPTED);
		}
		else{
			responseMessage.setMessage("creation failed.");
			return new ResponseEntity<Response>(responseMessage, HttpStatus.BAD_REQUEST);
		}
	}*/
	
	@RequestMapping(value = "/createLabel", method = RequestMethod.POST)
	public ResponseEntity<Response> addLabel(@RequestBody String labelName, HttpServletRequest request){
		Label label = new Label();
		System.out.println(labelName);
		Response response = new Response();
		String token = request.getHeader("TokenAccess");
		int userId = TokenGenerator.verifyToken(token);
		if(userId>0){
			User user = userServiceImpl.getUserById(userId);
			if(user!=null){
				label.setLabelName(labelName);
				label.setUser(user);
				int labelId = noteService.createLabel(label);
				if(labelId>0){
					response.setMessage("label created successfully");
					return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
				}
				response.setMessage("label creation failed.");
				return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
			}
			else{
				response.setMessage("label creation failed.");
				return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
			}
		}
		else{
			response.setMessage("label creation failed.");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
	}
	
	
	@RequestMapping(value="/addNoteInLabel", method = RequestMethod.POST)
	public ResponseEntity<Response> addNoteInLabel(@RequestBody Note note, HttpServletRequest request){
		Response response  = new Response();
		if(note!=null){
			System.out.println("note details : "+note.getTitle());
			response.setMessage("Ho gaya call");
			return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
		}
		else{
			response.setMessage("nahi hua");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value="/getLabels", method = RequestMethod.GET)
	public ResponseEntity<List<Label>> getLabels(HttpServletRequest request){
		String token = request.getHeader("TokenAccess");
		List<Label> listOfLabel = null;
		int user_id = TokenGenerator.verifyToken(token);
		if(user_id>0){
			listOfLabel = noteService.getLabelByUserId(user_id);
			if(listOfLabel!=null)
			{
				return new ResponseEntity<List<Label>>(listOfLabel, HttpStatus.ACCEPTED);
			}
			else
				return new ResponseEntity<List<Label>>(listOfLabel, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<List<Label>>(listOfLabel, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/deleteLabel", method = RequestMethod.POST)
	public ResponseEntity<Response> deleteLabel(@RequestBody Label label, HttpServletRequest request){
		System.out.println("delete the label");
		Response response = new Response();
		String token = request.getHeader("TokenAccess");
		int user_id = TokenGenerator.verifyToken(token);
		if(user_id>0 && label!=null)
		{
			int id = noteService.deleteLabel(label);
			if(id>0){
				response.setMessage("label successfully deleted.");
				return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
			}
			response.setMessage("label is note deleted. backend problem.");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
		else{
			response.setMessage("label is note deleted.");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@RequestMapping(value = "/updateLabel", method = RequestMethod.POST)
	public ResponseEntity<Response> updateLabel(@RequestBody Label label, HttpServletRequest request){
		
		Response response = new Response();
		String token = request.getHeader("TokenAccess");
		int user_id = TokenGenerator.verifyToken(token);
		if(user_id>0 && label!=null){
			User user = userServiceImpl.getUserById(user_id);
			label.setUser(user);
			Label labelForCheck = noteService.updateLabel(label);
			if(labelForCheck!=null){
				response.setMessage("label successfully updated.");
				return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
			}
			response.setMessage("label note updated.");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
		response.setMessage("label note updated.");
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(value = "/updateNoteInLabel", method = RequestMethod.POST)
	public ResponseEntity<Response> updateNoteForLabel(@RequestBody Note note, HttpServletRequest request){
		
		Response response = new Response();
		int label_id = request.getIntHeader("labelId");
		if(label_id>0 && note!=null){
			Label label = noteService.getLabelByLabelId(label_id);
			if(label!=null){
				note.getLabels().add(label);
				Note note2 = noteService.updateNote(note);
				if(note2!=null){
					response.setMessage("note successfully added.");
					return new ResponseEntity<Response>(response, HttpStatus.ACCEPTED);
				}
				response.setMessage("note is not added.");
				return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
			}
			response.setMessage("note is not added.");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
		response.setMessage("note is not added.");
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}
	
	
}
