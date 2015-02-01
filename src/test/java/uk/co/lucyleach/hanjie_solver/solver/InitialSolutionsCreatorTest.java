package uk.co.lucyleach.hanjie_solver.solver;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.*;

import static com.google.common.collect.Iterables.getOnlyElement;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.*;

public class InitialSolutionsCreatorTest
{
  private static InitialSolutionsCreator UNDER_TEST = new InitialSolutionsCreator();

  @Test
  public void testZeroClue()
  {
    List<Integer> zeroClue = newArrayList(0);
    int length = 5;
    PossibleSolutions solutions = UNDER_TEST.create(zeroClue, length);
    Map<Integer, SquareState> onlySolution = testForAndGetCompleteSolution(solutions, length);

    Set<SquareState> values = new HashSet<>(onlySolution.values());
    assertTrue("Solution should have blank squares", values.contains(SquareState.BLANK));
    assertFalse("Solution should not have full squares", values.contains(SquareState.FULL));
    assertFalse("Solution should not have unknown squares", values.contains(SquareState.UNKNOWN));
  }

  @Test
  public void testFullLengthSingleClue()
  {
    int length = 5;
    List<Integer> fullSingleClue = newArrayList(length);
    PossibleSolutions solutions = UNDER_TEST.create(fullSingleClue, length);
    Map<Integer, SquareState> onlySolution = testForAndGetCompleteSolution(solutions, length);

    Set<SquareState> values = new HashSet<>(onlySolution.values());
    assertTrue("Solution should have full squares", values.contains(SquareState.FULL));
    assertFalse("Solution should not have blank squares", values.contains(SquareState.BLANK));
    assertFalse("Solution should not have unknown squares", values.contains(SquareState.UNKNOWN));
  }

  @Test
  public void testFullLengthMultiClue()
  {
    int length = 7;
    List<Integer> clues = newArrayList(2, 1, 2);
    PossibleSolutions solutions = UNDER_TEST.create(clues, length);
    Map<Integer, SquareState> onlySolution = testForAndGetCompleteSolution(solutions, length);
    Map<Integer, SquareState> expectedSolution = ImmutableMap.<Integer, SquareState>builder()
        .put(1, SquareState.FULL)
        .put(2, SquareState.FULL)
        .put(3, SquareState.BLANK)
        .put(4, SquareState.FULL)
        .put(5, SquareState.BLANK)
        .put(6, SquareState.FULL)
        .put(7, SquareState.FULL)
        .build();

    assertEquals("Incorrect solution found", expectedSolution, onlySolution);
  }

  @Test
  public void testOneLessThanFullLengthMultiClue()
  {
    int length = 7;
    List<Integer> clues = newArrayList(2, 1, 1);
    PossibleSolutions solutions = UNDER_TEST.create(clues, length);

    assertEquals("Should have four solutions", 4, solutions.numberOfSolutions());
    assertFalse("Should not be completely fixed", solutions.allSquaresFixed());

    Set<Map<Integer, SquareState>> solutionMapSet = solutions.getAllSolutions();
    Set<Map<Integer, SquareState>> expectedSolutions = ImmutableSet.<Map<Integer, SquareState>>builder()
        .add(ImmutableMap.<Integer, SquareState>builder()
            .put(1, SquareState.FULL)
            .put(2, SquareState.FULL)
            .put(3, SquareState.BLANK)
            .put(4, SquareState.FULL)
            .put(5, SquareState.BLANK)
            .put(6, SquareState.FULL)
            .put(7, SquareState.BLANK)
            .build())
        .add(ImmutableMap.<Integer, SquareState>builder()
              .put(1, SquareState.FULL)
              .put(2, SquareState.FULL)
              .put(3, SquareState.BLANK)
              .put(4, SquareState.FULL)
              .put(5, SquareState.BLANK)
              .put(6, SquareState.BLANK)
              .put(7, SquareState.FULL)
              .build())
        .add(ImmutableMap.<Integer, SquareState>builder()
              .put(1, SquareState.FULL)
              .put(2, SquareState.FULL)
              .put(3, SquareState.BLANK)
              .put(4, SquareState.BLANK)
              .put(5, SquareState.FULL)
              .put(6, SquareState.BLANK)
              .put(7, SquareState.FULL)
              .build())
        .add(ImmutableMap.<Integer, SquareState>builder()
              .put(1, SquareState.BLANK)
              .put(2, SquareState.FULL)
              .put(3, SquareState.FULL)
              .put(4, SquareState.BLANK)
              .put(5, SquareState.FULL)
              .put(6, SquareState.BLANK)
              .put(7, SquareState.FULL)
              .build())
        .build();

    assertEquals("Didn't get the right solutions", expectedSolutions, solutionMapSet);
  }

