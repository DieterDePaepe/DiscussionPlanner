package com.github.dieterdepaepe.discussionplanner.domain;

import org.optaplanner.core.api.domain.entity.PlanningEntity;

/**
 *
 */
@PlanningEntity
public class Subject {
    private String subject;

    public Subject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return subject;
    }
}
