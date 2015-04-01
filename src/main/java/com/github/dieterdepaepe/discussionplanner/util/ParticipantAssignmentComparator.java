package com.github.dieterdepaepe.discussionplanner.util;

import com.github.dieterdepaepe.discussionplanner.domain.ParticipantAssignment;
import com.google.common.collect.ComparisonChain;

import java.util.Comparator;

/**
 *
 */
public class ParticipantAssignmentComparator implements Comparator<ParticipantAssignment> {
    @Override
    public int compare(ParticipantAssignment o1, ParticipantAssignment o2) {
        return ComparisonChain.start()
                .compare(o1.getParticipant().getName(), o2.getParticipant().getName())
                .result();
    }
}
