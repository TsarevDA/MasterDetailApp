package ru.tsarev.masterdetailapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.tsarev.masterdetailapp.model.Position;
import ru.tsarev.masterdetailapp.model.Document;
import ru.tsarev.masterdetailapp.repository.PositionRepository;

@Service
@Transactional(readOnly = true)
public class PositionService {

	private final PositionRepository positionRepository;

	public PositionService(PositionRepository positionRepository) {
		this.positionRepository = positionRepository;
	}

	public List<Position> getAll() {
		return positionRepository.findAll();
	}
	
	public Position getById(int id) {
		Optional<Position> position = positionRepository.findById(id);
		return position.orElse(null);
	}
	
	public Position getByNumber(int number) {
		return positionRepository.findByNumber(number);
	}

	@Transactional
	public void create(Position position) {

		Position newPos = getByNameAndPriceAndOwner(position);
		if (newPos != null) {
			newPos.setAmount(newPos.getAmount() + position.getAmount());
			update(newPos);
		} else
			positionRepository.save(position);
	}

	@Transactional
	public void update( Position position) {
		positionRepository.save(position);
	}

	@Transactional
	public void deleteById(int id) {
		positionRepository.deleteById(id);
	}
	
	public Position getByNameAndPriceAndOwner(Position position) {
		return positionRepository.findByNameAndPriceAndOwner(position.getName(),position.getPrice(), position.getOwner());
	}
	
	public List<Position> getByOwner(Document specification) {
		return positionRepository.findByOwner(specification);
	}
}