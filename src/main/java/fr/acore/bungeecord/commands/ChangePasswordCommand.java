package fr.acore.bungeecord.commands;

import fr.acore.bungeecord.manager.ALoginManager;
import fr.acore.bungeecord.task.ChangePasswordTask;
import fr.acore.bungeecord.utils.AuthUser;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class ChangePasswordCommand extends Command {

	private ALoginManager manager;
	
	public ChangePasswordCommand(ALoginManager manager) {
		super("changepassword", null, "changepass", "changpass", "changepasswd", "cpassword", "cpasswd", "cpass");
		this.manager = manager;
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(sender instanceof ProxiedPlayer) {
			ProxiedPlayer player = (ProxiedPlayer) sender;
			
			AuthUser aUser = manager.getAuthUserByName(player.getName());
			
			if(aUser.isPremium()) {
				player.sendMessage(new TextComponent(manager.getDeniedPremiumAccountMessage()));
			}else {
				
				if(args.length < 2) {
					player.sendMessage(new TextComponent(manager.getChangePassMessage()));
				}else {
					
					new ChangePasswordTask(manager, aUser, args[0], args[1], args[2]).auth();
					
				}
				
			}
			
		}
	}

}
