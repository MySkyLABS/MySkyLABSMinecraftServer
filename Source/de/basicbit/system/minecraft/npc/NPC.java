package de.basicbit.system.minecraft.npc;

import java.util.ArrayList;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R1.CraftServer;
import org.bukkit.craftbukkit.v1_8_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.basicbit.system.minecraft.Listener;
import de.basicbit.system.minecraft.ListenerSystem;
import de.basicbit.system.minecraft.MySkyLABS;
import de.basicbit.system.minecraft.tasks.TaskManager;
import net.minecraft.server.v1_8_R1.DataWatcher;
import net.minecraft.server.v1_8_R1.EntityPlayer;
import net.minecraft.server.v1_8_R1.EnumPlayerInfoAction;
import net.minecraft.server.v1_8_R1.MathHelper;
import net.minecraft.server.v1_8_R1.MinecraftServer;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityHeadRotation;
import net.minecraft.server.v1_8_R1.PacketPlayOutEntityLook;
import net.minecraft.server.v1_8_R1.PacketPlayOutNamedEntitySpawn;
import net.minecraft.server.v1_8_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R1.PlayerInteractManager;
import net.minecraft.server.v1_8_R1.WorldServer;

public class NPC extends Listener {

    private static ArrayList<NPC> npcs = new ArrayList<NPC>();
    private ArrayList<UUID> showByPlayers = new ArrayList<UUID>();

    private NPCType type;
    private EntityPlayer handle;
    private boolean lookToPlayers;
    private Location location;
    private String name;

    public static void init() {
        ListenerSystem.register(new NPC());

        for (NPCType type : NPCType.values()) {
            type.initNPC();
        }

        for (Player p : getPlayers()) {
            for (NPC npc : npcs) {
                if (isInWorld(p, npc.location.getWorld())) {
                    npc.spawn(p);
                }
            }
        } 
    }

    public static ArrayList<NPC> getNPCs() {
        return npcs;
    }

    public Location getLocation() {
        return location;
    }

    public NPCType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static void destroyAll() {
        for (Player p : getPlayers()) {
            for (NPC npc : npcs) {
                npc.destroy(p);
            }
        }
    }

    public int getId() {
        return handle.getId();
    }

    private NPC() { }

    public NPC(Location location, String name, Skin skin, boolean lookToPlayers, NPCType type) {
        this.name = name;
        this.lookToPlayers = lookToPlayers;
        this.location = location;
        this.type = type;

        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name.length() > 16 ? name.substring(0, 16) : name);
        gameProfile.getProperties().put("textures", new Property("textures", skin.TEX, skin.SIG));

        JavaPlugin plugin = MySkyLABS.getSystemPlugin();
        CraftServer craftServer = (CraftServer) plugin.getServer();
        MinecraftServer minecraftServer = craftServer.getServer();

        CraftWorld craftWorld = (CraftWorld) location.getWorld();
        WorldServer worldServer = craftWorld.getHandle();
        PlayerInteractManager playerInteractManager = new PlayerInteractManager(worldServer);

        handle = new EntityPlayer(minecraftServer, worldServer, gameProfile, playerInteractManager);
        handle.locX = location.getX();
        handle.locY = location.getY();
        handle.locZ = location.getZ();
        handle.setHealth(20f);

        npcs.add(this);

