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

package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.ShopGuard;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.ShopGuardDead;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ShopkeeperSprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTradeItem;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class Shopkeeper extends NPC {

	{
		spriteClass = ShopkeeperSprite.class;

		properties.add(Property.IMMOVABLE);
	}
	private boolean seenBefore = false;
	@Override
	protected boolean act() {
		if (!seenBefore && Dungeon.level.heroFOV[pos]) {
			yell(Messages.get(this, "greetings", Dungeon.hero.name()));
			Music.INSTANCE.play(Assets.Music.SHOP, true);
			seenBefore = true;
		}
		throwItem();

		sprite.turnTo( pos, Dungeon.hero.pos );
		spend( TICK );
		return true;
	}

	@Override
	public void damage( int dmg, Object src ) {
		flee();
	}
	
	@Override
	public void add( Buff buff ) {
		flee();
	}

	public void flee() {
		destroy();

		sprite.killAndErase();
		CellEmitter.get( pos ).burst( ElmoParticle.FACTORY, 6 );
		GLog.negative(Messages.get(this,"guards"));
			sprite.centerEmitter().start( Speck.factory( Speck.SCREAM ), 0.4f, 2 );
			Sample.INSTANCE.play( Assets.Sounds.ALERT );
			Music.INSTANCE.play(Assets.RUN, true);
			hero.sprite.burst(15597568, 9);
		sprite.killAndErase();
		CellEmitter.get( pos ).burst( ElmoParticle.FACTORY, 6 );
		GLog.negative(Messages.get(this,"guards"));
		Buff.prolong( Dungeon.hero, Blindness.class, Blindness.DURATION*4f );
		GameScene.flash(0x80FFFFFF);
		Buff.affect(hero, Burning.class ).reignite( hero, 15f );
		new ShopGuard().spawnAround(pos);
			yell( Messages.get(this, "arise") );
		if (Random.Int(0) == 0){
			new ShopGuardDead().spawnAround(pos);
			yell( Messages.get(this, "eye") );
		}
		next();
	}


	@Override
	public void destroy() {
		super.destroy();
		for (Heap heap: Dungeon.level.heaps.valueList()) {
			if (heap.type == Heap.Type.FOR_SALE) {
				CellEmitter.get( heap.pos ).burst( ElmoParticle.FACTORY, 4 );
				heap.type = Heap.Type.HEAP;//Allow them to be picked up
			}
		}
	}
	
	@Override
	public boolean reset() {
		return true;
	}

	//shopkeepers are greedy!
	public static int sellPrice(Item item){
		return item.value() * 5 * (Dungeon.depth / 5 + 1);
	}
	
	public static WndBag sell() {
		return GameScene.selectItem( itemSelector, WndBag.Mode.FOR_SALE, Messages.get(Shopkeeper.class, "sell"));
	}

	public static boolean willBuyItem( Item item ){
		if (item.value() <= 0)                                               return false;
		if (item.unique && !item.stackable)                                 return false;
		if (item instanceof Armor && ((Armor) item).checkSeal() != null)    return false;
		if (item.isEquipped(hero) && item.cursed)                   return false;
		return true;
	}
	
	private static WndBag.Listener itemSelector = new WndBag.Listener() {
		@Override
		public void onSelect( Item item ) {
			if (item != null) {
				WndBag parentWnd = sell();
				GameScene.show( new WndTradeItem( item, parentWnd ) );
			}
		}
	};

	@Override
	public boolean interact(Char c) {
		if (c != hero) {
			return true;
		}
		Game.runOnRenderThread(new Callback() {
			@Override
			public void call() {
				sell();
			}
		});
		return true;
	}
}
