package com.project.bookbook.service;

import org.springframework.ui.Model;

public interface InventoryService {
	
	void findBook(Model model);

	void deleteProcess(long bookNum);


}
