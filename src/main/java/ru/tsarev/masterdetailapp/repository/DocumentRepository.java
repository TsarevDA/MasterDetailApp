package ru.tsarev.masterdetailapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.tsarev.masterdetailapp.model.Document;

@Repository
public interface DocumentRepository extends JpaRepository <Document, Integer> {
	
	Document findByNumber(int number);
}
