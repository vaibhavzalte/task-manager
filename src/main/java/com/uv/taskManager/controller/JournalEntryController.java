package com.uv.taskManager.controller;

import com.uv.taskManager.entity.JournalEntity;
import com.uv.taskManager.entity.User;
import com.uv.taskManager.service.JournalEntryService;
import com.uv.taskManager.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    @Autowired
    private JournalEntryService journalEntryService;
    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<?> getAllJournalEntityOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByUserName(authentication.getName());
        List<JournalEntity> all = user.getJournalEntities();
        if (all != null && !all.isEmpty())
            return new ResponseEntity<>(all, HttpStatus.FOUND);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<JournalEntity> addJournalEntry(@RequestBody JournalEntity entity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        try {
            journalEntryService.saveEntry(entity, authentication.getName());
            return new ResponseEntity<>(entity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myid}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId myid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntity> journalEntityList = user.getJournalEntities().stream().filter(x -> x.getId().equals(myid)).toList();
        if (!journalEntityList.isEmpty()) {
            Optional<JournalEntity> journalEntity = journalEntryService.getEntityById(myid);
            if (journalEntity.isPresent()) {
                return new ResponseEntity<>(journalEntity, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myid}")
    public ResponseEntity<Boolean> deleteById(@PathVariable ObjectId myid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean b = journalEntryService.deleteById(myid, authentication.getName());
        return new ResponseEntity<>(b, HttpStatus.OK);
    }

    @PutMapping("id/{myid}")
    public ResponseEntity<JournalEntity> updateById(@PathVariable ObjectId myid, @RequestBody JournalEntity updatedEntity) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return journalEntryService.updateById(authentication.getName(), myid, updatedEntity);
    }

}
