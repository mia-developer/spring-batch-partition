package org.example.job.step;

import lombok.RequiredArgsConstructor;
import org.example.data.entity.enums.PaymentGatewayType;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
public class SamplePartitionStepConfig {

    public static final String STEP_NAME = "samplePartitionStep";
    public static final String PAYMENT_GATEWAY = "paymentGateway";
    public static final String PAYMENT_DATE = "paymentDate";

    private final Step sampleStep;

    @JobScope
    @Bean(STEP_NAME)
    public Step step(final JobRepository jobRepository) {
        return new StepBuilder(STEP_NAME, jobRepository)
                .partitioner(SampleStepConfig.STEP_NAME, partitioner())
                .step(sampleStep)
                .build();
    }

    public Partitioner partitioner() {
        return gridSize -> {
            Map<String, ExecutionContext> executionContextMap = new HashMap<>();

            for (PaymentGatewayType paymentGateway : PaymentGatewayType.values()) {
                ExecutionContext executionContext = new ExecutionContext(Map.of(PAYMENT_GATEWAY, paymentGateway));

                executionContextMap.put(paymentGateway.name(), executionContext);
            }

            return executionContextMap;
        };
    }
}
