package com.shatteredpixel.shatteredpixeldungeon.items.quest;

import static com.shatteredpixel.shatteredpixeldungeon.Dungeon.boss;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Buff;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Paralysis;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.SelectFoor;
import com.shatteredpixel.shatteredpixeldungeon.actors.hero.Hero;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Slyl;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.items.SPS;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndQuest;
import com.watabou.noosa.Game;
import com.watabou.noosa.audio.Sample;
import com.watabou.utils.Callback;

import java.util.ArrayList;

public class SkeletonGold extends SPS {

    {
        image = ItemSpriteSheet.SKELETONGOLD;

        unique = true;
    }



    public void execute( Hero hero, String action ) {

        super.execute( hero, action );

        if (action.equals(AC_SELECT_ONE) || action.equals(AC_SELECT_TWO)) {

            if (!Dungeon.selectbossLevel()) {
                GLog.n( Messages.get(this, "preventing") );
                return;
            }
        }

        //TODO A面赋Boss=1，下一层成为A面
        if (action.equals(AC_SELECT_ONE)){
            boss=1;
            GLog.p( Messages.get(this, "wow") );
            new Flare( 5, 32 ).color( 0xFF0000, true ).show( curUser.sprite, 2f );
            Buff.prolong(hero, Paralysis.class, Paralysis.DURATION);
            Sample.INSTANCE.play( Assets.Sounds.READ );
            if (Dungeon.hero.buff(SelectFoor.class) != null){
                Dungeon.hero.buff(SelectFoor.class).detach();
            }
        } else if (action.equals(AC_SELECT_TWO)) {
        //TODO B面赋Boss=2，下一层成为B面
            boss = 2;
            GLog.p( Messages.get(this, "wow") );
            new Flare( 5, 32 ).color( 0x00FFFF, true ).show( curUser.sprite, 2f );
            Buff.prolong(hero, Paralysis.class, Paralysis.DURATION);
            Sample.INSTANCE.play( Assets.Sounds.READ );
            if (Dungeon.hero.buff(SelectFoor.class) != null){
                Dungeon.hero.buff(SelectFoor.class).detach();
            }
            //TODO 使用方法按钮
        } else if (action.equals(AC_SELECT_HOW)){
            tell(Messages.get(Slyl.class, "howuse"));
            Sample.INSTANCE.play( Assets.Sounds.SECRET );
            //TODO C面赋Boss=3，下一层成为C面
        } else if(action.equals(AC_SELECT_THREE)) {
            Sample.INSTANCE.play( Assets.Sounds.READ );
            boss = 3;
            GLog.p( Messages.get(this, "wow") );
            new Flare( 5, 32 ).color( 0x00FF00, true ).show( curUser.sprite, 2f );
            Buff.prolong(hero, Paralysis.class, Paralysis.DURATION);
            if (Dungeon.hero.buff(SelectFoor.class) != null){
                Dungeon.hero.buff(SelectFoor.class).detach();
            }
        }
    }

    private void tell(String text) {
        Game.runOnRenderThread(new Callback() {
                                   @Override
                                   public void call() {
                                       GameScene.show(new WndQuest(new Slyl(), text));
                                   }
                               }
        );
    }

    protected static final String AC_SELECT_ONE = "SELECT_ONE";
    protected static final String AC_SELECT_HOW = "SELECT_HOW";
    protected static final String AC_SELECT_TWO = "SELECT_TWO";
    protected static final String AC_SELECT_THREE = "SELECT_THREE";

    @Override
    public ArrayList<String> actions(Hero hero) {
        ArrayList<String> actions = new ArrayList<>();
        if (Dungeon.selectbossLevel())
        actions.add( AC_SELECT_ONE );
        if (Dungeon.selectbossLevel())
        actions.add( AC_SELECT_TWO );
        if (Dungeon.depth == 14)
            actions.add(AC_SELECT_THREE);
        actions.add( AC_SELECT_HOW );
        if (Dungeon.depth == 114514) {
            actions.add(AC_DROP);
            actions.add(AC_THROW);
        }
        return actions;
    }

    @Override
    public boolean isUpgradable() {
        return false;
    }

    @Override
    public boolean isIdentified() {
        return true;
    }
}

