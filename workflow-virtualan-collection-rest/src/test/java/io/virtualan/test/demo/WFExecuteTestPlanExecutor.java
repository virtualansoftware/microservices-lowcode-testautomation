package io.virtualan.test.demo;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.virtualan.idaithalam.core.api.VirtualanTestPlanExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


@Test
public class WFExecuteTestPlanExecutor {


  @Test
  public void workflowExecution_1() {
    try {
      boolean isSuccess = VirtualanTestPlanExecutor
          .invoke("lakeside-Mutual-Customer-Self-Service.yml");
      Assert.assertTrue(isSuccess);
    } catch (InterruptedException e) {
      e.printStackTrace();
      Assert.assertTrue(false);
    }
  }

}
