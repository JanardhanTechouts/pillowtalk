package com.techouts.service;



import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import com.techouts.domain.Product;

@Configuration
@EnableAsync
@EnableScheduling
public class CsvToPojoConversion {

	static Logger logger=Logger.getLogger(CsvToPojoConversion.class);
	
@Async
//@Scheduled(cron="*/5 * * * * *")
private static void parseCSVToBeanList() throws IOException {
		
		HeaderColumnNameTranslateMappingStrategy<Product> beanStrategy = new HeaderColumnNameTranslateMappingStrategy<Product>();
		beanStrategy.setType(Product.class);
		
		Map<String, String> columnMapping = new HashMap<String, String>();
		columnMapping.put("product_id", "product_id");
		columnMapping.put("product_name", "product_name");
		columnMapping.put("stock", "stock");
	    
		beanStrategy.setColumnMapping(columnMapping);
		CsvToBean<Product> csvToBean = new CsvToBean<Product>();
		CSVReader reader = new CSVReader(new FileReader("D:/product.csv"));
		List<Product> products = csvToBean.parse(beanStrategy, reader);
		logger.info(products);
		
	}

}