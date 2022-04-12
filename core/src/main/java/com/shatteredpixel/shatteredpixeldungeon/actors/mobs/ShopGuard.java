/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Healing;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ShadowParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.MailArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.StatueSprite;
import com.watabou.noosa.tweeners.AlphaTweener;
import com.watabou.utils.Bundle;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class ShopGuard extends Statue {

    {
        maxLvl = -200;
        state = HUNTING;
        spriteClass = StatueSprite.class;
        HP=HT=Random.Int(50, 100);
    }

    protected Armor armor;

    public ShopGuard(){
        super();

        do {
            weapon = (MeleeWeapon) Generator.random(Generator.Category.WEP_T5);
        } while (weapon.cursed);

        weapon.enchant( Weapon.Enchantment.random() );

        HP = HT = 15 + Dungeon.depth * 5;
        defenseSkill = 4 + Dungeon.depth;

        do {
            armor = new MailArmor();
        } while (armor.cursed);
            armor = new MailArmor();

        //double HP
    }

    private static final String ARMOR	= "armor";



    public void spawnAround( int pos ) {
        for (int n : PathFinder.NEIGHBOURS4) {
            int cell = pos + n;
            if (Dungeon.level.passable[pos+1] && Actor.findChar( cell ) == null) {
                spawnAt( cell );
            }
        }
    }

    public void spawnAroundDragon( int pos ) {
        for (int n : PathFinder.NEIGHBOURS8) {
            int cell = pos + n;
            if (Dungeon.level.passable[pos+1] && Actor.findChar( cell ) == null) {
                spawnAt( cell );
            }
        }
    }
    private static final float SPAWN_DELAY	= 2f;
    private int level;
    public void adjustStats( int level ) {
        this.level = level;
        defenseSkill = attackSkill( null ) * 5;
        enemySeen = true;
    }
    public static ShopGuard spawnAt( int pos ) {
        if (!Dungeon.level.solid[pos] && Actor.findChar( pos ) == null) {

            ShopGuard w = new ShopGuard();
            w.adjustStats( Dungeon.depth );
            w.pos = pos;
            w.state = w.HUNTING;
            GameScene.add( w, SPAWN_DELAY );

            w.sprite.alpha( 0 );
            w.sprite.parent.add( new AlphaTweener( w.sprite, 1, 0.5f ) );

            w.sprite.emitter().burst( ShadowParticle.CURSE, 5 );

            return w;
        } else {
            return null;
        }
    }
    @Override
    protected boolean act() {
        for (Buff buff : hero.buffs()) {
            if (buff instanceof Healing) {
                buff.detach();
                yell( Messages.get(this, "noha") );
            }
        }
        return super.act();
    }

    @Override
    public void storeInBundle( Bundle bundle ) {
        super.storeInBundle( bundle );
        bundle.put( ARMOR, armor );
    }

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        super.restoreFromBundle( bundle );
        armor = (Armor)bundle.get( ARMOR );
    }

    @Override
    public int drRoll() {
        return super.drRoll() + Random.NormalIntRange( armor.DRMin(), armor.DRMax());
    }

    @Override
    public int defenseProc(Char enemy, int damage) {
        return armor.proc(enemy, this, damage);
    }

    @Override
    public CharSprite sprite() {
        CharSprite sprite = super.sprite();
        ((StatueSprite)sprite).setArmor(armor.tier);
        return sprite;
    }

    @Override
    public float speed() {
        return armor.speedFactor(this, super.speed());
    }

    @Override
    public float stealth() {
        return armor.stealthFactor(this, super.stealth());
    }

    @Override
    public int defenseSkill(Char enemy) {
        return Math.round(armor.evasionFactor(this, super.defenseSkill(enemy)));
    }

    @Override
    public void die( Object cause ) {
        ShopGuard.super.die(cause);
    }

    @Override
    public String description() {
        return Messages.get(this, "desc", weapon.name(), armor.name());
    }



}
