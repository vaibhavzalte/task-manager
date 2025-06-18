package com.uv.taskManager.controller;

import com.uv.taskManager.entity.JournalEntity;
import com.uv.taskManager.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntity> getAllJournalEntity() {
        return journalEntryService.getAllEntities();
    }

    @PostMapping
    public ResponseEntity<JournalEntity> addJournalEntry(@RequestBody JournalEntity entity) {
        try {
            journalEntryService.saveEntry(entity);
            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myid}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myid) {
        Optional<JournalEntity> journalEntity = journalEntryService.getEntityById(myid);
        if (journalEntity.isPresent()) {
            return new ResponseEntity<>(journalEntity, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myid}")
    public ResponseEntity<Boolean> deleteById(@PathVariable ObjectId myid) {
        journalEntryService.deleteEntityById(myid);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("id/{myid}")
    public ResponseEntity<JournalEntity> updateById(@PathVariable ObjectId myid, @RequestBody JournalEntity updatedEntity) {
        return journalEntryService.updateById(myid, updatedEntity);
    }

}
