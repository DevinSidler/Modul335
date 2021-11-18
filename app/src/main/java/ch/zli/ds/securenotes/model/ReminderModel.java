package ch.zli.ds.securenotes.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class ReminderModel {
    private int id;
    private AtomicInteger idCount = new AtomicInteger(0);
    private String name;
    private LocalDateTime dateTime;
    private DateTimeFormatter formatter;

    public ReminderModel(String name, LocalDate date, LocalTime time) {
        id = idCount.getAndIncrement();
        this.name = name;
        this.dateTime = LocalDateTime.of(date,time);
    }
}
