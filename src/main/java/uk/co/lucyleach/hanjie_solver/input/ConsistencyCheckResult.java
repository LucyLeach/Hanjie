package uk.co.lucyleach.hanjie_solver.input;

import com.google.common.base.Joiner;

import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * User: Lucy
 * Date: 31/01/2015
 * Time: 14:36
 */
public class ConsistencyCheckResult
{
  private final SortedSet<Integer> rowsWithErrors;
  private final SortedSet<Integer> columnsWithErrors;

  public ConsistencyCheckResult(Set<Integer> rowsWithErrors, Set<Integer> columnsWithErrors)
  {
    this.rowsWithErrors = new TreeSet<>(rowsWithErrors);
    this.columnsWithErrors = new TreeSet<>(columnsWithErrors);
  }

  public SortedSet<Integer> getRowsWithErrors()
  {
    return new TreeSet<>(rowsWithErrors);
  }

  public SortedSet<Integer> getColumnsWithErrors()
  {
    return new TreeSet<>(columnsWithErrors);
  }

  public boolean anyErrors()
  {
    return !rowsWithErrors.isEmpty() || !columnsWithErrors.isEmpty();
  }

  @Override
  public String toString()
  {
    String message;
    if(anyErrors())
    {
      if(rowsWithErrors.isEmpty()) //only column errors
      {
        message = "No problems with row clues, problem with column clues " + Joiner.on(", ").join(columnsWithErrors);
      }
      else if(columnsWithErrors.isEmpty()) //only row errors
      {
        message = "No problems with column clues, problem with row clues " + Joiner.on(", ").join(rowsWithErrors);
      }
      else //both have errors
      {
        message = "Problem with row clues " + Joiner.on(", ").join(rowsWithErrors) + ", and problem with column clues " + Joiner.on(", ").join(columnsWithErrors);
      }
    }
    else
    {
      message = "No errors";
    }
    return message;
  }
}
