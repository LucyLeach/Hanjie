package uk.co.lucyleach.hanjie_solver.solver2;

import org.junit.Test;
import uk.co.lucyleach.hanjie_solver.SquareState;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.*;
import static uk.co.lucyleach.hanjie_solver.SquareState.BLANK;
import static uk.co.lucyleach.hanjie_solver.SquareState.FULL;
import static uk.co.lucyleach.hanjie_solver.SquareState.UNKNOWN;

public class InitialRowSolverTest
{
  private final InitialRowSolver underTest = new InitialRowSolver();

  @Test
  public void noBlocks() {
    SolutionStepResult result = underTest.solve(newArrayList(), 5);
    assertEquals(newArrayList(0, 1, 2, 3, 4), result.getNewlyKnownSquares());
    assertArrayEquals(SquareState.arrayOf(5, SquareState.BLANK), result.getStepResult());
  }

  @Test
  public void singleBlockKnownCompletely() {
    SolutionStepResult result = underTest.solve(newArrayList(5), 5);
    assertEquals(newArrayList(0, 1, 2, 3, 4), result.getNewlyKnownSquares());
    assertArrayEquals(SquareState.arrayOf(5, SquareState.FULL), result.getStepResult());
  }

  @Test
  public void multiBlockKnownCompletely() {
    SolutionStepResult result = underTest.solve(newArrayList(3,1), 5);
    SquareState[] expected = new SquareState[]{FULL, FULL, FULL, BLANK, FULL};
    assertEquals(newArrayList(0, 1, 2, 3, 4), result.getNewlyKnownSquares());
    assertArrayEquals(expected, result.getStepResult());
  }

  @Test
  public void unknowableSingleBlock() {
    SolutionStepResult result = underTest.solve(newArrayList(1), 5);
    assertTrue(result.getNewlyKnownSquares().isEmpty());
    assertArrayEquals(SquareState.arrayOf(5, UNKNOWN), result.getStepResult());
  }
  
  @Test
  public void unknowableMultiBlock() {
    SolutionStepResult result = underTest.solve(newArrayList(1,1), 5);
    assertTrue(result.getNewlyKnownSquares().isEmpty());
    assertArrayEquals(SquareState.arrayOf(5, UNKNOWN), result.getStepResult());
  }
  
  @Test
  public void partiallyKnowableSingleBlock() {
    SolutionStepResult result = underTest.solve(newArrayList(4), 6);
    assertEquals(newArrayList(2, 3), result.getNewlyKnownSquares());
    SquareState[] expected = new SquareState[]{UNKNOWN, UNKNOWN, FULL, FULL, UNKNOWN, UNKNOWN};
    assertArrayEquals(expected, result.getStepResult());
  }
  
  @Test
  public void allBlocksPartiallyKnowable() {
    SolutionStepResult result = underTest.solve(newArrayList(3, 2), 7);
    SquareState[] expected = new SquareState[]{UNKNOWN, FULL, FULL, UNKNOWN, UNKNOWN, FULL, UNKNOWN};
    assertEquals(newArrayList(1, 2, 5), result.getNewlyKnownSquares());
    assertArrayEquals(expected, result.getStepResult());
  }
  
  @Test
  public void someBlocksPartiallyKnowable() {
    SolutionStepResult result = underTest.solve(newArrayList(3, 1), 7);
    SquareState[] expected = new SquareState[]{UNKNOWN, UNKNOWN, FULL, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN};
    assertEquals(newArrayList(2), result.getNewlyKnownSquares());
    assertArrayEquals(expected, result.getStepResult());
  }

  @Test(expected = IllegalArgumentException.class)
  public void blockTooLong() {
    underTest.solve(newArrayList(4), 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void multiBlockTooLong() {
    underTest.solve(newArrayList(3, 1), 4);
  }
}