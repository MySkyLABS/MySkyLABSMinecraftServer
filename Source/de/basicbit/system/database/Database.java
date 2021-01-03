package de.basicbit.system.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.World;

import de.basicbit.system.minecraft.Utils;
import de.basicbit.system.minecraft.tasks.TaskManager;

public class Database extends Utils {

	private static HashMap<Var, String> vars = new HashMap<Var, String>();
	private static Connection connection;
	private static String dataBasePath = "./database.db";

	public static void onStart() {
		try {
			Class.forName("org.sqlite.JDBC");
			log("SQLite driver found: org.sqlite.JDBC");

			connection = DriverManager.getConnection("jdbc:sqlite:" + dataBasePath);
			log("The connection to the database has been established: " + dataBasePath);

			execute("CREATE TABLE IF NOT EXISTS users (minecraftId);");

			ArrayList<String> fields = UserData.getFieldNames();

			for (String field : fields) {
				executeIgnoreErrors("ALTER TABLE users ADD " + field + ";");
			}

			String columns = "minecraftId";

			for (HashMap<String, String> row : fetchAll("PRAGMA table_info('users');")) {
				String name = row.get("name");

				if (name.contentEquals("minecraftId")) {
					continue;
				}

				if (fields.contains(name)) {
					columns += "," + name;
					log("[UserData] Column found: " + name);
				}
			}

			execute("BEGIN TRANSACTION;CREATE TEMPORARY TABLE users_backup(" + columns + ");INSERT INTO users_backup SELECT " + columns + " FROM users;DROP TABLE users;CREATE TABLE users(" + columns + ");INSERT INTO users SELECT " + columns + " FROM users_backup;DROP TABLE users_backup;COMMIT;");

			execute("CREATE TABLE IF NOT EXISTS vars (key, value, UNIQUE(key));");

			for (Var var : Var.values()) {
				execute("INSERT OR IGNORE INTO vars (key, value) VALUES ('" + var.toString() + "', '');");
			}

			loadVars();

			TaskManager.runAsyncLoop("DataBasePlayerUnloader", new Runnable() {
			
				@Override
				public void run() {
					for (int i = UserData.loadedUsers.size(); i-- > 0; ) {
						UserData userData = UserData.loadedUsers.get(i);
						UUID id = getUUIDFromTrimmed(userData.minecraftId);

						if (getPlayer(id) != null) {
							continue;
						}

						boolean unload = true;
						for (World w : Bukkit.getWorlds()) {
							if (isSkyblockWorld(w) && getUUIDFromSkyBlockWorld(w).equals(id)) {
								unload = false;
								break;
							}
						}

						if (!unload) {
							continue;
						}

						UserData.unload(id);
					}
				}

			}, 200);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void onExit() {
		try {
			saveVars();

			for (UserData userData : UserData.loadedUsers) {
				userData.close();
			}

			if (!connection.isClosed() && connection != null) {
				connection.close();

				if (connection.isClosed()) {
					log("The connection to the database has been closed.");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void setVar(Var data, String value) {
		vars.put(data, value);
	}

	public static String getVar(Var data) {
		if (vars.containsKey(data)) {
			return vars.get(data);
		} else {
			return "";
		}
	}

	public static void saveVars() {
		log("Saving vars to database...");
		for (Var key : vars.keySet()) {
			execute("UPDATE vars SET value = '" + vars.get(key) + "' WHERE key = '" + key + "';");
		}
	}

	public static void loadVars() {
		log("Loading vars from database...");
		vars.clear();

		for (HashMap<String, String> row : fetchAll("SELECT * FROM vars")) {
			vars.put(Var.valueOf(row.get("key")), row.get("value"));
		}
	}

	public static void execute(String cmd) {
		TaskManager.printStackTraceIfSync();

		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(cmd);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void executeIgnoreErrors(String cmd) {
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(cmd);
		} catch (Exception e) { }
	}

	public static ArrayList<HashMap<String, String>> fetchAll(final String query) {
		TaskManager.printStackTraceIfSync();
		
		try {
			final ArrayList<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>();
			final ResultSet resultSet = connection.prepareStatement(query).executeQuery();
			final ResultSetMetaData meta = resultSet.getMetaData();
			final int columnCount = meta.getColumnCount();

			while (resultSet.next()) {
				final HashMap<String, String> row = new HashMap<String, String>();

				for (int column = 0; column < columnCount; column++) {
					row.put(meta.getColumnName(column + 1), resultSet.getString(column + 1));
				}

				result.add(row);
			}

			return result;
		} catch (final SQLException e) {
			e.printStackTrace();
			return new ArrayList<HashMap<String, String>>();
		}
	}
}
