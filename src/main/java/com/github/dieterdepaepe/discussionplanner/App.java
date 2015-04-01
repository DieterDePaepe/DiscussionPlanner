package com.github.dieterdepaepe.discussionplanner;

import com.github.dieterdepaepe.discussionplanner.domain.*;
import com.github.dieterdepaepe.discussionplanner.util.ParticipantAssignmentComparator;
import com.github.dieterdepaepe.discussionplanner.util.SubjectAssignmentComparator;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 *
 */
public class App {
    private static final String SUBJECT_START_MARKER = "BEGIN SUBJECTS";
    private static final String PARTICIPANT_START_MARKER = "BEGIN PARTICIPANTS";

    public static void main( String[] args ) {
        if (args.length != 2) {
            showUsageAndExit();
        }

        int locations = 0;
        try {
            locations = Integer.parseInt(args[0]);
            if (locations <= 0)
                throw new IllegalArgumentException("Value is <= 0");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid value: " + e.getMessage());
            System.exit(-1);
        }

        File inputFile = new File(args[1]);
        if (!inputFile.isFile()) {
            System.out.println("File not found: " + inputFile.getAbsolutePath());
            System.exit(-1);
        }

        try (FileReader reader = new FileReader(inputFile);
                BufferedReader buffReader = new BufferedReader(reader)) {
            List<Subject> subjects = parseSubjects(buffReader);
            List<Participant> participants = parseParticipants(buffReader, subjects);
            DiscussionSetup uninitializedSetup = AppHelper.createSetup(subjects, participants, locations);
            DiscussionSetup solvedSetup = DiscussionSolver.solve(uninitializedSetup);
            output(solvedSetup);
        } catch (IllegalStateException | IOException e) {
            System.out.println("Error while parsing: " + e.getMessage());
            System.exit(-1);
        }
    }

    private static void output(DiscussionSetup solvedSetup) {
        List<SubjectAssignment> subjectAssignments = new ArrayList<>(solvedSetup.getSubjectAssignments());
        subjectAssignments.sort(new SubjectAssignmentComparator());
        List<ParticipantAssignment> participantAssignments = new ArrayList<>(solvedSetup.getParticipantAssignments());
        participantAssignments.sort(new ParticipantAssignmentComparator());

        for (SubjectAssignment subjectAssignment : subjectAssignments) {
            System.out.printf("Round %d @ %s: %s\n", subjectAssignment.getRound(),
                    subjectAssignment.getLocation().getName(), subjectAssignment.getSubject().getSubject());
            for (ParticipantAssignment participantAssignment : participantAssignments) {
                if (participantAssignment.getLocation() != subjectAssignment.getLocation() ||
                        participantAssignment.getRound() != subjectAssignment.getRound())
                    continue;
                System.out.println(participantAssignment.getParticipant().getName());
            }
            System.out.println();
        }

        System.out.println("Solution score: " + solvedSetup.getScore());
    }

    private static List<Participant> parseParticipants(BufferedReader buffReader, List<Subject> subjects) throws IOException {
        List<Participant> participants = new ArrayList<>();

        String line = buffReader.readLine();
        if (line == null || !line.equals(PARTICIPANT_START_MARKER))
            throw new IllegalStateException("Unexpected line content. Expected \"" + PARTICIPANT_START_MARKER+ "\" but found \"" + line + "\".");

        line = buffReader.readLine();
        while (line != null) {
            String[] splitLine = line.split("\\s*;\\s*");
            if (splitLine.length != subjects.size() + 1)
                throw new IllegalStateException("Invalid line: \"" + line + "\" (preferences do not match number of subjects)");
            LinkedHashSet<Subject> preferredSubjects = new LinkedHashSet<>(subjects.size());
            for (int i = 1; i < splitLine.length; i++) {
                int subjectIndex;
                try {
                    subjectIndex = Integer.parseInt(splitLine[i].trim());
                    if (subjectIndex <= 0 || subjectIndex > subjects.size())
                        throw new IllegalArgumentException();
                    preferredSubjects.add(subjects.get(subjectIndex - 1));
                } catch (IllegalArgumentException e) {
                    throw new IllegalStateException("Invalid line: \"" + line + "\" (invalid preference: \"" + splitLine[i].trim() + "\")");
                }
            }
            if (preferredSubjects.size() != subjects.size())
                throw new IllegalStateException("Invalid line: \"" + line + "\" (duplicate preferences)");
            participants.add(new Participant(splitLine[0], new ArrayList<>(preferredSubjects)));
            line = buffReader.readLine();
        }
        return participants;
    }

    private static List<Subject> parseSubjects(BufferedReader buffReader) throws IOException {
        List<Subject> subjects = new ArrayList<>();

        String line = buffReader.readLine();
        if (line == null || !line.equals(SUBJECT_START_MARKER))
            throw new IllegalStateException("Unexpected line content. Expected \"" + SUBJECT_START_MARKER + "\" but found \"" + line + "\".");

        buffReader.mark(100);
        line = buffReader.readLine();
        while (line != null && !line.equals(PARTICIPANT_START_MARKER)) {
            subjects.add(new Subject(line.trim()));
            buffReader.mark(100);
            line = buffReader.readLine();
        }
        buffReader.reset();
        return subjects;
    }

    private static void showUsageAndExit() {
        System.out.println("Usage: java com.github.dieterdepaepe.discussionplanner.App <locations> <file>");
        System.out.println("Where <locations> is the number of parallel discussions that may happen");
        System.out.println("Where <file> is the path to a text file of the following structure:");
        System.out.println(SUBJECT_START_MARKER);
        System.out.println("Subject 1");
        System.out.println("...");
        System.out.println(PARTICIPANT_START_MARKER);
        System.out.println("Name 1 [; <preference for subject x>]+");
        System.out.println("...");
        System.exit(-1);
    }
}
