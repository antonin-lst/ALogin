package fr.acore.spigot.commands;

import java.util.Arrays;

import fr.acore.spigot.api.command.CommandStats;
import fr.acore.spigot.api.command.sender.ICommandSender;
import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.commands.sender.CorePlayerSender;
import fr.acore.spigot.manager.ALoginManager;
import fr.acore.spigot.task.AuthTask;
import fr.acore.spigot.task.impl.LoginAuthTask;
import fr.acore.spigot.utils.AuthPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


public class CommandLogin extends APlayerCommand{
	
	private ALoginManager manager;
	
	public CommandLogin(ALoginManager manager) {
		super("login");
		setAlliases(Arrays.asList("l", "log"));
		this.manager = manager;
	}

	@Override
	public CommandStats performAPlayerCommand(CorePlayerSender sender, String... args) {
		Player player = sender.getSender().getPlayer();

		if(manager.containPlayer(player)) {
			
			AuthPlayer authPlayer = manager.getPlayer(player);
			if(!authPlayer.needRegister()) {
				
				if(args.length < 1) return CommandStats.SYNTAX_ERROR;
				
				AuthTask authTask = new LoginAuthTask(manager, authPlayer, args[0]);
				((Plugin)manager.getPlugin()).getServer().getScheduler().runTaskAsynchronously((Plugin) manager.getPlugin(), authTask);
				
			}else {
				player.sendMessage("Tu n'es pas inscrit /register <mdp> <mdp>.");
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
		return "login <mdp>";
	}

}
