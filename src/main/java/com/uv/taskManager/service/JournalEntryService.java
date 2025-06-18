package com.uv.taskManager.service;

import com.uv.taskManager.entity.JournalEntity;
import com.uv.taskManager.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {
    @Autowired
    JournalEntryRepository journalEntryRepository;

    public void saveEntry(JournalEntity journalEntity) {
        journalEntity.setDate(LocalDateTime.now());
        journalEntryRepository.save(journalEntity);
    }

    public List<JournalEntity> getAllEntities() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntity> getEntityById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteEntityById(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }

    public ResponseEntity<JournalEntity> updateById(ObjectId id, JournalEntity updatedEntity) {
        JournalEntity oldEntity = journalEntryRepository.findById(id).orElse(null);
        if (oldEntity != null) {
            oldEntity.setTitle(updatedEntity.getTitle() != null && !updatedEntity.getTitle().isEmpty() ? updatedEntity.getTitle() : oldEntity.getTitle());
            oldEntity.setContent(updatedEntity.getContent() != null && !updatedEntity.getContent().isEmpty() ? updatedEntity.getContent() : oldEntity.getContent());
        journalEntryRepository.save(oldEntity);
        return new ResponseEntity<>(oldEntity, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
