package com.softech.ls360.util.config.spring;

import java.util.concurrent.Executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration

/**
 * To enable asynchronous method execution on @Async methods, annotate your @Configuration class with @EnableAsync. Likewise, to 
 * enable scheduled method execution on @Scheduled methods, use the @EnableScheduling annotation. You want to place these 
 * annotations on the RootContextConfiguration to share this configuration across all beans in your application. However, 
 * @EnableAsync and @EnableScheduling by themselves simply establish default asynchronous and scheduling configurations. To 
 * customize this behavior, you need to implement the AsyncConfigurer interface to return the proper asynchronous executor and 
 * implement the SchedulingConfigurer class to assign the proper executor to the scheduler.
 * 
 * The proxyTargetClass attribute in the @EnableAsync annotation tells Spring to use the CGLIB library to proxy classes with 
 * asynchronous or scheduled methods instead of using Java interface proxies. This allows you to have asynchronous and scheduled 
 * methods on your beans that aren’t specified in an interface. If you set this attribute to false, only interface-specified methods
 * could be executed on a schedule or asynchronously.
 * 
 * Spring Framework can advise your methods using AspectJ pointcuts or proxies. Using AdviceMode.PROXY enables proxies, meaning proxy
 * classes wrap around advised methods to execute advice code before and after the methods as necessary. You can create these proxy 
 * methods using dynamic proxies (proxyTargetClass = false), which are part of the standard Java SE API. This is the preferred, 
 * best-practice proxy technique. However, dynamic proxies can advise only methods that are specified in an interface, and they 
 * apply only if the consuming code uses the interface instead of the actual class. If you need to advise public methods that are 
 * only part of the class and not part of an interface, you must use CGLIB proxies (proxyTargetClass = true). The important downside
 * to remember about using CGLIB proxies is that your bean constructors execute twice, not once, so plan accordingly.
 * 
 * When you use dynamic proxies, the method advice they provide applies only when another class executes methods on the 
 * Spring-managed bean instance. If a method invoked on an instance of FooBean executes another method on the same instance of 
 * FooBean (with or without using this), the method advice does not execute. (See org.springframework.aop.framework.AopContext for 
 * an ugly way to do this, which you should avoid whenever possible.) CGLIB proxies override every non-final method on a class, so
 * method advice is applied when FooBean calls another FooBean method (with or without using this). However, Spring cannot create 
 * CGLIB proxies for final classes.
 */
@EnableScheduling
@EnableAsync(mode = AdviceMode.PROXY, proxyTargetClass = false, order = 1)
public class AsyncConfig implements SchedulingConfigurer, AsyncConfigurer {

	private static final Logger log = LogManager.getLogger();
	private static final Logger schedulingLogger = LogManager.getLogger(log.getName() + ".[scheduling]");
	
	/**
	 * The @Bean annotation is used to declare a Spring bean and the DI requirements. The @Bean annotation is equivalent to
	 * the <bean> tag, the method name is equivalent to the id attribute within the <bean> tag.
	 * 
	 * The new @Bean method exposes the scheduler as a bean that any of your beans may use. getAsyncExecutor() and configureTasks()
	 * each call taskScheduler(), so aren’t two TaskSchedulers instantiated? And isn’t a third TaskScheduler instantiated when 
	 * Spring calls the @Bean method? Actually, only one TaskScheduler is instantiated. Spring proxies calls to all @Bean methods so
	 * that they are never called more than once. The result of the first invocation of a @Bean method is cached and used for all 
	 * future invocations. This allows multiple methods in your configuration to use other @Bean methods. Because of this, only one
	 * TaskScheduler is instantiated in this configuration, and that instance is used for the bean definition, in the 
	 * getAsyncExecutor() method, and in the configureTasks() method.
	 * 
	 * @return
	 */
	@Bean
	public ThreadPoolTaskScheduler taskScheduler() {

		/**
		 * Spring Framework defines the distinct but closely related concepts of executors and schedulers. An executor is exactly 
		 * what it sounds like: It executes tasks. The contract does not mandate that this happen asynchronously; instead, that is 
		 * handled differently in different implementations. Schedulers are responsible for remembering when a task is supposed to 
		 * execute, and then executing it on time (using an executor).

				-- The java.util.concurrent.Executor interface defines an executor that can execute a simple Runnable.
				-- Spring extends this interface with org.springframework.core.task.TaskExecutor.
				-- Spring also provides the org.springframework.scheduling.TaskScheduler interface that specifies several methods 
				   for scheduling tasks to run one or more times at some point in the future.

		 * There are many implementations of both these Spring interfaces, and most of them implement both interfaces. The most 
		 * common of these is the org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler, which provides both an executor
		 * and a scheduler (backed by the executor) and a thread pool for executing tasks in an orderly and efficient manner. When 
		 * your application shuts down, this class makes sure that all the threads it created are shut down properly to prevent 
		 * memory leaks and other issues.
		 * 
		 * This class also implements the java.util.concurrent.ThreadFactory interface. Because of this, you can define a 
		 * ThreadPoolTaskScheduler bean, and it fulfills any dependencies you have on an Executor, TaskExecutor, TaskScheduler, or 
		 * ThreadFactory. This is about to come in handy because you need it to configure asynchronous and scheduled method 
		 * execution.
		 */
		log.info("Setting up thread pool task scheduler with 20 threads.");
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setPoolSize(20);
		scheduler.setThreadNamePrefix("task-");
		
		scheduler.setAwaitTerminationSeconds(1200);  //20 min
		scheduler.setWaitForTasksToCompleteOnShutdown(true);
		scheduler.setErrorHandler(t -> schedulingLogger.error("Unknown error occurred while executing task.", t));
		scheduler.setRejectedExecutionHandler((r, e) -> schedulingLogger.error("Execution of task {} was rejected for unknown reasons.", r));
		return scheduler;
	}

	/**
	 * The getAsyncExecutor() method (specified in AsyncConfigurer) tells Spring to use the same scheduler for asynchronous method
	 * execution
	 */
	@Override
	public Executor getAsyncExecutor() {
		Executor executor = this.taskScheduler();
		log.info("Configuring asynchronous method executor {}.", executor);
		return executor;
	}

	/**
	 * the configureTasks method (specified in SchedulingConfigurer) tells Spring to use the same scheduler for scheduled method 
	 * execution.
	 */
	@Override
	public void configureTasks(ScheduledTaskRegistrar registrar) {
		TaskScheduler scheduler = this.taskScheduler();
		log.info("Configuring scheduled method executor {}.", scheduler);
		registrar.setTaskScheduler(scheduler);
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}
	
	
}
