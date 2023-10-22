package org.example.job.step;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.data.entity.QBasePaymentGatewayHistoryEntity;
import org.example.data.entity.enums.PaymentGatewayType;
import org.example.data.expression.PaymentHistoryProjection;
import org.example.data.expression.QPaymentHistoryProjection;
import org.example.job.step.manager.StepContextManager;
import org.example.job.step.reader.QuerydslPagingItemReader;
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

import static org.example.data.entity.QApplePaymentHistoryEntity.applePaymentHistoryEntity;
import static org.example.data.entity.QGooglePaymentHistoryEntity.googlePaymentHistoryEntity;
import static org.example.data.entity.QPaymentHistoryEntity.paymentHistoryEntity;
import static org.example.job.step.SamplePartitionStepConfig.PAYMENT_GATEWAY;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class SampleStepConfig {

    public static final String STEP_NAME = "sampleStep";
    private static final Integer CHUNK_SIZE = 20;
    private final EntityManagerFactory entityManagerFactory;

    @JobScope
    @Bean(STEP_NAME)
    public Step step(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
        return new StepBuilder(STEP_NAME, jobRepository)
                .<PaymentHistoryProjection, PaymentHistoryProjection>chunk(CHUNK_SIZE, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    private ItemReader<PaymentHistoryProjection> reader(){
        return new QuerydslPagingItemReader<>(entityManagerFactory, CHUNK_SIZE, this::createQuery);
    }

    private JPAQuery<PaymentHistoryProjection> createQuery(final JPAQueryFactory queryFactory) {
        PaymentGatewayType targetPaymentGatewayType = StepContextManager.get(PAYMENT_GATEWAY, v -> (PaymentGatewayType) v);
        QBasePaymentGatewayHistoryEntity basePaymentGatewayHistory =
                this.getBasePaymentGatewayHistoryEntity(targetPaymentGatewayType);

        return queryFactory
                .select(new QPaymentHistoryProjection(paymentHistoryEntity, basePaymentGatewayHistory))
                .from(paymentHistoryEntity)
                .innerJoin(basePaymentGatewayHistory)
                .on(paymentHistoryEntity.number.eq(basePaymentGatewayHistory.number))
                .where(paymentHistoryEntity.paymentGatewayType.eq(targetPaymentGatewayType));
    }

    private QBasePaymentGatewayHistoryEntity getBasePaymentGatewayHistoryEntity(final PaymentGatewayType type) {
        return switch (type) {
            case APPLE_PAY -> applePaymentHistoryEntity._super;
            case GOOGLE_PAY -> googlePaymentHistoryEntity._super;
        };
    }

    private ItemProcessor<PaymentHistoryProjection, PaymentHistoryProjection> processor(){
        return v -> v;
    }

    private ItemWriter<PaymentHistoryProjection> writer(){
        return chunk -> chunk.getItems().forEach(v -> log.info("paymentNumber :: {}", v.getNumber()));
    }
}
