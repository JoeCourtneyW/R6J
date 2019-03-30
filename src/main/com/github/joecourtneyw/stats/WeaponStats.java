package main.com.github.joecourtneyw.stats;

import main.com.github.joecourtneyw.declarations.Weapon;

public class WeaponStats implements Comparable<WeaponStats> {

    private Weapon weapon;
    private int kills;
    private int headshots;
    private int bulletsFired;
    private int bulletsHit;

    public WeaponStats(Weapon weapon, int kills, int headshots, int bulletsFired, int bulletsHit) {
        this.weapon = weapon;
        this.kills = kills;
        this.headshots = headshots;
        this.bulletsFired = bulletsFired;
        this.bulletsHit = bulletsHit;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public int getKills() {
        return kills;
    }

    public int getHeadshots() {
        return headshots;
    }

    public int getBulletsFired() {
        return bulletsFired;
    }

    public int getBulletsHit() {
        return bulletsHit;
    }

    public double getAccuracy() {
        return bulletsHit / (bulletsFired * 1.0);
    }

    public int compareTo(WeaponStats otherWeapon) {
        return otherWeapon.getKills() - getKills();
    }
}
