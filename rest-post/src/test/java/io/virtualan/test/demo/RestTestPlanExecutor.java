package io.virtualan.test.demo;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.virtualan.idaithalam.core.api.VirtualanTestPlanExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


@Test
public class RestTestPlanExecutor {

  @Test
  public void workflowExecution_xl() {
    try {
      boolean isSuccess = VirtualanTestPlanExecutor
              .invoke("rest-post.yml");
      Assert.assertTrue(isSuccess);
    } catch (InterruptedException e) {
      Assert.assertTrue(false);
    }
  }

}
