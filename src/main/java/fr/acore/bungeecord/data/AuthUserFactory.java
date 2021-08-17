package fr.acore.bungeecord.data;

import fr.acore.bungeecord.manager.ALoginManager;
import fr.acore.bungeecord.storage.factory.DataFactory;

public class AuthUserFactory extends DataFactory<AuthUserStorage, ALoginManager> {


    public AuthUserFactory(ALoginManager manager) {
        super(AuthUserStorage.class, manager);
    }

    @Override
    public void loadAll() {

    }

    @Override
    public void saveAll() {

    }

    @Override
    public void save(AuthUserStorage obj) {

    }
}
