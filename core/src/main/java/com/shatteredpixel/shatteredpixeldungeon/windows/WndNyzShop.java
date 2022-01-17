package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Chrome;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Ghost;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.npcs.Nyz;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.armor.Armor;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.Weapon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NyzSprites;
import com.shatteredpixel.shatteredpixeldungeon.ui.ItemSlot;
import com.shatteredpixel.shatteredpixeldungeon.ui.RedButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.utils.GLog;
import com.watabou.noosa.Game;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.Callback;

public class WndNyzShop extends Window {
    public int Buy;
    private static final int WIDTH		= 120;
    private static final int BTN_SIZE	= 16;
    private static final int BTN_GAP	= 3;
    private static final int GAP		= 2;

    public WndNyzShop(Callback callback) {
        IconTitle titlebar = new IconTitle();
        titlebar.setRect(0, 0, WIDTH, 0);
        titlebar.icon(new NyzSprites());
        titlebar.label(Messages.get(WndNyzShop.class,"nayazi"));
        add( titlebar );
        RenderedTextBlock message = PixelScene.renderTextBlock( (Messages.get(WndNyzShop.class,"nayaziwelcome")), 6 );
        message.maxWidth(WIDTH);
        message.setPos(0, titlebar.bottom() + GAP);
        add( message );

        WndNyzShop.RewardButton shop1 = new WndNyzShop.RewardButton( Nyz.shop1 );
        shop1.setRect( (WIDTH - BTN_GAP) / 6 - BTN_SIZE, message.top() + message.height() + BTN_GAP, BTN_SIZE,
                BTN_SIZE );
        add( shop1 );

        WndNyzShop.RewardButton shop2 = new WndNyzShop.RewardButton( Nyz.shop2 );
        shop2.setRect( shop1.right() + BTN_GAP, shop1.top(), BTN_SIZE, BTN_SIZE );
        add(shop2);

        WndNyzShop.RewardButton shop3 = new WndNyzShop.RewardButton( Nyz.shop3 );
        shop3.setRect( shop2.right() + BTN_GAP, shop2.top(), BTN_SIZE, BTN_SIZE );
        add(shop3);

        WndNyzShop.RewardButton shop4 = new WndNyzShop.RewardButton( Nyz.shop4 );
        shop4.setRect( shop3.right() + BTN_GAP, shop3.top(), BTN_SIZE, BTN_SIZE );
        add(shop4);

        WndNyzShop.RewardButton shop5 = new WndNyzShop.RewardButton( Nyz.shop5 );
        shop5.setRect( shop4.right() + BTN_GAP, shop4.top(), BTN_SIZE, BTN_SIZE );
        add(shop5);

        WndNyzShop.RewardButton shop6 = new WndNyzShop.RewardButton( Nyz.shop6 );
        shop6.setRect( shop5.right() + BTN_GAP, shop5.top(), BTN_SIZE, BTN_SIZE );
        add(shop6);

        WndNyzShop.RewardButton2 bomb1 = new WndNyzShop.RewardButton2( Nyz.bomb1 );
        bomb1.setRect( shop1.left() , shop1.bottom(), BTN_SIZE, BTN_SIZE );
        add(bomb1);

        WndNyzShop.RewardButton2 bomb2 = new WndNyzShop.RewardButton2( Nyz.bomb2 );
        bomb2.setRect( bomb1.right()+ BTN_GAP , bomb1.top(), BTN_SIZE, BTN_SIZE );
        add(bomb2);

        WndNyzShop.RewardButton2 bomb3 = new WndNyzShop.RewardButton2( Nyz.bomb3 );
        bomb3.setRect( bomb2.right()+ BTN_GAP , bomb2.top(), BTN_SIZE, BTN_SIZE );
        add(bomb3);

        WndNyzShop.RewardButton2 bomb4 = new WndNyzShop.RewardButton2( Nyz.bomb4 );
        bomb4.setRect( bomb3.right()+ BTN_GAP , bomb3.top(), BTN_SIZE, BTN_SIZE );
        add(bomb4);

        WndNyzShop.RewardButton2 bomb5 = new WndNyzShop.RewardButton2( Nyz.bomb5 );
        bomb5.setRect( bomb4.right()+ BTN_GAP , bomb4.top(), BTN_SIZE, BTN_SIZE );
        add(bomb5);

        WndNyzShop.RewardButton2 bomb6 = new WndNyzShop.RewardButton2( Nyz.bomb6 );
        bomb6.setRect( bomb5.right() + BTN_GAP, bomb5.top(), BTN_SIZE, BTN_SIZE );
        add(bomb6);


        resize(WIDTH, (int) bomb6.bottom());
    }

