package com.kshrd.wearekhmer;

import com.kshrd.wearekhmer.config.ThymeleafTemplateConfig;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
public class SpringBootEmailDemoApplication implements CommandLineRunner {


	private final ThymeleafTemplateConfig thymeleafTemplateConfig;

	public static void main(String[] args) {
		SpringApplication.run(SpringBootEmailDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		thymeleafTemplateConfig.getTemplateExtension();
		thymeleafTemplateConfig.getTemplateLocation();
	}
}
