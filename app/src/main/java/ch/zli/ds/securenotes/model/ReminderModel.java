package ch.zli.ds.securenotes.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class ReminderModel implements Serializable {
    private int id;
    private AtomicInteger idCount = new AtomicInteger();
    private String name;
    private Date dateTime;

    public ReminderModel(String name, Date date ){
        id = idCount.getAndIncrement();
        this.name = name;
        this.dateTime = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AtomicInteger getIdCount() {
        return idCount;
    }

    public void setIdCount(AtomicInteger idCount) {
        this.idCount = idCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }
}
