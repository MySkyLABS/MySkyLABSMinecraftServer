package de.basicbit.system.minecraft.gui;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.dynamic.DynamicObject;
import de.basicbit.system.minecraft.dynamic.Field;
import de.basicbit.system.minecraft.dynamic.FieldAndObject;
import de.basicbit.system.minecraft.tasks.TaskManager;

public class GuiListener extends Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        HumanEntity h = e.getWhoClicked();
        if (h instanceof Player) {
            Player p = (Player)h;
            Inventory inv = e.getClickedInventory();
            if (inv != null) {
                InventoryHolder holder = inv.getHolder();
                if (holder instanceof GuiHolder) {
                    GuiHolder guiHolder = (GuiHolder)holder;
                    Gui gui = guiHolder.getGui();
                    int page = guiHolder.getPage();
                    int invSlot = e.getSlot();
                    int globalSlot = 45 * page + invSlot;
                    
                    if (invSlot < 45) {
                        int click = 0;

                        if (e.isRightClick()) {
                            click += Click.RIGHT;
                        }

                        if (e.isShiftClick()) {
                            click += Click.SHIFT;
                        }

                        DynamicObject result;

                        try {
                            result = gui.onClick(new DynamicObject(
                                new FieldAndObject(
                                    Field.PLAYER_GUI_CLICKTYPE,
                                    click
                                ),
                                new FieldAndObject(
                                    Field.PLAYER_MAIN,
                                    p
                                ),
                                new FieldAndObject(
                                    Field.PLAYER_GUI_SLOT_POS,
                                    globalSlot
                                ),
                                new FieldAndObject(
                                    Field.PLAYER_GUI_PAGE,
                                    page
                                ),
                                new FieldAndObject(
                                    Field.PLAYER_GUI_SLOT_POS_X,
                                    invSlot % 9
                                ),
                                new FieldAndObject(
                                    Field.PLAYER_GUI_SLOT_POS_Y,
                                    invSlot / 9
                                ),
                                new FieldAndObject(
                                    Field.PLAYER_GUI_ITEM_CLICKED,
                                    gui.getItemAt(globalSlot)
                                ),
                                new FieldAndObject(
                                    Field.PLAYER_GUI_ITEM_CURSOR,
                                    e.getCursor()
                                )
                            ));
                        } catch (Exception ex) {
                            e.setCancelled(true);
                            ex.printStackTrace();
                            return;
                        }

                        if (result.contains(Field.PLAYER_GUI_ALLOW_DRAG)) {
                            e.setCancelled(!result.getBoolean(Field.PLAYER_GUI_ALLOW_DRAG));
                        } else {
                            e.setCancelled(true);
                        }

                        //e.setCancelled(!gui.onClick(p, page, globalSlot % 9, globalSlot / 9, globalSlot, inv.getItem(invSlot), e.isLeftClick(), e.isRightClick(), e.isShiftClick()));
                    } else {
                        e.setCancelled(true);
    
                        invSlot %= 45;

                        if (invSlot == 0 && page != 0) {
                            Gui.open(p, gui, page - 1);
                            return;
                        }

                        if (invSlot == 8 && page + 1 != gui.getPages()) {
                            Gui.open(p, gui, page + 1);
                            return;
                        }

                        final int invSlotAsArgument = invSlot;

                        TaskManager.runAsyncTask(new Runnable() {
                        
                            @Override
                            public void run() {
                                int click = 0;

                                if (e.isRightClick()) {
                                    click += Click.RIGHT;
                                }

                                if (e.isShiftClick()) {
                                    click += Click.SHIFT;
                                }

                                gui.onDownBarClick(new DynamicObject(
                                    new FieldAndObject(
                                        Field.PLAYER_GUI_CLICKTYPE,
                                        click
                                    ),
                                    new FieldAndObject(
                                        Field.PLAYER_MAIN,
                                        p
                                    ),
                                    new FieldAndObject(
                                        Field.PLAYER_GUI_SLOT_POS,
                                        invSlotAsArgument
                                    ),
                                    new FieldAndObject(
                                        Field.PLAYER_GUI_ITEM_CLICKED,
                                        inv.getItem(invSlotAsArgument + 45)
                                    ),
                                    new FieldAndObject(
                                        Field.PLAYER_GUI_ITEM_CURSOR,
                                        e.getCursor()
                                    )
                                ));

                                //gui.onDownBarClick(p, invSlotAsArgument, inv.getItem(invSlotAsArgument + 45), e.isLeftClick(), e.isRightClick(), e.isShiftClick());
                            }
                        });
                    }
                }
            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        HumanEntity h = e.getPlayer();
        if (h instanceof Player) {
            Player p = (Player)h;
            Inventory inv = e.getInventory();
            if (inv != null) {
                InventoryHolder holder = inv.getHolder();
                if (holder instanceof GuiHolder) {
                    GuiHolder guiHolder = (GuiHolder)holder;
                    Gui gui = guiHolder.getGui();
                    int page = guiHolder.getPage();

                    TaskManager.runAsyncTask(new Runnable(){
                        
                        @Override
                        public void run() {
                            gui.onClose(new DynamicObject(
                                new FieldAndObject(
                                    Field.PLAYER_GUI_PAGE,
                                    page
                                ),
                                new FieldAndObject(
                                    Field.PLAYER_MAIN,
                                    p
                                )
                            ));
                        }
                    });
                }
            }
        }
    }
}