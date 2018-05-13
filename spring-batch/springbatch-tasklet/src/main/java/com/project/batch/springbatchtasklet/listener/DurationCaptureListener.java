package com.project.batch.springbatchtasklet.listener;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Component("DurationCaptureListener")
public class DurationCaptureListener implements JobExecutionListener {

  private Logger logger = LoggerFactory.getLogger(DurationCaptureListener.class);

  @Override
  public void beforeJob(JobExecution jobExecution) {
    logger.info(
        "*********************************** Batch Job Started : {} ************************************"
            + jobExecution.getJobConfigurationName());
    logger.info("Start Time :" + new Date());
    logger.info(
        "****************************************************************************************************************************************");
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    logger.info(
        "*********************************** Batch Job Ended : {} ************************************"
            + jobExecution.getJobConfigurationName());
    logger.info("End Time :" + new Date());
    logger.info(
        "*************************************************************************************************************************************");
  }

}
