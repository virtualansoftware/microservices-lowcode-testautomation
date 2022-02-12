package io.virtualan.test;



import io.virtualan.idaithalam.core.api.VirtualanTestPlanExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Assert;
import io.virtualan.Kafka2SpringBoot;

@RunWith(SpringRunner.class)
@SpringBootTest(
        classes = Kafka2SpringBoot.class)
public class APITestWithExcelAsTestManager {


  @Test
  public static void execute_workflow() {
    try {
      boolean isSuccess = io.virtualan.idaithalam.core.api.VirtualanTestPlanExecutor.invoke("work-flow.yaml");
      if (!isSuccess) {
        Assert.assertTrue(false);
      }

    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

}