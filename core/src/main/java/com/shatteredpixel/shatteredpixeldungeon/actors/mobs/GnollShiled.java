package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Poison;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.AlchemicalCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.BombGnollTricksterSprites;
import com.watabou.utils.Bundle;
import com.watabou.utils.Random;

public class GnollShiled extends Gnoll {
    private static final String COMBO = "combo";
    private int combo = 0;

    public GnollShiled() {
        this.spriteClass = BombGnollTricksterSprites.class;
        this.HT = HP = Random.Int(50,150);
        this.defenseSkill = 5;
        this.EXP = 8;
        this.state = this.WANDERING;
        this.loot = AlchemicalCatalyst.class;
        this.lootChance = 0.3f;
        this.properties.add(Char.Property.MINIBOSS);
    }

    public int attackSkill(Char target) {
        return 12;
    }

    protected boolean canAttack(Char enemy) {
        return !Dungeon.level.adjacent(this.pos, enemy.pos) && new Ballistica(this.pos, enemy.pos, 7).collisionPos.intValue() == enemy.pos;
    }

    public int attackProc(Char enemy, int damage) {
        int damage2 = GnollShiled.super.attackProc(enemy, damage);
        this.combo++;
        int effect = Random.Int(4) + this.combo;
        if (effect > 2) {
            switch (Random.Int(3)) {
                case 0:
                    Buff.affect(enemy, Blindness.class).set((float) (effect - 2));
                    break;
                case 1:
                    Buff.affect(enemy, Poison.class).set((float) (effect - 2));
                    break;
                case 2:
                    Buff.affect(enemy, Bleeding.class).set((float) (effect - 2));
                    break;
                default:
                    Buff.affect(enemy, Poison.class).set((float) (effect - 2));
                    break;
            }

        }
        return damage2;
    }

    protected boolean getCloser(int target) {
        this.combo = 0;
        if (this.state != this.HUNTING) {
            return GnollShiled.super.getCloser(target);
        }
        if (!this.enemySeen || !getFurther(target)) {
            return false;
        }
        return true;
    }

    public void die(Object cause) {
        GnollShiled.super.die(cause);
    }

    public void storeInBundle(Bundle bundle) {
        GnollShiled.super.storeInBundle(bundle);
        bundle.put("combo", this.combo);
    }

    public void restoreFromBundle(Bundle bundle) {
        GnollShiled.super.restoreFromBundle(bundle);
        this.combo = bundle.getInt("combo");
    }
}
