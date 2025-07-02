package com.uv.taskManager.service;

import com.uv.taskManager.entity.JournalEntity;
import com.uv.taskManager.entity.User;
import com.uv.taskManager.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    JournalEntryRepository journalEntryRepository;

    @Autowired
    UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryService.class);

    @Transactional
    public void saveEntry(JournalEntity journalEntity, String userName) {
        try {
            User user = userService.findByUserName(userName);
            journalEntity.setDate(LocalDateTime.now());
            JournalEntity saved = journalEntryRepository.save(journalEntity);
            user.getJournalEntities().add(saved);
            userService.saveUser(user);
        } catch (Exception e) {
            logger.info("failed to save entry" + e.getMessage());
            throw new RuntimeException("failed to save entry");
        }

    }

    public List<JournalEntity> getAllEntities() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntity> getEntityById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        boolean isRemoved = false;
        try {
            User user = userService.findByUserName(userName);
            boolean removed = user.getJournalEntities().removeIf(x -> x.getId().equals(id));
            if (removed) {
                userService.saveUser(user);
                journalEntryRepository.deleteById(id);
                isRemoved = true;
            }
            return isRemoved;
        } catch (Exception e) {
            throw new RuntimeException("An error occur during deleting entity." + e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<JournalEntity> updateById(String userName, ObjectId id, JournalEntity updatedEntity) {
        User user = userService.findByUserName(userName);
        List<JournalEntity> list = user.getJournalEntities().stream().filter(x -> x.getId().equals(id)).toList();
        if (!list.isEmpty()) {
            JournalEntity oldEntity = journalEntryRepository.findById(id).orElse(null);
            if (oldEntity != null) {
                oldEntity.setTitle(updatedEntity.getTitle() != null && !updatedEntity.getTitle().isEmpty() ? updatedEntity.getTitle() : oldEntity.getTitle());
                oldEntity.setContent(updatedEntity.getContent() != null && !updatedEntity.getContent().isEmpty() ? updatedEntity.getContent() : oldEntity.getContent());
                journalEntryRepository.save(oldEntity);
                return new ResponseEntity<>(oldEntity, HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
