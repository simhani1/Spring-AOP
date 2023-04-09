package com.example.aop.service;

import com.example.aop.annotation.PerLogging;
import org.springframework.stereotype.Component;

@Component
public class EventServiceImpl implements EventService {

    @PerLogging
    @Override
    public void createEvent() {
        try {
            System.out.println("EventServiceImpl.createEvent");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void publishEvent(int A) {
        try {
            System.out.println("EventServiceImpl.publishEvent");
            System.out.println(A);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PerLogging
    @Override
    public void deleteEvent() {
        System.out.println("EventServiceImpl.deleteEvent");
    }

}
