package fr.acore.bungeecord.data;


import fr.acore.bungeecord.api.storage.column.Column;
import fr.acore.bungeecord.api.storage.table.Table;

@Table(name = "playerStorage")
public class AuthUserStorage {

    @Column(primary = true, isUnique = true)
    private String uuid;

    @Column()
    private String name;

    @Column()
    private int kills;

    @Column()
    private int morts;

    @Column()
    private String encryptedPassword;

    @Column()
    private boolean premium;

    @Column()
    private String premiumUuid;

    public AuthUserStorage(){

    }

    public AuthUserStorage(String uuid, String name){
        this.uuid = uuid;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public String getPremiumUuid() {
        return premiumUuid;
    }

    public void setPremiumUuid(String premiumUuid) {
        this.premiumUuid = premiumUuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
