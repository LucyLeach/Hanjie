package uk.co.lucyleach.hanjie_solver;

import com.google.common.collect.Table;

import java.util.Map;

/**
 * User: Lucy
 * Date: 16/12/2014
 * Time: 14:32
 */
public interface Puzzle
{
  Clues getClues();

  //First key is row number
  Table<Integer, Integer, SquareState> getStates();

  boolean isFullySolved();

  Puzzle updateRow(int rowKey, Map<Integer, SquareState> newRowState);

  Puzzle updateColumn(int columnKey, Map<Integer, SquareState> newColumnState);
}
