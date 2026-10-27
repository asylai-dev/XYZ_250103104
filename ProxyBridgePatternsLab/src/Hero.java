public abstract class Hero {
    protected Weapon weapon;
    public Hero(Weapon weapon) { this.weapon = weapon; }
    public abstract void attack();
}
