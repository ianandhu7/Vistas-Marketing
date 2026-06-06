package co.vistafoundation.vlearning;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import co.vistafoundation.vlearning.utils.SMSHorizonService;

/**
 * @author vk
 *
 */
@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
@EnableCaching
public class VLearningApplication extends SpringBootServletInitializer {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("IST"));
	}

	public static void main(String[] args) {
		SpringApplication.run(VLearningApplication.class);
	}

	/**
	 * updated by @author NaveenKumar
	 * 
	 */
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(VLearningApplication.class);
	}
	
	@Autowired
    private ApplicationContext applicationContext;

	 public void run(String... args) throws Exception {
	        System.out.println("Beans in the application context:");

	        String[] beanNames = applicationContext.getBeanDefinitionNames();
	        for (String beanName : beanNames) {
	            System.out.println(beanName);
	        }

	        CaffeineCacheManager cacheManager = (CaffeineCacheManager) applicationContext.getBean("caffeineCacheManager");
	        System.out.println("Cache Manager: " + cacheManager);
	    }
}
