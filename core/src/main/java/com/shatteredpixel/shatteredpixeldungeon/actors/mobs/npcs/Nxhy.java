package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.hero;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Blindness;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Burning;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Doom;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.FlameC01;
import com.shatteredpixel.shatteredpixeldungeon.effects.CellEmitter;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.effects.particles.ElmoParticle;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NxhySprite;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndBag;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndTradeItem;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

public class Nxhy extends Shopkeeper {

    {
        spriteClass = NxhySprite.class;

        properties.add(Property.IMMOVABLE);
    }
    private boolean seenBefore = false;
    private boolean seenBeforeq= false;
    @Override
    protected boolean act() {
        if (!seenBefore && Dungeon.level.heroFOV[pos]) {
            yell(Messages.get(this, "greetings", Dungeon.hero.name()));
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
        Buff.affect(hero, Doom.class);
        new FlameC01().spawnAround(pos);
        yell( Messages.get(this, "arise") );
        next();
    }

    @Override
    public void destroy() {
        super.destroy();
        for (Heap heap: Dungeon.level.heaps.valueList()) {
            if (heap.type == Heap.Type.FOR_SALE) {
                CellEmitter.get( heap.pos ).burst( ElmoParticle.FACTORY, 4 );
                heap.destroy();
            }
        }
    }

    @Override
    public boolean reset() {
        return true;
    }

    public static WndBag sell() {
        return GameScene.selectItem( itemSelector, WndBag.Mode.FOR_SALE, Messages.get(Shopkeeper.class, "sell"));
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
        if (c != Dungeon.hero) {
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

