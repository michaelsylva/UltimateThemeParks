package me.toaster.mcamusementcore.network;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import fr.klemms.syncit.ServerListener;
import fr.klemms.syncit.SyncChannel;
import fr.klemms.syncit.SyncData;
import fr.klemms.syncit.SyncItAPI;
import me.toaster.mcamusementcore.MCACore;

public class MCANetworkCore {

	public static SyncChannel infoChannel;
	
	@Deprecated
	public static void registerChannels() {
		MCANetworkCore.infoChannel = SyncItAPI.createChannel(MCACore.MCA_CORE, "INFO", new ServerListener() {
			
			@Override
			public void newMessage(UUID arg0, List<SyncData> arg1, HashMap<String, String> arg2) {
				System.out.println("New message");
			}
			
			@Override
			public void newConnection(UUID arg0) {
				System.out.println("new connection");
			}
			
			@Override
			public void connectionLost(UUID arg0) {
				System.out.println("connection lost");
			}
		});
	}
	
}
