package fr.acore.bungeecord.utils;

import fr.acore.bungeecord.data.AuthUserStorage;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class AuthUser {
	
	private AuthUserStorage authUserStorage;
	public AuthUserStorage getAuthUserStorage(){ return this.authUserStorage;}
	
	private PendingConnection connection;
	private ProxiedPlayer player;
	
	public AuthUser(AuthUserStorage authUserStorage, PendingConnection connection) {
		this.authUserStorage = authUserStorage;
		this.connection = connection;
	}

	public AuthUser(AuthUserStorage authUserStorage) {
		this.authUserStorage = authUserStorage;
	}
	
	public String getPassword() {
		return authUserStorage.getEncryptedPassword();
	}
	
	public void setPassword(String password) {
		this.authUserStorage.setEncryptedPassword(password);
	}
	
	public boolean hasPassword() {
		return authUserStorage.getEncryptedPassword() != null && !authUserStorage.getEncryptedPassword().isEmpty();
	}
	
	public void setPlayer(ProxiedPlayer player) {
		this.player = player;
	}
	
	public ProxiedPlayer getPlayer() {
		return player;
	}
	
	public PendingConnection getConnection() {
		return this.connection;
	}

	public String getOnlineUUID() {
		return authUserStorage.getPremiumUuid();
	}
	
	public void setOnlineUUID(String onlineUUID) {
		this.authUserStorage.setPremiumUuid(onlineUUID);
	}
	
	public String getUniqueUUID() {
		return authUserStorage.getUuid();
	}
	
	public void setUniqueUUID(String uniqueUUID) {
		this.authUserStorage.setUuid(uniqueUUID);
	}
	
	public String getName() {
		return authUserStorage.getName();
	}
	
	public boolean isPremium() {
		return authUserStorage.isPremium();
	}
	
	public void setPremium(boolean isPremium) {
		this.authUserStorage.setPremium(isPremium);
	}
	
	public void sendMessage(String message) {
		this.player.sendMessage(new TextComponent(message));
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof AuthUser) {
			AuthUser target = (AuthUser)obj;
			if(target.getUniqueUUID().equals(getUniqueUUID())) return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return "name:" + authUserStorage.getName() + "\nuniqueUUID:" + authUserStorage.getUuid() + "\npremium:" + String.valueOf(authUserStorage.isPremium()) + (authUserStorage.isPremium() ? "\nonlineUUID:" + authUserStorage.getPremiumUuid() : "");
	}
	
}
