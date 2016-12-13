package com.softech.ls360.lms.repository.test.repositories;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import com.softech.ls360.lms.repository.entities.CreditReportingField;
import com.softech.ls360.lms.repository.entities.RegulatorCategory;
import com.softech.ls360.lms.repository.entities.RegulatoryApproval;
import com.softech.ls360.lms.repository.repositories.RegulatoryApprovalRepository;
import com.softech.ls360.lms.repository.test.LmsRepositoryAbstractTest;

public class RegulatoryApprovalRepositoryTest extends LmsRepositoryAbstractTest {

	private static final Logger logger = LogManager.getLogger();
	
	@Inject
	private RegulatoryApprovalRepository regulatoryApprovalRepository;
	
	//@Test
	public void test1() {
		
	}
	
	@Test
	public void findCreditReportingFields() {
		
		logger.info("findCreditReportingFields");
		
		try {
			
			List<Long> courseApprovalIds = Arrays.asList(2802L, 5609L, 6052L, 9052L, 13655L, 16877L, 30652L, 31452L, 35702L, 35703L, 35752L, 43453L);
			List<RegulatoryApproval> regulatoryApprovals = regulatoryApprovalRepository.findDistinctByIdInAndDeletedFalse(courseApprovalIds);
			if (!CollectionUtils.isEmpty(regulatoryApprovals)) {
				Set<CreditReportingField> creditReportingFields = regulatoryApprovals
					.stream()
					.map(RegulatoryApproval::getRegulatorCategory)
					.filter(p -> p!= null)
					.map(RegulatorCategory::getCreditReportingFields)
					.filter(p -> !CollectionUtils.isEmpty(p))
					.flatMap(p -> p.
						stream()
						.filter(creditReportingField -> creditReportingField != null)
					)
					.collect(Collectors.toSet());
				logger.info("");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
