package ru.tsarev.masterdetailapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.tsarev.masterdetailapp.model.Document;
import ru.tsarev.masterdetailapp.repository.DocumentRepository;

@Service
@Transactional(readOnly = true)
public class DocumentService {

	private final DocumentRepository documentRepository;

	public DocumentService(DocumentRepository specificationRepository) {
		this.documentRepository = specificationRepository;
	}

	public List<Document> getAll() {
		return documentRepository.findAll();
	}

	public Page<Document> getAll(Pageable pageable) {
		int page = pageable.getPageNumber();
		int itemPerPage = pageable.getPageSize();
		return documentRepository.findAll(PageRequest.of(page, itemPerPage));
	}

	public Document getById(int id) {
		Optional<Document> specification = documentRepository.findById(id);
		return specification.orElse(null);
	}
	
	public Document getByNumber(int number) {
		return documentRepository.findByNumber(number);
	}

	@Transactional
	public void create(Document specification) {
		documentRepository.save(specification);
	}

	@Transactional
	public void update(Document specification) {
		documentRepository.save(specification);
	}

	@Transactional
	public void deleteById(int id) {
		documentRepository.deleteById(id);
	}
}
