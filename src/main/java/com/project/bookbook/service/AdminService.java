package com.project.bookbook.service;

import org.springframework.ui.Model;

public interface AdminService {

	void find(Model model);

	void findReviews(Model model);

	void deleteReview(Long reviewNum);

	void findOrder(Model model);

	void findinquiryUpdate(long qnaNum);

}
