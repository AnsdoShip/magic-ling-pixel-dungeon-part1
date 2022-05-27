package com.shatteredpixel.shatteredpixeldungeon.ui.changelist.mlpd;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.ChangesScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DM150Sprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DM275Sprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DM300AttackSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DM300DeathBallSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DM300SpiderSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DM300Sprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DM75Sprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DiedMonkLoaderSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.FireMagicGirlSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.RedNecromancerSprite_EX;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.ui.changelist.ChangeButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.changelist.ChangeInfo;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class vM0_6_7_X_Changes {

    public static void addAllChanges(ArrayList<ChangeInfo> changeInfos) {
        add_v0_6_0_Changes(changeInfos);
    }

    public static void add_v0_6_0_Changes( ArrayList<ChangeInfo> changeInfos ) {
        ChangeInfo changes = new ChangeInfo("V0.6.0.0-上半段最终版", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo("平衡与修复", false, null);
        changes.hardlight(Window.SKYBULE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.LING), (Messages.get(vM0_5_X_Changes.class, "dev060")),
                Messages.get(vM0_5_X_Changes.class, "dev060logs")));

        changes.addButton(new ChangeButton(Icons.get(Icons.INFO), (Messages.get(vM0_5_X_Changes.class, "dm920")),
                Messages.get(vM0_5_X_Changes.class, "dm920logos")));

        changes.addButton(new ChangeButton(new Image("sprites/spinner.png", 144, 0, 16, 16), (Messages.get(ChangesScene.class, "bugfixes")),
                Messages.get(vM0_5_X_Changes.class, "bug_0600")));

        changes = new ChangeInfo("新机制", false, null);
        changes.hardlight(Window.SKYBULE_COLOR);
        changeInfos.add(changes);

        Image issxsas =new Image(Assets.Interfaces.BUFFS_LARGE, 112, 48, 16, 16);
        issxsas.scale.set(PixelScene.align(0.80f));
        changes.addButton(new ChangeButton(issxsas,
                (Messages.get(vM0_5_X_Changes.class, "newbuffs")),
                Messages.get(vM0_5_X_Changes.class, "newbuffslogs")));

        changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.DG20, null), ("吸血鬼刀"),
                ("原3阶武器，现在迁移到4阶武器")));

        changes = new ChangeInfo("新物品", false, null);
        changes.hardlight(Window.SKYBULE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.FBK, null), ("吸血鬼刀"),
                ("原3阶武器，现在迁移到4阶武器")));

        changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.SKELETONGOLD, null), ("吸血鬼刀"),
                ("原3阶武器，现在迁移到4阶武器")));

        changes = new ChangeInfo("新武器", false, null);
        changes.hardlight(Window.SKYBULE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.ICEFISHSWORD, null), ("吸血鬼刀"),
                ("原3阶武器，现在迁移到4阶武器")));

        changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.ENDDIED, null), ("吸血鬼刀"),
                ("原3阶武器，现在迁移到4阶武器")));

        changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.FIREFISHSWORD, null), ("吸血鬼刀"),
                ("原3阶武器，现在迁移到4阶武器")));

        changes = new ChangeInfo("新BOSS:浊焰魔女", false, null);
        changes.hardlight(Window.SKYBULE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new DiedMonkLoaderSprite(),
                (Messages.get(vM0_5_X_Changes.class,
                "mkdied")),
                Messages.get(vM0_5_X_Changes.class,
                        "mkdied_desc")));

        changes.addButton(new ChangeButton(new RedNecromancerSprite_EX(), (Messages.get(vM0_5_X_Changes.class,
                "rmkdied")),
                Messages.get(vM0_5_X_Changes.class,
                        "rmkdied_desc")));

        Image fr = new FireMagicGirlSprite();
        fr.scale.set(PixelScene.align(1f));
        changes.addButton(new ChangeButton(fr, (Messages.get(vM0_5_X_Changes.class,
                "fr")),
                Messages.get(vM0_5_X_Changes.class, "frlogs")));

        changes = new ChangeInfo("新BOSS:DM920", false, null);
        changes.hardlight(Window.SKYBULE_COLOR);
        changeInfos.add(changes);

        Image dm920 = new DM300Sprite();
        dm920.scale.set(PixelScene.align(0.75f));
        changes.addButton(new ChangeButton(dm920, (Messages.get(vM0_5_X_Changes.class,
                "dm920")),
                Messages.get(vM0_5_X_Changes.class, "dm920logs")));

        Image dm9201 = new DM300AttackSprite();
        dm9201.scale.set(PixelScene.align(0.75f));
        changes.addButton(new ChangeButton(dm9201, (Messages.get(vM0_5_X_Changes.class,
                "dm9201")),
                Messages.get(vM0_5_X_Changes.class, "dm9201logs")));

        Image dm9202 = new DM300SpiderSprite();
        dm9202.scale.set(PixelScene.align(0.75f));
        changes.addButton(new ChangeButton(dm9202, (Messages.get(vM0_5_X_Changes.class,
                "dm9202")),
                Messages.get(vM0_5_X_Changes.class, "dm9202logs")));

        Image dm9203 = new DM300DeathBallSprite();
        dm9203.scale.set(PixelScene.align(0.75f));
        changes.addButton(new ChangeButton(dm9203, (Messages.get(vM0_5_X_Changes.class,
                "dm9203")),
                Messages.get(vM0_5_X_Changes.class, "dm9203logs")));

        Image dm9204 = new DM275Sprite();
        dm9204.scale.set(PixelScene.align(0.75f));
        changes.addButton(new ChangeButton(dm9204, (Messages.get(vM0_5_X_Changes.class,
                "dm9204")),
                Messages.get(vM0_5_X_Changes.class, "dm9204logs")));

        changes.addButton(new ChangeButton(new DM75Sprite(), (Messages.get(vM0_5_X_Changes.class,
                "dm75")),
                Messages.get(vM0_5_X_Changes.class, "dm75logs")));

        changes.addButton(new ChangeButton(new DM150Sprite(), (Messages.get(vM0_5_X_Changes.class,
                "dm150")),
                Messages.get(vM0_5_X_Changes.class, "dm150logs")));

        changes.addButton(new ChangeButton(Icons.get(Icons.INFO), (Messages.get(vM0_5_X_Changes.class, "dm920")),
                Messages.get(vM0_5_X_Changes.class, "dm920logos")));
    }
}
