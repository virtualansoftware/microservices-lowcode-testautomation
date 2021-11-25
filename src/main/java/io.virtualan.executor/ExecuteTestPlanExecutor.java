package io.virtualan.executor;

import io.virtualan.idaithalam.core.api.VirtualanTestPlanExecutor;
import org.junit.Assert;


public class ExecuteTestPlanExecutor {



  public static void main(String[] args) {
    try {
      boolean isSuccess = VirtualanTestPlanExecutor
              .invoke("input.yml");
      Assert.assertTrue(isSuccess);
    } catch (InterruptedException e) {
      e.printStackTrace();
      Assert.assertTrue(e.getMessage(),false);
    } catch (Exception e) {
      e.printStackTrace();
      Assert.assertTrue(e.getMessage(),false);
    }
  }

}
