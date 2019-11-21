package com.diviso.graeshoppe.client.product.model;

import java.util.List;

public class StockEntryBundle {

	private StockEntry stockEntry;

	private List<EntryLineItem> entryLineItems;

	private Reason reason;

	private Location location;

	public StockEntry getStockEntry() {
		return stockEntry;
	}

	public void setStockEntry(StockEntry stockEntry) {
		this.stockEntry = stockEntry;
	}

	public List<EntryLineItem> getEntryLineItems() {
		return entryLineItems;
	}

	public void setEntryLineItems(List<EntryLineItem> entryLineItems) {
		this.entryLineItems = entryLineItems;
	}

	public Reason getReason() {
		return reason;
	}

	public void setReason(Reason reason) {
		this.reason = reason;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
}
