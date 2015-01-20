package uk.co.lucyleach.hanjie_solver.solver;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Maps;
import uk.co.lucyleach.hanjie_solver.Clues;
import uk.co.lucyleach.hanjie_solver.Puzzle;
import uk.co.lucyleach.hanjie_solver.PuzzleImpl;
import uk.co.lucyleach.hanjie_solver.SquareState;

import java.util.List;
import java.util.Map;

/**
 * User: Lucy
 * Date: 19/01/2015
 * Time: 19:52
 */
public class HanjieSolver
{
  private final InitialSolutionsCreator initialSolutionsCreator = new InitialSolutionsCreator();
  private final FixedSquareMerger fixedSquareMerger = new FixedSquareMerger();

  public Puzzle solve(Clues clues) throws UnsolvableException
  {
    Map<Integer, PossibleSolutions> rowSolutions = createInitialSolutions(clues.getRowClues(), clues.getRowLength());
    Map<Integer, PossibleSolutions> columnSolutions = createInitialSolutions(clues.getColumnClues(), clues.getColumnLength());

    boolean somethingHasChanged = true;
    boolean completelySolved = false;
    while(somethingHasChanged && !completelySolved)
    {
      boolean changedInFirstMerge = mergeRowsAndColumns(rowSolutions, columnSolutions);
      boolean changedInSecondMerge = mergeRowsAndColumns(columnSolutions, rowSolutions);
      somethingHasChanged = changedInFirstMerge || changedInSecondMerge;
      completelySolved = checkIfSolved(rowSolutions);
    }

    Puzzle puzzle = solutionFromPossibilities(rowSolutions, clues);
    if(!completelySolved)
      throw new UnsolvableException("Cannot solve puzzle from the clues given", puzzle);
    else
      return puzzle;
  }

  private Map<Integer, PossibleSolutions> createInitialSolutions(Map<Integer, List<Integer>> clues, final int length)
  {
    return Maps.transformValues(clues, new Function<List<Integer>, PossibleSolutions>()
    {
      @Override
      public PossibleSolutions apply(List<Integer> clues)
      {
        return initialSolutionsCreator.create(clues, length);
      }
    });
  }

  //Cross reference fixed squares between rows & columns, eliminate contradictory possibilities
  private boolean mergeRowsAndColumns(Map<Integer, PossibleSolutions> rowSolutions, Map<Integer, PossibleSolutions> columnSolutions) throws UnsolvableException
  {
    boolean hasAnythingChanged = false;
    for(Map.Entry<Integer, PossibleSolutions> rowEntry: rowSolutions.entrySet())
    {
      int rowNum = rowEntry.getKey();
      Map<Integer, SquareState> rowFixedSquares = rowEntry.getValue().getFixedSquares();
      for(Map.Entry<Integer, SquareState> rowFixedSquare: rowFixedSquares.entrySet())
      {
        int fixedSquareColumnNumber = rowFixedSquare.getKey();
        SquareState fixedState = rowFixedSquare.getValue();
        PossibleSolutions possColSolutions = columnSolutions.get(fixedSquareColumnNumber);
        OperationResult mergeResult = fixedSquareMerger.merge(rowNum, fixedState, possColSolutions);
        columnSolutions.put(fixedSquareColumnNumber, mergeResult.getSolutions());
        hasAnythingChanged |= mergeResult.hasChanged();
      }
    }

    return hasAnythingChanged;
  }

  //All squares fixed
  private static boolean checkIfSolved(Map<Integer, PossibleSolutions> rowSolutions)
  {
    boolean allSolved = true;
    for(PossibleSolutions solutions: rowSolutions.values())
    {
      allSolved &= solutions.allSquaresFixed();
    }
    return allSolved;
  }

  //Don't need column solutions, duplicate information. NB may not have a fully solved set of solutions.
  private static Puzzle solutionFromPossibilities(Map<Integer, PossibleSolutions> rowSolutions, Clues clues)
  {
    ImmutableTable.Builder<Integer, Integer, SquareState> states = ImmutableTable.builder();
    for(Map.Entry<Integer, PossibleSolutions> row: rowSolutions.entrySet())
    {
      for(Map.Entry<Integer, SquareState> rowValue: row.getValue().getFixedSquares().entrySet())
      {
        states.put(row.getKey(), rowValue.getKey(), rowValue.getValue());
      }
    }
    return new PuzzleImpl(clues, states.build());
  }
}
