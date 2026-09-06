package net.okil.voxelizedfurniture.world.inventory;

import net.okil.voxelizedfurniture.init.VoxelizedFurnitureModMenus;

import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.Container;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.BlockPos;

import java.util.function.Supplier;
import java.util.Map;
import java.util.HashMap;
import java.util.Collections;

public class FridgeGuiMenu extends AbstractContainerMenu implements VoxelizedFurnitureModMenus.MenuAccessor {
	public final Map<String, Object> menuState = new HashMap<>() {
		@Override
		public Object put(String key, Object value) {
			if (!this.containsKey(key) && this.size() >= 40)
				return null;
			return super.put(key, value);
		}
	};
	public final Level world;
	public final Player entity;
	public int x, y, z;
	private ContainerLevelAccess access = ContainerLevelAccess.NULL;
	private final Container inventory;
	private final Map<Integer, Slot> customSlots = new HashMap<>();
	private boolean bound = false;
	private Supplier<Boolean> boundItemMatcher = null;
	private Entity boundEntity = null;
	private BlockEntity boundBlockEntity = null;
	private ItemStack boundItem = null;

	public FridgeGuiMenu(int id, Inventory inv) {
		this(id, inv, new SimpleContainer(39));
		this.x = (int) inv.player.getX();
		this.y = (int) inv.player.getY();
		this.z = (int) inv.player.getZ();
		access = ContainerLevelAccess.create(inv.player.level(), new BlockPos(x, y, z));
	}

	public FridgeGuiMenu(int id, Inventory inv, FriendlyByteBuf extraData) {
		this(id, inv, resolveBlockContainer(inv, extraData), extraData);
	}

	private static Container resolveBlockContainer(Inventory inv, FriendlyByteBuf extraData) {
		// The MenuType factory uses this constructor on both sides. Keep a temporary
		// mirror on the client (slot sync populates it), but on the dedicated/integrated
		// server bind procedure-opened GUIs to the real block inventory at the supplied
		// position. This makes "Open GUI at x/y/z" persist items just like a GUI
		// opened directly by its bound block entity.
		if (extraData != null && !inv.player.level().isClientSide()) {
			extraData.markReaderIndex();
			try {
				BlockPos pos = extraData.readBlockPos();
				// Item/entity-bound internal opens append marker data after BlockPos. Only
				// treat a buffer containing exactly the position as a block-bound open.
				if (extraData.readableBytes() == 0) {
					BlockEntity blockEntity = inv.player.level().getBlockEntity(pos);
					if (blockEntity instanceof Container blockContainer && blockContainer.getContainerSize() >= 39) {
						return blockContainer;
					}
				}
			} catch (IndexOutOfBoundsException ignored) {
				// Malformed/short extra data: fall back to a temporary GUI inventory.
			} finally {
				extraData.resetReaderIndex();
			}
		}
		return new SimpleContainer(39);
	}

	public FridgeGuiMenu(int id, Inventory inv, Container container, FriendlyByteBuf extraData) {
		this(id, inv, container);
		BlockPos pos = null;
		if (extraData != null) {
			pos = extraData.readBlockPos();
			this.x = pos.getX();
			this.y = pos.getY();
			this.z = pos.getZ();
			access = ContainerLevelAccess.create(world, pos);
		}
		if (pos != null) {
			if (extraData.readableBytes() == 1) {
				byte hand = extraData.readByte();
				ItemStack itemstack = hand == 0 ? this.entity.getMainHandItem() : this.entity.getOffhandItem();
				this.boundItem = itemstack;
				this.boundItemMatcher = () -> itemstack == (hand == 0 ? this.entity.getMainHandItem() : this.entity.getOffhandItem());
				this.bound = true;
			} else if (extraData.readableBytes() > 1) {
				extraData.readByte();
				boundEntity = world.getEntity(extraData.readVarInt());
				if (boundEntity != null)
					this.bound = true;
			} else {
				boundBlockEntity = this.world.getBlockEntity(pos);
				// Do not mark a temporary SimpleContainer as bound merely because a
				// container block exists at this position. The backing inventory itself
				// must be that block entity, otherwise closing the GUI can lose items.
				if (boundBlockEntity instanceof Container && this.inventory == boundBlockEntity)
					this.bound = true;
			}
		}
	}

