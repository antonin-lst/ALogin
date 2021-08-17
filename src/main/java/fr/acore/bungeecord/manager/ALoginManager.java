package fr.acore.bungeecord.manager;


import fr.acore.bungeecord.commands.ChangePasswordCommand;
import fr.acore.bungeecord.data.AuthUserFactory;
import fr.acore.bungeecord.listener.ALoginListener;
import fr.acore.bungeecord.module.AManager;
import fr.acore.bungeecord.module.AModule;
import fr.acore.bungeecord.utils.AuthUser;
import net.md_5.bungee.config.Configuration;

import java.util.ArrayList;
import java.util.List;

public class ALoginManager extends AManager {

    private AuthUserFactory authUserFactory;
    public AuthUserFactory getAuthUserFactory(){ return this.authUserFactory;}

    private List<AuthUser> authUsers;
    public List<AuthUser> getAuthUsers(){ return this.authUsers;}

    private String changePassMessage;
    public String getChangePassMessage() { return this.changePassMessage;}

    private String deniedPremiumAccountMessage;
    public String getDeniedPremiumAccountMessage() { return this.deniedPremiumAccountMessage;}

    private String failWrondPassMessage;
    public String getFailWrondPassMessage() { return this.failWrondPassMessage;}

    private String failToShortPassMessage;
    public String getFailToShortPassMessage() { return this.failToShortPassMessage;}

    private String failNotSamePassMessage;
    public String getFailNotSamePassMessage() { return this.failNotSamePassMessage;}

    private String failSamePassBefore;
    public String getFailSamePassBefore() { return this.failSamePassBefore;}

    private String changePassSuccessMessage;
    public String getChangePassSuccessMessage() { return this.changePassSuccessMessage;}


    public ALoginManager(AModule key) {
        super(key, true);
        this.authUsers = new ArrayList<>();
        registerDataFactory(authUserFactory = new AuthUserFactory(this));
        registerListener(new ALoginListener(this));
        key.getProxy().getPluginManager().registerCommand(key, new ChangePasswordCommand(this));
    }

    @Override
    public void setup(Configuration config) {
        changePassMessage = convertColor(config.getString("changepass.message"));
        deniedPremiumAccountMessage = convertColor(config.getString("changepass.deniedPremiumAccount"));
        failWrondPassMessage = convertColor(config.getString("changepass.failWrongPass"));
        failToShortPassMessage = convertColor(config.getString("changepass.failToShort"));
        failNotSamePassMessage = convertColor(config.getString("changepass.failNotSame"));
        failSamePassBefore = convertColor(config.getString("changepass.failSameBefore"));
        changePassSuccessMessage = convertColor(config.getString("changepass.success"));

    }

    public void addAuthUser(AuthUser user) {
        this.authUsers.add(user);
    }

    public void removeAuthUser(AuthUser user) {
        this.authUsers.remove(user);
    }

    public boolean containAuthUserByName(String name) {
        for(AuthUser user : authUsers) {
            if(user.getName().equals(name)) return true;
        }
        return false;
    }

    public AuthUser getAuthUserByName(String name) {
        for(AuthUser user : authUsers) {
            if(user.getName().equals(name)) return user;
        }
        return null;
    }

    public boolean containAuthUserByUUID(String uuid) {
        for(AuthUser user : authUsers) {
            if(user.getUniqueUUID().equals(uuid)) return true;
        }
        return false;
    }

    public AuthUser getAuthUserByUUID(String uuid) {
        for(AuthUser user : authUsers) {
            if(user.getUniqueUUID().equals(uuid)) return user;
        }
        return null;
    }



}
