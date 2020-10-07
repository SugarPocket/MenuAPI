package ru.ragnok123.menuAPI.inventory.utils;

import cn.nukkit.inventory.ContainerInventory;
import cn.nukkit.inventory.Inventory;
import cn.nukkit.inventory.InventoryHolder;
import cn.nukkit.inventory.InventoryType;
import cn.nukkit.item.Item;
import cn.nukkit.math.Vector3;
import cn.nukkit.Player;
import ru.ragnok123.menuAPI.inventory.InventoryCategory;
import ru.ragnok123.menuAPI.inventory.InventoryMenu;
import ru.ragnok123.menuAPI.inventory.item.ItemData;

import lombok.Getter;

import java.util.Map;

public final class MenuInventory extends ContainerInventory {
	
	@Getter
	private Player owner = null;
	
	public MenuInventory(Player owner, Vector3 holder, InventoryMenu menu) {
		super(new Holder(holder.x, holder.y, holder.z), InventoryType.CHEST);
		this.owner = owner;
		InventoryCategory category = menu.getMainCategory();
		for(Map.Entry<Integer,ItemData> entry : category.itemDataMap().entrySet()) {
			int position = entry.getKey();
			ItemData data = entry.getValue();
			setItem(position, data.build());
		}
	}

	static final class Holder extends Vector3 implements InventoryHolder {

		private Holder(double x, double y, double z) {
			super(x, y, z);
		}

		@Override
		public Inventory getInventory() {
			return null;
		}
	}
}