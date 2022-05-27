package com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Chill;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.effects.MagicMissile;
import com.shatteredpixel.shatteredpixeldungeon.items.food.FrozenCarpaccio;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.AlchemicalCatalyst;
import com.shatteredpixel.shatteredpixeldungeon.items.spells.MagicalInfusion;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.watabou.utils.Callback;

public class IceFishSword extends MeleeWeapon{


    {
        image = ItemSpriteSheet.ICEFISHSWORD;
        hitSound = Assets.Sounds.HIT_CRUSH;
        hitSoundPitch = 1f;
        tier = 6;
        ACC = 2.90f; //20% boost to accuracy
        DLY = 0.7f; //2x speed
    }

    public static class Recipe extends com.shatteredpixel.shatteredpixeldungeon.items.Recipe.SimpleRecipe {

        {
            inputs =  new Class[]{FrozenCarpaccio.class, MagicalInfusion.class, AlchemicalCatalyst.class};
            inQuantity = new int[]{1, 1, 1};

            cost = 9+Dungeon.depth/2;

            output = IceFishSword.class;
            outQuantity = 1;
        }

    }

    public void bolt(Integer target, final Mob mob){
        if (target != null) {

            final Ballistica shot = new Ballistica( Dungeon.hero.pos, target, Ballistica.PROJECTILE);

            fx(shot, new Callback() {
                public void call() {
                    onHit(shot, mob);
                }
            });
        }
    }

    protected void onHit(Ballistica bolt, Mob mob) {

        //presses all tiles in the AOE first

        if (mob != null){

            if (mob.isAlive() && bolt.path.size() > bolt.dist+1) {
                Buff.prolong(mob, Chill.class, Chill.DURATION/2f);
                Buff.affect(mob, Bleeding.class).set((float) (4));
            }
        }

    }

    protected void fx(Ballistica bolt, Callback callback) {
        MagicMissile.boltFromChar( Dungeon.hero.sprite.emitter(), MagicMissile.WIND, Dungeon.hero.sprite, bolt.collisionPos,
                callback);
    }

    public int proc(Char attacker, Char defender, int damage) {
        if(attacker instanceof Hero){
            for(Mob mob : ((Hero) attacker).visibleEnemiesList()){
                bolt(mob.pos, mob);
            }
        }

        return super.proc(attacker, defender, damage);
    }
}
