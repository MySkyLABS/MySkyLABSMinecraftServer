package de.basicbit.system.minecraft.listeners.npc.guis;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.dynamic.DynamicObject;
import de.basicbit.system.minecraft.dynamic.Field;
import de.basicbit.system.minecraft.events.PlayerNPCRightClickEvent;
import de.basicbit.system.minecraft.gui.Gui;
import de.basicbit.system.minecraft.npc.NPC;
import de.basicbit.system.minecraft.npc.NPCType;

@SuppressWarnings("deprecation")
public class ServerShop extends Listener {

    protected int page;

    @EventHandler
    public void onInteractAtNPC(PlayerNPCRightClickEvent e) {
        NPC npc = e.getNPC();
        Player p = e.getPlayer();

        if (npc.getType().equals(NPCType.ServerShop)) {
            Gui.open(p, new Gui(new String[] {
                "§2§lNatur",
                "§3§lBlöcke",
                "§1§lRohstoffe & Erze",
                "§4§lAusrüstung",
                "§2§lDekorationen",
                "§b§lSpezial"
            }, 6) {

                @Override
                public void onInit(DynamicObject args) {

                    // Ankaufen für Viertel des Preises.

                    setBackgroundToGlassColor(0, 13);
                    setBackgroundToGlassColor(1, 9);
                    setBackgroundToGlassColor(2, 11);
                    setBackgroundToGlassColor(3, 14);
                    setBackgroundToGlassColor(4, 2);
                    setBackgroundToGlassColor(5, 1);
                    
                    //Seite 1 Natur
                    setItemAtWithCost(0, 1, 0, new ItemStack(6, 1, (short)0), 20); //OakSapling
                    setItemAtWithCost(1, 1, 0, new ItemStack(6, 1, (short)1), 20); //SpruceSapling
                    setItemAtWithCost(2, 1, 0, new ItemStack(6, 1, (short)2), 20); //BirchSapling
                    setItemAtWithCost(3, 1, 0, new ItemStack(6, 1, (short)3), 20); //JungleSapling
                    setItemAtWithCost(4, 1, 0, new ItemStack(6, 1, (short)4), 20); //AcaciaSapling
                    setItemAtWithCost(5, 1, 0, new ItemStack(6, 1, (short)5), 20); //DarkOakSapling
                    setItemAtWithCost(6, 1, 0, new ItemStack(18, 1,(short)0), 5); //OakLeaves
                    setItemAtWithCost(7, 1, 0, new ItemStack(18, 1,(short)1), 5); //SpruceLeaves
                    setItemAtWithCost(8, 1, 0, new ItemStack(18, 1,(short)2), 5); //BirchLeaves

                    setItemAtWithCost(0, 2, 0, new ItemStack(18, 1,(short)3), 5); //JungleLeaves
                    setItemAtWithCost(1, 2, 0, new ItemStack(161, 1,(short)0), 5); //AcaciaLeaves
                    setItemAtWithCost(2, 2, 0, new ItemStack(161, 1,(short)1), 5); //DarkOakLeaves
                    setItemAtWithCost(3, 2, 0, new ItemStack(295, 1,(short)0), 3); //Seeds
                    setItemAtWithCost(4, 2, 0, new ItemStack(391, 1,(short)0), 10); //Carrot
                    setItemAtWithCost(5, 2, 0, new ItemStack(392, 1,(short)0), 10); //Potato
                    setItemAtWithCost(6, 2, 0, new ItemStack(362, 1,(short)0), 5); //MelonSeeds
                    setItemAtWithCost(7, 2, 0, new ItemStack(361, 1,(short)0), 3); //PumpkinSeeds
                    setItemAtWithCost(8, 2, 0, new ItemStack(81, 1,(short)0), 5); //Cactus

                    setItemAtWithCost(1, 3, 0, new ItemStack(338, 1,(short)0), 5); //SugarCanes
                    setItemAtWithCost(3, 3, 0, new ItemStack(351, 1,(short)3), 5); //CocoaBeans
                    setItemAtWithCost(5, 3, 0, new ItemStack(106, 1,(short)0), 3); //Vines
                    setItemAtWithCost(7, 3, 0, new ItemStack(372, 1,(short)0), 5); //NetherWart

                    //Seite 2 Blöcke
                    setItemAtWithCost(0, 0, 1, new ItemStack(4, 1,(short)0), 2); //Cobblestone
                    setItemAtWithCost(1, 0, 1, new ItemStack(1, 1,(short)0), 4); //Stone
                    setItemAtWithCost(2, 0, 1, new ItemStack(1, 1,(short)1), 4); //Granite
                    setItemAtWithCost(3, 0, 1, new ItemStack(1, 1,(short)3), 4); //Diorite
                    setItemAtWithCost(4, 0, 1, new ItemStack(1, 1,(short)5), 4); //Andesite
                    setItemAtWithCost(5, 0, 1, new ItemStack(82, 1,(short)0), 50); //Clay
                    setItemAtWithCost(6, 0, 1, new ItemStack(159, 1,(short)0), 8); //WhiteStaindClay
                    setItemAtWithCost(7, 0, 1, new ItemStack(159, 1,(short)1), 8); //OrangeStaindClay
                    setItemAtWithCost(8, 0, 1, new ItemStack(159, 1,(short)2), 8); //MagentaStaindClay

                    setItemAtWithCost(0, 1, 1, new ItemStack(159, 1,(short)3), 8); //LightBlueStaindClay
                    setItemAtWithCost(1, 1, 1, new ItemStack(159, 1,(short)4), 8); //YellowStaindClay
                    setItemAtWithCost(2, 1, 1, new ItemStack(159, 1,(short)5), 8); //LimeStaindClay
                    setItemAtWithCost(3, 1, 1, new ItemStack(159, 1,(short)6), 8); //PinkStaindClay
                    setItemAtWithCost(4, 1, 1, new ItemStack(159, 1,(short)7), 8); //GrayStaindClay
                    setItemAtWithCost(5, 1, 1, new ItemStack(159, 1,(short)8), 8); //LightGrayStaindClay
                    setItemAtWithCost(6, 1, 1, new ItemStack(159, 1,(short)9), 8); //CyanStaindClay
                    setItemAtWithCost(7, 1, 1, new ItemStack(159, 1,(short)10), 8); //PurpleStaindClay
                    setItemAtWithCost(8, 1, 1, new ItemStack(159, 1,(short)11), 8); //BlueStaindClay

                    setItemAtWithCost(0, 2, 1, new ItemStack(159, 1,(short)12), 8); //BrownStaindClay
                    setItemAtWithCost(1, 2, 1, new ItemStack(159, 1,(short)13), 8); //GreenStaindClay
                    setItemAtWithCost(2, 2, 1, new ItemStack(159, 1,(short)14), 8); //RedStaindClay
                    setItemAtWithCost(3, 2, 1, new ItemStack(159, 1,(short)15), 8); //BlackStaindClay
                    setItemAtWithCost(4, 2, 1, new ItemStack(13, 1,(short)0), 5); //Gravel
                    setItemAtWithCost(5, 2, 1, new ItemStack(3, 1,(short)0), 10); //Dirt
                    setItemAtWithCost(6, 2, 1, new ItemStack(2, 1,(short)0), 15); //GrassBlock
                    setItemAtWithCost(7, 2, 1, new ItemStack(3, 1,(short)2), 15); //Podzol
                    setItemAtWithCost(8, 2, 1, new ItemStack(110, 1,(short)0), 15); //Mycelium

                    setItemAtWithCost(0, 3, 1, new ItemStack(17, 1,(short)0), 5); //OakWood
                    setItemAtWithCost(1, 3, 1, new ItemStack(17, 1,(short)1), 5); //SpruceWood
                    setItemAtWithCost(2, 3, 1, new ItemStack(17, 1,(short)2), 5); //BirchWood
                    setItemAtWithCost(3, 3, 1, new ItemStack(17, 1,(short)3), 5); //JungleWood
                    setItemAtWithCost(4, 3, 1, new ItemStack(162, 1,(short)0), 5); //AcaciaWood
                    setItemAtWithCost(5, 3, 1, new ItemStack(162, 1,(short)1), 5); //DarkOakWood
                    setItemAtWithCost(6, 3, 1, new ItemStack(12, 1,(short)0), 8); //Sand
                    setItemAtWithCost(7, 3, 1, new ItemStack(12, 1,(short)1), 16); //RedSand
                    setItemAtWithCost(8, 3, 1, new ItemStack(168, 1,(short)0), 15); //Prismarine
                                                            
                    setItemAtWithCost(0, 4, 1, new ItemStack(168, 1,(short)1), 15); //PrismarineBricks
                    setItemAtWithCost(1, 4, 1, new ItemStack(168, 1,(short)2), 15); //DarkPrismarine
                    setItemAtWithCost(2, 4, 1, new ItemStack(169, 1,(short)0), 20); //SeaLantern
                    setItemAtWithCost(3, 4, 1, new ItemStack(87, 1,(short)0), 5); //Netherrack
                    setItemAtWithCost(4, 4, 1, new ItemStack(112, 1,(short)0), 5); //NetherBrick
                    setItemAtWithCost(5, 4, 1, new ItemStack(88, 1,(short)0), 5); //SoulSand
                    setItemAtWithCost(6, 4, 1, new ItemStack(89, 1,(short)0), 10); //Glowstone
                    setItemAtWithCost(7, 4, 1, new ItemStack(79, 1, (short) 0), 10); //Eis
                    setItemAtWithCost(8, 4, 1, new ItemStack(174, 1, (short) 0), 15); //Packeis

                    //Seite 3 Rohstoffe & Erze
                    setItemAtWithCost(0, 0, 2, new ItemStack(263, 1,(short)0), 25); //Coal
                    setItemAtWithCost(1, 0, 2, new ItemStack(265, 1,(short)0), 30); //IronIngot
                    setItemAtWithCost(2, 0, 2, new ItemStack(266, 1,(short)0), 600); //GoldIngot
                    setItemAtWithCost(3, 0, 2, new ItemStack(351, 1,(short)4), 15); //LapisLazuli
                    setItemAtWithCost(4, 0, 2, new ItemStack(331, 1,(short)0), 10); //Redstone
                    setItemAtWithCost(5, 0, 2, new ItemStack(388, 1,(short)0), 15); //Emerald
                    setItemAtWithCost(6, 0, 2, new ItemStack(264, 1,(short)0), 40); //Diamond
                    setItemAtWithCost(7, 0, 2, new ItemStack(406, 1,(short)0), 3); //NetherQuartz
                    setItemAtWithCost(8, 0, 2, new ItemStack(318, 1,(short)0), 1); //Flint

                    setItemAtWithCost(0, 1, 2, new ItemStack(16, 1,(short)0), 40); //CoalOre
                    setItemAtWithCost(1, 1, 2, new ItemStack(15, 1,(short)0), 20); //IronOre
                    setItemAtWithCost(2, 1, 2, new ItemStack(14, 1,(short)0), 1200); //GoldOre
                    setItemAtWithCost(3, 1, 2, new ItemStack(21, 1,(short)0), 40); //LapisLazuliOre
                    setItemAtWithCost(4, 1, 2, new ItemStack(73, 1,(short)0), 40); //RedstoneOre
                    setItemAtWithCost(5, 1, 2, new ItemStack(129, 1,(short)0), 60); //EmeraldOre
                    setItemAtWithCost(6, 1, 2, new ItemStack(56, 1,(short)0), 80); //DiamondOre
                    setItemAtWithCost(7, 1, 2, new ItemStack(337, 1,(short)0), 8); //Clay
                    setItemAtWithCost(8, 1, 2, new ItemStack(348, 1,(short)0), 4); //GlowstoneDuste

                    setItemAtWithCost(0, 2, 2, new ItemStack(352, 1,(short)0), 3); //Bone
                    setItemAtWithCost(1, 2, 2, new ItemStack(367, 1,(short)0), 3); //RottenFlesh
                    setItemAtWithCost(2, 2, 2, new ItemStack(287, 1,(short)0), 3); //String
                    setItemAtWithCost(3, 2, 2, new ItemStack(375, 1,(short)0), 3); //SpiderEye
                    setItemAtWithCost(4, 2, 2, new ItemStack(409, 1,(short)0), 3); //PrismarineShard
                    setItemAtWithCost(5, 2, 2, new ItemStack(410, 1,(short)0), 3); //PrismarineCrystals
                    setItemAtWithCost(6, 2, 2, new ItemStack(341, 1,(short)0), 10); //Slimeball
                    setItemAtWithCost(7, 2, 2, new ItemStack(378, 1,(short)0), 10); //MagmaCream
                    setItemAtWithCost(8, 2, 2, new ItemStack(368, 1,(short)0), 15); //EnderPearl

                    setItemAtWithCost(1, 3, 2, new ItemStack(369, 1,(short)0), 7); //BlazeRod
                    setItemAtWithCost(2, 3, 2, new ItemStack(370, 1,(short)0), 5); //GhastTear
                    setItemAtWithCost(3, 3, 2, new ItemStack(30, 1,(short)0), 15); //Cobweb
                    setItemAtWithCost(5, 3, 2, new ItemStack(334, 1,(short)0), 10); //Leather
                    setItemAtWithCost(6, 3, 2, new ItemStack(288, 1,(short)0), 8); //Feather
                    setItemAtWithCost(7, 3, 2, new ItemStack(35, 1,(short)0), 4); //Wool

                    //Seite 4 Ausrüstung
                    setItemAtWithCost(0, 1, 3, new ItemStack(261, 1,(short)0), 15); //Bow
                    setItemAtWithCost(2, 1, 3, new ItemStack(267, 1,(short)0), 15); //IronSword
                    setItemAtWithCost(3, 1, 3, new ItemStack(306, 1,(short)0), 15); //IronHelmet
                    setItemAtWithCost(4, 1, 3, new ItemStack(307, 1,(short)0), 20); //IronChestplate
                    setItemAtWithCost(5, 1, 3, new ItemStack(308, 1,(short)0), 15); //IronLeggins
                    setItemAtWithCost(6, 1, 3, new ItemStack(309, 1,(short)0), 10); //IronBoots
                    setItemAtWithCost(8, 1, 3, new ItemStack(359, 1,(short)0), 10); //Shears

                    setItemAtWithCost(0, 2, 3, new ItemStack(262, 1,(short)0), 2); //Arrow
                    setItemAtWithCost(2, 2, 3, new ItemStack(276, 1,(short)0), 40); //DiamondSword
                    setItemAtWithCost(3, 2, 3, new ItemStack(310, 1,(short)0), 120); //DiamondHelmet
                    setItemAtWithCost(4, 2, 3, new ItemStack(311, 1,(short)0), 180); //DiamondChestplate
                    setItemAtWithCost(5, 2, 3, new ItemStack(312, 1,(short)0), 140); //DiamondLeggins
                    setItemAtWithCost(6, 2, 3, new ItemStack(313, 1,(short)0), 100); //DiamondBoots
                    setItemAtWithCost(8, 2, 3, new ItemStack(346, 1,(short)0), 30); //Fishing Rod

                    setItemAtWithCost(1, 3, 3, new ItemStack(257, 1,(short)0), 20); //IronPickaxe
                    setItemAtWithCost(3, 3, 3, new ItemStack(258, 1,(short)0), 20); //IronAxe
                    setItemAtWithCost(5, 3, 3, new ItemStack(256, 1,(short)0), 15); //IronShovel
                    setItemAtWithCost(7, 3, 3, new ItemStack(292, 1,(short)0), 10); //IronHoe

                    //Seite 5 Dekorationen
                    setItemAtWithCost(0, 1, 4, new ItemStack(38, 1,(short)0), 3); //Poppy
                    setItemAtWithCost(1, 1, 4, new ItemStack(37, 1,(short)0), 3); //Dandelion
                    setItemAtWithCost(2, 1, 4, new ItemStack(38, 1,(short)4), 3); //RedTulip
                    setItemAtWithCost(3, 1, 4, new ItemStack(38, 1,(short)5), 3); //OrangeTulip
                    setItemAtWithCost(4, 1, 4, new ItemStack(38, 1,(short)6), 3); //WhiteTulip
                    setItemAtWithCost(5, 1, 4, new ItemStack(38, 1,(short)7), 3); //PinkTulip
                    setItemAtWithCost(6, 1, 4, new ItemStack(38, 1,(short)1), 3); //BlueOrchid
                    setItemAtWithCost(7, 1, 4, new ItemStack(38, 1,(short)2), 3); //Allium
                    setItemAtWithCost(8, 1, 4, new ItemStack(38, 1,(short)8), 3); //OxeyeDaisy

                    setItemAtWithCost(0, 2, 4, new ItemStack(31, 1,(short)1), 3); //Grass
                    setItemAtWithCost(1, 2, 4, new ItemStack(175, 1,(short)2), 6); //DoubleTallgrass
                    setItemAtWithCost(2, 2, 4, new ItemStack(31, 1,(short)2), 3); //Fern
                    setItemAtWithCost(3, 2, 4, new ItemStack(175, 1,(short)3), 3); //LargeFern
                    setItemAtWithCost(4, 2, 4, new ItemStack(175, 1,(short)0), 3); //Sunflower
                    setItemAtWithCost(5, 2, 4, new ItemStack(175, 1,(short)1), 3); //Lilac
                    setItemAtWithCost(6, 2, 4, new ItemStack(175, 1,(short)5), 3); //Peony
                    setItemAtWithCost(7, 2, 4, new ItemStack(175, 1,(short)4), 3); //RoseBush
                    setItemAtWithCost(8, 2, 4, new ItemStack(32, 1,(short)0), 3); //DeadBush

                    setItemAtWithCost(2, 3, 4, new ItemStack(106, 1,(short)0), 3); //Vines
                    setItemAtWithCost(3, 3, 4, new ItemStack(111, 1,(short)0), 5); //LilyPad
                    setItemAtWithCost(5, 3, 4, new ItemStack(40, 1,(short)0), 3); //Mushroom
                    setItemAtWithCost(6, 3, 4, new ItemStack(39, 1,(short)0), 3); //Mushroom

                    //Seite 6 Spezial
                    setItemAtWithCost(1, 1, 5, new ItemStack(58, 1,(short)0), 5); //CraftingTable
                    setItemAtWithCost(2, 1, 5, new ItemStack(145, 1,(short)0), 200); //Anvil
                    setItemAtWithCost(3, 1, 5, new ItemStack(116, 1,(short)0), 250); //EntchantmentTable
                    setItemAtWithCost(5, 1, 5, new ItemStack(379, 1,(short)0), 120); //BrewingStand
                    setItemAtWithCost(6, 1, 5, new ItemStack(130, 1,(short)0), 90); //EnderChest
                    setItemAtWithCost(7, 1, 5, new ItemStack(47, 1,(short)0), 30); //Bookshelf
                    setItemAtWithCost(4, 2, 5, new ItemStack(384, 1,(short)0), 350); //Bottleo'Enchanting
                    setItemAtWithCost(4, 3, 5, new ItemStack(329, 1,(short)0), 50); //Saddle

                    ItemStack is = new ItemStack(331, 1, (short)0);
                    ItemMeta im = is.getItemMeta();
                    im.setDisplayName("§c§lSchließen");
                    is.setItemMeta(im);
                    setDownBarItemAt(4, is);
                }

                public void setItemAtWithCost(int x, int y, int page, ItemStack is, int cost) {
                    cost *= is.getAmount();
                    ItemMeta im = is.getItemMeta();
                    ArrayList<String> lore = new ArrayList<String>();
                    lore.add("§r");
                    lore.add("§e" + (cost == 1 ? "Ein Coin" : cost + " Coins"));
                    lore.add("§r");
                    im.setLore(lore);
                    is.setItemMeta(im);
                    setItemAt(x, y, page, is);
                }

                @SuppressWarnings("all")
                @Override
                public DynamicObject onClick(DynamicObject args) {
                    page = (int) args.get(Field.PLAYER_GUI_PAGE);
                    ItemStack is = args.getItemStack(Field.PLAYER_GUI_ITEM_CLICKED);

                    // Überprüfe die ID und wenn die ID nicht von der Glassscheibe ist, (dann kaufe
                    // es)
                    if (is != null && is.getTypeId() != 160) {
                        ItemMeta im = is.getItemMeta();
                        List<String> lore = im.getLore();
                        
                        String costAsString = lore.get(1).substring(2).split(" ")[0];
                        int cost = 0;

                        if (costAsString.contentEquals("Ein")) {
                            cost = 1;
                        } else {
                            cost = Integer.parseInt(costAsString);
                        }

                        is = is.clone();
                        lore.remove(0);
                        lore.remove(0);
                        lore.remove(0);
                        im.setLore(lore);
                        is.setItemMeta(im);

                        if (getCoins(p) < cost) {
                            sendMessage(p, "§cDu hast nicht genug Coins.");
                        } else {
                            giveItemToPlayer(p, is);
                            removeCoins(p, cost, " ServerShopRemoveCoins");

                            sendMessage(p, "§aKauf erfolgreich!");
                        }
                    }

                    return new DynamicObject();
                }

                @Override
                public void onClose(DynamicObject args) {
                    
                }

                @Override
                public void onDownBarClick(DynamicObject args) {
                    int slot = args.getInt(Field.PLAYER_GUI_SLOT_POS);

                    if (slot == 4) {
                        p.closeInventory();
                    }
                }
                
            }, 0);
        }
    }
}