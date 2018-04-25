package model;

import java.time.LocalDate;

public class Entry {

    private String name;
    private String message;
    private LocalDate date;

    public Entry(String name, String message) {
        this.name = name;
        this.message = message;
        this.date = LocalDate.now();
    }

    public Entry(String name, String message, String date) {
        this.date = LocalDate.parse(date);
        this.name = name;
        this.message = message;

    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public LocalDate getDate() {
        return date;
    }
}
