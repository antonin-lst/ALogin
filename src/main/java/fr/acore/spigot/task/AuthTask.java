package fr.acore.spigot.task;


import fr.acore.spigot.cryptographie.CryptoManager;
import fr.acore.spigot.manager.ALoginManager;
import fr.acore.spigot.utils.AuthPlayer;

public abstract class AuthTask implements Runnable{

	protected ALoginManager manager;
	protected AuthPlayer authPlayer;
	protected CryptoManager cryptoManager;
	
	public AuthTask(ALoginManager manager, AuthPlayer authPlayer) {
		this.manager = manager;
		this.authPlayer = authPlayer;
		this.cryptoManager = manager.getPlugin().getManager(CryptoManager.class);
	}
	
	@Override
	public void run() {
		auth();
	}
	
	public abstract void auth();
	
}
