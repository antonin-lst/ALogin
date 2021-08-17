package fr.acore.bungeecord.listener;

import fr.acore.bungeecord.ACoreBungeeCordAPI;
import fr.acore.bungeecord.api.storage.constraint.query.QueryConstraintType;
import fr.acore.bungeecord.data.AuthUserFactory;
import fr.acore.bungeecord.data.AuthUserStorage;
import fr.acore.bungeecord.manager.ALoginManager;
import fr.acore.bungeecord.storage.constraint.QueryConstraint;
import fr.acore.bungeecord.task.PremiumCheckTask;
import fr.acore.bungeecord.utils.AuthUser;
import fr.acore.bungeecord.utils.PremiumUtils;
import net.md_5.bungee.api.connection.PendingConnection;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.PreLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.event.EventHandler;

public class ALoginListener implements Listener {

    private ALoginManager loginManager;

    public ALoginListener(ALoginManager loginManager){
        this.loginManager = loginManager;
    }

    @EventHandler
    public void onPreLoginEvent(PreLoginEvent event) {
        PendingConnection connection = event.getConnection();
        String username = connection.getName();
        String uuid = PremiumUtils.generateOfflineId(username).toString();
        AuthUser authUser = null;
        if(loginManager.getAuthUserFactory().contain("uuid = ?", uuid)){
            authUser = new AuthUser(loginManager.getAuthUserFactory().selectFirst(new QueryConstraint(QueryConstraintType.WHERE, "uuid = ?", uuid)), connection);
            System.out.println("Debug : " + authUser.isPremium());
            loginManager.addAuthUser(authUser);
            if(authUser.isPremium()) authUser.getConnection().setOnlineMode(true);
        }else{
            authUser = new AuthUser(new AuthUserStorage(uuid, username), connection);
            loginManager.addAuthUser(authUser);
            event.registerIntent((Plugin) loginManager.getPlugin());
            PremiumUtils.isPremiumName(username, new PremiumCheckTask(loginManager, authUser, event));
        }
    }


    @EventHandler
    public void onPostLoginEvent(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        AuthUser user = loginManager.getAuthUserByName(player.getName());

        if(user == null){
            System.out.println("Une erreur c'est produite pendant le chargement du joueur : " + event.getPlayer().getName());
            return;
        }

        if(user.isPremium() && (user.getOnlineUUID() == null ||user.getOnlineUUID().isEmpty())){
            user.setOnlineUUID(player.getUniqueId().toString());
            loginManager.getAuthUserFactory().update(user.getAuthUserStorage());
        }

        if(user.getUniqueUUID().isEmpty()){
            user.setUniqueUUID(PremiumUtils.generateOfflineId(player.getName()).toString());
            loginManager.getAuthUserFactory().update(user.getAuthUserStorage());
        }

        user.setPlayer(player);
		/*System.out.println("PlayerName : " + event.getPlayer().getName());
		System.out.println("PremiumUUID : " + event.getPlayer().getUniqueId());
		System.out.println("CrackedUUID : " + PremiumUtils.generateOfflineId(event.getPlayer().getName()));*/
    }

    @EventHandler
    public void playerQuitEvent(PlayerDisconnectEvent event) {
        ProxiedPlayer player = event.getPlayer();
        AuthUser user = loginManager.getAuthUserByName(player.getName());
        loginManager.removeAuthUser(user);
    }


}