		log("NPC created: " + handle.getId() + ", \"" + name + "§r\"");
    }

    @EventHandler
    public static void onJoin(PlayerJoinEvent e) {
        TaskManager.runSyncTaskLater("UpdateNPC (Join)", new Runnable() {

            @Override
            public void run() {
                Player p = e.getPlayer();
                UUID id = p.getUniqueId();

                for (NPC npc : npcs) {
                    if (npc.showByPlayers.contains(id)) {
                        npc.showByPlayers.remove(id);
                    }
                }

                update(p, p.getLocation().getChunk());
            }
            
        }, 20);
    }

    @EventHandler
    public static void onMove(PlayerMoveEvent e) {
        sendMoveHeadToPlayerFromAllNPCs(e.getPlayer());
    }

    private static void sendMoveHeadToPlayerFromAllNPCs(Player p) {
        for (NPC npc : npcs) {
            npc.sendMoveHeadToPlayer(p);
        }
    }

    private void sendMoveHeadToPlayer(Player p) {
        if (p.getWorld().getName().equalsIgnoreCase(location.getWorld().getName()) && lookToPlayers) {
            final float[] floats = getRotationsNeeded(p);
            handle.yaw = floats[0];
            handle.pitch = floats[1];
            sendPacket(p, new PacketPlayOutEntityLook(handle.getId(), (byte) (handle.yaw * 256f / 360f), (byte) (handle.pitch * 256f / 360f), true));
            sendPacket(p, new PacketPlayOutEntityHeadRotation(handle, (byte) (handle.yaw * 256f / 360f)));
        }
    }

    private float[] getRotationsNeeded(final Player p) {
        final EntityPlayer entity = ((CraftPlayer) p).getHandle();

        final double diffX = entity.locX - handle.locX;
        final double diffZ = entity.locZ - handle.locZ;
        final double diffY = entity.locY + entity.getHeadHeight() - (handle.locY + handle.getHeadHeight());

        final double dist = MathHelper.sqrt(diffX * diffX + diffZ * diffZ);
        final float yaw = (float) (Math.atan2(diffZ, diffX) * 180.0D / Math.PI) - 90.0F;
        final float pitch = (float) -(Math.atan2(diffY, dist) * 180.0D / Math.PI);
        return new float[] { handle.yaw + MathHelper.g(yaw - handle.yaw), handle.pitch + MathHelper.g(pitch - handle.pitch) };
    }

    @EventHandler
    public static void onWorldChanged(PlayerChangedWorldEvent e) {
        TaskManager.runSyncTaskLater("UpdateNPC (WorldChange)", new Runnable() {

            @Override
            public void run() {
                Player p = e.getPlayer();
                UUID id = p.getUniqueId();

                for (NPC npc : npcs) {
                    if (npc.showByPlayers.contains(id)) {
                        npc.showByPlayers.remove(id);
                    }
                }

                update(p, p.getLocation().getChunk());
            }
            
        }, 20);
    }

    @EventHandler
    public static void onTeleport(PlayerTeleportEvent e) {
        TaskManager.runSyncTaskLater("UpdateNPC (Teleport)", new Runnable() {

            @Override
            public void run() {
                Player p = e.getPlayer();
                UUID id = p.getUniqueId();

                for (NPC npc : npcs) {
                    if (npc.showByPlayers.contains(id)) {
                        npc.showByPlayers.remove(id);
                    }
                }

                update(p, p.getLocation().getChunk());
            }
            
        }, 20);
    }

    public static void update(Player p, Chunk p_c) {
        UUID id = p.getUniqueId();
        int maxDistance = 1;
        int p_x = p_c.getX();
        int p_z = p_c.getZ();
        for (NPC npc : npcs) {
            Location npc_l = npc.location;

            if (isInWorld(p, npc_l.getWorld())) {
                Chunk npc_c = npc_l.getChunk();
                int npc_x = npc_c.getX();
                int npc_z = npc_c.getZ();

                int distance_x = p_x - npc_x;
                if (distance_x < 0) {
                    distance_x *= -1;
                }

                int distance_z = p_z - npc_z;
                if (distance_z < 0) {
                    distance_z *= -1;
                }

                int distance = Math.max(distance_x, distance_z);
                boolean showPlayerBefore = npc.showByPlayers.contains(id);

                if (distance <= maxDistance) {
                    if (!npc.showByPlayers.contains(id)) {
                        npc.showByPlayers.add(id);
                    }
                } else {
                    if (npc.showByPlayers.contains(id)) {
                        npc.showByPlayers.remove(id);
                    }
                }

                boolean showPlayerAfter = npc.showByPlayers.contains(id);

                if (showPlayerBefore == showPlayerAfter) {
                    continue;
                }

                if (showPlayerAfter) {
                    npc.spawn(p);
                } else {
                    npc.destroy(p);
                }
            } else {
                npc.destroy(p);
            }
        }
    }

    public void destroy(Player p) {
        sendPacket(p, new PacketPlayOutEntityDestroy(handle.getId()));
    }

    public void spawn(Player p) {
        PacketPlayOutNamedEntitySpawn entityPlayerPacket = new PacketPlayOutNamedEntitySpawn(handle);

        DataWatcher dataWatcher = new DataWatcher(null);
        dataWatcher.a(10, (byte) 127);
        setValue(entityPlayerPacket, "i", dataWatcher);

        sendPacket(p, new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.ADD_PLAYER, handle));
        sendPacket(p, entityPlayerPacket);

        TaskManager.runAsyncTaskLater(new Runnable() {
        
            @Override
            public void run() {
                sendPacket(p, new PacketPlayOutPlayerInfo(EnumPlayerInfoAction.REMOVE_PLAYER, handle));
            }
        }, 5);
    }

	public static NPC getById(int id) {
        for (NPC npc : npcs) {
            if (npc.handle.getId() == id) {
                return npc;
            }
        }

		return null;
	}
}