package com.customerService.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.customerService.entity.CSTracker;
import com.customerService.entity.CSusers;
import com.customerService.model.CSHistory;
import com.customerService.model.CustomerTrackRequest;
import com.customerService.model.UserMessage;
import com.customerService.service.ICSSystemService;

@RestController
@Validated
@RequestMapping(value = "/v1/cs")
public class CustomerServiceController {
	
	Logger logger = LoggerFactory.getLogger(CustomerServiceController.class);
	
	@Autowired
	private ICSSystemService csService;
	
	@RequestMapping(method = RequestMethod.GET, path = "/login")
	public CSusers login(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord) {
		CSusers userDetails = csService.login(userName, passWord);
		return userDetails;
    }
	
	@RequestMapping(method = RequestMethod.GET, path = "/csDetails")
	public List<CSTracker> csDetails(@RequestParam(name="status", required=false) String status, 
			@RequestParam(name="reference_number", required=false) String reference_number,
			@RequestParam(name="brokerName", required=true) String brokerName
			) {
		List<CSTracker> csTrack = csService.csDetails(status, reference_number, brokerName);
		return csTrack;
    }
	
	@RequestMapping( method = RequestMethod.POST, path = "/csStatus-update")
    public UserMessage csStatusUpdate( @RequestBody List<CustomerTrackRequest> orderDetailList){
		UserMessage updatedMsg = csService.csStatusUpdate(orderDetailList);
		return updatedMsg;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/csDetailsHistory")
	public List<CSHistory> csDetailsHist(@RequestParam("reference_number") String reference_number) {
		List<CSHistory> csTrackHist = csService.csDetailsHist(reference_number);
		return csTrackHist;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/csDetailsStatus")
	public List<CSHistory> csDetailsStatusList(@RequestParam("status") String status, 
			@RequestParam(name="reference_number", required=false) String reference_number,
			@RequestParam(name="brokerName", required=true) String brokerName) {
		List<CSHistory> csTrackHist = csService.csDetailsStatusList(status, reference_number, brokerName);
		return csTrackHist;
	}

}
