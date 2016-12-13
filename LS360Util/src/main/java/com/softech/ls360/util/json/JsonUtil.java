package com.softech.ls360.util.json;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.xml.bind.JAXBException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonUtil {

	public static String convertObjectToJson(Object obj) {
		
		String jsonString = null;
		
		DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		mapper.setDateFormat(dateFormatter);
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
    	try {
    		jsonString = mapper.writeValueAsString(obj);
    	} catch (JsonGenerationException e) {
    		e.printStackTrace();
    	} catch (JsonMappingException e) {
    		e.printStackTrace();
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
		return jsonString;
	}
	
	public static <T> T convertJsonToObject(String jsonString, Class<T> type) throws JAXBException, JsonParseException, JsonMappingException, 
		IOException {
		
		ObjectMapper objectMapper = new ObjectMapper();
		T obj = objectMapper.readValue(jsonString, type);
		return obj;
	}
	
}
