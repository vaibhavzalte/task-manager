package com.uv.taskManager.controller;

import com.uv.taskManager.entity.JournalEntity;
import com.uv.taskManager.entity.User;
import com.uv.taskManager.service.JournalEntryService;
import com.uv.taskManager.service.UserService;
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
    @Autowired
    private UserService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllJournalEntityOfUser(@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        List<JournalEntity> all = user.getJournalEntities();
        if(all!=null && !all.isEmpty())
            return  new ResponseEntity<>(all,HttpStatus.FOUND);
        return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("{userName}")
    public ResponseEntity<JournalEntity> addJournalEntry(@RequestBody JournalEntity entity,@PathVariable String userName) {
        try {
            journalEntryService.saveEntry(entity,userName);
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

    @DeleteMapping("id/{userName}/{myid}")
    public ResponseEntity<Boolean> deleteById(@PathVariable String userName,@PathVariable ObjectId myid) {
        journalEntryService.deleteEntityById(myid,userName);
        return new ResponseEntity<>(true, HttpStatus.OK);
    }

    @PutMapping("id/{userName}/{myid}")
    public ResponseEntity<JournalEntity> updateById(@PathVariable String userName,@PathVariable ObjectId myid, @RequestBody JournalEntity updatedEntity) {
        return journalEntryService.updateById(userName,myid, updatedEntity);
    }

}
