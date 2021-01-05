package de.basicbit.system.minecraft.npc;

import org.bukkit.Location;
import org.bukkit.World;
import de.basicbit.system.minecraft.Utils;

public enum NPCType {
    
    Bank("§eBankkaufmann", Utils.getSpawnWorld(), -31.5, 198, 20.5, Skin.bank),
    DayBonus("§6Tagesbonus", Utils.getSpawnWorld(), -32.5, 203, 39.5, Skin.dayBonus),
    //Settings("§dEinstellungen", Utils.getSpawnWorld(), -26.5, 118, 8.5, Skin.settings),
    KnockIt("§3Knock§bIt", Utils.getSpawnWorld(), -29.5, 198, 24.5, Skin.knockIt),
    KnockItBackToSpawn("§eZum Spawn", Utils.getKnockItWorld(), -1.5, 46, 2.5, Skin.knockIt),
    UserShop("§9Markt", Utils.getSpawnWorld(), -20.5, 198, 34.5, Skin.userShop),
    SkyBlock("§2SkyBlock", Utils.getSpawnWorld(), -30.5, 198, 22.5, Skin.skyBlock),
    Tutorial("§cTutorial", Utils.getSpawnWorld(), -21.5, 198, 26.5, Skin.tutorial),
    MajoShop("§fMajoverkäufer", Utils.getSpawnWorld(), -37.5, 197, 16.5, Skin.majoShop),
    ServerShop("§5Verkäufer", Utils.getSpawnWorld(), -21.5, 198, 35.5, Skin.serverShop),
    CandyShop("§dCandyshop", Utils.getSpawnWorld(), -69.5, 204, 27.5, Skin.candyShop),
    Farmer("§eFarmer", Utils.getSpawnWorld(), -46.5, 201, 36.5, Skin.farmer),
    Kit("§3Kits", Utils.getSpawnWorld(), -21.5, 196, 11.5, Skin.kit),
    CraftingGuide("§eCraftingGuide", Utils.getSpawnWorld(), -20.5, 198, 28.5, Skin.craftingGuide),
    //Game1vs1("§cKit 1vs1", Utils.getSpawnWorld(), -22.5, 118, 16.5, Skin.game1vs1),
    Smith("§bSchmied", Utils.getSpawnWorld(), -59.5, 198, 25.5, Skin.smith);

    private String name;
    private World world;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private boolean lookToPlayers;
    private Skin skin;
    // private boolean sit;

    NPCType(String name, World world, double x, double y, double z, float yaw, float pitch, Skin skin) {
        this.name = name;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.skin = skin;

        lookToPlayers = false;
    }

    NPCType(String name, World world, double x, double y, double z, Skin skin) {
        this.name = name;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
        this.skin = skin;

        lookToPlayers = true;
    }

    public void initNPC() {
        if (lookToPlayers) {
            new NPC(new Location(world, x, y, z), name, skin, true, this);
        } else {
            new NPC(new Location(world, x, y, z, yaw, pitch), name, skin, false, this);
        }
    }
}