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
              .invoke("demo-all.yml");
      Assert.assertTrue(isSuccess);
    } catch (Exception e) {
      Assert.assertTrue(false);
    }
  }

}
