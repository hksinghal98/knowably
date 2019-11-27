package com.stackroute.quartz.job;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;


@Component
public class WriteJob extends QuartzJobBean {
    private static final Logger logger = LoggerFactory.getLogger(WriteJob.class);

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;
    private String topic="schedulerFlag";

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("Executing Job with key {}", jobExecutionContext.getJobDetail().getKey());
        KafkaTemplate<String,String> kt=this.kafkaTemplate;
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        String subject = jobDataMap.getString("flagForScheduler");
        //Object body = jobDataMap.get("actors");

        kt.send("schedulerFlag",subject);
        System.out.println("flag for data populated ="+subject);
    }
}
