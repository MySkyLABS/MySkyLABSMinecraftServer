package de.basicbit.system.minecraft.commands;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.basicbit.system.minecraft.Command;
import de.basicbit.system.minecraft.CommandResult;
import de.basicbit.system.minecraft.cases.CaseType;
import de.basicbit.system.minecraft.dynamic.DynamicObject;
import de.basicbit.system.minecraft.dynamic.Field;
import de.basicbit.system.minecraft.dynamic.FieldAndObject;
import de.basicbit.system.minecraft.gui.Gui;
import de.basicbit.system.minecraft.listeners.ShowcaseListener;

public class DevItems extends Command {

    public DevItems() {
        ShowcaseListener.showcases.add("§b§lDevItems");
    }

    @Override
    public String getUsage(Player p) {
        return "";
    }

    @Override
    public ArrayList<String> getNames() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("devitems");
        names.add("devcase");
        return names;
    }

    @Override
    public boolean hasPermissions(Player p) {
        return isDev(p);
    }

    @Override
    public String getDescription(Player p) {
        return "Öffnet ein Inventar mit Test-Items.";
    }

    @Override
    public CommandResult onCommand(Player p, String[] args, String allArgs) {
        if (args.length == 0) {
            CaseType type = CaseType.DEVITEMS;
            ArrayList<ItemStack> items = type.getItemsInView();
            int size = items.size();
            Gui.open(p, new Gui(type.getTitle(), size / 45 + (size % 45 == 0 ? 0 : 1)) {

                @Override
                public void onInit(DynamicObject args) {
                    setBackgroundInAllPagesToGlassColor(15);

                    for (int i = 0; i < size; i++) {
                        setItemAt(i, items.get(i).clone());
                    }
                }

                @Override
                public DynamicObject onClick(DynamicObject args) {
                    int slot = args.getInt(Field.PLAYER_GUI_SLOT_POS);

                    if (slot < size) {
                        ItemStack is = items.get(slot);

                        if (isAdmin(p)) {
                            giveItemToPlayer(p, is);
                        }
                    }

                    return new DynamicObject(new FieldAndObject(Field.CANCEL, true));
                }

            }, 0);
            return CommandResult.None;
        } else {
            return CommandResult.InvalidUsage;
        }
    }
}
