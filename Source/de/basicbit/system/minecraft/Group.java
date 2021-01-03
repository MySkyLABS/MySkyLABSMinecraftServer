package de.basicbit.system.minecraft;

public enum Group {
	OWNER("§4§lB §8┃ §4§l", "§4§lBesitzer", "§4§lBesitzerin"), 
	ADMIN("§c§lA §8┃ §c§l", "§c§lAdministrator", "§c§lAdministratorin"),
	DEVELOPER("§b§lD §8┃ §b§l", "§b§lDeveloper", "§b§lDeveloperin"),
	BUILDER("§b§lB §8┃ §b§l", "§b§lBuilder", "§b§lBuilderin"),
	MODERATOR("§2§lM §8┃ §2§l", "§2§lModerator", "§2§lModeratorin"),
	SUPPORTER("§2§lS §8┃ §2§l", "§2§lSupporter", "§2§lSupporterin"),
	TEST_SUPPORTER("§2§lT §8┃ §2§l", "§2§lT-Supporter", "§2§lT-Supporterin"),
	CONTENT("§5§lC §8┃ §5§l", "§5§lContent", "§5§lContent"),
	
	PROMO_PLUS("§5§lP §8┃ §5", "§5Promo+", "§5Promo+"),
	PROMO("§d§lP §8┃ §d", "§dPromo", "§dPromo"),
	SENPAI("§3§lS §8┃ §3", "§3Senpai", "§3Senpai"),
	HERO("§e§lH §8┃ §e", "§eHero", "§eHero"),
	STAMMI("§a§lS §8┃ §a", "§aStammi", "§aStammi"),
	MEMBER("§7§lS §8┃ §7", "§7Spieler", "§7Spielerin");

	private String prefix;
	private String name_men;
	private String name_women;

	Group(final String p, final String man, final String women) {
		prefix = p;
		name_men = man;
		name_women = women;
	}

	public String getPrefix() {
		return prefix;
	}

	public String getName(Gender gender) {
		if (gender == Gender.MAN) {
			return name_men;
		} else {
			return name_women;
		}
	}
}
