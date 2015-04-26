package com.github.dieterdepaepe.discussionplanner.domain;

/**
 * Problem fact which tracks the target size of all groups.
 *
 * @author Dieter De Paepe
 */
public class GroupTargetSize {
    private final int number;

    public GroupTargetSize(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
