package de.basicbit.system.minecraft.skyblock;

import java.util.UUID;

import de.basicbit.system.minecraft.Gender;
import de.basicbit.system.minecraft.Group;
import de.basicbit.system.minecraft.Utils;

public class IslandTrust {
    public UUID id;
    public String name;
    public Group group;
    public Gender gender;

    public IslandTrust(UUID id, String name, Group group, Gender gender) {
        this.id = id;
        this.name = name;
        this.group = group;
        this.gender = gender;
    }

    public static IslandTrust parse(String trust) {
        String[] trustValues = trust.split(",");
        String uuidAsString = trustValues[0];
        String groupAsString = trustValues[2];
        String genderAsString = trustValues[3];

        UUID id = Utils.getUUIDFromTrimmed(uuidAsString);
        String name = trustValues[1];
        Group group = Group.valueOf(groupAsString);
        
        Gender gender;
        try {
            gender = Gender.valueOf(genderAsString);
        } catch (Exception e) {
            gender = Gender.MAN;
        }

        return new IslandTrust(id, name, group, gender);
    }

    @Override
    public String toString() {
        return Utils.toTrimmed(id) + "," + name + "," + group + "," + gender;
    }
}