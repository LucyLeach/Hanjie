package uk.co.lucyleach.hanjie_solver.input;

import com.google.common.collect.ImmutableMap;
import org.junit.Test;
import uk.co.lucyleach.hanjie_solver.Clues;
import uk.co.lucyleach.hanjie_solver.CluesImpl;

import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ClueConsistencyCheckerTest
{
  private static final ClueConsistencyChecker UNDER_TEST = new ClueConsistencyChecker();

  @Test
  public void checkGoodClues()
  {
    Map<Integer, List<Integer>> rowClues = ImmutableMap.<Integer, List<Integer>>builder()
        .put(1, newArrayList(1))
        .put(2, newArrayList(1))
        .build();

    Map<Integer, List<Integer>> columnClues = ImmutableMap.<Integer, List<Integer>>builder()
        .put(1, newArrayList(1))
        .put(2, newArrayList(1))
        .build();

    Clues clues = new CluesImpl(rowClues, columnClues);
    ConsistencyCheckResult result = UNDER_TEST.check(clues);
    assertFalse(result.anyErrors());
  }

  @Test
  public void testImpossibleSingleClue()
  {
    Map<Integer, List<Integer>> rowClues = ImmutableMap.<Integer, List<Integer>>builder()
        .put(1, newArrayList(1))
        .put(2, newArrayList(1))
        .build();

    Map<Integer, List<Integer>> columnClues = ImmutableMap.<Integer, List<Integer>>builder()
        .put(1, newArrayList(1))
        .put(2, newArrayList(3))
        .build();

    Clues clues = new CluesImpl(rowClues, columnClues);
    ConsistencyCheckResult result = UNDER_TEST.check(clues);
    assertTrue(result.anyErrors());
    assertTrue(result.getRowsWithErrors().isEmpty());
    assertFalse(result.getColumnsWithErrors().isEmpty());
    assertEquals(1, result.getColumnsWithErrors().size());
    assertTrue(result.getColumnsWithErrors().contains(2));
  }

  @Test
  public void testImpossibleMultiClue()
  {
    Map<Integer, List<Integer>> rowClues = ImmutableMap.<Integer, List<Integer>>builder()
        .put(1, newArrayList(1))
        .put(2, newArrayList(1))
        .build();

    Map<Integer, List<Integer>> columnClues = ImmutableMap.<Integer, List<Integer>>builder()
        .put(1, newArrayList(1))
        .put(2, newArrayList(1,1))
        .build();

    Clues clues = new CluesImpl(rowClues, columnClues);
    ConsistencyCheckResult result = UNDER_TEST.check(clues);
    assertTrue(result.anyErrors());
    assertTrue(result.getRowsWithErrors().isEmpty());
    assertFalse(result.getColumnsWithErrors().isEmpty());
    assertEquals(1, result.getColumnsWithErrors().size());
    assertTrue(result.getColumnsWithErrors().contains(2));
  }

  @Test
  public void testMultipleProblematicClues()
  {

    Map<Integer, List<Integer>> rowClues = ImmutableMap.<Integer, List<Integer>>builder()
        .put(1, newArrayList(1,1))
        .put(2, newArrayList(1))
        .build();

    Map<Integer, List<Integer>> columnClues = ImmutableMap.<Integer, List<Integer>>builder()
        .put(1, newArrayList(1))
        .put(2, newArrayList(3))
        .build();

    Clues clues = new CluesImpl(rowClues, columnClues);
    ConsistencyCheckResult result = UNDER_TEST.check(clues);
    assertTrue(result.anyErrors());
    assertFalse(result.getRowsWithErrors().isEmpty());
    assertEquals(1, result.getRowsWithErrors().size());
    assertTrue(result.getRowsWithErrors().contains(1));
    assertFalse(result.getColumnsWithErrors().isEmpty());
    assertEquals(1, result.getColumnsWithErrors().size());
    assertTrue(result.getColumnsWithErrors().contains(2));
  }
}