package fr.acore.bungeecord.task;

import fr.acore.bungeecord.manager.ALoginManager;
import fr.acore.bungeecord.module.AManager;
import fr.acore.bungeecord.utils.AuthUser;
import fr.acore.bungeecord.utils.PremiumUtils;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Plugin;

public class PremiumCheckTask implements PremiumUtils.RequestCallBack {

	private ALoginManager manager;
	private AuthUser user;
	private PreLoginEvent event;
	
	public PremiumCheckTask(ALoginManager manager, AuthUser user, PreLoginEvent event) {
		this.manager = manager;
		this.user = user;
		this.event = event;
	}
	
	@Override
	public void callBack(boolean success, String response, Exception e, int responceCode) {
		if(success && !response.isEmpty()) {
			user.getConnection().setOnlineMode(true);
			user.setPremium(true);
			event.setCancelReason(new TextComponent("Premium Account Found.."));
			event.setCancelled(true);
		}
		System.out.println("debug register user premium : " + user.isPremium());
		manager.getAuthUserFactory().insert(user.getAuthUserStorage());
		event.completeIntent((Plugin) manager.getPlugin());
		
	}

}
