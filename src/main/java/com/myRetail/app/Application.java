package com.myRetail.app;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.data.mongodb.core.convert.DbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myRetail.model.Product;
import com.myRetail.repository.PriceMongoRepository;

/**
 * @author Nitin Patidar
 *
 */

@ComponentScan(basePackages = "com.myRetail")
@EnableMongoRepositories(value= {"com.myRetail.repository"})
@SpringBootApplication
@EnableAutoConfiguration
public class Application {

	@Autowired
	PriceMongoRepository priceMongoRepository;

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}

	public SessionLocaleResolver localeResolver() {
		SessionLocaleResolver localResolver = new SessionLocaleResolver();
		localResolver.setDefaultLocale(Locale.US); 
		return localResolver;
	}

	@Bean(name = "messageSource")
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageBundle = new ReloadableResourceBundleMessageSource();
		messageBundle.setBasename("classpath:i18n/messages");
		return messageBundle;
	}

	@Bean
	public MappingMongoConverter mappingMongoConverter(MongoDbFactory factory, MongoMappingContext context, BeanFactory beanFactory) {
		DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
		MappingMongoConverter mappingConverter = new MappingMongoConverter(dbRefResolver, context);
		mappingConverter.setCustomConversions(beanFactory.getBean(CustomConversions.class));
		mappingConverter.setTypeMapper(new DefaultMongoTypeMapper(null));

		return mappingConverter;
	}

	@PostConstruct
	public void loadInitialData() {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<List<Product>> mapType = new TypeReference<List<Product>>() {};
		InputStream is = TypeReference.class.getResourceAsStream("/price.json");
		try {
			List<Product> products = mapper.readValue(is, mapType);

			if(products != null) {
				for(Product product : products) {

					if(product.getId()!=null && !priceMongoRepository.exists(product.getId())) {
						if(product.getPrice() != null) {
							product.getPrice().setId(product.getId());
							priceMongoRepository.save(product.getPrice());
						}
					}
				}
			}


		} catch (IOException e) {
			System.out.println(e.getMessage());
		}	

	}

}
