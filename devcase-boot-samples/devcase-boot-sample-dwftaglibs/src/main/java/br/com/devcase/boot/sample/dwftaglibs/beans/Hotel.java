package br.com.devcase.boot.sample.dwftaglibs.beans;

import java.util.Date;

import org.javamoney.moneta.Money;

public class Hotel {

	private String id;
	private String name;
	private int rating;
	private Money price;
	private Date foundationDate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public Money getPrice() {
		return price;
	}
	public void setPrice(Money price) {
		this.price = price;
	}
	public Date getFoundationDate() {
		return foundationDate;
	}
	public void setFoundationDate(Date foundationDate) {
		this.foundationDate = foundationDate;
	}
	
	
}
