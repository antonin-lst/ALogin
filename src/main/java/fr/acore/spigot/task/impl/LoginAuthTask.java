package fr.acore.spigot.task.impl;


import fr.acore.spigot.event.PlayerAuthSuccessEvent;
import fr.acore.spigot.event.manager.EventWrapperManager;
import fr.acore.spigot.manager.ALoginManager;
import fr.acore.spigot.task.AuthTask;
import fr.acore.spigot.utils.AuthPlayer;

public class LoginAuthTask extends AuthTask {

	private String passwd;
	
	public LoginAuthTask(ALoginManager manager, AuthPlayer authPlayer, String passwd) {
		super(manager, authPlayer);
		this.passwd = passwd;
	}

	@Override
	public void auth() {
		if(!cryptoManager.checkString(passwd, authPlayer.getCryptedPassword())) {
			authPlayer.sendMessage(manager.getFailLoginAtentMessage());
			int current = authPlayer.addAtent();
			if(current > manager.getMaxLoginAtent()) authPlayer.syncKick(manager.getKickMaxAtentMessage());
		}else {
			authPlayer.sendMessage(manager.getSuccessLoginMessage());
			manager.removePlayer(authPlayer);
			manager.getManager(EventWrapperManager.class).call(new PlayerAuthSuccessEvent(authPlayer.getPlayer(), false));
		}
		
	}

}
