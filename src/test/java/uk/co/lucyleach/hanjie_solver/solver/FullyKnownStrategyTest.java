package uk.co.lucyleach.hanjie_solver.solver;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import uk.co.lucyleach.hanjie_solver.CluesImpl;
import uk.co.lucyleach.hanjie_solver.Puzzle;
import uk.co.lucyleach.hanjie_solver.PuzzleImpl;
import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

//TODO write tests
public class FullyKnownStrategyTest
{
  private static final Strategy UNDER_TEST = new FullyKnownStrategy();

  @Test
  public void testSingleClueFullSingleSquare() throws UnsolvableException
  {
    ImmutableMap<Integer, List<Integer>> singleClue = ImmutableMap.of(1, newArrayList(1));
    Puzzle tinyPuzzle = new PuzzleImpl(new CluesImpl(singleClue, singleClue));
    Puzzle solvedPuzzle = UNDER_TEST.solve(tinyPuzzle);

    assertTrue("Puzzle should be fully solved", solvedPuzzle.isFullySolved());
    assertEquals("Single square should be filled in", SquareState.FULL, solvedPuzzle.getStates().get(1,1));
  }

  @Test
  public void testSingleClueEmptySingleSquare() throws UnsolvableException
  {
    ImmutableMap<Integer, List<Integer>> singleClue = ImmutableMap.of(1, newArrayList(0));
    Puzzle tinyPuzzle = new PuzzleImpl(new CluesImpl(singleClue, singleClue));
    Puzzle solvedPuzzle = UNDER_TEST.solve(tinyPuzzle);

    assertTrue("Puzzle should be fully solved", solvedPuzzle.isFullySolved());
    assertEquals("Single square should be blank", SquareState.BLANK, solvedPuzzle.getStates().get(1,1));
  }

  @Test
  public void testSingleClueMultipleSquaresRow() throws UnsolvableException
  {
    Map<Integer, List<Integer>> rowClues = ImmutableMap.of(1, newArrayList(3));
    Map<Integer, List<Integer>> columnClues = ImmutableMap.of(
        1, newArrayList(0),
        2, newArrayList(1),
        3, newArrayList(1),
        4, newArrayList(1),
        5, newArrayList(0));
    Puzzle puzzleBefore = new PuzzleImpl(new CluesImpl(rowClues, columnClues));
    Puzzle solvedPuzzle = UNDER_TEST.solve(puzzleBefore);

    assertTrue("Puzzle should be solved", solvedPuzzle.isFullySolved());
    assertEquals("Square one should be blank", SquareState.BLANK, solvedPuzzle.getStates().get(1,1));
    assertEquals("Square two should be full", SquareState.FULL, solvedPuzzle.getStates().get(1,2));
    assertEquals("Square three should be full", SquareState.FULL, solvedPuzzle.getStates().get(1,3));
    assertEquals("Square four should be full", SquareState.FULL, solvedPuzzle.getStates().get(1,4));
    assertEquals("Square five should be blank", SquareState.BLANK, solvedPuzzle.getStates().get(1,5));
  }

  @Test
  public void testMultiClueRow() throws UnsolvableException
  {
    Map<Integer, List<Integer>> rowClues = ImmutableMap.of(1, newArrayList(2,2));
    Map<Integer, List<Integer>> columnClues = ImmutableMap.of(
        1, newArrayList(1),
        2, newArrayList(1),
        3, newArrayList(0),
        4, newArrayList(1),
        5, newArrayList(1));
    Puzzle puzzleBefore = new PuzzleImpl(new CluesImpl(rowClues, columnClues));
    Puzzle solvedPuzzle = UNDER_TEST.solve(puzzleBefore);

    assertTrue("Puzzle should be solved", solvedPuzzle.isFullySolved());
    assertEquals("Square one should be full", SquareState.FULL, solvedPuzzle.getStates().get(1,1));
    assertEquals("Square two should be full", SquareState.FULL, solvedPuzzle.getStates().get(1,2));
    assertEquals("Square three should be blank", SquareState.BLANK, solvedPuzzle.getStates().get(1,3));
    assertEquals("Square four should be full", SquareState.FULL, solvedPuzzle.getStates().get(1, 4));
    assertEquals("Square five should be full", SquareState.FULL, solvedPuzzle.getStates().get(1,5));
  }

