package com.zerobase.customer.controller;

import com.zerobase.customer.service.StoreService;
import com.zerobase.customer.util.PageUtil;
import com.zerobase.domain.dto.common.StoreDto;
import com.zerobase.domain.model.common.StoreParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/store")
@RequiredArgsConstructor
public class StoreController {

	private final StoreService storeService;

	@GetMapping
	public String customer(){
		return "store/index";
	}

	/**
	 * 매장 목록 조회
	 */
	@GetMapping("/list")
	public String list(Model model, StoreParam parameter) {

		parameter.init();

		List<StoreDto> list = storeService.list(parameter);

		long totalCount = 0;
		if (list != null && list.size() > 0) {
			totalCount = list.get(0).getTotalCount();
		}

		String queryString = parameter.getQueryString();
		PageUtil pageUtil = new PageUtil(totalCount, parameter.getPageSize(), parameter.getPageIndex(), queryString);

		model.addAttribute("storeList", list);
		model.addAttribute("pager", pageUtil.pager()) ;

		return "store/list";
	}

	/**
	 * 매장 상세 내용 조회
	 */
	@GetMapping("/detail")
	public String detail(Model model, StoreParam parameter) {

		StoreDto store = storeService.detail(parameter.getId());
		model.addAttribute("store", store);

		return "store/detail";
	}


}
