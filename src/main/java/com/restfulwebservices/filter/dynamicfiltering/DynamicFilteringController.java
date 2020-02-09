package com.restfulwebservices.filter.dynamicfiltering;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class DynamicFilteringController {
	
	//field1 and field2
	@GetMapping("/filtering-d")
	public MappingJacksonValue retriveSomeBean(){
		
		
		
		SomeBean somebean	= new SomeBean("value1","value2","value3");
		
		SimpleBeanPropertyFilter filter= SimpleBeanPropertyFilter.filterOutAllExcept("field1","field2");
		FilterProvider filters= new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		MappingJacksonValue mapping=new MappingJacksonValue(somebean);
		mapping.setFilters(filters);
		
		return mapping;
		
	}
	
	@GetMapping("/filtering-dlist")
	public MappingJacksonValue retriveListSomeBean(){
		List<SomeBean> list	=  Arrays.asList (new SomeBean("value1","value2","value3"),
	              new SomeBean("value12","value22","value33"));
		
		SimpleBeanPropertyFilter filter= SimpleBeanPropertyFilter.filterOutAllExcept("field2","field3");
		FilterProvider filters= new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		MappingJacksonValue mapping=new MappingJacksonValue(list);
		mapping.setFilters(filters);
		
		return mapping;
		
	}


}