  @Test
  public void testSingleClueMultipleSquaresColumn() throws UnsolvableException
  {
    Map<Integer, List<Integer>> columnClues = ImmutableMap.of(1, newArrayList(3));
    Map<Integer, List<Integer>> rowClues = ImmutableMap.of(
        1, newArrayList(0),
        2, newArrayList(1),
        3, newArrayList(1),
        4, newArrayList(1),
        5, newArrayList(0));
    Puzzle puzzleBefore = new PuzzleImpl(new CluesImpl(columnClues, rowClues));
    Puzzle solvedPuzzle = UNDER_TEST.solve(puzzleBefore);

    assertTrue("Puzzle should be solved", solvedPuzzle.isFullySolved());
    assertEquals("Square one should be blank", SquareState.BLANK, solvedPuzzle.getStates().get(1,1));
    assertEquals("Square two should be full", SquareState.FULL, solvedPuzzle.getStates().get(2,1));
    assertEquals("Square three should be full", SquareState.FULL, solvedPuzzle.getStates().get(3,1));
    assertEquals("Square four should be full", SquareState.FULL, solvedPuzzle.getStates().get(4,1));
    assertEquals("Square five should be blank", SquareState.BLANK, solvedPuzzle.getStates().get(5,1));
  }

  @Test
  public void testMultiClueColumn() throws UnsolvableException
  {
    Map<Integer, List<Integer>> columnClues = ImmutableMap.of(1, newArrayList(2,2));
    Map<Integer, List<Integer>> rowClues = ImmutableMap.of(
        1, newArrayList(1),
        2, newArrayList(1),
        3, newArrayList(0),
        4, newArrayList(1),
        5, newArrayList(1));
    Puzzle puzzleBefore = new PuzzleImpl(new CluesImpl(columnClues, rowClues));
    Puzzle solvedPuzzle = UNDER_TEST.solve(puzzleBefore);

    assertTrue("Puzzle should be solved", solvedPuzzle.isFullySolved());
    assertEquals("Square one should be full", SquareState.FULL, solvedPuzzle.getStates().get(1,1));
    assertEquals("Square two should be full", SquareState.FULL, solvedPuzzle.getStates().get(2,1));
    assertEquals("Square three should be blank", SquareState.BLANK, solvedPuzzle.getStates().get(3,1));
    assertEquals("Square four should be full", SquareState.FULL, solvedPuzzle.getStates().get(4,1));
    assertEquals("Square five should be full", SquareState.FULL, solvedPuzzle.getStates().get(5,1));
  }

  @Test
  public void testCantSolve() throws UnsolvableException
  {
    //Ambiguous - one square in each row and column, two possible solutions
    Map<Integer, List<Integer>> clues = ImmutableMap.of(1, newArrayList(1), 2, newArrayList(1));
    Puzzle puzzleBefore = new PuzzleImpl(new CluesImpl(clues, clues));
    Puzzle puzzleAfter = UNDER_TEST.solve(puzzleBefore);

    assertFalse("Puzzle should not be fully solved", puzzleAfter.isFullySolved());
    assertEquals("Square 1,1 should be unknown", SquareState.UNKNOWN, puzzleAfter.getStates().get(1,1));
    assertEquals("Square 1,2 should be unknown", SquareState.UNKNOWN, puzzleAfter.getStates().get(1,2));
    assertEquals("Square 2,1 should be unknown", SquareState.UNKNOWN, puzzleAfter.getStates().get(2,1));
    assertEquals("Square 2,2 should be unknown", SquareState.UNKNOWN, puzzleAfter.getStates().get(2,2));
  }

  @Test(expected = UnsolvableException.class)
  public void testCollision() throws UnsolvableException
  {
    Map<Integer, List<Integer>> rowClues = ImmutableMap.of(3, newArrayList(3));
    Map<Integer, List<Integer>> columnClues = ImmutableMap.of(
        1, newArrayList(1),
        2, newArrayList(0),
        3, newArrayList(1)
    );
    Puzzle puzzleBefore = new PuzzleImpl(new CluesImpl(rowClues, columnClues));
    UNDER_TEST.solve(puzzleBefore);
  }
}