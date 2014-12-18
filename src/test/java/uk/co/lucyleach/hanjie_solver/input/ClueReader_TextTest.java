package uk.co.lucyleach.hanjie_solver.input;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import org.junit.Test;
import uk.co.lucyleach.hanjie_solver.Clues;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ClueReader_TextTest
{
  private static ClueReader<File> UNDER_TEST = new ClueReader_Text();

  @Test
  public void testCorrectFile()
  {
    Clues clues = UNDER_TEST.readInput(new File(this.getClass().getResource("test_input.txt").getFile()));
    assertNotNull(clues);

    Map<Integer, List<Integer>> columnClues = clues.getColumnClues();
    Map<Integer, List<Integer>> expectedColumnClues = ImmutableMap.<Integer, List<Integer>>builder()
        .put(1, Lists.newArrayList(2,6))
        .put(2, Lists.newArrayList(1,4))
        .put(3, Lists.newArrayList(1,1))
        .put(4, Lists.newArrayList(2,1))
        .put(5, Lists.newArrayList(1,1))
        .put(6, Lists.newArrayList(2,1))
        .put(7, Lists.newArrayList(1,1))
        .put(8, Lists.newArrayList(2,1,1))
        .put(9, Lists.newArrayList(1,1,2))
        .put(10, Lists.newArrayList(2,6))
        .build();

    assertEquals("Column clues should match", expectedColumnClues, columnClues);

    Map<Integer, List<Integer>> rowClues = clues.getRowClues();
    Map<Integer, List<Integer>> expectedRowClues = ImmutableMap.<Integer, List<Integer>>builder()
        .put(1, Lists.newArrayList(2,2))
        .put(2, Lists.newArrayList(1,1))
        .put(3, Lists.newArrayList(2,2))
        .put(4, Lists.newArrayList(1,1))
        .put(5, Lists.newArrayList(1,1,1))
        .put(6, Lists.newArrayList(1,2,1))
        .put(7, Lists.newArrayList(2,2))
        .put(8, Lists.newArrayList(2,1,1,1,1))
        .put(9, Lists.newArrayList(2,1,1,2))
        .put(10, Lists.newArrayList(3,3))
        .build();

    assertEquals("Row clues should match", expectedRowClues, rowClues);
  }

  @Test(expected = FileNotFoundException.class)
  public void testMissingFile()
  {
    UNDER_TEST.readInput(new File(""));
  }

  @Test(expected = NullPointerException.class)
  public void testNullFile()
  {
    UNDER_TEST.readInput(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNonNumericFile()
  {
    UNDER_TEST.readInput(new File(this.getClass().getResource("non_numeric_test_input.txt").getFile()));
  }
}