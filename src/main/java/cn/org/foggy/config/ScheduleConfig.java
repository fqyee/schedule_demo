package cn.org.foggy.config;

import cn.org.foggy.dto.TimeTask;
import cn.org.foggy.mapper.TimeTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

@SuppressWarnings("ALL")
@Configuration
@EnableScheduling
public class ScheduleConfig implements SchedulingConfigurer {

    @Autowired
    TaskScheduler poolScheduler;
    @Autowired
    TimeTaskMapper timeTaskMapper;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        if (taskRegistrar.getScheduler() == null) {
            taskRegistrar.setScheduler(poolScheduler);
        }

        taskRegistrar.getScheduler().schedule(runTask(), trigger());
    }

    private Trigger trigger() {
        return t -> {
            List<TimeTask> timeTasks = timeTaskMapper.selectTimeTaskList();
            if (timeTasks == null || timeTasks.isEmpty()) {
                System.out.println("timeTask is empty");
                Calendar nextExecutionTime = new GregorianCalendar();
                Date lastActualExecutionTime = t.lastActualExecutionTime();
                nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
                nextExecutionTime.add(Calendar.SECOND, 10);
                return nextExecutionTime.getTime();
            } else {
                TimeTask timeTask = timeTasks.get(0);
                String taskTime = timeTask.getTaskTime();
                System.out.println("timeTask: " + timeTask);
                LocalDateTime dateTime = LocalDateTime.parse(taskTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                Date from = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());
                Calendar nextExecutionTime = new GregorianCalendar();
                nextExecutionTime.setTime(from);
                return nextExecutionTime.getTime();
            }
        };
    }

    private Runnable runTask() {
        return () -> {
            TimeTask timeTask;
            List<TimeTask> timeTasks = timeTaskMapper.selectTimeTaskList();
            LocalDateTime current = LocalDateTime.now();
            if (timeTasks != null && !timeTasks.isEmpty()) {
                timeTask = timeTasks.get(0);
                String taskTime = timeTask.getTaskTime();
                LocalDateTime parse = LocalDateTime.parse(taskTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                if (current.isEqual(parse) || current.isAfter(parse)) {
                    int i = timeTaskMapper.updateTimeTask(timeTask.getId());
                    System.out.println("执行定时任务: " + current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " updatedCount: " + i + " task: " + timeTask);
                } else {
                    System.out.println("未到任务执行时间 date: " + current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + " task: " + timeTask);
                }
            } else {
                System.out.println("当前无任务待执行 date: " + current.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        };
    }

}
