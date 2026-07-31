package net.okil.voxelizedfurniture.item;

import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;

public class JamBottleItem extends Item {
	public JamBottleItem(Item.Properties properties) {
		super(properties.food((new FoodProperties.Builder()).nutrition(4).saturationModifier(0.3f).alwaysEdible().build(), Consumables.defaultDrink().consumeSeconds(0F).build()).usingConvertsTo(Items.GLASS_BOTTLE));
	}
}