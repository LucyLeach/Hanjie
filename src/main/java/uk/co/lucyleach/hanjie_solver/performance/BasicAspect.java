package uk.co.lucyleach.hanjie_solver.performance;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * User: Lucy
 * Date: 01/02/2015
 * Time: 15:54
 */
@Aspect
public class BasicAspect
{
  @Before("hanjieSolver()")
  public void basicAspect()
  {
    System.out.println("I made an aspect!");
  }

  @Pointcut("call(uk.co.lucyleach.hanjie_solver.Puzzle uk.co.lucyleach.hanjie_solver.solver.HanjieSolver.solve(uk.co.lucyleach.hanjie_solver.Clues))")
  private void hanjieSolver()
  {}
}
