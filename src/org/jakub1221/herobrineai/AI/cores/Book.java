package org.jakub1221.herobrineai.AI.cores;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.jakub1221.herobrineai.HerobrineAI;
import org.jakub1221.herobrineai.AI.Core;
import org.jakub1221.herobrineai.AI.CoreResult;

public class Book extends Core {

	public Book() {
		super(CoreType.BOOK, AppearType.NORMAL);
	}

	@Override
	public CoreResult CallCore(final Object[] data) {
		final Player player = (Player) data[0];
		if (HerobrineAI.getPluginCore().getConfigDB().useWorlds.contains(player.getLocation().getWorld().getName())) {
			if (!HerobrineAI.getPluginCore().getConfigDB().WriteBooks || !HerobrineAI.getPluginCore().getSupport().checkBooks(player.getLocation())) {
				return new CoreResult(false, "Player is not in allowed world!");
			}
			final int chance = new Random().nextInt(100);
			if (chance <= (100 - HerobrineAI.getPluginCore().getConfigDB().BookChance)) {
				return new CoreResult(false, "Books are not allowed!");
			}
			final Inventory chest = (Inventory) data[1];
			if (chest.firstEmpty() == -1) {
				return new CoreResult(false, "Book create failed!");
			}
			if (HerobrineAI.getPluginCore().getAICore().getResetLimits().isBook()) {
				chest.setItem(chest.firstEmpty(), newBook());
				return new CoreResult(true, "Book created!");
			}
		}
		return new CoreResult(false, "Book create failed!");
	}

	public ItemStack newBook() {
		final int count = HerobrineAI.getPluginCore().getConfigDB().useBookMessages.size();
		final int chance = new Random().nextInt(count);
		final ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
		final BookMeta meta = (BookMeta) book.getItemMeta();
		final ArrayList<String> list = new ArrayList<String>();
		meta.setTitle("");
		meta.setAuthor("");
		list.add(0, HerobrineAI.getPluginCore().getConfigDB().useBookMessages.get(chance));
		meta.setPages(list);
		book.setItemMeta(meta);
		return book;
	}

}