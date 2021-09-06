package fr.acore.spigot.listener;

import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.api.player.impl.OfflineCorePlayer;
import fr.acore.spigot.api.runnable.IRunnable;
import fr.acore.spigot.api.runnable.RunnableUsage;
import fr.acore.spigot.event.PlayerAuthSuccessEvent;
import fr.acore.spigot.event.manager.EventWrapperManager;
import fr.acore.spigot.manager.ALoginManager;
import fr.acore.spigot.player.manager.PlayerManager;
import fr.acore.spigot.utils.AuthPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ALoginListener implements Listener {

    private ALoginManager loginManager;

    private List<CorePlayer<?>> inLoadingPlayer;

    public ALoginListener(ALoginManager loginManager){
        this.loginManager = loginManager;
        inLoadingPlayer = new ArrayList<>();
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        CorePlayer<?> player = loginManager.getManager(PlayerManager.class).getOnlinePlayer(event.getPlayer().getUniqueId());

        System.out.println("Debug : " + player.isPremium());

        event.getPlayer().getInventory().clear();
        auth((CorePlayer<?>) player);

    }

    @EventHandler
    public void onProcessCommand(PlayerCommandPreprocessEvent event) {

        Player player = event.getPlayer();
        CorePlayer<?> cp = loginManager.getCorePlayer(player);
        String root = event.getMessage().contains(" ") ? event.getMessage().split(" ")[0] : event.getMessage();
        root = root.replaceFirst("/", "");
        System.out.println("debug : " + root);
        if(inLoadingPlayer.contains(cp) || loginManager.containPlayer(player)) {
            if(!loginManager.getAuthCommands().contains(root)) {
                player.sendMessage("houla tu fait quoi");
                event.setCancelled(true);
            }
        }


    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        AuthPlayer player = loginManager.getPlayer(event.getPlayer());
        loginManager.removePlayer(player);
    }



    private void auth(CorePlayer<?> player) {
        //System.out.println("Debug auth : " + String.valueOf(player.isPremium()));
        if(!player.isPremium()) {
            //System.out.println("wtf is that");
            loginManager.addPlayer(new AuthPlayer(loginManager, player, loginManager.getTimeToAuth()));
        }else {
            player.sendMessage(loginManager.getAutoLoginMessage());
            loginManager.getManager(EventWrapperManager.class).call(new PlayerAuthSuccessEvent(player, true));

        }
    }


}
