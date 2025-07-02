package com.uv.taskManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TaskManagerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext applicationContext = SpringApplication.run(TaskManagerApplication.class, args);
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		System.out.println("active profile:="+environment.getActiveProfiles()[0]);
	}

	@Bean
	public PlatformTransactionManager get(MongoDatabaseFactory mongoDatabaseFactory)
	{
		return new MongoTransactionManager(mongoDatabaseFactory);
	}

}
