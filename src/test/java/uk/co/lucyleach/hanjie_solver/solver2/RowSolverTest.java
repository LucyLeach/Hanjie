package uk.co.lucyleach.hanjie_solver.solver2;

import org.junit.Test;
import uk.co.lucyleach.hanjie_solver.SquareState;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static uk.co.lucyleach.hanjie_solver.SquareState.*;

/**
 * User: Lucy
 * Date: 16/09/2015
 * Time: 20:30
 */
public abstract class RowSolverTest extends InitialRowSolverTest
{
  private final RowSolver underTest;

  public RowSolverTest(RowSolver underTest)
  {
    super(underTest);
    this.underTest = underTest;
  }

  @Test
  public void fixedStartSingleBlock() {
    SolutionStepResult result = underTest.solveRow(newArrayList(3), new SquareState[]{FULL, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN});
    assertArrayEquals(new SquareState[]{FULL, FULL, FULL, BLANK, BLANK, BLANK}, result.getStepResult());
    assertEquals(newArrayList(1, 2, 3, 4, 5), result.getNewlyKnownSquares());
  }

  @Test
  public void partiallyKnownSingleBlock() {
    SolutionStepResult result = underTest.solveRow(newArrayList(3), new SquareState[]{UNKNOWN, FULL, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN});
    assertArrayEquals(new SquareState[]{UNKNOWN, FULL, FULL, UNKNOWN, BLANK, BLANK}, result.getStepResult());
    assertEquals(newArrayList(2, 4, 5), result.getNewlyKnownSquares());
  }

  @Test
  public void singleBlockWithBumperAtStart() {
    SolutionStepResult result = underTest.solveRow(newArrayList(3), new SquareState[]{BLANK, BLANK, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN});
    assertArrayEquals(new SquareState[]{BLANK, BLANK, UNKNOWN, FULL, FULL, UNKNOWN}, result.getStepResult());
    assertEquals(newArrayList(3, 4), result.getNewlyKnownSquares());
  }

  @Test
  public void singleBlockWithBumperAtEnd() {
    SolutionStepResult result = underTest.solveRow(newArrayList(3), new SquareState[]{UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, BLANK, BLANK});
    assertArrayEquals(new SquareState[]{UNKNOWN, FULL, FULL, UNKNOWN, BLANK, BLANK}, result.getStepResult());
    assertEquals(newArrayList(1, 2), result.getNewlyKnownSquares());
  }

  @Test
  public void singleBlockFixedAtEnd() {
    SolutionStepResult result = underTest.solveRow(newArrayList(3), new SquareState[]{UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, FULL});
    assertArrayEquals(new SquareState[]{BLANK, BLANK, BLANK, FULL, FULL, FULL}, result.getStepResult());
    assertEquals(newArrayList(0, 1, 2, 3, 4), result.getNewlyKnownSquares());
  }

  @Test
  public void partiallyKnownAtEndSingleBlock() {
    SolutionStepResult result = underTest.solveRow(newArrayList(3), new SquareState[]{UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, FULL, UNKNOWN});
    assertArrayEquals(new SquareState[]{BLANK, BLANK, UNKNOWN, FULL, FULL, UNKNOWN}, result.getStepResult());
    assertEquals(newArrayList(0, 1, 3), result.getNewlyKnownSquares());
  }

  @Test
  public void singleBlockCantFitInHoleAtStart() {
    SolutionStepResult result = underTest.solveRow(newArrayList(3), new SquareState[]{UNKNOWN, BLANK, UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN});
    assertArrayEquals(new SquareState[]{BLANK, BLANK, UNKNOWN, FULL, FULL, UNKNOWN}, result.getStepResult());
    assertEquals(newArrayList(0, 3, 4), result.getNewlyKnownSquares());
  }

  @Test
  public void singleBlockCantFitHoleAtEnd() {
    SolutionStepResult result = underTest.solveRow(newArrayList(3), new SquareState[]{UNKNOWN, UNKNOWN, UNKNOWN, UNKNOWN, BLANK, UNKNOWN});
    assertArrayEquals(new SquareState[]{UNKNOWN, FULL, FULL, UNKNOWN, BLANK, BLANK}, result.getStepResult());
    assertEquals(newArrayList(1, 2, 5), result.getNewlyKnownSquares());
  }

  @Test
  public void singleBlockPeggedInMiddle() {
    SolutionStepResult result = underTest.solveRow(newArrayList(3), new SquareState[]{UNKNOWN, UNKNOWN, UNKNOWN, FULL, UNKNOWN, UNKNOWN, UNKNOWN});
    assertArrayEquals(new SquareState[]{BLANK, UNKNOWN, UNKNOWN, FULL, UNKNOWN, UNKNOWN, BLANK}, result.getStepResult());
    assertEquals(newArrayList(0, 6), result.getNewlyKnownSquares());
  }

  @Test
  public void fillInMiddleSquareOfSingleBlock() {
    SolutionStepResult result = underTest.solveRow(newArrayList(4), new SquareState[]{UNKNOWN, UNKNOWN, FULL, UNKNOWN, FULL, UNKNOWN, UNKNOWN});
    assertArrayEquals(new SquareState[]{BLANK, UNKNOWN, FULL, FULL, FULL, UNKNOWN, BLANK}, result.getStepResult());
    assertEquals(newArrayList(0, 3, 6), result.getNewlyKnownSquares());
  }
}