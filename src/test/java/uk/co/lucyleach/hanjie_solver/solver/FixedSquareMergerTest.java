package uk.co.lucyleach.hanjie_solver.solver;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class FixedSquareMergerTest
{
  private static FixedSquareMerger UNDER_TEST = new FixedSquareMerger();

  @Test
  public void testAlreadyHaveThatSquareFixedTheSame() throws UnsolvableException
  {
    PossibleSolutions solutions = new PossibleSolutionsImpl(ImmutableSet.of(ImmutableMap.of(1, SquareState.FULL)));
    OperationResult result = UNDER_TEST.merge(1, SquareState.FULL, solutions);
    assertFalse("Result should show no change", result.hasChanged());
    assertEquals("Result should be same object", solutions, result.getSolutions());
  }

  @Test(expected = UnsolvableException.class)
  public void testAlreadyHaveThatSquareFixedDifferently() throws UnsolvableException
  {
    PossibleSolutions solutions = new PossibleSolutionsImpl(ImmutableSet.of(ImmutableMap.of(1, SquareState.FULL)));
    UNDER_TEST.merge(1, SquareState.BLANK, solutions);
  }

  @Test
  public void testFindUniqueSolution() throws UnsolvableException
  {
    PossibleSolutions solutions = new PossibleSolutionsImpl(ImmutableSet.of(
        ImmutableMap.of(1, SquareState.FULL, 2, SquareState.BLANK),
        ImmutableMap.of(1, SquareState.BLANK, 2, SquareState.FULL)
    ));
    OperationResult result = UNDER_TEST.merge(1, SquareState.FULL, solutions);

    assertTrue("Result should show change", result.hasChanged());
    PossibleSolutions mergedSolutions = result.getSolutions();
    assertTrue("Result should be fully solved", mergedSolutions.allSquaresFixed());
    assertEquals(ImmutableMap.of(1, SquareState.FULL, 2, SquareState.BLANK), mergedSolutions.getFixedSquares());
  }

  @Test
  public void testDontFindUniqueSolution() throws UnsolvableException
  {
    PossibleSolutions solutions = new PossibleSolutionsImpl(ImmutableSet.of(
        ImmutableMap.of(1, SquareState.FULL, 2, SquareState.BLANK, 3, SquareState.BLANK),
        ImmutableMap.of(1, SquareState.BLANK, 2, SquareState.FULL, 3, SquareState.BLANK),
        ImmutableMap.of(1, SquareState.BLANK, 2, SquareState.BLANK, 3, SquareState.FULL)
    ));
    OperationResult result = UNDER_TEST.merge(3, SquareState.BLANK, solutions);

    assertTrue("Result should show change", result.hasChanged());
    PossibleSolutions mergedSolutions = result.getSolutions();
    assertFalse("Result should not be fully solved", mergedSolutions.allSquaresFixed());

    Set<Map<Integer, SquareState>> expectedSolutions = ImmutableSet.of(
        ImmutableMap.of(1, SquareState.FULL, 2, SquareState.BLANK, 3, SquareState.BLANK),
        ImmutableMap.of(1, SquareState.BLANK, 2, SquareState.FULL, 3, SquareState.BLANK)
    );
    assertEquals(expectedSolutions, mergedSolutions.getAllSolutions());
    assertTrue("Square three should be fixed", mergedSolutions.getFixedSquares().containsKey(3));
    assertEquals("Square three should be fixed to blank", SquareState.BLANK, mergedSolutions.getFixedSquares().get(3));
  }
}