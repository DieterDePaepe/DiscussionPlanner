package com.github.dieterdepaepe.discussionplanner.domain;

import java.util.List;

/**
 *
 */
public class Participant {
    private String name;
    private List<Subject> subjectPreference;

    public Participant(String name, List<Subject> subjectPreference) {
        this.name = name;
        this.subjectPreference = subjectPreference;
    }

    public String getName() {
        return name;
    }

    public List<Subject> getSubjectPreference() {
        return subjectPreference;
    }

    public int dissatisfactionOf(Subject subject) {
        int i = subjectPreference.indexOf(subject);
        return - (i*i);
    }
}
