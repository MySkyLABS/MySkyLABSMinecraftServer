package de.basicbit.system.teamspeak;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.TS3Query.FloodRate;
import com.github.theholywaffle.teamspeak3.api.event.ChannelCreateEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDeletedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelDescriptionEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ChannelPasswordChangedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientJoinEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientLeaveEvent;
import com.github.theholywaffle.teamspeak3.api.event.ClientMovedEvent;
import com.github.theholywaffle.teamspeak3.api.event.PrivilegeKeyUsedEvent;
import com.github.theholywaffle.teamspeak3.api.event.ServerEditedEvent;
import com.github.theholywaffle.teamspeak3.api.event.TS3Listener;
import com.github.theholywaffle.teamspeak3.api.event.TextMessageEvent;
import com.github.theholywaffle.teamspeak3.api.wrapper.ClientInfo;
import com.github.theholywaffle.teamspeak3.api.wrapper.ServerGroupClient;

public class Teamspeak implements TS3Listener {

	private static TS3Config config = new TS3Config();
	private static TS3Query query;
	private static TS3Api ts;

	public static void main(String[] args) {
		onStart();
	}

	public static void onStart() {
		try {
			config.setFloodRate(FloodRate.DEFAULT);
			config.setQueryPort(10011);
			config.setHost("134.255.232.43");
			query = new TS3Query(config);
			query.connect();
			ts = query.getApi();
			ts.selectVirtualServerById(1);
			ts.login("serveradmin", "'!as?d6/5fG_G4gD6'");
			ts.setNickname("MySkyLABS.de");
			ts.addTS3Listeners(new Teamspeak());
			ts.registerAllEvents();
			ts.addServerGroupPermission(2, "i_client_poke_power", 999999999, false, false);
		} catch (Exception e) {
			query.exit();
		}
	}
	
	public static void onExit() {
		query.exit();
	}

	@Override
	public void onChannelCreate(ChannelCreateEvent e) {
		
	}

	@Override
	public void onChannelDeleted(ChannelDeletedEvent e) {

	}

	@Override
	public void onChannelDescriptionChanged(ChannelDescriptionEditedEvent e) {
		
	}

	@Override
	public void onChannelEdit(ChannelEditedEvent e) {
		
	}

	@Override
	public void onChannelMoved(ChannelMovedEvent e) {
		
	}

	@Override
	public void onChannelPasswordChanged(ChannelPasswordChangedEvent e) {
		
	}

	@Override
	public void onClientJoin(ClientJoinEvent e) {

	}

	@Override
	public void onClientLeave(ClientLeaveEvent e) {
		
	}

	@Override
	public void onClientMoved(ClientMovedEvent e) {
		int id = e.getTargetChannelId();
		String name = ts.getClientInfo(e.getClientId()).getNickname();

		if (id == 116) {
			for (ServerGroupClient groupClient : ts.getServerGroupClients(90)) {
				ClientInfo client = ts.getClientByUId(groupClient.getUniqueIdentifier());
				ts.pokeClient(client.getId(), "Der Spieler " + name + " befindet sich im Support!");
			}
		}
	}

	@Override
	public void onPrivilegeKeyUsed(PrivilegeKeyUsedEvent e) {
		
	}

	@Override
	public void onServerEdit(ServerEditedEvent e) {
		
	}

	@Override
	public void onTextMessage(TextMessageEvent e) {

	}

	public static TS3Api getTeamSpeakAPI() {
		return ts;
	}
}