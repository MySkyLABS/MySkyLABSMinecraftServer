package de.basicbit.system.minecraft.simpleblockarea;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R1.block.CraftBlock;
import org.bukkit.entity.Player;
import org.apache.commons.io.FileUtils;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.material.MaterialData;

import de.basicbit.system.minecraft.Utils;
import de.basicbit.system.minecraft.geometry.Point3D;

@SuppressWarnings("all")
public class SimpleBlockAreaFile {

    private HashMap<Integer, MaterialData> blocks = new HashMap<Integer, MaterialData>();
    private File handle;
    private int length;

    private byte widthX;
    private byte height;
    private byte widthZ;
    
    private byte pasteX;
    private byte pasteY;
    private byte pasteZ;

    private static final byte[] MAGIC_HEADER = new byte[] { // magic header to identify file format
        (byte) 0xF3,
        (byte) 0x78,
        (byte) 0x7F,
        (byte) 0x0B,
        (byte) 0xED,
        (byte) 0xA3,
        (byte) 0xC6,
        (byte) 0xFA,
        (byte) 0x47,
        (byte) 0x7A,
        (byte) 0x9D,
        (byte) 0x37
    };

    private static final int BLOCK_BYTE_COUNT = 5; // length of block 5 (#0328/1)

    public SimpleBlockAreaFile(File file) throws IOException, IllegalArgumentException {
        handle = file;
        FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
        
        //INDEX: 0 reads magic header to validate file type
        byte[] buffer = new byte[12];
        fileInputStream.read(buffer);

        for (int i = 0; i < 12; i++) {
            if (MAGIC_HEADER[i] != buffer[i]) {
                throw new IllegalArgumentException("The argument is not a valid file.");
            }
        }

        //INDEX: 12 size of file (length of bytearray)
        buffer = new byte[4];
        fileInputStream.read(buffer);
        length = getIntFromBytes(buffer);

        //INDEX: 16 coords of selected area & coords of where area will be pasted
        buffer = new byte[6];
        fileInputStream.read(buffer);
        widthX = buffer[0];
        height = buffer[1];
        widthZ = buffer[2];
        pasteX = buffer[3];
        pasteY = buffer[4];
        pasteZ = buffer[5];

        //INDEX: 22 reads 3D selected area (slices from x -> z -> y and x+1 -> z -> y)
        buffer = new byte[BLOCK_BYTE_COUNT * widthX * height * widthZ];
        fileInputStream.read(buffer);

        for (byte x = 0; x < widthX; x++) {
            for (byte y = 0; y < height; y++) {
                for (byte z = 0; z < widthZ; z++) {
                    byte[] blockBytes = subBytes(buffer, getArrayIndex(x, y, z, widthX, height), BLOCK_BYTE_COUNT); // get single block info from file
                    int id = getIntFromBytes(subBytes(blockBytes, 0, 4)); // get id from block (#328)
                    byte subId = blockBytes[4]; // subid of block (e.g. color of glass)
                    if (id < 0) {
                        id = (id ^ -1) - 1;
                    }
                    MaterialData materialData = new MaterialData(id, subId);
                    blocks.put(getPosition(x, y, z), materialData); // puts block id & subid at coords in hashmap for coords
                }
            }
        }

        fileInputStream.close();
    }

    private static int getArrayIndex(int x, int y, int z, int widthX, int height) { // 3D block area to 1D block array
        return ((widthX * y) + (widthX * height * z) + x) * BLOCK_BYTE_COUNT;
    }

    private static HashSet<Block> getBlocks(Block block) {
        HashSet<Block> input = new HashSet<Block>();
        input.add(block);
        return getBlocks(input, input.size());
    }

    private static HashSet<Block> getBlocks(HashSet<Block> result, int sizeBefore) { // scans the blocks around you
        HashSet<Block> blocksToAdd = new HashSet<Block>();

        for (Block b : result) {
            for (int x = 0; x < 3; x++) {
                for (int y = 0; y < 3; y++) {
                    for (int z = 0; z < 3; z++) {
                        if (x == 0 && y == 0 && z == 0) {
                            continue;
                        }

                        Block c = b.getRelative(x - 1, y - 1, z - 1);
                        if (c.getTypeId() != 0) {
                            blocksToAdd.add(c);
                        }
                    }
                }
            }
        }

        for (Block b : blocksToAdd) {
            result.add(b);
        }

        return sizeBefore == result.size() ? result : getBlocks(result, result.size());
    }

    public MaterialData getBlock(byte x, byte y, byte z) {
        return blocks.get(getPosition(x, y, z));
    }

    public static SimpleBlockAreaFile createFromPlayerStandingOnIsland(File file, Player player)
            throws IOException, IllegalArgumentException {
        return create(file, player.getLocation());
    }

    public static SimpleBlockAreaFile create(File file, Location pastePos) throws IOException, IllegalArgumentException {
        return create(file, pastePos.getBlock().getRelative(0, -1, 0), pastePos);
    }