    private void tell(String text) {
        Game.runOnRenderThread(new Callback() {
                                   @Override
                                   public void call() {
                                       GameScene.show(new WndQuest(new Nyz(), text));
                                   }
                               }
        );
    }
    Nyz nyz;
    private void selectReward( Item reward ) {

        hide();

        if (reward == null) return;

        if (reward instanceof Weapon && Ghost.Quest.enchant != null){
            ((Weapon) reward).enchant(Ghost.Quest.enchant);
        } else if (reward instanceof Armor && Ghost.Quest.glyph != null){
            ((Armor) reward).inscribe(Ghost.Quest.glyph);
        }

        reward.identify();
        if (reward.doPickUp( Dungeon.hero )) {
            GLog.i( Messages.get(Dungeon.hero, "you_now_have", reward.name()) );
        } else {
            Dungeon.level.drop( reward, nyz.pos ).sprite.drop();
        }

        Ghost.Quest.complete();
    }

    private class RewardWindow extends WndInfoItem {

        public RewardWindow( Item item ) {
            super(item);

            RedButton btnConfirm = new RedButton(Messages.get(WndNyzShop.class, "buy")){
                @Override
                protected void onClick() {
                    if(Dungeon.nyzbuy == 0){
                       tell(Messages.get(WndNyzShop.class,"maxbuy"));
                       WndNyzShop.RewardWindow.this.hide();
                    } else if(Dungeon.gold > 200) {
                        Dungeon.gold-=200;
                        WndNyzShop.this.selectReward( item );
                        Dungeon.nyzbuy--;
                        WndNyzShop.RewardWindow.this.hide();
                    } else {
                        tell(Messages.get(WndNyzShop.class,"nomoney"));
                    }
                }
            };
            btnConfirm.setRect(0, height+2, width/2-1, 16);
            add(btnConfirm);

            RedButton btnCancel = new RedButton(Messages.get(WndNyzShop.class, "cancel")){
                @Override
                protected void onClick() {
                    hide();
                }
            };
            btnCancel.setRect(btnConfirm.right()+2, height+2, btnConfirm.width(), 16);
            add(btnCancel);

            resize(width, (int)btnCancel.bottom());
        }
    }

    private class RewardWindow2 extends WndInfoItem {

        public RewardWindow2( Item item ) {
            super(item);

            RedButton btnConfirm = new RedButton(Messages.get(WndNyzShop.class, "buy")){
                @Override
                protected void onClick() {
                    if(Dungeon.nyzbuy == 0){
                        tell(Messages.get(WndNyzShop.class,"maxbuy"));
                        WndNyzShop.RewardWindow2.this.hide();
                    } else if(Dungeon.gold > 270) {
                        Dungeon.gold-=270;
                        WndNyzShop.this.selectReward( item );
                        Dungeon.nyzbuy--;
                        WndNyzShop.RewardWindow2.this.hide();
                    } else {
                        tell(Messages.get(WndNyzShop.class,"nomoney"));
                    }
                }
            };
            btnConfirm.setRect(0, height+2, width/2-1, 16);
            add(btnConfirm);

            RedButton btnCancel = new RedButton(Messages.get(WndNyzShop.class, "cancel")){
                @Override
                protected void onClick() {
                    hide();
                }
            };
            btnCancel.setRect(btnConfirm.right()+2, height+2, btnConfirm.width(), 16);
            add(btnCancel);

            resize(width, (int)btnCancel.bottom());
        }
    }

    public class RewardButton2 extends Component {

        protected NinePatch bg;
        protected ItemSlot slot;

        public RewardButton2( Item item ){
            bg = Chrome.get( Chrome.Type.RED_BUTTON);
            add( bg );

            slot = new ItemSlot( item ){
                @Override
                protected void onPointerDown() {
                    bg.brightness( 1.2f );
                    Sample.INSTANCE.play( Assets.Sounds.CLICK );
                }
                @Override
                protected void onPointerUp() {
                    bg.resetColor();
                }
                @Override
                protected void onClick() {
                    ShatteredPixelDungeon.scene().addToFront(new WndNyzShop.RewardWindow2(item));
                }
            };
            add(slot);
        }

        @Override
        protected void layout() {
            super.layout();

            bg.x = x;
            bg.y = y;
            bg.size( width, height );

            slot.setRect( x + 2, y + 2, width - 4, height - 4 );
        }
    }

    public class RewardButton extends Component {

        protected NinePatch bg;
        protected ItemSlot slot;

        public RewardButton( Item item ){
            bg = Chrome.get( Chrome.Type.RED_BUTTON);
            add( bg );

            slot = new ItemSlot( item ){
                @Override
                protected void onPointerDown() {
                    bg.brightness( 1.2f );
                    Sample.INSTANCE.play( Assets.Sounds.CLICK );
                }
                @Override
                protected void onPointerUp() {
                    bg.resetColor();
                }
                @Override
                protected void onClick() {
                    ShatteredPixelDungeon.scene().addToFront(new WndNyzShop.RewardWindow(item));
                }
            };
            add(slot);
        }

        @Override
        protected void layout() {
            super.layout();

            bg.x = x;
            bg.y = y;
            bg.size( width, height );

            slot.setRect( x + 2, y + 2, width - 4, height - 4 );
        }
    }
}
