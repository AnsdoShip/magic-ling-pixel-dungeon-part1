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

package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.watabou.noosa.MovieClip;
import com.watabou.noosa.TextureFilm;
//红龙
public class RedDragonSprite extends MobSprite {

    public RedDragonSprite() {
        super();

        texture( Assets.Sprites.REDDRAGONPET );

        TextureFilm frames = new TextureFilm( texture, 16, 16 );

        idle = new MovieClip.Animation( 3, true );
        idle.frames( frames, 0, 1, 2, 3,4,5 );

        run = new MovieClip.Animation( 7, true );
        run.frames( frames, 6,7,10,11 );

        attack = new MovieClip.Animation( 12, false );
        attack.frames( frames, 8,9 );

        die = new MovieClip.Animation( 12, false );
        die.frames( frames, 12,13,14 );

        play( idle );
    }

    @Override
    public int blood() {
        return 0xFFFFEA80;
    }
}
