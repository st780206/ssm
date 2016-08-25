package quartz2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.quartz.ObjectAlreadyExistsException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestPickNewsJob implements Job {

	@Override
	public void execute(JobExecutionContext jec) throws JobExecutionException {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		System.out.println("在" + sdf.format(new Date()) + "更新日志");
	}

	public static void main(String args[]) throws SchedulerException {
		JobDetail jobDetail = JobBuilder.newJob(TestPickNewsJob.class).withIdentity("job1", "jgroup1").build();
		SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity("trigger1")
				.withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(10, 2)).startNow().build();
		try {
			ApplicationContext ac = new ClassPathXmlApplicationContext("spring/spring-task.xml");
			Scheduler scheduler = (Scheduler) ac.getBean("quartzScheduler");
			scheduler.scheduleJob(jobDetail, simpleTrigger);
			scheduler.start();
		} catch (ObjectAlreadyExistsException e) {
			resumeJob();
		}
	}

	/**
	 * 根据数据库中的记录 恢复异常中断的任务
	 */
	public static void resumeJob() throws SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		Scheduler scheduler = schedulerFactory.getScheduler();
		// ①获取调度器中所有的触发器组
		List<String> triggerGroups = scheduler.getTriggerGroupNames();
		// ②重新恢复在tgroup1组中，名为trigger1触发器的运行
		for (int i = 0; i < triggerGroups.size(); i++) {
			List<String> triggers = scheduler.getTriggerGroupNames();
			for (int j = 0; j < triggers.size(); j++) {
				Trigger tg = scheduler.getTrigger(new TriggerKey(triggers.get(j), triggerGroups.get(i)));
				// ②-1:根据名称判断
				if (tg instanceof SimpleTrigger && tg.getDescription().equals("jgroup1.DEFAULT")) {// 由于我们之前测试没有设置触发器所在组，所以默认为DEFAULT
					// ②-1:恢复运行
					scheduler.resumeJob(new JobKey(triggers.get(j), triggerGroups.get(i)));
				}
			}
		}
		scheduler.start();

	}
}
