package com.project.bookbook.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.CustomerDTO;
import com.project.bookbook.mapper.CustomerMapper;
import com.project.bookbook.service.CustomerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerServiceProcess implements CustomerService{

	private final CustomerMapper customerMapper;

	@Override
	public void findCustomer(Model model) {
		List<CustomerDTO> list = customerMapper.findCustomer();
		model.addAttribute("list",list);
	}

	@Override
	public void findCustomerDetail(Model model, long userId) {
		CustomerDTO user = customerMapper.findCustomerDetail(userId);
		model.addAttribute("user",user);
		
		
	}




}
