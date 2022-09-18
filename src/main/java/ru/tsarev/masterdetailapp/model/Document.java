package ru.tsarev.masterdetailapp.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Document")
public class Document {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "number")
	private int number;
	@Column(name = "creation_date")
	@Temporal(value = TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date creationDate;
	@Column(name = "final_price")
	private int finalPrice;
	@Column(name = "note")
	private String note;

	@OneToMany(mappedBy = "owner")
	private List<Position> positions;

	public Document() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public int getFinalPrice() {
		calculateFinalPrice();
		return finalPrice;
	}

	public void setFinalPrice(int finalPrice) {
		this.finalPrice = finalPrice;
	}
	
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public List<Position> getPositions() {
		return positions;
	}

	public void setPositions(List<Position> positions) {
		this.positions = positions;
	}

	public void calculateFinalPrice() {
		int finalPrice = 0;
		for (Position position : positions) {
			finalPrice += position.getTotalPrice();
		}
		this.finalPrice = finalPrice;
	}

	@Override
	public String toString() {
		return "Document [id=" + id + ", number=" + number + ", creationDate=" + creationDate + ", finalPrice="
				+ finalPrice + ", note=" + note + ", positions=" + positions + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((creationDate == null) ? 0 : creationDate.hashCode());
		result = prime * result + finalPrice;
		result = prime * result + id;
		result = prime * result + ((note == null) ? 0 : note.hashCode());
		result = prime * result + number;
		result = prime * result + ((positions == null) ? 0 : positions.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Document other = (Document) obj;
		if (creationDate == null) {
			if (other.creationDate != null)
				return false;
		} else if (!creationDate.equals(other.creationDate))
			return false;
		if (finalPrice != other.finalPrice)
			return false;
		if (id != other.id)
			return false;
		if (note == null) {
			if (other.note != null)
				return false;
		} else if (!note.equals(other.note))
			return false;
		if (number != other.number)
			return false;
		if (positions == null) {
			if (other.positions != null)
				return false;
		} else if (!positions.equals(other.positions))
			return false;
		return true;
	}
	
	public static DocumentBuilder builder() {
		return new DocumentBuilder();
	}

	public static class DocumentBuilder {
		private int id;
		private int number;
		private Date creationDate;
		private List<Position> positions;
		private int finalPrice;
		private String note;

		public DocumentBuilder id(int id) {
			this.id = id;
			return this;
		}

		public DocumentBuilder number(int number) {
			this.number = number;
			return this;
		}
		
		public DocumentBuilder creationDate(Date creationDate) {
			this.creationDate = creationDate;
			return this;
		}

		public DocumentBuilder positions(List<Position> positions) {
			this.positions = positions;
			return this;
		}

		public DocumentBuilder finalPrice(int finalPrice) {
			this.finalPrice = finalPrice;
			return this;
		}
		
		public DocumentBuilder note(String note) {
			this.note = note;
			return this;
		}

		public Document build() {
			Document document = new Document();
			document.setId(id);
			document.setNumber(number);
			document.setCreationDate(creationDate);
			document.setPositions(positions);
			document.setFinalPrice(finalPrice);
			document.setNote(note);
			return document;
		}
	}
}