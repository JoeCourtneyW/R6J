package declarations;

public enum Weapon {
    ASSAULT_RIFLE(0, "Assault Rifle"),
    SUBMACHINE_GUN(1, "Submachine Gun"),
    MARKSMAN_RIFLE(2, "Marksman Rifle"),
    SHOTGUN(3, "Shotgun"),
    HANDGUN(4, "Handgun"),
    LIGHT_MACHINE_GUN(5, "Light Machine Gun"),
    MACHINE_PISTOL(6, "Machine Pistol");

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
}
