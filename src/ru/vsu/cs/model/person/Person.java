package ru.vsu.cs.model.person;

import java.util.List;

public abstract class Person {
    private String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
