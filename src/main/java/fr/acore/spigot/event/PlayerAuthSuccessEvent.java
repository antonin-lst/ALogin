package fr.acore.spigot.event;

import fr.acore.spigot.api.player.impl.CorePlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;


public class PlayerAuthSuccessEvent extends Event{

	private static final HandlerList handlers = new HandlerList();
	
	private CorePlayer<?> player;
	private boolean autoLogin;
	
	public PlayerAuthSuccessEvent(CorePlayer<?> player, boolean autoLogin) {
		this.player = player;
		this.autoLogin = autoLogin;
	}
	
	public CorePlayer<?> getPlayer() {
		return this.player;
	}
	
	public boolean isAutoLogin() {
		return autoLogin;
	}
	
	public HandlerList getHandlers(){
		return handlers;
	}
     
	public static HandlerList getHandlerList() {
		return handlers;
	}
	
}
