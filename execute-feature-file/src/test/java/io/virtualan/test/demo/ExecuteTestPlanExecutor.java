package io.virtualan.test.demo;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.virtualan.idaithalam.core.api.VirtualanTestPlanExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


@org.testng.annotations.Test
public class ExecuteTestPlanExecutor {



  @org.testng.annotations.Test
  public void workflowExecution_xl() {
    try {
      boolean isSuccess = io.virtualan.idaithalam.core.api.VirtualanTestPlanExecutor
              .invoke("Customer-Self-Service-xl-db.yml");
      org.testng.Assert.assertTrue(isSuccess);
    } catch (Exception e) {
      org.testng.Assert.assertTrue(false);
    }
  }


}
