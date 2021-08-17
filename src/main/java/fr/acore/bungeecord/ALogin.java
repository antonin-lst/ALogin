package fr.acore.bungeecord;

import fr.acore.bungeecord.manager.ALoginManager;
import fr.acore.bungeecord.module.AModule;

public class ALogin extends AModule {

    @Override
    public void onEnable() {
        super.onEnable();
        registerManager(new ALoginManager(this));
        log("ALogin Enabled");
    }
}
