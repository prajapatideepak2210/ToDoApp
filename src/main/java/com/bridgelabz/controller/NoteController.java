package com.bridgelabz.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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

	@RequestMapping(value = "/deleteNote/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> deleteNote(@PathVariable int id) {
		Response response = new Response();
		int noteId = noteService.deleteNote(id);
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
	public ResponseEntity<List<Note>> getNoteById(HttpSession session) {
		int user_id=(int) session.getAttribute("user_id");
		List<Note> list = noteService.getNoteById(user_id);
		if (list != null)
			return new ResponseEntity<List<Note>>(list, HttpStatus.ACCEPTED);
		else
			return new ResponseEntity<List<Note>>(list, HttpStatus.BAD_REQUEST);
	}
	
	
	/*@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	//@Produces(MediaType.APPLICATION_JSON) 
	public Response continueFileUpload(@RequestBody Note note,HttpServletRequest request, HttpServletResponse response){
	System.out.println(note.getNoteBackGround());
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request; 

	//String filename = "upload.xlsx";
	try {
		System.out.println(request);
	   mRequest = (MultipartHttpServletRequest) request;
	   mRequest.getParameterMap();
	   Iterator<?> itr = mRequest.getFileNames();
	   
	   while (itr.hasNext()) {
	        MultipartFile mFile = mRequest.getFile((String) itr.next());
	        String fileName = mFile.getOriginalFilename();
	        System.out.println(fileName);
	        
	        InputStream inputStream = mFile.getInputStream();
	        
	        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int n = 0;
			while (-1 != (n = inputStream.read(buf))) {
				outputStream.write(buf, 0, n);
			}
			outputStream.close();
			inputStream.close();
			
			String image = "data:image/png;base64," + new String(Base64.getEncoder().encode(outputStream.toByteArray()));

			//noteService.
	 }
	   } catch (Exception e) {
	        e.printStackTrace();
	   }
	return null;
	}*/

}
