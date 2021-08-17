package fr.acore.bungeecord.task;

import fr.acore.bungeecord.cryptographie.CryptoManager;
import fr.acore.bungeecord.manager.ALoginManager;
import fr.acore.bungeecord.utils.AuthUser;


public class ChangePasswordTask implements Runnable{

	private ALoginManager manager;
	private AuthUser user;
	private String oldPass;
	private String newPass1, newPass2;
	
	public ChangePasswordTask(ALoginManager manager, AuthUser aUser, String oldPass, String newPass1, String newPass2) {
		this.manager = manager;
		this.user = aUser;
		this.oldPass = oldPass;
		this.newPass1 = newPass1;
		this.newPass2 = newPass2;
	}
	
	@Override
	public void run() {
		auth();
	}
	
	public void auth() {
		CryptoManager crypt = manager.getPlugin().getManager(CryptoManager.class);
		
		if(!user.hasPassword()) {
			user.sendMessage("Tu n'est pas encore inscrit");
			return;
		}
		
		if(!crypt.checkString(oldPass, user.getPassword())) {
			user.sendMessage(manager.getFailWrondPassMessage());
			return;
		}
		
		if(!newPass1.equals(newPass2)) {
			user.sendMessage(manager.getFailNotSamePassMessage());
			return;
		}
		String cryptedPass = crypt.hashString(newPass1);
		user.setPassword(cryptedPass);
		manager.getAuthUserFactory().update(user.getAuthUserStorage());
		user.sendMessage(manager.getChangePassSuccessMessage());
	}

}
