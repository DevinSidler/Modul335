package ch.zli.ds.securenotes.model;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class NoteModel {
    private int id;
    private AtomicInteger idCount = new AtomicInteger(0);
    private String name;
    private LocalDateTime dateTime;
    private String note;

    public NoteModel(String name,  String note) {
        id = idCount.getAndIncrement();
        this.name = name;
        this.dateTime = LocalDateTime.now();
        this.note = note;
    }
}
