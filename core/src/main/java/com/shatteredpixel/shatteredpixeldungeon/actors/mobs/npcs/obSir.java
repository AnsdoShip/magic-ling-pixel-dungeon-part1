package com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs;

import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.obSirSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndQuest;
import com.watabou.noosa.Game;
import com.watabou.utils.Bundle;
import com.watabou.utils.Callback;
import com.watabou.utils.Random;

public class obSir extends NPC {

    private static final String[] TXT_RANDOM = {"说实话我的确被人追杀，虽然是\"黑骑士\"追的，不过我也利用我的帽子强制传送的，不过我还是知道那个\"黑骑士\"也来这里了，欸真让人头疼，如果你能帮我解决掉它，那么我会赠送你一个\"礼物\"的。","你知道那个叫回忆吗，那可是奸商我跟你讲，我还被迫给他写广告牌，不过他卖的商品真有点东西"};

    {
        spriteClass = obSirSprite.class;

        properties.add(Property.IMMOVABLE);
    }

    private boolean first=true;
    private boolean secnod=true;
    private boolean rd=true;

    private static final String FIRST = "first";
    private static final String SECNOD = "secnod";
    private static final String RD = "rd";

    @Override
    public void storeInBundle(Bundle bundle) {
        super.storeInBundle(bundle);
        bundle.put(FIRST, first);
    }

    @Override
    public void restoreFromBundle(Bundle bundle) {
        super.restoreFromBundle(bundle);
        first = bundle.getBoolean(FIRST);
        secnod = bundle.getBoolean(SECNOD);
        rd = bundle.getBoolean(RD);
    }

    @Override
    protected boolean act() {

        throwItem();

        sprite.turnTo( pos, Dungeon.hero.pos );
        spend( TICK );
        return true;
    }

    @Override
    public void damage( int dmg, Object src ) {
    }

    @Override
    public int defenseSkill( Char enemy ) {
        return INFINITE_EVASION;
    }

    @Override
    public boolean reset() {
        return true;
    }

    @Override
    public boolean interact(Char c) {

        sprite.turnTo(pos, Dungeon.hero.pos);

        if(first) {
            first=false;
            tell(Messages.get(obSir.class, "message1"));
        }else if(secnod) {
            secnod=false;
            tell(Messages.get(obSir.class, "message2"));
        } else if(secnod) {
            rd=false;
            tell(Messages.get(obSir.class, "message3"));
        } else {
            yell(TXT_RANDOM[Random.Int(TXT_RANDOM.length)]);
        }

        return true;
    }

    private void tell(String text) {
        Game.runOnRenderThread(new Callback() {
                                   @Override
                                   public void call() {
                                       GameScene.show(new WndQuest(new obSir(), text));
                                   }
                               }
        );
    }
}