  @Test
  public void testBiggerGaps()
  {
    int length = 6;
    List<Integer> clues = newArrayList(1, 1);
    PossibleSolutions solutions = UNDER_TEST.create(clues, length);

    assertEquals("Should have ten solutions", 10, solutions.numberOfSolutions());
    assertFalse("Shouldn't be fully fixed", solutions.allSquaresFixed());

    Set<Map<Integer, SquareState>> solutionMapSet = solutions.getAllSolutions();
        Set<Map<Integer, SquareState>> expectedSolutions = ImmutableSet.<Map<Integer, SquareState>>builder()
            .add(ImmutableMap.<Integer, SquareState>builder()
                .put(1, SquareState.FULL)
                .put(2, SquareState.BLANK)
                .put(3, SquareState.FULL)
                .put(4, SquareState.BLANK)
                .put(5, SquareState.BLANK)
                .put(6, SquareState.BLANK)
                .build())
            .add(ImmutableMap.<Integer, SquareState>builder()
                .put(1, SquareState.FULL)
                .put(2, SquareState.BLANK)
                .put(3, SquareState.BLANK)
                .put(4, SquareState.FULL)
                .put(5, SquareState.BLANK)
                .put(6, SquareState.BLANK)
                .build())
            .add(ImmutableMap.<Integer, SquareState>builder()
                .put(1, SquareState.FULL)
                .put(2, SquareState.BLANK)
                .put(3, SquareState.BLANK)
                .put(4, SquareState.BLANK)
                .put(5, SquareState.FULL)
                .put(6, SquareState.BLANK)
                .build())
            .add(ImmutableMap.<Integer, SquareState>builder()
                .put(1, SquareState.FULL)
                .put(2, SquareState.BLANK)
                .put(3, SquareState.BLANK)
                .put(4, SquareState.BLANK)
                .put(5, SquareState.BLANK)
                .put(6, SquareState.FULL)
                .build())
            .add(ImmutableMap.<Integer, SquareState>builder()
                .put(1, SquareState.BLANK)
                .put(2, SquareState.FULL)
                .put(3, SquareState.BLANK)
                .put(4, SquareState.BLANK)
                .put(5, SquareState.BLANK)
                .put(6, SquareState.FULL)
                .build())
            .add(ImmutableMap.<Integer, SquareState>builder()
                .put(1, SquareState.BLANK)
                .put(2, SquareState.BLANK)
                .put(3, SquareState.FULL)
                .put(4, SquareState.BLANK)
                .put(5, SquareState.BLANK)
                .put(6, SquareState.FULL)
                .build())
            .add(ImmutableMap.<Integer, SquareState>builder()
                .put(1, SquareState.BLANK)
                .put(2, SquareState.BLANK)
                .put(3, SquareState.BLANK)
                .put(4, SquareState.FULL)
                .put(5, SquareState.BLANK)
                .put(6, SquareState.FULL)
                .build())
            .add(ImmutableMap.<Integer, SquareState>builder()
                .put(1, SquareState.BLANK)
                .put(2, SquareState.FULL)
                .put(3, SquareState.BLANK)
                .put(4, SquareState.FULL)
                .put(5, SquareState.BLANK)
                .put(6, SquareState.BLANK)
                .build())
            .add(ImmutableMap.<Integer, SquareState>builder()
                .put(1, SquareState.BLANK)
                .put(2, SquareState.FULL)
                .put(3, SquareState.BLANK)
                .put(4, SquareState.BLANK)
                .put(5, SquareState.FULL)
                .put(6, SquareState.BLANK)
                .build())
            .add(ImmutableMap.<Integer, SquareState>builder()
                .put(1, SquareState.BLANK)
                .put(2, SquareState.BLANK)
                .put(3, SquareState.FULL)
                .put(4, SquareState.BLANK)
                .put(5, SquareState.FULL)
                .put(6, SquareState.BLANK)
                .build())
            .build();

        assertEquals("Didn't get the right solutions", expectedSolutions, solutionMapSet);
  }

