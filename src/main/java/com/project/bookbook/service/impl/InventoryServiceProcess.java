package com.project.bookbook.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.project.bookbook.domain.dto.InventoryDTO;
import com.project.bookbook.mapper.InventoryMapper;
import com.project.bookbook.service.InventoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InventoryServiceProcess implements InventoryService {

	private final InventoryMapper inventoryMapper;

	@Override
	public void findBook(Model model) {
		List<InventoryDTO> list =inventoryMapper.findBook();
		model.addAttribute("list",list);
		
	}

	@Override
	public void deleteProcess(long bookNum) {
		inventoryMapper.deleteBook(bookNum);
		
	}

	

}
