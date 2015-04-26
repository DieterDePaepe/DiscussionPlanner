package com.github.dieterdepaepe.discussionplanner.util;

import com.github.dieterdepaepe.discussionplanner.domain.SubjectAssignment;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionFilter;
import org.optaplanner.core.impl.heuristic.selector.move.generic.SwapMove;
import org.optaplanner.core.impl.score.director.ScoreDirector;

/**
 * Filter which prevents swapping of {@link com.github.dieterdepaepe.discussionplanner.domain.Subject}s
 * between {@link com.github.dieterdepaepe.discussionplanner.domain.SubjectAssignment}s that take place
 * in the same round.
 *
 * @author Dieter De Paepe
 */
public class DifferentRoundsSwapMoveFilter implements SelectionFilter<SwapMove> {
    @Override
    public boolean accept(ScoreDirector scoreDirector, SwapMove selection) {
        SubjectAssignment left = (SubjectAssignment) selection.getLeftEntity();
        SubjectAssignment right = (SubjectAssignment) selection.getRightEntity();
        return left.getRound() != right.getRound();
    }
}
