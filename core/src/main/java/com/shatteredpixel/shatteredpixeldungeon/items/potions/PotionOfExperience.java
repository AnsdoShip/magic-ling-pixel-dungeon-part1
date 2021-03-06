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

package com.shatteredpixel.shatteredpixeldungeon.items.potions;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Bleeding;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;

public class PotionOfExperience extends Potion {

	{
		icon = ItemSpriteSheet.Icons.POTION_EXP;

		bones = true;
	}

	@Override
	public String info() {
		if(Dungeon.isChallenged(Challenges.EXSG)){
			return isKnown() ? baddesc() : Messages.get(this, "unknown_desc");
		} else {
			return isKnown() ? desc() : Messages.get(this, "unknown_desc");
		}
	}
	
	@Override
	public void apply( Hero hero ) {
		identify();

		if (Dungeon.isChallenged(Challenges.EXSG)){
			Buff.affect(hero, Bleeding.class).set( 6 );
		} else {
			hero.earnExp( hero.maxExp(), getClass() );
		}

	}
	
	@Override
	public int value() {
		return isKnown() ? 50 * quantity : super.value();
	}
}
