package ch.zli.ds.securenotes.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class NoteModel implements Serializable {
    private String name;
    private String note;

    public NoteModel(String name,  String note) {
        this.name = name;
        this.note = note;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
