package ru.tsarev.masterdetailapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.tsarev.masterdetailapp.model.Position;
import ru.tsarev.masterdetailapp.model.Document;

@Repository
public interface PositionRepository extends JpaRepository <Position, Integer> {
	
	Position findByNameAndPriceAndOwner(String name, int price, Document owner);
	List<Position> findByOwner(Document owner);
	Position findByNumber(int number);
	
}