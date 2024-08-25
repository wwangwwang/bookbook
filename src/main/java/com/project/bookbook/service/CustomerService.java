package com.project.bookbook.service;

import org.springframework.ui.Model;

public interface CustomerService {

	void findCustomer(Model model);

	void findCustomerDetail(Model model, long userId);
	
}
