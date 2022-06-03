package com.shatteredpixel.shatteredpixeldungeon.ui;

import com.shatteredpixel.shatteredpixeldungeon.Chrome;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.bags.LingBag;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GuideScene;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndOptions;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.ui.Button;

public class MainHandIndicator extends Button {

    public static Char lastTarget = null;

    protected NinePatch bg;
    protected float lightness = 0;
    private float r;
    private float g;
    private float b;

    public void flash() {
        lightness = 1f;
    }//although it sounds damn stupid,but extends tag means unable to auto-target,so I have had to copy its visual and add the auto-targeting feature,may create a father class for these two later

    public void flip(boolean value) {
        bg.flipHorizontal(value);
    }

    Image icon;

    private static Image crossB;
    private static Image crossM;
    private static boolean targeting = false;
    //private static Image crossM=Icons.TARGET.get();;

    private Toolbar.Tool slot;

    private static Item item = null;

    public MainHandIndicator() {

        int color=0x7C8072;
        this.r = (color >> 16) / 255f;
        this.g = ((color >> 8) & 0xFF) / 255f;
        this.b = (color & 0xFF) / 255f;

        bg.ra = bg.ga = bg.ba = 0;
        bg.rm = (0x7C8072 >> 16) / 255f;
        bg.gm = ((0x7C8072 >> 8) & 0xFF) / 255f;
        bg.bm = (0x7C8072 & 0xFF) / 255f;

        setSize( 21, 21 );

        visible=false;
    }

    @Override
    protected void createChildren() {

        super.createChildren();

        bg = Chrome.get(Chrome.Type.SCROLL);
        bg.hardlight(0x00ffff);
        add(bg);

        add(slot = new Toolbar.Tool(0, 25, 24, 53) {

            @Override
            protected void onClick() {
                super.onClick();
                    ShatteredPixelDungeon.scene().add(new WndOptions(
                            Messages.get(this, "saver"),
                            Messages.get(this, "saver_desc"),
                            Messages.get(this, "okay"),
                            Messages.get(this, "cancel")) {
                        @Override
                        protected void onSelect(int index) {
                            if (index == 0) {
                                ShatteredPixelDungeon.switchNoFade(GuideScene.class);
                            }
                        }
                    });
            }

        });

    }

    @Override
    protected void layout() {
        super.layout();

        bg.x = x;
        bg.y = y;
        bg.size(width, height);

        bg.scale.x = -1.0f;
        bg.x += bg.width;
        bg.y=y;

        slot.setRect( x, y, width, height);
    }

    @Override
    public void update() {
        super.update();

        if (visible && lightness > 0.5) {
            if ((lightness -= Game.elapsed) > 0.5) {
                bg.ra = bg.ga = bg.ba = 2 * lightness - 1;
                bg.rm = 2 * r * (1 - lightness);
                bg.gm = 2 * g * (1 - lightness);
                bg.bm = 2 * b * (1 - lightness);
            } else {
                bg.hardlight(r, g, b);
            }
        }

        slot.enable(Dungeon.hero.isAlive() && Dungeon.hero.ready);
        if (item == null) {
            visible = true;
        }
        else visible=true;
    }
    public void update(LingBag LingBag) {
        item=LingBag;
        update();
    }

    public static boolean targeting()
    {
        return targeting;
    }

    public static void cancel() {
        if (targeting) {
            crossB.visible = true;
            crossM.remove();
            targeting = false;
        }
    }
}

