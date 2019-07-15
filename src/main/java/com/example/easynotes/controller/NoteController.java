package com.example.easynotes.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.easynotes.exception.ResourceNotFoundException;
import com.example.easynotes.model.Note;
import com.example.easynotes.repository.NoteRepository;

@RestController
@RequestMapping("/api")
public class NoteController {

	@Autowired
	NoteRepository noterepository;
	
	//Get all notes
	@GetMapping("/notes")
	public List<Note> getAllNote()
	{
		return noterepository.findAll();
	}

	//Post a Note
	//{"title":"My First  Note","content":"This is also very   good for reading"}
	@PostMapping("/notes")
	public Note createNote(@Valid @RequestBody Note note) 
	{
		return noterepository.save(note);
	}
	
	//Get a single note
	@GetMapping("/notes/{id}")
	public Note getNoteById(@PathVariable(value="id") long noteid)
	{
		return noterepository.findById(noteid)
				.orElseThrow(()-> new ResourceNotFoundException("Note","id",noteid)); 
	}
	
	//put -- update a note
	@PutMapping("notes/{id}")
	public Note updateNote(@PathVariable(value="id") long noteid,@Valid @RequestBody Note notedetail)
	{
		Note note=noterepository.findById(noteid)
				.orElseThrow(()-> new ResourceNotFoundException("Note","id",noteid)); 
		
		note.setTitle(notedetail.getTitle());
		note.setContent(notedetail.getContent());
		
		Note updateNote=noterepository.save(note);
		return updateNote;
	}
	
	//Delete a note
	@DeleteMapping("/notes/{id}")
	public ResponseEntity<?> deleteNote(@PathVariable(value="id") long noteid)
	{
		Note note=noterepository.findById(noteid)
				.orElseThrow(()-> new ResourceNotFoundException("Note","id",noteid)); 
		
		noterepository.delete(note);
		return ResponseEntity.ok().build();
	}
	
}
