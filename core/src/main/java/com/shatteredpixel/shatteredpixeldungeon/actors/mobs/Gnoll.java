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

import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CharSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.GnollSprite;
import com.watabou.utils.Random;

public class Gnoll extends Mob {
	
	{
		spriteClass = GnollSprite.class;
		
		HP = HT = 12;
		defenseSkill = 4;
		
		EXP = 2;
		maxLvl = 8;
		
		loot = Gold.class;
		lootChance = 0.5f;
	}

	/*@Override
	public void damage( int dmg, Object src ) {
		//半血狂暴
		super.damage( dmg, src );
		if (HP <= HT / 2) {
			destroy();
			Mob mob = new OGPDLLS();
			mob.HP = mob.HT / 2;
			mob.pos = pos;
			GameScene.add(mob);
		}
	}*/

	private String[] attackRandom = {"你们这些肮脏的人类", "扭曲的野兽", "滚开！", "你们毁了我的家园！", "你这个狡猾的人类！", "人类，肮脏的人类！", "去死吧！！！",
			"300" +
					"年前，这里曾经是一片祥和"};

	private int combo = 0;
	public int attackProc(Char enemy, int damage) {
		//随机0-10 大于7返回下面的数据
		//现在让他必定为true 且 是随机话语
		if (1==1) {
			//他将会返回attackRandom的随机字符串
			this.sprite.showStatus( CharSprite.NEGATIVE,
					this.attackRandom[Random.Int(this.attackRandom.length)] );
			//this.sprite.showStatus( CharSprite.NEGATIVE, "你这怪物" );
		}
		int damage2 = Gnoll.super.attackProc(enemy, this.combo + damage);
		this.combo++;
		return damage2;
	}

	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 1, 6 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 10;
	}
	
	@Override
	public int drRoll() {
		return Random.NormalIntRange(0, 2);
	}
}
