package uk.co.lucyleach.hanjie_solver;

import java.util.Arrays;

/**
 * User: Lucy
 * Date: 16/12/2014
 * Time: 14:31
 */
public enum SquareState
{
  FULL,
  BLANK,
  UNKNOWN;

  public static SquareState[] arrayOf(int length, SquareState state) {
    SquareState[] array = new SquareState[length];
    Arrays.fill(array, state);
    return array;
  }
}
