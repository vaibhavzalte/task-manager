package com.uv.taskManager.repository;

import com.uv.taskManager.entity.JournalEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepository extends MongoRepository<JournalEntity,Long> {
}
