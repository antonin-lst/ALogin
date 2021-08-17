package fr.acore.spigot;

import fr.acore.spigot.manager.ALoginManager;
import fr.acore.spigot.module.AModule;

public class ALogin extends AModule {

    @Override
    public void onEnable() {
        super.onEnable();
        registerManager(new ALoginManager(this));
        log("ALogin Enabled");
    }
}
