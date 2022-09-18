package ru.tsarev.masterdetailapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "Position")
public class Position {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "number")
	private int number;
	@Column(name = "name")
	private String name;
	@Column(name = "price")
	private int price;

	@ManyToOne
	@JoinColumn(name = "document_id", referencedColumnName = "id")
	private Document owner;

	@Column(name = "amount")
	private int amount;

	private Position() {
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public Document getOwner() {
		return owner;
	}

	public void setOwner(Document owner) {
		this.owner = owner;
	}

	public int getTotalPrice() {
		return price * amount;
	}

	@Override
	public String toString() {
		return "Position [id=" + id + ", number=" + number + ", name=" + name + ", price=" + price + ", owner=" + owner
				+ ", amount=" + amount + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + number;
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + price;
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
		Position other = (Position) obj;
		if (amount != other.amount)
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (number != other.number)
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (price != other.price)
			return false;
		return true;
	}

	public static PositionBuilder builder() {
		return new PositionBuilder();
	}

	public static class PositionBuilder {

		private int id;
		private int number;
		private String name;	
		private int price;
		private int amount;
		private Document owner;

		public PositionBuilder id(int id) {
			this.id = id;
			return this;
		}
		
		public PositionBuilder number(int number) {
			this.number = number;
			return this;
		}

		public PositionBuilder name(String name) {
			this.name = name;
			return this;
		}

		public PositionBuilder price(int price) {
			this.price = price;
			return this;
		}

		public PositionBuilder owner(Document owner) {
			this.owner = owner;
			return this;
		}

		public PositionBuilder amount(int amount) {
			this.amount = amount;
			return this;
		}
		
		public Position build() {
			Position position = new Position();
			position.setId(id);
			position.setNumber(number);
			position.setName(name);
			position.setPrice(price);
			position.setAmount(amount);
			position.setOwner(owner);
			return position;
		}
	}
}
