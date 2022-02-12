package io.virtualan.test;

import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import io.virtualan.idaithalam.core.api.VirtualanTestPlanExecutor;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = io.virtualan.Kafka2SpringBoot.class)
public class KafkaMessageTest {

    @Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
    }

    @Test
    public void execute_workflow() {
        try {
            boolean isSuccess = VirtualanTestPlanExecutor.invoke("work-flow.yaml");
            if (!isSuccess) {
                org.junit.Assert.assertTrue(false);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}