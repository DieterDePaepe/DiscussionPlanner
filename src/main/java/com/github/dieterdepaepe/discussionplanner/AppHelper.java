package com.github.dieterdepaepe.discussionplanner;

import com.github.dieterdepaepe.discussionplanner.domain.*;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class AppHelper {
    public static DiscussionSetup createSetup(List<Subject> subjects, List<Participant> participants, int locations) {
        int rounds = (int) Math.ceil(1. * subjects.size() / locations);
        List<Location> locationList = createLocations(locations);
        List<ParticipantAssignment> participantAssignments = createParticipantAssignments(participants, rounds);
        List<SubjectAssignment> subjectAssignments = createSubjectAssignments(locationList, rounds, subjects.size());

        DiscussionSetup setup = new DiscussionSetup();
        setup.setSubjects(subjects);
        setup.setParticipants(participants);
        setup.setLocations(locationList);
        setup.setParticipantAssignments(participantAssignments);
        setup.setSubjectAssignments(subjectAssignments);

        return setup;
    }

    private static List<SubjectAssignment> createSubjectAssignments(List<Location> locationList, int rounds, int numberRequired) {
        List<SubjectAssignment> result = new ArrayList<>(numberRequired);
        for (int round = 0; round < rounds; round++) {
            for (Location location : locationList) {
                if (result.size() >= numberRequired)
                    return result;
                SubjectAssignment assignment = new SubjectAssignment();
                assignment.setId(result.size());
                assignment.setRound(round);
                assignment.setLocation(location);
                result.add(assignment);
            }
        }
        return result;
    }

    private static List<ParticipantAssignment> createParticipantAssignments(List<Participant> participants, int rounds) {
        List<ParticipantAssignment> result = new ArrayList<>(participants.size() * rounds);
        for (int round = 0; round < rounds; round++) {
            for (Participant participant : participants) {
                ParticipantAssignment assigment = new ParticipantAssignment();
                assigment.setId(result.size());
                assigment.setParticipant(participant);
                assigment.setRound(round);
                result.add(assigment);
            }
        }
        return result;
    }

    private static List<Location> createLocations(int number) {
        List<Location> locations = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            locations.add(new Location(i, "Location " + (i + 1)));
        }
        return locations;
    }
}
