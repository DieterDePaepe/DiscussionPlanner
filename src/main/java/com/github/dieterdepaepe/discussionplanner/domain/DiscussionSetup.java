package com.github.dieterdepaepe.discussionplanner.domain;

import org.optaplanner.core.api.domain.solution.PlanningEntityCollectionProperty;
import org.optaplanner.core.api.domain.solution.PlanningSolution;
import org.optaplanner.core.api.domain.solution.Solution;
import org.optaplanner.core.api.domain.valuerange.ValueRangeProvider;
import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

import java.util.*;

/**
 *
 */
@PlanningSolution
public class DiscussionSetup implements Solution<HardMediumSoftScore> {
    private List<Participant> participants;
    private List<Subject> subjects;
    private List<Location> locations;

    private List<SubjectAssignment> subjectAssignments;
    private List<ParticipantAssignment> participantAssignments;

    private HardMediumSoftScore score;

    @PlanningEntityCollectionProperty
    public List<SubjectAssignment> getSubjectAssignments() {
        return subjectAssignments;
    }

    public void setSubjectAssignments(List<SubjectAssignment> subjectAssignments) {
        this.subjectAssignments = subjectAssignments;
    }

    @PlanningEntityCollectionProperty
    public List<ParticipantAssignment> getParticipantAssignments() {
        return participantAssignments;
    }

    public void setParticipantAssignments(List<ParticipantAssignment> participantAssignments) {
        this.participantAssignments = participantAssignments;
    }

    @ValueRangeProvider(id = "participants")
    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    @ValueRangeProvider(id = "subjects")
    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @ValueRangeProvider(id = "locations")
    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    @Override
    public HardMediumSoftScore getScore() {
        return score;
    }

    @Override
    public void setScore(HardMediumSoftScore score) {
        this.score = score;
    }

    @Override
    public Collection<?> getProblemFacts() {
        List<Object> facts = new ArrayList<>();
        facts.add(new GroupTargetSize(participantAssignments.size() / subjectAssignments.size()));
        facts.addAll(participants);
        facts.addAll(subjects);
        facts.addAll(locations);
        return facts;
    }
}
