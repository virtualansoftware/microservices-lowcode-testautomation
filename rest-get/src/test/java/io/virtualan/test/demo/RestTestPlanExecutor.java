package io.virtualan.test.demo;

import io.virtualan.idaithalam.core.api.VirtualanTestPlanExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;


@Test
public class RestTestPlanExecutor {

  @Test
  public void workflowExecution_xl() {
    try {
      boolean isSuccess = VirtualanTestPlanExecutor
              .invoke("rest-get.yml");
      Assert.assertTrue(isSuccess);
    } catch (InterruptedException e) {
      Assert.assertTrue(false);
    }
  }

  @Test
  public void workflowExecution_admin() {
    try {
      boolean isSuccess = VirtualanTestPlanExecutor
              .invoke("Org.yml");
      Assert.assertTrue(isSuccess);
    } catch (Exception e) {
      Assert.assertTrue(false);
    }
  }

}
