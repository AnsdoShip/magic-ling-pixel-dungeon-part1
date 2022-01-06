/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
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

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Badges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Cripple;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.HalomethaneBurning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Ooze;
import com.shatteredpixel.shatteredpixeldungeon.effects.Chains;
import com.shatteredpixel.shatteredpixeldungeon.effects.Pushing;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.keys.SkeletonKey;
import com.shatteredpixel.shatteredpixeldungeon.items.quest.GooBlob;
import com.shatteredpixel.shatteredpixeldungeon.mechanics.Ballistica;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SlimeKingSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.BossHealthBar;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.PathFinder;
import com.watabou.utils.Random;

public class SlimeKing extends Golem {

	{
		HP =120;
		HT= 140;
		EXP = 20;
		defenseSkill = 12;
		spriteClass = SlimeKingSprite.class;
		properties.add(Property.LARGE);
		lootChance = 1;
		HUNTING = new Hunting();
		properties.add(Property.BOSS);
	}

	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 5, 8 );
	}

	@Override
	public int attackSkill( Char target ) {
		return 12;
	}

	@Override
	public int drRoll() {
		return Random.NormalIntRange(5, 2);
	}

    private int delay = 0;

	private boolean canTryToSummon() {

		int ratCount = 0;
		for (Mob mob : Dungeon.level.mobs.toArray(new Mob[0])){

			if (mob instanceof Rat){
				ratCount++;
			}
		}
		if (ratCount < 3 && delay <= 0) {
			return true;
		} else {
			return false;
		}
	}

	private int combo = 0;
	@Override
	protected boolean getCloser( int target ) {
		combo = 0; //if he's moving, he isn't attacking, reset combo.
		if (state == HUNTING) {
			return enemySeen && getFurther( target );
		} else {
			return super.getCloser( target );
		}
	}

	@Override
	public boolean attack( Char enemy ) {
		if (canAttack(enemy) && canTryToSummon()) {
			return true;
		} else if(canAttack(enemy)){
			spend( attackDelay()*7f );
			summon();
			return super.attack(enemy);
		}else if(canAttack(enemy)) {
			return super.attack(enemy);
		}
		else return false;
	}
	private boolean chainsUsed = false;
	private boolean chain(int target){
		if (chainsUsed || enemy.properties().contains(Property.IMMOVABLE))
			return false;

		Ballistica chain = new Ballistica(pos, target, Ballistica.PROJECTILE);

		if (chain.collisionPos != enemy.pos
				|| chain.path.size() < 2
				|| Dungeon.level.pit[chain.path.get(1)])
			return false;
		else {
			int newPos = -1;
			for (int i : chain.subPath(1, chain.dist)){
				if (!Dungeon.level.solid[i] && Actor.findChar(i) == null){
					newPos = i;
					break;
				}
			}

			if (newPos == -1){
				return false;
			} else {
				final int newPosFinal = newPos;
				this.target = newPos;

				if (sprite.visible) {
					yell(Messages.get(this, "scorpion"));
					new Item().throwSound();
					Sample.INSTANCE.play(Assets.Sounds.CHAINS);
					sprite.parent.add(new Chains(sprite.center(), enemy.sprite.center(), new Callback() {
						public void call() {
							Actor.addDelayed(new Pushing(enemy, enemy.pos, newPosFinal, new Callback() {
								public void call() {
									pullEnemy(enemy, newPosFinal);
								}
							}), -1);
							next();
						}
					}));
				} else {
					pullEnemy(enemy, newPos);
				}
			}
		}
		chainsUsed = true;
		return true;
	}

	private void pullEnemy( Char enemy, int pullPos ){
		enemy.pos = pullPos;
		Dungeon.level.occupyCell(enemy);
		Cripple.prolong(enemy, Cripple.class, 4f);
		if (enemy == hero) {
			hero.interrupt();
			Dungeon.observe();
			GameScene.updateFog();
		}
	}
	private int var2;
    @Override
    public int attackProc( Char enemy, int damage ) {
		combo++;

        if (Random.Int( 2 ) == 0) {
            Buff.affect( enemy, Ooze.class ).set( Ooze.DURATION );
            enemy.sprite.burst( 0x000000, 5 );
        }

        return super.attackProc( enemy, damage );
    }

	@Override
	public void move( int step ) {
		Dungeon.level.seal();
		super.move( step );
	}
    private int summonCooldown = 10;

	private void summon() {

		delay = (int)(summonCooldown - (5 - 2 * (float)HP/HT));

		sprite.centerEmitter().start( Speck.factory( Speck.SCREAM ), 0.4f, 2 );
		Sample.INSTANCE.play( Assets.Sounds.CHALLENGE );

		for(int i = 0;i <= (1 + (1 -(float)HP/HT)) ; i++){
			int newPos = 0;
			do {
				newPos = Random.Int(Dungeon.level.length());
			} while (
					Dungeon.level.solid[newPos] ||
							Dungeon.level.distance(newPos, enemy.pos) < 4 ||
							Actor.findChar(newPos) != null);
			if (Random.Int(1000) <= 500){
				Slime_Red rat = new Slime_Red();
				rat.state = rat.WANDERING;
				rat.pos = newPos;
				GameScene.add(rat);
				rat.beckon(pos );
			}else if(Random.Int(500) <= 250){
				Slime_Orange rat = new Slime_Orange();
				rat.state = rat.WANDERING;
				rat.pos = newPos;
				GameScene.add(rat);
				rat.beckon(pos );
			}else if(Random.Int(250) <= 125){
				Slime_Lt rat = new Slime_Lt();
				rat.state = rat.WANDERING;
				rat.pos = newPos;
				GameScene.add(rat);
				rat.beckon(pos );
			}else if(Random.Int(125) <= 75){
				Slime_Qs rat = new Slime_Qs();
				rat.state = rat.WANDERING;
				rat.pos = newPos;
				GameScene.add(rat);
				rat.beckon(pos );
			}else if(Random.Int(75) <= 37){
				Slime_Lg rat = new Slime_Lg();
				rat.state = rat.WANDERING;
				rat.pos = newPos;
				GameScene.add(rat);
				rat.beckon(pos );
			}else if(Random.Int(37) <= 18){
				Slime_Sn rat = new Slime_Sn();
				rat.state = rat.WANDERING;
				rat.pos = newPos;
				GameScene.add(rat);
				rat.beckon(pos );
			}else{
				Slime_Sz rat = new Slime_Sz();
				rat.state = rat.WANDERING;
				rat.pos = newPos;
				GameScene.add(rat);
				rat.beckon(pos );
			}
		}

		yell( Messages.get(this, "arise") );
	}

	@Override
	public void notice() {
		super.notice();
		BossHealthBar.assignBoss(this);
		Music.INSTANCE.play(Assets.BGM_BOSSA, true);
		yell( Messages.get(this, "notice") );
	}

		@Override
	public void die( Object cause ) {

		super.die( cause );

		Dungeon.level.unseal();

		GameScene.bossSlain();
		Dungeon.level.drop( new SkeletonKey( Dungeon.depth ), pos ).sprite.drop();

		//60% chance of 2 blobs, 30% chance of 3, 10% chance for 4. Average of 2.5
		int blobs = Random.chances(new float[]{0, 0, 6, 3, 1});
		for (int i = 0; i < blobs; i++){
			int ofs;
			do {
				ofs = PathFinder.NEIGHBOURS8[Random.Int(8)];
			} while (!Dungeon.level.passable[pos + ofs]);
			Dungeon.level.drop( new GooBlob(), pos + ofs ).sprite.drop( pos );
		}

		Badges.validateBossSlain();
			Badges.KILLSLIMKING();
		yell( Messages.get(this, "defeated") );
			for (Mob mob : (Iterable<Mob>)Dungeon.level.mobs.clone()) {
				if (	mob instanceof Slime_Lg||
						mob instanceof Slime_Qs||
						mob instanceof Slime_Sn||
						mob instanceof Slime_Sz||
						mob instanceof Slime_Lt||
						mob instanceof Slime_Red||
						mob instanceof Slime_Orange) {
					mob.die( cause );
				}
			}
	}

    @Override
    public void rollToDropLoot() {
        if (hero.lvl > maxLvl + 2) return;

        super.rollToDropLoot();

        int ofs;
        do {
            ofs = PathFinder.NEIGHBOURS8[Random.Int(8)];
        } while (!Dungeon.level.passable[pos + ofs]);
        Dungeon.level.drop( new GooBlob(), pos + ofs ).sprite.drop( pos );
    }

	private class Hunting extends Mob.Hunting{
		@Override
		public boolean act( boolean enemyInFOV, boolean justAlerted ) {
			enemySeen = enemyInFOV;
			//放风筝必死 恼
			//140血强制更新玩家血量为1 赋予燃烧 失明 流血 弱化
			if (++HP+1 >= 141){
				hero.HP = 	1;
				Buff.affect(hero, HalomethaneBurning.class).reignite(hero);
				GLog.b( Messages.get(this, "cus") );
			}
			if (!chainsUsed
					&& enemyInFOV
					&& !isCharmedBy( enemy )
					&& !canAttack( enemy )
					&& Dungeon.level.distance( pos, enemy.pos ) < 3
					&& Random.Int(3) == 0

					&& chain(enemy.pos)){
				return !(sprite.visible || enemy.sprite.visible);
			} else {
				return super.act( enemyInFOV, justAlerted );
			}

		}
	}
}
