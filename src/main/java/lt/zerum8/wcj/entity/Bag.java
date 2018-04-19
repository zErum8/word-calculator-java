package lt.zerum8.wcj.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Bag {

	@Id
	@GeneratedValue
	private Long id;

	private String bagName;
	private char charFrom;
	private char charTill;

	public Bag(String bagName, char charFrom, char charTill) {
		this.bagName = bagName;
		this.charFrom = charFrom;
		this.charTill = charTill;
	}

	public Bag() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBagName() {
		return bagName;
	}

	public void setBagName(String bagName) {
		this.bagName = bagName;
	}

	public char getCharFrom() {
		return charFrom;
	}

	public void setCharFrom(char charFrom) {
		this.charFrom = charFrom;
	}

	public char getCharTill() {
		return charTill;
	}

	public void setCharTill(char charTill) {
		this.charTill = charTill;
	}

}
