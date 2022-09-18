package ru.tsarev.masterdetailapp.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ru.tsarev.masterdetailapp.exceptions.AlreadyExistException;
import ru.tsarev.masterdetailapp.model.Position;
import ru.tsarev.masterdetailapp.model.Document;
import ru.tsarev.masterdetailapp.service.PositionService;
import ru.tsarev.masterdetailapp.service.DocumentService;

@Controller
public class DocumentController {

	private DocumentService documentService;
	private PositionService positionService;
	private static final Logger log = LogManager.getLogger();

	public DocumentController(DocumentService specificationService, PositionService positionService) {
		this.documentService = specificationService;
		this.positionService = positionService;
	}

	@GetMapping("/documents/delete")
	public String deleteById(@RequestParam int id) {
		List<Position> positions = positionService.getByOwner(documentService.getById(id));
		positions.stream().forEach(p -> positionService.deleteById(p.getId()));
		documentService.deleteById(id);
		return "redirect:/";
	}

	@GetMapping("/documents")
	public String getAllWithSelectedId(@RequestParam int id, Model model,
			@PageableDefault(value = 5) Pageable pageable) {
		Page<Document> specifications = documentService.getAll(pageable);
		List<Position> positions = positionService.getByOwner(documentService.getById(id));
		model.addAttribute("selectedId", id);
		model.addAttribute("documents", specifications);
		model.addAttribute("positions", positions);
		model.addAttribute("document", Document.builder().build());
		model.addAttribute("position", Position.builder().build());
		return "index";
	}

	@PostMapping("/documents/create")
	public String createSpecification(@ModelAttribute Document document) {
		if (documentService.getByNumber(document.getNumber()) == null) {
			documentService.create(document);
		}
		else
			throw new AlreadyExistException(
					"Документ с номером: " + document.getNumber() + " уже существует");
		return "redirect:/";
	}

	@PostMapping("/documents/update")
	public String updateSpecification(@ModelAttribute Document document) {
		Document oldDocument = documentService.getByNumber(document.getNumber());
		if (oldDocument == null)
			documentService.update(document);
		else if (oldDocument.getId() == document.getId()) documentService.update(document);
		else
			throw new AlreadyExistException(
					"Документ с номером: " + document.getNumber() + " уже существует");
		return "redirect:/";
	}

	@ExceptionHandler
	private String handleException(AlreadyExistException exception, final RedirectAttributes redirectAttributes) {
		log.warn(exception.getMessage(), exception);
		redirectAttributes.addAttribute("exceptionMessage", exception.getMessage());
		return "redirect:/#error";
	}
	
	@ExceptionHandler
	private String handleException(BindException exception, final RedirectAttributes redirectAttributes) {
		if (exception.hasFieldErrors("creationDate")) {
		log.warn("Дата должна быть в формате гггг-мм-дд", exception);
		redirectAttributes.addAttribute("exceptionMessage", "Дата должна быть в формате гггг-мм-дд");
		} else if (exception.hasFieldErrors("number")) {
		log.warn("Установите значение номера в диапазоне между 0 и " + Integer.MAX_VALUE, exception);
		redirectAttributes.addAttribute("exceptionMessage", "Установите значение номера в диапазоне между 0 и " + Integer.MAX_VALUE);	
		}
		return "redirect:/#error";
	}	
}
