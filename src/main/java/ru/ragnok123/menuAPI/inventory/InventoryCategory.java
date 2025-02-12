package ru.ragnok123.menuAPI.inventory;

import java.util.HashMap;

import cn.nukkit.item.Item;
import lombok.NonNull;
import ru.ragnok123.menuAPI.inventory.item.ItemClick;
import ru.ragnok123.menuAPI.inventory.item.ItemData;

public class InventoryCategory {
	
	public InventoryMenu menu = null;
	
	/*							Y(row)
	 *   	x x x x x x x x x 	0
	 *   	x x x x x x x x x	1
	 *   	x x x x x x x x x	2
	 *   	x x x x x x x x x	3
	 *   	x x x x x x x x x	4
	 *   	x x x x x x x x x	5
	 *   
	 *   X: 0 1 2 3 4 5 6 7 8 
	 */
	
	public String[] inventoryGui;
	
	private HashMap<Integer, ItemData> itemData = new HashMap<Integer,ItemData>();
	private HashMap<Integer, ItemClick> itemClick = new HashMap<Integer,ItemClick>();
	
	public HashMap<Integer,ItemData> itemDataMap(){
		return this.itemData;
	}
	
	public InventoryMenu getMenu() {
		return this.menu;
	}
	
	public void setStringElements(String[] elements, HashMap<Character, ItemData> itemDatas) {
		setStringElements(elements, itemDatas, null);
	}
	
	private int convertLineToSlot(int line, int rowSlot) {
		return (9 * line) + rowSlot;
	}
	
	public void setStringElements(String[] elements, HashMap<Character, ItemData> itemDatas, HashMap<Character, ItemClick> itemClicks) {
		if(elements.length == 3 || elements.length == 6) {
			this.inventoryGui = elements;
			for(int line = 0; line < elements.length; line++) {
				String row = elements[line];
				for(int rowSize = 0; rowSize < row.length(); rowSize++) {
					char c = row.charAt(rowSize);
					if(c != ' ' && !itemDatas.containsKey(c)) {
						throw new RuntimeException("Element with symbol " + c + " not registered");
					}
					int slot = convertLineToSlot(line, rowSize);
					if(c == ' ') {
						continue;
					}
					if(itemClicks != null) {
						if(itemClicks.containsKey(c)) {
							addElement(slot, itemDatas.get(c), itemClicks.get(c));
						} else {
							addElement(slot, itemDatas.get(c));
						}
					} else {
						addElement(slot, itemDatas.get(c));
					}
				}
			}
		}
	}
	
	public void addElement(int position, @NonNull ItemData item) {
		addElement(position,item,null);
	}
	
	public void addElement(int position, @NonNull ItemData item, ItemClick click) {
		if(!itemData.containsKey(position)) {
			itemData.put(position, item);
			if(click != null) {
				itemClick.put(position, click);
			}
		}
	}
	
	public ItemData getItemData(int position) {
		if(this.itemData.containsKey(position)) {
			return this.itemData.get(position);
		}
		return null;
	}
	
	public ItemClick getItemClick(int position) {
		if(this.itemClick.containsKey(position)) {
			return this.itemClick.get(position);
		}
		return null;
	}
	
}
