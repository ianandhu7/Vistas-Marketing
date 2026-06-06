/**
 * 
 */
package co.vistafoundation.vlearning;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author vk
 * 
 * update by 
 * 
 * @author Naveen Kumar A
 *
 */
@Configuration
@EnableAsync
public class AsyncConfiguration {
	
	@Value("${thread.car.pool.size}")
	private int corePoolSize;
	
	@Value("${thread.max.pool.size}")
	private int maxPoolSize;
	
	@Value("${thread.queue.capacity}")
	private int queueCapacity;
	
	
	 private static final Logger logger = LoggerFactory.getLogger(AsyncConfiguration.class);

	 @Bean(name = "asyncExecutor")
		public ThreadPoolTaskExecutor asyncExecutor() {

			ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
			executor.setCorePoolSize(corePoolSize); // Set the core pool size as desired
			executor.setMaxPoolSize(maxPoolSize); // Set the maximum pool size as desired
			executor.setQueueCapacity(queueCapacity); // Set the queue capacity as desired
			executor.setThreadNamePrefix("Async-Thread-Pool-1"); // Optionally, provide a prefix for the thread names
			executor.initialize();
			logger.info("Async Executor Intalized  with core pool : " + corePoolSize + ", max pool size :" + maxPoolSize
					+ ", queue capacity : " + queueCapacity + ".");
			return executor;
		}
}
