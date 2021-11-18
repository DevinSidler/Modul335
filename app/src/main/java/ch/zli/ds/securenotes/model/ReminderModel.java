package ch.zli.ds.securenotes.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class ReminderModel {
    private int id;
    private AtomicInteger idCount = new AtomicInteger();
    private String name;
    private Date dateTime;

    public ReminderModel(String name, Date date ){
        id = idCount.getAndIncrement();
        this.name = name;
        this.dateTime = date;
    }
}
