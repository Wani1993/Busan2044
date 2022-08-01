package main;
public class Item {
	private int item_id;
	private int item_durability;
	private int item_count;
	
	
	
	public Item(int item_id) {
		super();
		this.item_id = item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	public Item(int item_id, int item_durability, int item_count) {
		super();
		this.item_id = item_id;
		this.item_durability = item_durability;
		this.item_count = item_count;
	}
	public Item() {}
	public void setItem_durability(int item_durability) {
		this.item_durability = item_durability;
	}
	public void setItem_count(int item_count) {
		this.item_count = item_count;
	}
	
	public int getItem_id() {
		return item_id;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		if (item_count != other.item_count)
			return false;
		if (item_durability != other.item_durability)
			return false;
		if (item_id != other.item_id)
			return false;
		return true;
	}
	public int getItem_durability() {
		return item_durability;
	}
	public int getItem_count() {
		return item_count;
	}
	@Override
	public String toString() {
		return "Item [item_id=" + item_id + ", item_durability=" + item_durability + ", item_count=" + item_count + "]";
	}


}
