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

package com.shatteredpixel.shatteredpixeldungeon;

import com.shatteredpixel.shatteredpixeldungeon.items.Ankh;
import com.shatteredpixel.shatteredpixeldungeon.items.Dewdrop;
import com.shatteredpixel.shatteredpixeldungeon.items.Gold;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClassArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.ClothArmor;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.HornOfPlenty;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Blandfruit;
import com.shatteredpixel.shatteredpixeldungeon.items.food.SmallRation;
import com.shatteredpixel.shatteredpixeldungeon.items.potions.PotionOfHealing;
import com.shatteredpixel.shatteredpixeldungeon.scenes.InterlevelScene;

public class Challenges {


	//Some of these internal IDs are outdated and don't represent what these challenges do
	public static final int NO_FOOD = 1;
	public static final int NO_ARMOR = 2;
	public static final int NO_HEALING = 4;
	public static final int NO_HERBALISM = 8;
	public static final int SWARM_INTELLIGENCE = 16;
	public static final int DARKNESS = 32;
	public static final int NO_SCROLLS = 64;
	public static final int AQUAPHOBIA = 128;
	public static final int CHAMPION_ENEMIES = 256;
	public static final int NO_GOLD = 512;
	public static final int JGSF = 1024;
	public static final int LYSL = 2048;
	public static final int RLPT = 4096;
	public static final int PRO = 8192;
	public static final int HAPPY = 16384;

	public static final int MAX_VALUE = 32768;

	public static final String[] NAME_IDS = {
			"no_food",
			"no_armor",
			"no_healing",
			"no_herbalism",
			"swarm_intelligence",
			"darkness",
			"no_scrolls",
			"aquaphobia",
			"champion_enemies",
			"no_gold",
			"jgsf",
			"lysl",
			"rlpt",
			"pro",
			"happy",
	};

	public static final int[] MASKS = {
			NO_FOOD, NO_ARMOR, NO_HEALING, NO_HERBALISM, SWARM_INTELLIGENCE, DARKNESS, NO_SCROLLS
			, AQUAPHOBIA, CHAMPION_ENEMIES, NO_GOLD, JGSF,LYSL, RLPT,PRO,HAPPY
	};
	public String name;

	public static boolean isItemBlocked(Item item) {
		if (Dungeon.isChallenged(NO_FOOD)) {
			if (item instanceof SmallRation) {
				return true;
			} else if (item instanceof HornOfPlenty) {
				return true;
			}
		}

		if(InterlevelScene.mode == InterlevelScene.Mode.RESET){
			if (item instanceof Ankh) {
				return true;
			}
		}

		if (Dungeon.isChallenged(NO_ARMOR)) {
			if (item instanceof Armor && !(item instanceof ClothArmor || item instanceof ClassArmor)) {
				return true;
			}
		}

		if (Dungeon.isChallenged(NO_HEALING)) {
			if (item instanceof PotionOfHealing) {
				return true;
			} else if (item instanceof Blandfruit
					&& ((Blandfruit) item).potionAttrib instanceof PotionOfHealing) {
				return true;
			}
		}

		if (Dungeon.isChallenged(NO_GOLD)) {
			if (item instanceof Gold) {
				return true;
			}
			if (Dungeon.isChallenged(NO_GOLD)) {
				if (item instanceof Ankh) {
					return true;
				}
			}
			if (Dungeon.isChallenged(NO_HERBALISM)) {
				if (item instanceof Dewdrop) {
					return true;
				}
			}
		}

		return false;

	}

	public static int activeChallenges(){
		int chCount = 0;
		for (int ch : Challenges.MASKS){
			if ((Dungeon.challenges & ch) != 0) chCount++;
		}
		return chCount;
	}
}