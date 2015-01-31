package uk.co.lucyleach.hanjie_solver.solver;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;
import org.junit.Test;
import uk.co.lucyleach.hanjie_solver.Clues;
import uk.co.lucyleach.hanjie_solver.CluesImpl;
import uk.co.lucyleach.hanjie_solver.Puzzle;
import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.*;

//Not strictly a unit test since I'm not mocking out any of the things it's composed of.  But tbh, more useful this way.
public class HanjieSolverTest
{
  private static final HanjieSolver UNDER_TEST = new HanjieSolver();

  @Test
  public void testSolvablePuzzle() throws UnsolvableException
  {
    Map<Integer, List<Integer>> rowClues = ImmutableMap.<Integer, List<Integer>>builder()
        .put(1, newArrayList(2, 2))
        .put(2, newArrayList(1, 1))
        .put(3, newArrayList(2, 2))
        .put(4, newArrayList(1, 1))
        .put(5, newArrayList(1, 1, 1))
        .put(6, newArrayList(1, 2, 1))
        .put(7, newArrayList(2, 2))
        .put(8, newArrayList(2, 1, 1, 1, 1))
        .put(9, newArrayList(2, 1, 1, 2))
        .put(10, newArrayList(3, 3))
        .build();

    Map<Integer, List<Integer>> columnClues = ImmutableMap.<Integer, List<Integer>>builder()
        .put(1, newArrayList(2, 6))
        .put(2, newArrayList(1, 4))
        .put(3, newArrayList(1, 1))
        .put(4, newArrayList(2, 1))
        .put(5, newArrayList(1, 1))
        .put(6, newArrayList(2, 1))
        .put(7, newArrayList(1, 1))
        .put(8, newArrayList(2, 1, 1))
        .put(9, newArrayList(1, 1, 2))
        .put(10, newArrayList(2, 6))
        .build();

    Clues clues = new CluesImpl(rowClues, columnClues);

    Puzzle puzzle = UNDER_TEST.solve(clues);

    assertNotNull(puzzle);

    Table<Integer, Integer, SquareState> expectedSolution = getExpectedSolution();

    assertEquals(expectedSolution, puzzle.getStates());
  }

