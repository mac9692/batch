package com.js.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author parkjinseong
 * @version 1.0
 * @since 2024/04/05
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
public class DevelopJobConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Job developJob() {
        return new JobBuilder("developJob", jobRepository)
                .start(firstStep())
                .next(secondStep())
                .build();
    }

    @Bean
    public Step firstStep() {
        return new StepBuilder("firstStep", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    log.info("======================");
                    log.info("firstStep");
                    log.info("======================");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }

    @Bean
    public Step secondStep() {
        return new StepBuilder("secondStep", jobRepository).tasklet((contribution, chunkContext) -> {
                    log.info("======================");
                    log.info("secondStep");
                    log.info("======================");
                    return RepeatStatus.FINISHED;
                }, platformTransactionManager)
                .build();
    }
}
