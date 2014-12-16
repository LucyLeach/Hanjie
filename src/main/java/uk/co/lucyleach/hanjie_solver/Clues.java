package uk.co.lucyleach.hanjie_solver;

import java.util.List;
import java.util.Map;

/**
 * User: Lucy
 * Date: 16/12/2014
 * Time: 14:27
 */
public interface Clues
{
  //Corresponds to clues read down side of puzzle
  Map<Integer, List<Integer>> getRowClues();

  //Corresponds to clues read along the top of the puzzle
  Map<Integer, List<Integer>> getColumnClues();
}
