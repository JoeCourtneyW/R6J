package main.declarations;

public enum Weapon {
    ASSAULT_RIFLE(0, "Assault Rifle"),
    SUBMACHINE_GUN(1, "Submachine Gun"),
    MARKSMAN_RIFLE(2, "Marksman Rifle"),
    SHOTGUN(3, "Shotgun"),
    SNIPER(4, "Sniper"),
    HANDGUN(5, "Handgun"),
    LIGHT_MACHINE_GUN(6, "Light Machine Gun"),
    MACHINE_PISTOL(7, "Machine Pistol"),
    SHIELD(8, "Shield"),
    LAUNCHER(9, "Launcher");

    private int weaponId;
    private String name;

    Weapon(int weaponId, String name){
        this.weaponId = weaponId;
        this.name = name;
    }

    public int getWeaponId() {
        return weaponId;
    }

    public String getName() {
        return name;
    }

    public Weapon getByWeaponId(int id){
        return values()[id];
    }

    public String getStatisticName(String statistic) {
        return "weapontypepvp_" + statistic + ":" + weaponId + ":infinite";
    }
}
