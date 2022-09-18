package ru.tsarev.masterdetailapp.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import ru.tsarev.masterdetailapp.exceptions.NegativeAmountException;
import ru.tsarev.masterdetailapp.exceptions.NullOwnerException;
import ru.tsarev.masterdetailapp.exceptions.PositionException;
import ru.tsarev.masterdetailapp.model.Position;
import ru.tsarev.masterdetailapp.service.PositionService;
import ru.tsarev.masterdetailapp.service.DocumentService;

@Controller
public class PositionController {

	private final PositionService positionService;
	private final DocumentService specificationService;
	private static final Logger log = LogManager.getLogger();
	
	public PositionController(PositionService positionService, DocumentService specificationService) {
		this.positionService = positionService;
		this.specificationService = specificationService;
	}

	@GetMapping("/positions/add")
	public String addPosition(@RequestParam int itemId, Model model) {
		Position position = Position.builder().amount(1).build();
		positionService.create(position);
		return "redirect:/positions";
	}

	@GetMapping("/positions/delete")
	public String deleteById(@RequestParam int id) {
		positionService.deleteById(id);
		return "redirect:/";
	}

	@PostMapping("/positions/create")
	public String createGroup(@ModelAttribute Position position) {
		if (position.getAmount() < 0)
			throw new NegativeAmountException("Установлено отрицательное значение количества");
		if (position.getPrice() < 0)
			throw new NegativeAmountException("Установлено отрицательное значение цены");
		if (position.getOwner() == null)
			throw new NullOwnerException("Должен быть выбран документ, которому принадлежит позиция");
		if (positionService.getByNumber(position.getNumber()) == null) {
			position.setOwner(specificationService.getById(position.getOwner().getId()));
			positionService.create(position);
		}
		else
			throw new AlreadyExistException(
					"Позиция с номером: " + position.getNumber() + " уже существует");
		return "redirect:/";
	}

	@PostMapping("/positions/update")
	public String updateSpecification(@ModelAttribute Position position) {
		Position oldPosition = positionService.getByNumber(position.getNumber());
		if (position.getAmount() < 0)
			throw new NegativeAmountException("Установлено отрицательное значение количества");
		if (position.getPrice() < 0)
			throw new NegativeAmountException("Установлено отрицательное значение цены");
		if (position.getOwner() == null)
			throw new NullOwnerException("Должен быть выбран документ, которому принадлежит позиция");
		
		if (oldPosition == null)
		{
			position.setOwner(specificationService.getById(position.getOwner().getId()));
			positionService.update(position);
		}
		else if (oldPosition.getId() == position.getId()) {
			position.setOwner(specificationService.getById(position.getOwner().getId()));
			positionService.update
			(position);
		}
		
		else
			throw new AlreadyExistException(
					"Позиция с номером: " + position.getNumber() + " уже существует");
		return "redirect:/";
	}
		
	@ExceptionHandler
	private String handleException(PositionException exception, final RedirectAttributes redirectAttributes) {
		log.warn(exception.getMessage(), exception);
		redirectAttributes.addAttribute("exceptionMessage", exception.getMessage());
		return "redirect:/#error";
	}
	
	@ExceptionHandler
	private String handleException(AlreadyExistException exception, final RedirectAttributes redirectAttributes) {
		log.warn(exception.getMessage(), exception);
		redirectAttributes.addAttribute("exceptionMessage", exception.getMessage());
		return "redirect:/#error";
	}

	@ExceptionHandler
	private String handleException(BindException exception, final RedirectAttributes redirectAttributes) {
		if (exception.hasFieldErrors("number")) {
			log.warn("Установите значение номера в диапазоне между 0 и " + Integer.MAX_VALUE, exception);
			redirectAttributes.addAttribute("exceptionMessage", "Установите значение номера в диапазоне между 0 и " + Integer.MAX_VALUE);	
		} else if (exception.hasFieldErrors("price")) {
			log.warn("Установите значение цены в диапазоне между 0 и " + Integer.MAX_VALUE, exception);
			redirectAttributes.addAttribute("exceptionMessage", "Установите значение цены в диапазоне между 0 и " + Integer.MAX_VALUE);	
		} else if (exception.hasFieldErrors("amount")) {
			log.warn("Установите значение количества в диапазоне между 0 и " + Integer.MAX_VALUE, exception);
			redirectAttributes.addAttribute("exceptionMessage", "Установите значение количества в диапазоне между 0 и " + Integer.MAX_VALUE);	
		}
		return "redirect:/#error";
	}
}
