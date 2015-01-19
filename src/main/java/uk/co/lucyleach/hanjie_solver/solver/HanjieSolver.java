package uk.co.lucyleach.hanjie_solver.solver;

import uk.co.lucyleach.hanjie_solver.Clues;
import uk.co.lucyleach.hanjie_solver.Puzzle;

import java.util.List;
import java.util.Map;

/**
 * User: Lucy
 * Date: 19/01/2015
 * Time: 19:52
 */
public class HanjieSolver
{
  public Puzzle solve(Clues clues) throws UnsolvableException
  {
    Map<Integer, PossibleSolutions> rowSolutions = createInitialSolutions(clues.getRowClues().size(), clues.getColumnClues().size());
    Map<Integer, PossibleSolutions> columnSolutions = createInitialSolutions(clues.getColumnClues().size(), clues.getRowClues().size());

    boolean somethingHasChanged = true;
    boolean completelySolved = false;
    while(somethingHasChanged && !completelySolved)
    {
      boolean changedRows = collapseSolutions(clues.getRowClues(), rowSolutions);
      boolean changedColumns = collapseSolutions(clues.getColumnClues(), columnSolutions);
      boolean changedInMerge = mergeRowsAndColumns(rowSolutions, columnSolutions);
      somethingHasChanged = changedColumns || changedRows || changedInMerge;
      completelySolved = checkIfSolved(rowSolutions);
    }

    if(!completelySolved)
      throw new UnsolvableException();
    else
      return solutionFromPossibilities(rowSolutions);
  }

  private Map<Integer, PossibleSolutions> createInitialSolutions(int numberOfEntries, int length)
  {

  }

  private static boolean collapseSolutions(Map<Integer, List<Integer>> clues, Map<Integer, PossibleSolutions> possibleSolutions)
      throws UnsolvableException
  {

  }

  private static boolean mergeRowsAndColumns(Map<Integer, PossibleSolutions> rowSolutions, Map<Integer, PossibleSolutions> columnSolutions)
  {

  }

  private static boolean checkIfSolved(Map<Integer, PossibleSolutions> rowSolutions)
  {

  }

  //Don't need column solutions, duplicate information
  private static Puzzle solutionFromPossibilities(Map<Integer, PossibleSolutions> rowSolutions)
  {

  }
}
