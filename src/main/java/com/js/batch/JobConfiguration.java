package com.js.batch;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @author parkjinseong
 * @version 1.0
 * @since 2024/04/04
 */
@Configuration
@Slf4j
public class JobConfiguration {

    @Bean
    public Job testJob(JobRepository jobRepository, Step firstStep) {
        return new JobBuilder("testJob", jobRepository)
                .start(firstStep)
                .build();
    }

    @Bean
    public Step firstStep(JobRepository jobRepository, Tasklet testTasklet, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("firstStep", jobRepository)
                .tasklet(testTasklet, platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet testTasklet() {
        return (contribution, chunkContext) -> {
            log.info("======================");
            log.info("익명 실행 함수");
            log.info("======================");

            return RepeatStatus.FINISHED;
        };
    }
}
