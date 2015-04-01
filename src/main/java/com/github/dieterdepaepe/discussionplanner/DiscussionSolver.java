package com.github.dieterdepaepe.discussionplanner;

import com.github.dieterdepaepe.discussionplanner.domain.DiscussionSetup;
import org.optaplanner.core.api.solver.Solver;
import org.optaplanner.core.api.solver.SolverFactory;

/**
 *
 */
public class DiscussionSolver {
    public static DiscussionSetup solve(DiscussionSetup uninitializedSetup) {
        SolverFactory solverFactory = SolverFactory.createFromXmlResource(
                "com/github/dieterdepaepe/discussionplanner/solver/discussionSolverConfig.xml");
        Solver solver = solverFactory.buildSolver();

        solver.solve(uninitializedSetup);
        return (DiscussionSetup) solver.getBestSolution();
    }
}
