package com.uv.taskManager.controller;

import com.uv.taskManager.entity.JournalEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
    private Map<Long ,JournalEntity> map=new HashMap<>();
    @GetMapping
    public List<JournalEntity> getAllJournalEntity(){
        return new ArrayList<>(map.values());
    }

    @PostMapping
    public boolean addJournalEntry(@RequestBody JournalEntity entity){
        map.put(entity.getId(),entity);
        return true;
    }

    @GetMapping("id/{myid}")
    public JournalEntity getEntryById(@PathVariable Long myid)
    {
        return map.get(myid);
    }
    @DeleteMapping("id/{myid}")
    public JournalEntity deleteById(@PathVariable Long myid)
    {
        return map.remove(myid);
    }

    @PutMapping("id/{myid}")
    public JournalEntity updateById(@PathVariable Long myid,@RequestBody JournalEntity updatedEntity)
    {
        return map.put(myid,updatedEntity);
    }

}
