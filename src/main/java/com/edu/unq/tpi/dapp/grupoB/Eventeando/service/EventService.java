package com.edu.unq.tpi.dapp.grupoB.Eventeando.service;

import com.edu.unq.tpi.dapp.grupoB.Eventeando.dominio.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventService {

    private final CrudRepository<Event, Long> eventDao;

    @Autowired
    public EventService(CrudRepository eventDao) {
        this.eventDao = eventDao;
    }

    public List<Event> allEvents() {
        List<Event> list = new ArrayList<>();
        eventDao.findAll().forEach(list::add);
        return list;
    }
}
