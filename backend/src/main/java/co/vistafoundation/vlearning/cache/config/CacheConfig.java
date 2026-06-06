package co.vistafoundation.vlearning.cache.config;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;





/**
 * @author NaveenKumar
 *
 */
//@Configuration
//@EnableCaching
public class CacheConfig {

//	   @Bean
//	   public CaffeineCacheManager caffeineCacheManager() {
//	       CaffeineCacheManager cacheManager = new CaffeineCacheManager();
//	       cacheManager.setCaffeine(cacheBuilder());
//	       cacheManager.setCacheNames( Arrays.asList("userCache", "studentSubscriptionCache",
//	    		   "productCache","studentCache","classStandardCache","syllabusCache","stateCache","subjectCache"));
//	       return cacheManager;
//	   }
//
//	   @Bean
//	   public Caffeine<Object, Object> cacheBuilder() {
//	       return Caffeine.newBuilder()
//              .maximumSize(500) // Adjust the maximum size as needed
//	           .expireAfterWrite(10, TimeUnit.MINUTES); // Set the cache expiration time
//	   }
	   
	
	
	
}
