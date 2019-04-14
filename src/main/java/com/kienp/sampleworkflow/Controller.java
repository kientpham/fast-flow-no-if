package com.kienp.sampleworkflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {

	@Autowired
	private TestRequest request;
	
	@RequestMapping("/")
	public String index() {
		// return new SampleRequest("Hello my workflow").processRequest();

//		SampleRequest<TransactionModel, OmnibusDTO> request = new SampleRequest();
		request.setRequestValue1("test 1");
		request.setRequestValue2("test 2");
		request.processRequest();
		return "Successfull";
	}
}
