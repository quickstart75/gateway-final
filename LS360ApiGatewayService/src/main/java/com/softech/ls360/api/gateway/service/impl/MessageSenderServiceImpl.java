package com.softech.ls360.api.gateway.service.impl;

import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.softech.ls360.api.gateway.service.MessageSenderService;
import com.softech.ls360.api.gateway.service.model.request.GlobalBatchImportParamSerialized;
import com.softech.ls360.lms.repository.entities.BatchimportFailure;
import com.softech.ls360.lms.repository.repositories.BatchimportFailureRepository;

@Service
public class MessageSenderServiceImpl implements MessageSenderService{
	 @Inject
	 JmsTemplate jmsTemplate;
	 
	 @Autowired
	 private BatchimportFailureRepository BIFRepository;
	    
	 public void sendMessage( GlobalBatchImportParamSerialized product) {
	 
	        jmsTemplate.send(new MessageCreator(){
	                @Override
	                public Message createMessage(Session session) throws JMSException{
	                	MapMessage message = session.createMapMessage();
	                	message.setString("customerEmail", product.getCustomerEmail());
	                	message.setString("customerUsername", product.getCustomerUsername());
	    				message.setString("file", product.getFilePath());
	    				message.setString("action", product.getAction());
	    				message.setString("websiteId", product.getWebsiteId());
	    				message.setString("storeId", product.getStoreId());
	    				message.setStringProperty("objectType", "batchInfo");
	                	return message;
	                }
	            });
	    }
	    
	   public  boolean saveBatchimportFailure(BatchimportFailure objBIF){
			BIFRepository.save(objBIF);
			return true;
		}

}
