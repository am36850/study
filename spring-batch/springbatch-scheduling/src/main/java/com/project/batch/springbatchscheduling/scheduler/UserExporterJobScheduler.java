package com.project.batch.springbatchscheduling.scheduler;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class UserExporterJobScheduler {

  private static final Logger logger = LoggerFactory.getLogger(UserExporterJobScheduler.class);

  @Autowired
  private JobLauncher jobLauncher;

  @Autowired
  private Job job;

  @Scheduled(cron = "${schedule.user.export}")
  public void userExportJob() {
    JobParameters jobParameter = new JobParametersBuilder()
        .addLong("StartTime", System.currentTimeMillis()).toJobParameters();
    try {
      JobExecution jobExecution = jobLauncher.run(job, jobParameter);
      logger.info("Job Started Execution:" + jobExecution.getStatus());
    } catch (JobExecutionAlreadyRunningException e) {
      logger.error(e.toString());
    } catch (JobRestartException e) {
      logger.error(e.toString());
    } catch (JobInstanceAlreadyCompleteException e) {
      logger.error(e.toString());
    } catch (JobParametersInvalidException e) {
      logger.error(e.toString());
    }
  }
}