	public FridgeGuiMenu(int id, Inventory inv, Container container) {
		super(VoxelizedFurnitureModMenus.FRIDGE_GUI, id);
		this.entity = inv.player;
		this.world = inv.player.level();
		this.inventory = container;
		this.customSlots.put(0, this.addSlot(new Slot(inventory, 0, 9, 17) {
			private final int slot = 0;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(1, this.addSlot(new Slot(inventory, 1, 27, 17) {
			private final int slot = 1;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(2, this.addSlot(new Slot(inventory, 2, 45, 17) {
			private final int slot = 2;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(3, this.addSlot(new Slot(inventory, 3, 63, 17) {
			private final int slot = 3;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(4, this.addSlot(new Slot(inventory, 4, 81, 17) {
			private final int slot = 4;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(5, this.addSlot(new Slot(inventory, 5, 99, 17) {
			private final int slot = 5;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(6, this.addSlot(new Slot(inventory, 6, 117, 17) {
			private final int slot = 6;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(7, this.addSlot(new Slot(inventory, 7, 135, 17) {
			private final int slot = 7;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(8, this.addSlot(new Slot(inventory, 8, 153, 17) {
			private final int slot = 8;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(9, this.addSlot(new Slot(inventory, 9, 9, 35) {
			private final int slot = 9;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(10, this.addSlot(new Slot(inventory, 10, 27, 35) {
			private final int slot = 10;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(11, this.addSlot(new Slot(inventory, 11, 45, 35) {
			private final int slot = 11;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(12, this.addSlot(new Slot(inventory, 12, 63, 35) {
			private final int slot = 12;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(13, this.addSlot(new Slot(inventory, 13, 81, 35) {
			private final int slot = 13;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(14, this.addSlot(new Slot(inventory, 14, 99, 35) {
			private final int slot = 14;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(15, this.addSlot(new Slot(inventory, 15, 117, 35) {
			private final int slot = 15;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(16, this.addSlot(new Slot(inventory, 16, 135, 35) {
			private final int slot = 16;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(17, this.addSlot(new Slot(inventory, 17, 153, 35) {
			private final int slot = 17;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(18, this.addSlot(new Slot(inventory, 18, 9, 53) {
			private final int slot = 18;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(19, this.addSlot(new Slot(inventory, 19, 27, 53) {
			private final int slot = 19;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(20, this.addSlot(new Slot(inventory, 20, 45, 53) {
			private final int slot = 20;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(21, this.addSlot(new Slot(inventory, 21, 63, 53) {
			private final int slot = 21;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(22, this.addSlot(new Slot(inventory, 22, 81, 53) {
			private final int slot = 22;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(23, this.addSlot(new Slot(inventory, 23, 99, 53) {
			private final int slot = 23;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(24, this.addSlot(new Slot(inventory, 24, 117, 53) {
			private final int slot = 24;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(25, this.addSlot(new Slot(inventory, 25, 135, 53) {
			private final int slot = 25;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(26, this.addSlot(new Slot(inventory, 26, 153, 53) {
			private final int slot = 26;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(27, this.addSlot(new Slot(inventory, 27, 36, 80) {
			private final int slot = 27;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(28, this.addSlot(new Slot(inventory, 28, 54, 80) {
			private final int slot = 28;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(29, this.addSlot(new Slot(inventory, 29, 72, 80) {
			private final int slot = 29;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(30, this.addSlot(new Slot(inventory, 30, 90, 80) {
			private final int slot = 30;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(31, this.addSlot(new Slot(inventory, 31, 108, 80) {
			private final int slot = 31;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(32, this.addSlot(new Slot(inventory, 32, 126, 80) {
			private final int slot = 32;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(33, this.addSlot(new Slot(inventory, 33, 36, 98) {
			private final int slot = 33;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(34, this.addSlot(new Slot(inventory, 34, 54, 98) {
			private final int slot = 34;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(35, this.addSlot(new Slot(inventory, 35, 72, 98) {
			private final int slot = 35;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(36, this.addSlot(new Slot(inventory, 36, 90, 98) {
			private final int slot = 36;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(37, this.addSlot(new Slot(inventory, 37, 108, 98) {
			private final int slot = 37;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		this.customSlots.put(38, this.addSlot(new Slot(inventory, 38, 126, 98) {
			private final int slot = 38;
			private int x = FridgeGuiMenu.this.x;
			private int y = FridgeGuiMenu.this.y;
		}));
		for (int si = 0; si < 3; ++si)
			for (int sj = 0; sj < 9; ++sj)
				this.addSlot(new Slot(inv, sj + (si + 1) * 9, 2 + 8 + sj * 18, 53 + 84 + si * 18));
		for (int si = 0; si < 9; ++si)
			this.addSlot(new Slot(inv, si, 2 + 8 + si * 18, 53 + 142));
	}

	@Override
	public boolean stillValid(Player player) {
		if (this.bound) {
			if (this.boundItemMatcher != null)
				return this.boundItemMatcher.get();
			else if (this.boundBlockEntity != null)
				return AbstractContainerMenu.stillValid(this.access, player, this.boundBlockEntity.getBlockState().getBlock());
			else if (this.boundEntity != null)
				return this.boundEntity.isAlive();
		}
		return this.inventory.stillValid(player);
	}

	@Override
	public ItemStack quickMoveStack(Player playerIn, int index) {
		ItemStack itemstack = ItemStack.EMPTY;
		Slot slot = (Slot) this.slots.get(index);
		if (slot != null && slot.hasItem()) {
			ItemStack itemstack1 = slot.getItem();
			itemstack = itemstack1.copy();
			if (index < 39) {
				if (!this.moveItemStackTo(itemstack1, 39, this.slots.size(), true))
					return ItemStack.EMPTY;
				slot.onQuickCraft(itemstack1, itemstack);
			} else if (boundItem != null && itemstack1 == boundItem) {
				return ItemStack.EMPTY;
			} else if (!this.moveItemStackTo(itemstack1, 0, 39, false)) {
				if (index < 39 + 27) {
					if (!this.moveItemStackTo(itemstack1, 39 + 27, this.slots.size(), true))
						return ItemStack.EMPTY;
				} else {
					if (!this.moveItemStackTo(itemstack1, 39, 39 + 27, false))
						return ItemStack.EMPTY;
				}
				return ItemStack.EMPTY;
			}
			if (itemstack1.isEmpty()) {
				slot.setByPlayer(ItemStack.EMPTY);
			} else {
				slot.setChanged();
			}
			if (itemstack1.getCount() == itemstack.getCount()) {
				return ItemStack.EMPTY;
			}
			slot.onTake(playerIn, itemstack1);
		}
		return itemstack;
	}

	@Override
	public void clicked(int slotId, int button, ContainerInput containerInput, Player player) {
		if (containerInput == ContainerInput.SWAP && boundItem != null) {
			if (slotId >= 0 && slotId < this.slots.size()) {
				ItemStack slotItem = this.slots.get(slotId).getItem();
				ItemStack hotbarItem = player.getInventory().getItem(button);
				if (slotItem == boundItem || hotbarItem == boundItem) {
					return;
				}
			}
		}
		super.clicked(slotId, button, containerInput, player);
	}

	@Override
	protected boolean moveItemStackTo(ItemStack itemstack, int i, int j, boolean bl) {
		int l;
		ItemStack itemstack2;
		Slot slot;
		boolean bl2 = false;
		int k = i;
		if (bl) {
			k = j - 1;
		}
		if (itemstack.isStackable()) {
			while (!itemstack.isEmpty() && (bl ? k >= i : k < j)) {
				slot = this.slots.get(k);
				itemstack2 = slot.getItem();
				if (!itemstack2.isEmpty() && ItemStack.isSameItemSameComponents(itemstack, itemstack2)) {
					int m;
					l = itemstack2.getCount() + itemstack.getCount();
					if (l <= (m = slot.getMaxStackSize(itemstack2))) {
						itemstack.setCount(0);
						itemstack2.setCount(l);
						slot.set(itemstack2);
						bl2 = true;
					} else if (itemstack2.getCount() < m) {
						itemstack.shrink(m - itemstack2.getCount());
						itemstack2.setCount(m);
						slot.set(itemstack2);
						bl2 = true;
					}
				}
				if (bl) {
					--k;
					continue;
				}
				++k;
			}
		}
		if (!itemstack.isEmpty()) {
			k = bl ? j - 1 : i;
			while (bl ? k >= i : k < j) {
				slot = this.slots.get(k);
				itemstack2 = slot.getItem();
				if (itemstack2.isEmpty() && slot.mayPlace(itemstack)) {
					l = slot.getMaxStackSize(itemstack);
					slot.setByPlayer(itemstack.split(Math.min(itemstack.getCount(), l)));
					bl2 = true;
					break;
				}
				if (bl) {
					--k;
					continue;
				}
				++k;
			}
		}
		return bl2;
	}

	@Override
	public void removed(Player playerIn) {
		super.removed(playerIn);
		// Only clear temporary, unbound GUI slots that are explicitly configured to drop/return
		// their contents when the GUI closes. Bound block/entity/item inventories must persist.
		if (!bound && playerIn instanceof ServerPlayer serverPlayer) {
			if (!serverPlayer.isAlive() || serverPlayer.hasDisconnected()) {
				for (int i = 0; i < inventory.getContainerSize(); ++i) {
					ItemStack stack = inventory.getItem(i);
					if (!stack.isEmpty()) {
						playerIn.drop(stack.copy(), false);
						inventory.setItem(i, ItemStack.EMPTY);
					}
				}
			} else {
				for (int i = 0; i < inventory.getContainerSize(); ++i) {
					ItemStack stack = inventory.getItem(i);
					if (!stack.isEmpty()) {
						playerIn.getInventory().placeItemBackInInventory(stack);
						inventory.setItem(i, ItemStack.EMPTY);
					}
				}
			}
		}
	}

	@Override
	public Map<Integer, Slot> getSlots() {
		return Collections.unmodifiableMap(customSlots);
	}

	@Override
	public Map<String, Object> getMenuState() {
		return menuState;
	}

	public static void screenInit() {
	}
}