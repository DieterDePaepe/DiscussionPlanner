package com.github.dieterdepaepe.discussionplanner.domain;

import java.util.Map;

/**
 *
 */
public class Participant {
    private String name;
    private Map<Subject, Integer> subjectPreference;

    /**
     * Creates a new participant
     * @param name the name of the participant
     * @param subjectPreference a map containing the preferences of each subject (higher number = more preference)
     */
    public Participant(String name, Map<Subject, Integer> subjectPreference) {
        this.name = name;
        this.subjectPreference = subjectPreference;
    }

    public String getName() {
        return name;
    }

    public Map<Subject, Integer> getSubjectPreference() {
        return subjectPreference;
    }

    public int satisfactionOf(Subject subject) {
        return subjectPreference.get(subject);
    }

    @Override
    public String toString() {
        return name;
    }
}
