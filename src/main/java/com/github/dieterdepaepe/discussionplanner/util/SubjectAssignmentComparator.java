package com.github.dieterdepaepe.discussionplanner.util;

import com.github.dieterdepaepe.discussionplanner.domain.SubjectAssignment;
import com.google.common.collect.ComparisonChain;

import java.util.Comparator;

/**
 *
 */
public class SubjectAssignmentComparator implements Comparator<SubjectAssignment> {
    @Override
    public int compare(SubjectAssignment o1, SubjectAssignment o2) {
        return ComparisonChain.start()
                .compare(o1.getRound(), o2.getRound())
                .compare(o1.getLocation().getName(), o2.getLocation().getName())
                .result();
    }
}
