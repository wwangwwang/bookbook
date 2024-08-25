package com.project.bookbook.service;

import java.io.InputStream;

public interface VisionService {
	
	String extractTextFromImage(InputStream imageStream) throws Exception;

}
