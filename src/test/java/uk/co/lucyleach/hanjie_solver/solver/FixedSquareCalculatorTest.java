package uk.co.lucyleach.hanjie_solver.solver;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import org.junit.Test;
import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

public class FixedSquareCalculatorTest
{
  private static final FixedSquareCalculator UNDER_TEST = new FixedSquareCalculator();

  @Test
  public void testNoFixedSquares()
  {
    Set<Map<Integer, SquareState>> input = ImmutableSet.<Map<Integer, SquareState>>builder()
        .add(ImmutableMap.of(1, SquareState.BLANK, 2, SquareState.FULL))
        .add(ImmutableMap.of(1, SquareState.FULL, 2, SquareState.BLANK))
        .build();

    Map<Integer, SquareState> output = UNDER_TEST.calculate(input);
    assertNotNull(output);
    assertTrue(output.isEmpty());
  }

  @Test
  public void testOnlyOneSolution()
  {
    Set<Map<Integer, SquareState>> input = ImmutableSet.<Map<Integer, SquareState>>builder()
        .add(ImmutableMap.of(1, SquareState.FULL, 2, SquareState.BLANK))
        .build();

    Map<Integer, SquareState> output = UNDER_TEST.calculate(input);
    assertNotNull(output);
    assertEquals(2, output.size());
    assertEquals(SquareState.FULL, output.get(1));
    assertEquals(SquareState.BLANK, output.get(2));
  }

  @Test
  public void testGeneralCase()
  {
    Set<Map<Integer, SquareState>> input = ImmutableSet.<Map<Integer, SquareState>>builder()
        .add(ImmutableMap.of(1, SquareState.FULL, 2, SquareState.BLANK))
        .add(ImmutableMap.of(1, SquareState.BLANK, 2, SquareState.BLANK))
        .build();

    Map<Integer, SquareState> output = UNDER_TEST.calculate(input);
    assertNotNull(output);
    assertEquals(1, output.size());
    assertFalse(output.containsKey(1));
    assertEquals(SquareState.BLANK, output.get(2));
  }
}