    public static SimpleBlockAreaFile create(File file, Block block, Location pastePos)
            throws IOException, IllegalArgumentException {
        if (!block.getType().isSolid()) {
            throw new IllegalArgumentException("The block is not a valid argument.");
        }

        return create(file, getBlocks(block), pastePos);
    }

    public static SimpleBlockAreaFile create(File file, HashSet<Block> blocks, Location pastePos) throws IOException,
    	IllegalArgumentException {
        Point3D pastePoint = new Point3D(pastePos);
        World world = pastePos.getWorld();
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minZ = Integer.MAX_VALUE;
        int maxZ = Integer.MIN_VALUE;
        
        for (Block block : blocks) { // gets the min/max coordinates of island
            Point3D point = new Point3D(block);
            minX = Math.min(minX, point.getXInt());
            maxX = Math.max(maxX, point.getXInt());
            minY = Math.min(minY, point.getYInt());
            maxY = Math.max(maxY, point.getYInt());
            minZ = Math.min(minZ, point.getZInt());
            maxZ = Math.max(maxZ, point.getZInt());
        }
        
        byte widthX = (byte)(maxX - minX + 1);
        byte height = (byte)(maxY - minY + 1);
        byte widthZ = (byte)(maxZ - minZ + 1);
        byte pasteX = (byte)(pastePoint.getXInt() - minX);
        byte pasteY = (byte)(pastePoint.getYInt() - minY);
        byte pasteZ = (byte)(pastePoint.getZInt() - minZ);

        if (widthX < 0) {
        	widthX *= -1;
        }

        if (height < 0) {
        	height *= -1;
        }

        if (widthZ < 0) {
        	widthZ *= -1;
        }
        
        byte[] data = new byte[widthX * height * widthZ * BLOCK_BYTE_COUNT + 22];

        for (int i = 0; i < 12; i++) {
            data[i] = MAGIC_HEADER[i];
        }

        byte[] lengthAsBytes = getBytesFromInt(data.length);

        for (int i = 0; i < 4; i++) {
            data[12 + i] = lengthAsBytes[i];
        }

        byte[] cuboidSizeAndPasteLocation = new byte[] {
            widthX,
            height,
            widthZ,
            pasteX,
            pasteY,
            pasteZ
        };

        for (int i = 0; i < 6; i++) {
            data[16 + i] = cuboidSizeAndPasteLocation[i];
        }

        for (byte x = 0; x < widthX; x++) {
            for (byte y = 0; y < height; y++) {
                for (byte z = 0; z < widthZ; z++) {
                    Block block = world.getBlockAt(minX + x, minY + y, minZ + z);
                    int blockIndex = getArrayIndex(x, y, z, widthX, height);
                    byte[] blockIdAndSubId = Arrays.copyOf(getBytesFromInt(block.getTypeId()), 5);
                    blockIdAndSubId[4] = block.getData();

                    for (int i = 0; i < 5; i++) {
                        data[blockIndex + i + 22] = blockIdAndSubId[i];
                    }
                }
            }
        }

        if (file.exists()) {
            file.delete();
        }

        FileUtils.writeByteArrayToFile(file, data);

        return new SimpleBlockAreaFile(file);
    }

    private static int getPosition(byte x, byte y, byte z) {
        try {
            return getIntFromBytes(new byte[] { x, y, z, 0 });
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return -1;
        }
    }

    private static byte[] subBytes(byte[] bytes, int index, int length) {
        return Arrays.copyOfRange(bytes, index, index + length);
    }

    private static byte[] getBytesFromInt(int i) {
        return ByteBuffer.allocate(4).putInt(i).array();
    }

    private static int getIntFromBytes(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length != 4) {
            throw new IllegalArgumentException("The input must be 4 bytes.");
        }
        return ByteBuffer.wrap(bytes).getInt();
    }

    public int getLength() {
        return length;
    }

    public String getPath() {
        return handle.getAbsolutePath();
    }

    public void paste(Location location) throws IllegalArgumentException {
        int pastePosX = location.getBlockX() - pasteX;
        int pastePosY = location.getBlockY() - pasteY;
        int pastePosZ = location.getBlockZ() - pasteZ;
        if (pastePosY < 0 || pastePosY + this.widthX > 256) {
            throw new IllegalArgumentException("Y Position out of bounds");
        }
        for (Map.Entry<Integer, MaterialData> entry : this.blocks.entrySet()) {
            byte[] bytes = getBytesFromInt(entry.getKey());
            byte currentPosX = bytes[0];
            byte currentPosY = bytes[1];
            byte currentPosZ = bytes[2];
            Location currentLocation = new Location(location.getWorld(), currentPosX, currentPosY, currentPosZ);
            currentLocation = currentLocation.add(location);
            MaterialData materialData = entry.getValue();
            Block block = currentLocation.getWorld().getBlockAt(currentLocation);

            if (materialData == null) {
                continue;
            }
            
            Utils.log("id:   " + materialData.getItemTypeId());
            Utils.log("data: " + materialData.getData());
            
            block.setTypeId(materialData.getItemTypeId());
            block.setData(materialData.getData());
        }
    }
}