package com.softech.ls360.lms.repository.converters;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class LocalDateConverter implements AttributeConverter<LocalDate, Timestamp> {

	@Override
	public Timestamp convertToDatabaseColumn(LocalDate date) {
		
		if (date == null) {
			return null;
		}
		
		return Timestamp.valueOf(date.atStartOfDay());
		 
	}

	@Override
	public LocalDate convertToEntityAttribute(Timestamp timestamp) {
		return asLocalDate(timestamp);
	}
	
	
	/**
     * Creates {@link LocalDateTime} from {@code java.util.Date} or it's subclasses. Null-safe.
     */
    public static LocalDate asLocalDate(Timestamp timestamp) {
        if (timestamp == null) {
        	 return null;
        }
           
        return timestamp.toLocalDateTime().toLocalDate();
           
    }
	
}
