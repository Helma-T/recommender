package cn.hesheng.recommender;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@EnableAutoConfiguration
//@ComponentScan({ "cn.hesheng.recommender" })
public class RecommenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecommenderApplication.class, args);
	}
}
