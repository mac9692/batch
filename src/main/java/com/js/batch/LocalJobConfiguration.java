package com.js.batch;

import lombok.RequiredArgsConstructor;
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
@Slf4j
@Configuration
@RequiredArgsConstructor
public class LocalJobConfiguration {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager platformTransactionManager;

    @Bean
    public Job localJob() {
        return new JobBuilder("localJob", jobRepository)
                .start(step())
                .build();
    }

    @Bean
    public Step step() {
        return new StepBuilder("localStep", jobRepository)
                .tasklet(localTasklet(), platformTransactionManager)
                .build();
    }

    @Bean
    public Tasklet localTasklet() {
        return (contribution, chunkContext) -> {
            log.info("======================");
            log.info("localTasklet");
            log.info("======================");

            return RepeatStatus.FINISHED;
        };
    }
}
