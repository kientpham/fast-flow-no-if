package com.datatransforming.baseapp.services.model;

import java.io.File;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author Kien Pham
 *
 */
@Getter
@Setter
public class SampleBuilderDTO {

	private String inputValue;
	
	private File ddmfPackage;

	private List<File> listContentFile;

}
