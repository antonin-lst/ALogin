package fr.acore.spigot.commands;

import java.util.Arrays;

import fr.acore.spigot.api.command.CommandStats;
import fr.acore.spigot.api.command.sender.ICommandSender;
import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.commands.sender.CorePlayerSender;
import fr.acore.spigot.manager.ALoginManager;
import fr.acore.spigot.task.AuthTask;
import fr.acore.spigot.task.impl.RegisterAuthTask;
import fr.acore.spigot.utils.AuthPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class CommandRegister extends APlayerCommand {

	private ALoginManager manager;
	
	public CommandRegister(ALoginManager manager) {
		super("register");
		this.manager = manager;
		setAlliases(Arrays.asList("r", "reg"));
	}


	@Override
	public CommandStats performAPlayerCommand(CorePlayerSender sender, String... args) {
		Player player = sender.getSender().getPlayer();
		if(manager.containPlayer(player)) {
			
			AuthPlayer authPlayer = manager.getPlayer(player);
			if(authPlayer.needRegister()) {
				
				if(args.length < 2) return CommandStats.SYNTAX_ERROR;
				
				if(!args[0].equals(args[1])) {
					player.sendMessage(manager.getFailedNotSameMessage());
				}else {
					if(args[0].length() < manager.getMinRegisterLenght()) {
						player.sendMessage(manager.getFailToShotMessage());
						return CommandStats.SUCCESS;
					}
					
					AuthTask authTask = new RegisterAuthTask(manager, authPlayer, args[0]);

					((Plugin)manager.getPlugin()).getServer().getScheduler().runTaskAsynchronously((Plugin) manager.getPlugin(), authTask);
				}
				
			}else {
				player.sendMessage("Tu es deja inscrit /login <mdp>.");
			}
			
		}else {
			player.sendMessage("Tu es deja connectée");
		}
		return CommandStats.SUCCESS;
	}

	@Override
	public String getPermission() {
		return null;
	}

	@Override
	public String getSyntax() {
		return "register <mdp> <mdp>";
	}

}
