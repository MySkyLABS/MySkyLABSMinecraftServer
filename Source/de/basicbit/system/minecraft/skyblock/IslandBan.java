package de.basicbit.system.minecraft.skyblock;

import java.util.UUID;

import de.basicbit.system.minecraft.Gender;
import de.basicbit.system.minecraft.Group;
import de.basicbit.system.minecraft.Utils;

public class IslandBan {
    public UUID id;
    public String name;
    public Group group;
    public Gender gender;

    public IslandBan(UUID id, String name, Group group, Gender gender) {
        this.id = id;
        this.name = name;
        this.group = group;
    }

    public static IslandBan parse(String ban) {
        String[] banValues = ban.split(",");
        String uuidAsString = banValues[0];
        String groupAsString = banValues[2];
        String genderAsString = banValues[3];

        UUID id = Utils.getUUIDFromTrimmed(uuidAsString);
        String name = banValues[1];
        Group group = Group.valueOf(groupAsString);

        Gender gender;
        try {
            gender = Gender.valueOf(genderAsString);
        } catch (Exception e) {
            gender = Gender.MAN;
        }

        return new IslandBan(id, name, group, gender);
    }

    @Override
    public String toString() {
        return Utils.toTrimmed(id) + "," + name + "," + group + "," + gender;
    }
}