  private ImmutableTable<Integer, Integer, SquareState> getExpectedSolution()
  {
    return ImmutableTable.<Integer, Integer, SquareState>builder()
        .put(1, 1, SquareState.FULL)
        .put(1, 2, SquareState.FULL)
        .put(1, 3, SquareState.BLANK)
        .put(1, 4, SquareState.BLANK)
        .put(1, 5, SquareState.BLANK)
        .put(1, 6, SquareState.BLANK)
        .put(1, 7, SquareState.BLANK)
        .put(1, 8, SquareState.BLANK)
        .put(1, 9, SquareState.FULL)
        .put(1, 10, SquareState.FULL)
        .put(2, 1, SquareState.FULL)
        .put(2, 2, SquareState.BLANK)
        .put(2, 3, SquareState.BLANK)
        .put(2, 4, SquareState.BLANK)
        .put(2, 5, SquareState.BLANK)
        .put(2, 6, SquareState.BLANK)
        .put(2, 7, SquareState.BLANK)
        .put(2, 8, SquareState.BLANK)
        .put(2, 9, SquareState.BLANK)
        .put(2, 10, SquareState.FULL)
        .put(3, 1, SquareState.BLANK)
        .put(3, 2, SquareState.BLANK)
        .put(3, 3, SquareState.FULL)
        .put(3, 4, SquareState.FULL)
        .put(3, 5, SquareState.BLANK)
        .put(3, 6, SquareState.BLANK)
        .put(3, 7, SquareState.FULL)
        .put(3, 8, SquareState.FULL)
        .put(3, 9, SquareState.BLANK)
        .put(3, 10, SquareState.BLANK)
        .put(4, 1, SquareState.BLANK)
        .put(4, 2, SquareState.BLANK)
        .put(4, 3, SquareState.BLANK)
        .put(4, 4, SquareState.FULL)
        .put(4, 5, SquareState.BLANK)
        .put(4, 6, SquareState.BLANK)
        .put(4, 7, SquareState.BLANK)
        .put(4, 8, SquareState.FULL)
        .put(4, 9, SquareState.BLANK)
        .put(4, 10, SquareState.BLANK)
        .put(5, 1, SquareState.FULL)
        .put(5, 2, SquareState.BLANK)
        .put(5, 3, SquareState.BLANK)
        .put(5, 4, SquareState.BLANK)
        .put(5, 5, SquareState.BLANK)
        .put(5, 6, SquareState.FULL)
        .put(5, 7, SquareState.BLANK)
        .put(5, 8, SquareState.BLANK)
        .put(5, 9, SquareState.BLANK)
        .put(5, 10, SquareState.FULL)
        .put(6, 1, SquareState.FULL)
        .put(6, 2, SquareState.BLANK)
        .put(6, 3, SquareState.BLANK)
        .put(6, 4, SquareState.BLANK)
        .put(6, 5, SquareState.FULL)
        .put(6, 6, SquareState.FULL)
        .put(6, 7, SquareState.BLANK)
        .put(6, 8, SquareState.BLANK)
        .put(6, 9, SquareState.BLANK)
        .put(6, 10, SquareState.FULL)
        .put(7, 1, SquareState.FULL)
        .put(7, 2, SquareState.FULL)
        .put(7, 3, SquareState.BLANK)
        .put(7, 4, SquareState.BLANK)
        .put(7, 5, SquareState.BLANK)
        .put(7, 6, SquareState.BLANK)
        .put(7, 7, SquareState.BLANK)
        .put(7, 8, SquareState.BLANK)
        .put(7, 9, SquareState.FULL)
        .put(7, 10, SquareState.FULL)
        .put(8, 1, SquareState.FULL)
        .put(8, 2, SquareState.FULL)
        .put(8, 3, SquareState.BLANK)
        .put(8, 4, SquareState.FULL)
        .put(8, 5, SquareState.BLANK)
        .put(8, 6, SquareState.FULL)
        .put(8, 7, SquareState.BLANK)
        .put(8, 8, SquareState.FULL)
        .put(8, 9, SquareState.BLANK)
        .put(8, 10, SquareState.FULL)
        .put(9, 1, SquareState.FULL)
        .put(9, 2, SquareState.FULL)
        .put(9, 3, SquareState.BLANK)
        .put(9, 4, SquareState.BLANK)
        .put(9, 5, SquareState.FULL)
        .put(9, 6, SquareState.BLANK)
        .put(9, 7, SquareState.FULL)
        .put(9, 8, SquareState.BLANK)
        .put(9, 9, SquareState.FULL)
        .put(9, 10, SquareState.FULL)
        .put(10, 1, SquareState.FULL)
        .put(10, 2, SquareState.FULL)
        .put(10, 3, SquareState.FULL)
        .put(10, 4, SquareState.BLANK)
        .put(10, 5, SquareState.BLANK)
        .put(10, 6, SquareState.BLANK)
        .put(10, 7, SquareState.BLANK)
        .put(10, 8, SquareState.FULL)
        .put(10, 9, SquareState.FULL)
        .put(10, 10, SquareState.FULL)
        .build();
  }

  @Test
  public void testCluesThatCantBeSolved()
  {
    Map<Integer, List<Integer>> rowClues = ImmutableMap.<Integer, List<Integer>>builder()
        .put(1, newArrayList(3))
        .put(2, newArrayList(2))
        .put(3, newArrayList(2)) //this clue...
        .build();

    Map<Integer, List<Integer>> columnClues = ImmutableMap.<Integer, List<Integer>>builder()
        .put(1, newArrayList(1, 1))
        .put(2, newArrayList(2)) //...clashes with this clue
        .put(3, newArrayList(1))
        .build();

    Clues clues = new CluesImpl(rowClues, columnClues);
    try
    {
      UNDER_TEST.solve(clues);
      fail("Should have thrown exception");
    } catch (UnsolvableException e)
    {
      assertTrue(e.getRow().isPresent());
      assertEquals(3, e.getRow().get().intValue());
      assertTrue(e.getColumn().isPresent());
      assertEquals(2, e.getColumn().get().intValue());
    }
  }
}