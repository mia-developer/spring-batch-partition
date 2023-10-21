package org.example.job.step;

import lombok.RequiredArgsConstructor;
import org.example.job.model.Sample;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@RequiredArgsConstructor
@Configuration
public class SampleStepConfig {

    public static final String STEP_NAME = "initializeTransactionLedgerStep";

    @JobScope
    @Bean(STEP_NAME)
    public Step step(
            final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
        return new StepBuilder(STEP_NAME, jobRepository)
                .<Sample, Sample>chunk(20, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    private ItemReader<Sample> reader(){
        return null;
    }

    private ItemProcessor<Sample, Sample> processor(){
        return null;
    }

    private ItemWriter<Sample> writer(){
        return null;
    }
}
