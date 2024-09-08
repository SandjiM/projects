package ru.project.library.models;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class Person {
    private int personId;
    private String fullName;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateBirth;

    public Person() {}
    public Person(int personId, String fullName, LocalDate dateBirth) {
        this.personId = personId;
        this.fullName = fullName;
        this.dateBirth = dateBirth;
    }

    public int getPersonId() {return personId;}

    public void setPersonId(int id) {this.personId = id;}

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public LocalDate getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(LocalDate dateBirth) {
        this.dateBirth = dateBirth;
    }
}
