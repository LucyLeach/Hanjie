package uk.co.lucyleach.hanjie_solver.solver2;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;
import uk.co.lucyleach.hanjie_solver.Clues;
import uk.co.lucyleach.hanjie_solver.CluesImpl;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

//NB clues in this test are frequently unsolvable
public class ClueHelpfulnessSorterTest
{
  private static final ClueHelpfulnessSorter UNDER_TEST = new ClueHelpfulnessSorter();

  @Test
  public void preferCompleteOverUnknown() {
    Map<Integer, List<Integer>> rows = ImmutableMap.<Integer, List<Integer>>builder()
        .put(0, Lists.newArrayList(1))
        .put(1, Lists.newArrayList(4))
        .put(2, Lists.newArrayList(3))
        .put(3, Lists.newArrayList(1, 1))
        .build();
    Map<Integer, List<Integer>> columns = ImmutableMap.<Integer, List<Integer>>builder()
        .put(0, Lists.newArrayList(0))
        .put(1, Lists.newArrayList(0))
        .put(2, Lists.newArrayList(0))
        .put(3, Lists.newArrayList(0))
        .build();
    Clues clues = new CluesImpl(rows, columns);
    ClueHelpfulnessScore winningScore = UNDER_TEST.bestClue(clues);

    assertNotNull(winningScore);
    assertTrue(winningScore.isRow());
    assertEquals(1, winningScore.getNumber());
  }

  @Test
  public void preferEdgeToCentre() {
    Map<Integer, List<Integer>> rows = ImmutableMap.<Integer, List<Integer>>builder()
        .put(0, Lists.newArrayList(0))
        .put(1, Lists.newArrayList(0))
        .put(2, Lists.newArrayList(0))
        .put(3, Lists.newArrayList(0))
        .build();
    Map<Integer, List<Integer>> columns = ImmutableMap.<Integer, List<Integer>>builder()
        .put(0, Lists.newArrayList(2,1))
        .put(1, Lists.newArrayList(4))
        .put(2, Lists.newArrayList(0))
        .put(3, Lists.newArrayList(0))
        .build();
    Clues clues = new CluesImpl(rows, columns);
    ClueHelpfulnessScore winningScore = UNDER_TEST.bestClue(clues);

    assertNotNull(winningScore);
    assertFalse(winningScore.isRow());
    assertEquals(0, winningScore.getNumber());
  }

  @Test
  public void copeWithAllKnown() {
    Map<Integer, List<Integer>> rows = ImmutableMap.<Integer, List<Integer>>builder()
        .put(0, Lists.newArrayList(4))
        .put(1, Lists.newArrayList(4))
        .put(2, Lists.newArrayList(4))
        .put(3, Lists.newArrayList(4))
        .build();
    Map<Integer, List<Integer>> columns = ImmutableMap.<Integer, List<Integer>>builder()
        .put(0, Lists.newArrayList(2,1))
        .put(1, Lists.newArrayList(2,1))
        .put(2, Lists.newArrayList(2,1))
        .put(3, Lists.newArrayList(2,1))
        .build();
    Clues clues = new CluesImpl(rows, columns);
    ClueHelpfulnessScore winningScore = UNDER_TEST.bestClue(clues);

    assertNotNull(winningScore);
    //Doesn't matter which edge, just that it returns one
    assertTrue(Sets.newHashSet(0, 3).contains(winningScore.getNumber()));
  }

  @Test
  public void throwErrorIfAllUnknowable() {
    Map<Integer, List<Integer>> rows = ImmutableMap.<Integer, List<Integer>>builder()
        .put(0, Lists.newArrayList(1,1))
        .put(1, Lists.newArrayList(1,1))
        .put(2, Lists.newArrayList(1,1))
        .put(3, Lists.newArrayList(1,1))
        .build();
    Map<Integer, List<Integer>> columns = ImmutableMap.<Integer, List<Integer>>builder()
        .put(0, Lists.newArrayList(1,1))
        .put(1, Lists.newArrayList(1,1))
        .put(2, Lists.newArrayList(1,1))
        .put(3, Lists.newArrayList(1,1))
        .build();
    Clues clues = new CluesImpl(rows, columns);
    ClueHelpfulnessScore winningScore = UNDER_TEST.bestClue(clues);

    assertNotNull(winningScore);
    //Doesn't matter which edge, just that it returns one
    assertTrue(Sets.newHashSet(0, 3).contains(winningScore.getNumber()));
  }
}