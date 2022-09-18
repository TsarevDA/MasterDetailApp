package ru.tsarev.masterdetailapp.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import ru.tsarev.masterdetailapp.model.Position;
import ru.tsarev.masterdetailapp.model.Document;
import ru.tsarev.masterdetailapp.service.DocumentService;

@Controller
public class MasterDetailController {

	private DocumentService documentService;

	public MasterDetailController(DocumentService documentService) {
		this.documentService = documentService;
	}
	
	@GetMapping("/")
	public String getAll(Model model, @PageableDefault(value = 5) Pageable pageable, @ModelAttribute("exceptionMessage") final String exceptionMessage) {
		Page<Document> documents = documentService.getAll(pageable);
		model.addAttribute("exceptionMessage",exceptionMessage);
		model.addAttribute("documents", documents);
		model.addAttribute("document", Document.builder().build());
		model.addAttribute("position", Position.builder().build());
		return "index";
	}
}
