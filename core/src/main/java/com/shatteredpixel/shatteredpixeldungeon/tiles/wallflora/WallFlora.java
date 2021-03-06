/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2019 Evan Debenham
 *
 * Overgrown Pixel Dungeon
 * Copyright (C) 2018-2019 Anon
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

package com.shatteredpixel.shatteredpixeldungeon.tiles.wallflora;


import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.LeafParticle;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.utils.Bundlable;
import com.watabou.utils.Bundle;

public abstract class WallFlora implements Bundlable {

    public int image;
    public int pos;

    public void wither(){
        Dungeon.level.flora.remove(pos);
        GameScene.updateMap(pos);

        if (Dungeon.level.heroFOV[pos]) {
            CellEmitter.get( pos ).burst( LeafParticle.GENERAL, 6 );
        }
    }

    public WallFlora set(int pos){
        this.pos = pos;
        return this;
    }

    private static final String POS	= "pos";

    @Override
    public void restoreFromBundle( Bundle bundle ) {
        pos = bundle.getInt( POS );
    }

    @Override
    public void storeInBundle( Bundle bundle ) {
        bundle.put( POS, pos );
    }
}
