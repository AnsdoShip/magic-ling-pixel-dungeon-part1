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

package com.shatteredpixel.shatteredpixeldungeon.levels.rooms.standard;

import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.books.bookslist.BrokenBooks;
import com.shatteredpixel.shatteredpixeldungeon.items.books.bookslist.DeepBloodBooks;
import com.shatteredpixel.shatteredpixeldungeon.items.books.bookslist.GrassKingBooks;
import com.shatteredpixel.shatteredpixeldungeon.items.books.bookslist.IceCityBooks;
import com.shatteredpixel.shatteredpixeldungeon.items.books.bookslist.MagicGirlBooks;
import com.shatteredpixel.shatteredpixeldungeon.items.books.bookslist.YellowSunBooks;
import com.shatteredpixel.shatteredpixeldungeon.items.food.Food;
import com.shatteredpixel.shatteredpixeldungeon.levels.Level;
import com.shatteredpixel.shatteredpixeldungeon.levels.Terrain;
import com.shatteredpixel.shatteredpixeldungeon.levels.painters.Painter;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class StudyRoom extends StandardRoom {

	@Override
	public int minWidth() {
		return Math.max(super.minWidth(), 7);
	}

	@Override
	public int minHeight() {
		return Math.max(super.minHeight(), 7);
	}

	@Override
	public float[] sizeCatProbs() {
		return new float[]{2, 1, 0};
	}

	@Override
	public void paint(Level level) {
		Painter.fill( level, this, Terrain.WALL );
		Painter.fill( level, this, 1 , Terrain.BOOKSHELF );
		Painter.fill( level, this, 2 , Terrain.EMPTY_SP );

		for (Door door : connected.values()) {
			Painter.drawInside(level, this, door, 2, Terrain.EMPTY_SP);
			door.set( Door.Type.REGULAR );
		}

		//TODO add support for giant size as well
		if (sizeCat == SizeCategory.LARGE){
			int pillarW = (width()-7)/2;
			int pillarH = (height()-7)/2;

			Painter.fill(level, left+3, top+3, pillarW, 1, Terrain.BOOKSHELF);
			Painter.fill(level, left+3, top+3, 1, pillarH, Terrain.BOOKSHELF);

			Painter.fill(level, left+3, bottom-2-1, pillarW, 1, Terrain.BOOKSHELF);
			Painter.fill(level, left+3, bottom-2-pillarH, 1, pillarH, Terrain.BOOKSHELF);

			Painter.fill(level, right-2-pillarW, top+3, pillarW, 1, Terrain.BOOKSHELF);
			Painter.fill(level, right-2-1, top+3, 1, pillarH, Terrain.BOOKSHELF);

			Painter.fill(level, right-2-pillarW, bottom-2-1, pillarW, 1, Terrain.BOOKSHELF);
			Painter.fill(level, right-2-1, bottom-2-pillarH, 1, pillarH, Terrain.BOOKSHELF);
		}

		Point center = center();
		Painter.set( level, center, Terrain.PEDESTAL );

		Item prize = (Random.Int(2) == 0) ? level.findPrizeItem() : null;

		if (prize != null) {
			switch (Random.Int(6)) {
				case 0:
				default:
					level.drop(new Food().quantity(3),(center.x + center.y * level.width()));
					//保底3个食物
					break;
				case 1:
					level.drop(new DeepBloodBooks(),(center.x + center.y * level.width()));
					break;
				case 2:
					level.drop(new GrassKingBooks(),(center.x + center.y * level.width()));
					break;
				case 3:
					level.drop(new YellowSunBooks(),(center.x + center.y * level.width()));
					break;
				case 4:
					level.drop(new MagicGirlBooks(),(center.x + center.y * level.width()));
					break;
				case 5:
					level.drop(new IceCityBooks(),(center.x + center.y * level.width()));
					break;
				case 6:
					level.drop(new BrokenBooks(),(center.x + center.y * level.width()));
					break;

			}

		}
	}
}
