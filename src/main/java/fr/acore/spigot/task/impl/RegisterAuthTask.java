package fr.acore.spigot.task.impl;


import fr.acore.spigot.event.PlayerAuthSuccessEvent;
import fr.acore.spigot.event.manager.EventWrapperManager;
import fr.acore.spigot.manager.ALoginManager;
import fr.acore.spigot.task.AuthTask;
import fr.acore.spigot.utils.AuthPlayer;

public class RegisterAuthTask extends AuthTask {

	private String passwd;
	
	public RegisterAuthTask(ALoginManager manager, AuthPlayer authPlayer, String passwd) {
		super(manager, authPlayer);
		this.passwd = passwd;
	}

	@Override
	public void auth() {
		if(manager.getDeprecatedPassword().contains(passwd)) {
			authPlayer.sendMessage(manager.getDeprecatedMessage());
			return;
		}
		String hashedPasswd = cryptoManager.hashString(passwd);
		authPlayer.setPassword(hashedPasswd);
		authPlayer.sendMessage(manager.getSuccessRegisterMessage());
		manager.removePlayer(authPlayer);
		manager.getManager(EventWrapperManager.class).call(new PlayerAuthSuccessEvent(authPlayer.getPlayer(), false));
	}

}
