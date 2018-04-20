package com.softech.ls360.api.gateway.service;

import com.softech.ls360.api.gateway.service.model.request.GlobalBatchImportParamSerialized;
import com.softech.ls360.lms.repository.entities.BatchimportFailure;

public interface MessageSenderService {
	void sendMessage( GlobalBatchImportParamSerialized product);
	boolean saveBatchimportFailure(BatchimportFailure objBIF);
}
