package fr.acore.spigot.manager;

import fr.acore.spigot.api.player.impl.CorePlayer;
import fr.acore.spigot.api.plugin.module.IModule;
import fr.acore.spigot.api.runnable.IRunnable;
import fr.acore.spigot.api.runnable.RunnableUsage;
import fr.acore.spigot.commands.CommandLogin;
import fr.acore.spigot.commands.CommandRegister;
import fr.acore.spigot.listener.ALoginListener;
import fr.acore.spigot.module.AManager;
import fr.acore.spigot.utils.AuthPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ALoginManager extends AManager implements IRunnable {

    private List<AuthPlayer> players;

    private CommandRegister cmdR;
    private CommandLogin cmdL;

    /*
     *
     * Auth
     *
     */
    private long timeToAuth;
    public long getTimeToAuth() { return this.timeToAuth;}
    private String kickToLongMessage;
    public String getKickToLongMessage() { return this.kickToLongMessage;}


    /*
     *
     * Premium Conf
     *
     */
    private boolean forcePremiumLogin;
    public boolean isForcePremiumLogin() { return this.forcePremiumLogin;}
    private String autoLoginMessage;
    public String getAutoLoginMessage() { return this.autoLoginMessage;}

    /*
     *
     * Login Conf
     *
     */
    private String loginMesage;
    public String getLoginMessage() { return this.loginMesage;}
    private int maxLoginAtent;
    public int getMaxLoginAtent() { return this.maxLoginAtent;}
    private String failLoginAtentMessage;
    public String getFailLoginAtentMessage() { return this.failLoginAtentMessage;}
    private String kickMaxAtentMessage;
    public String getKickMaxAtentMessage() { return this.kickMaxAtentMessage;}
    private String successLoginMessage;
    public String getSuccessLoginMessage() { return this.successLoginMessage;}

    /*
     *
     * Register Conf
     *
     */
    private String registerMessage;
    public String getRegisterMessage() { return this.registerMessage;}
    private String failedNotSameMessage;
    public String getFailedNotSameMessage() { return this.failedNotSameMessage;}
    private int minRegisterLenght;
    public int getMinRegisterLenght() { return this.minRegisterLenght;}
    private String failToShortMessage;
    public String getFailToShotMessage() { return this.failToShortMessage;}
    private List<String> deprecatedPassword;
    public List<String> getDeprecatedPassword() { return this.deprecatedPassword;}
    private String deprecatedMessage;
    public String getDeprecatedMessage() { return this.deprecatedMessage;}
    private String successRegisterMessage;
    public String getSuccessRegisterMessage() { return this.successRegisterMessage;}


    public ALoginManager(IModule key) {
        super(key, true);
        players = new ArrayList<>();
        registerListener(new ALoginListener(this));
        registerSyncRunnable(this);
        registerCommand(cmdR = new CommandRegister(this));
        registerCommand(cmdL = new CommandLogin(this));
    }

    @Override
    public void setup(FileConfiguration config) {
        this.timeToAuth = (long) config.getInt("auth.timeToAuth");
        this.kickToLongMessage = convertColor(config.getString("auth.kickToLongMessage"));

        this.forcePremiumLogin = config.getBoolean("premium.forceLogin");
        this.autoLoginMessage = convertColor(config.getString("premium.autoLoginMessage"));

        this.loginMesage = convertColor(config.getString("login.message"));
        this.maxLoginAtent = config.getInt("login.maxAtent");
        this.failLoginAtentMessage = convertColor(config.getString("login.failAtentMessage"));
        this.kickMaxAtentMessage = convertColor(config.getString("login.kickMaxAtentMessage"));
        this.successLoginMessage = convertColor(config.getString("login.successMessage"));

        this.registerMessage = convertColor(config.getString("register.message"));
        this.failedNotSameMessage = convertColor(config.getString("register.failedNotSameMessage"));
        this.minRegisterLenght = config.getInt("register.minLenght");
        this.failToShortMessage = convertColor(config.getString("register.failToShortMessage"));
        this.deprecatedPassword = config.getStringList("register.deprecatedPassword");
        this.deprecatedMessage = convertColor(config.getString("register.deprecatedMessage"));
        this.successRegisterMessage = convertColor(config.getString("register.successMessage"));

    }

    @Override
    public void ticks() {
        Iterator<AuthPlayer> player = players.iterator();
        while(player.hasNext()) {
            AuthPlayer current = player.next();
            System.out.println("test");
            System.out.println(getAutoLoginMessage());
            current.sendAuthMessage();
            if(current.isFinish()) {
                player.remove();
                current.getPlayer().getPlayer().kickPlayer(this.kickToLongMessage);
            }
        }
    }

    public List<String> getAuthCommands(){
        List<String> cmds = new ArrayList<>();
        cmds.add(cmdR.getName());
        cmds.addAll(cmdR.getAlliases());
        cmds.add(cmdL.getName());
        cmds.addAll(cmdL.getAlliases());
        return cmds;
    }

    public void addPlayer(AuthPlayer player) {
        this.players.add(player);
    }

    public boolean containPlayer(Player player) {
        return containPlayer(getCorePlayer(player));
    }

    public boolean containPlayer(CorePlayer player) {
        for(AuthPlayer p : players) {
            if(p.getPlayer().equals(player)) return true;
        }
        return false;
    }

    public void removePlayer(AuthPlayer player) {
        players.remove(player);
    }

    public AuthPlayer getPlayer(Player player) {
        return getPlayer(getCorePlayer(player));
    }

    public AuthPlayer getPlayer(CorePlayer player) {
        for(AuthPlayer p : players) {
            if(p.getPlayer().equals(player)) return p;
        }
        return null;
    }
}