  @Test
  public void testBiggerGapsClueNotEndingInOne() //Yes, this is a real gap in the test cases that came up
  {
    int length = 5;
    List<Integer> clues = newArrayList(1, 2);
    PossibleSolutions solutions = UNDER_TEST.create(clues, length);
    assertFalse("Shouldn't have fixed all squares", solutions.allSquaresFixed());
    assertEquals("Should have three solutions", 3, solutions.numberOfSolutions());

    Set<Map<Integer, SquareState>> expectedSolutions = ImmutableSet.<Map<Integer, SquareState>>builder()
        .add(ImmutableMap.<Integer, SquareState>builder()
            .put(1, SquareState.FULL)
            .put(2, SquareState.BLANK)
            .put(3, SquareState.FULL)
            .put(4, SquareState.FULL)
            .put(5, SquareState.BLANK)
            .build())
        .add(ImmutableMap.<Integer, SquareState>builder()
            .put(1, SquareState.FULL)
            .put(2, SquareState.BLANK)
            .put(3, SquareState.BLANK)
            .put(4, SquareState.FULL)
            .put(5, SquareState.FULL)
            .build())
        .add(ImmutableMap.<Integer, SquareState>builder()
            .put(1, SquareState.BLANK)
            .put(2, SquareState.FULL)
            .put(3, SquareState.BLANK)
            .put(4, SquareState.FULL)
            .put(5, SquareState.FULL)
            .build())
        .build();

    assertEquals(expectedSolutions, solutions.getAllSolutions());
  }

  @Test
  public void testSingleSquare()
  {
    int length = 4;
    List<Integer> clue = newArrayList(1);
    PossibleSolutions solutions = UNDER_TEST.create(clue, length);

    assertEquals("Should have four solutions", length, solutions.numberOfSolutions());
    assertFalse("Should not be completely fixed", solutions.allSquaresFixed());

    Set<Map<Integer, SquareState>> solutionMapSet = solutions.getAllSolutions();
    Set<Map<Integer, SquareState>> expectedSolutions = ImmutableSet.<Map<Integer, SquareState>>builder()
        .add(ImmutableMap.<Integer, SquareState>builder()
            .put(1, SquareState.FULL)
            .put(2, SquareState.BLANK)
            .put(3, SquareState.BLANK)
            .put(4, SquareState.BLANK)
            .build())
        .add(ImmutableMap.<Integer, SquareState>builder()
            .put(1, SquareState.BLANK)
            .put(2, SquareState.FULL)
            .put(3, SquareState.BLANK)
            .put(4, SquareState.BLANK)
            .build())
        .add(ImmutableMap.<Integer, SquareState>builder()
            .put(1, SquareState.BLANK)
            .put(2, SquareState.BLANK)
            .put(3, SquareState.FULL)
            .put(4, SquareState.BLANK)
            .build())
        .add(ImmutableMap.<Integer, SquareState>builder()
            .put(1, SquareState.BLANK)
            .put(2, SquareState.BLANK)
            .put(3, SquareState.BLANK)
            .put(4, SquareState.FULL)
            .build())
        .build();

    assertEquals("Didn't get the right solutions", expectedSolutions, solutionMapSet);
  }

  private static void testMapHasCorrectKeys(Map<Integer, SquareState> map)
  {
    SortedSet<Integer> sortedKeySet = new TreeSet<>(map.keySet());
    int toCompareTo = 1;
    for(int key: sortedKeySet)
    {
      assertEquals("Map is missing key " + toCompareTo, toCompareTo, key);
      toCompareTo++;
    }
  }

  private static Map<Integer, SquareState> testForAndGetCompleteSolution(PossibleSolutions solutions, int length)
  {
    assertEquals("Should have one solution", 1, solutions.numberOfSolutions());
    assertTrue("Should be completely fixed", solutions.allSquaresFixed());

    Set<Map<Integer, SquareState>> solutionMapSet = solutions.getAllSolutions();
    assertEquals("Should have one solution in map set", 1, solutionMapSet.size());
    Map<Integer, SquareState> onlySolution = getOnlyElement(solutionMapSet);
    assertEquals("Solution should have length input into function", length, onlySolution.size());
    testMapHasCorrectKeys(onlySolution);

    return onlySolution;
  }
}