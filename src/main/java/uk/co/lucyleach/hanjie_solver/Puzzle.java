package uk.co.lucyleach.hanjie_solver;

import com.google.common.collect.Table;

/**
 * User: Lucy
 * Date: 16/12/2014
 * Time: 14:32
 */
public interface Puzzle
{
  Clues getClues();

  Table<Integer, Integer, SquareState> getStates();
}
