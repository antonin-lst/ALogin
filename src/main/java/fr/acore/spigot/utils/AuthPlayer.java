package fr.acore.spigot.utils;

import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.manager.ALoginManager;
import fr.acore.spigot.utils.time.TimerBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;


public class AuthPlayer {
	
	private ALoginManager manager;
	private CorePlayer<?> player;
	private TimerBuilder timer;
	
	private int atent;
	
	public AuthPlayer(ALoginManager manager, CorePlayer<?> player, long timer) {
		this.manager = manager;
		this.player = player;
		this.timer = new TimerBuilder(timer);
		this.atent = 0;
	}
	
	public CorePlayer<?> getPlayer() {
		return this.player;
	}
	
	public boolean isFinish() {
		return timer.isFinish();
	}

	public boolean needRegister() {
		return player.getEncryptedPassword() == null || player.getEncryptedPassword().isEmpty();
	}
	
	public int getAtent() {
		return atent;
	}
	
	public int addAtent() {
		return ++this.atent;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof AuthPlayer) {
			AuthPlayer target = (AuthPlayer)obj;
			if(target.getPlayer().equals(player)) return true;
		}
		return false;
	}

	public void sendAuthMessage() {
		if(needRegister()) {
			player.sendMessage(manager.getRegisterMessage());
		}else {
			player.sendMessage(manager.getLoginMessage());
		}
	}

	public String getCryptedPassword() {
		return player.getEncryptedPassword();
	}
	
	public void sendMessage(String message) {
		player.sendMessage(message);
	}

	public void setPassword(String hashedPasswd) {
		player.setEncryptedPassword(hashedPasswd);
	}

	public void syncKick(String kickMaxAtentMessage) {
		Bukkit.getScheduler().runTask((Plugin) manager.getKey(), new Runnable() {
			
			@Override
			public void run() {
				player.getPlayer().kickPlayer(kickMaxAtentMessage);
			}
		});
	}
}
