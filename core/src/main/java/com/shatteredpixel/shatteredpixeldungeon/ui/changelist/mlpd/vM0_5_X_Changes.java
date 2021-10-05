package com.shatteredpixel.shatteredpixeldungeon.ui.changelist.mlpd;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.scenes.ChangesScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.CausticSlimeSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ColdRatSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.DM300AttackSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.FireGhostSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.KagenoNusujinSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.MimicSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NxhySprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.NyzSprites;
import com.shatteredpixel.shatteredpixeldungeon.sprites.PylonSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.RedSwarmSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.RenSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.SlylSprite;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.ui.changelist.ChangeButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.changelist.ChangeInfo;
import com.watabou.noosa.Image;

import java.util.ArrayList;

public class vM0_5_X_Changes {

    public static void addAllChanges( ArrayList<ChangeInfo> changeInfos ){
        add_v0_5_5_Changes(changeInfos);
        add_v0_5_4_Changes(changeInfos);
        add_v0_5_2_Changes(changeInfos);
        add_v0_5_1_Changes(changeInfos);
    }

    public static void add_v0_5_5_Changes( ArrayList<ChangeInfo> changeInfos ) {

        ChangeInfo changes = new ChangeInfo("v0.5.2.0", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.SKYBULE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.LING), (Messages.get(vM0_5_X_Changes.class, "dev6")),
                Messages.get(vM0_5_X_Changes.class, "dev6logs")));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), (Messages.get(vM0_5_X_Changes.class, "j1")),
                Messages.get(vM0_5_X_Changes.class, "j1logs")));

        changes.addButton(new ChangeButton(Icons.get(Icons.CHALLENGE_ON), (Messages.get(vM0_5_X_Changes.class, "new1cs")),
                Messages.get(vM0_5_X_Changes.class, "new1cslogs")));

        changes.addButton(new ChangeButton(Icons.get(Icons.CHANGES), (Messages.get(vM0_5_X_Changes.class, "new2cs")),
                Messages.get(vM0_5_X_Changes.class, "new2cslogs")));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(Window.RED_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.KOTLIN), (Messages.get(vM0_5_X_Changes.class, "kotlinss")),
                Messages.get(vM0_5_X_Changes.class, "kotlinsslogs")));

        changes.addButton(new ChangeButton(new Image(Assets.Environment.TILES_SEWERS, 48, 48, 16
                        , 16), (Messages.get(vM0_5_X_Changes.class, "sss")),
                Messages.get(vM0_5_X_Changes.class, "ssslogs")));
    }

    public static void add_v0_5_4_Changes( ArrayList<ChangeInfo> changeInfos ) {

        ChangeInfo changes = new ChangeInfo("v0.5.0.8-9", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.SKYBULE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new ColdRatSprite(), (Messages.get(vM0_5_X_Changes.class, "coldrat")),
                Messages.get(vM0_5_X_Changes.class, "coldratlogs")));

        changes.addButton(new ChangeButton(new RedSwarmSprite(), (Messages.get(vM0_5_X_Changes.class, "redswarm")),
                Messages.get(vM0_5_X_Changes.class, "redswarmlogs")));


        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(Window.GREEN_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new DM300AttackSprite(), (Messages.get(vM0_5_X_Changes.class, "dm300")),
                Messages.get(vM0_5_X_Changes.class, "dm300logs")));

        changes.addButton(new ChangeButton(new CausticSlimeSprite(), (Messages.get(vM0_5_X_Changes.class, "goo3")),
                Messages.get(vM0_5_X_Changes.class, "goo3logs")));

    }

    public static void add_v0_5_3_Changes( ArrayList<ChangeInfo> changeInfos ) {

        ChangeInfo changes = new ChangeInfo("v0.5.0.7", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.SKYBULE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new Image(Assets.Environment.TILES_COLD, 48, 48, 16
                , 16), (Messages.get(vM0_5_X_Changes.class, "cold")),
                Messages.get(vM0_5_X_Changes.class, "coldlogs")));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(Window.GREEN_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new Image("sprites/spinner.png", 144, 0, 16, 16), (Messages.get(ChangesScene.class, "bugfixes")),
                Messages.get(vM0_5_X_Changes.class, "bug_0507")));

    }

    public static void add_v0_5_2_Changes( ArrayList<ChangeInfo> changeInfos ) {

        ChangeInfo changes = new ChangeInfo("v0.5.0.6", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.SKYBULE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new CausticSlimeSprite(), (Messages.get(vM0_5_X_Changes.class, "cau1")),
                Messages.get(vM0_5_X_Changes.class, "caulogs")));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(Window.GREEN_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new KagenoNusujinSprite(), (Messages.get(vM0_5_X_Changes.class, "kage1")),
                Messages.get(vM0_5_X_Changes.class, "kagelogs")));

    }

    public static void add_v0_5_1_Changes( ArrayList<ChangeInfo> changeInfos ) {

        ChangeInfo changes = new ChangeInfo("v0.5.0.5", true, "");
        changes.hardlight(Window.TITLE_COLOR);
        changeInfos.add(changes);

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "new"), false, null);
        changes.hardlight(Window.SKYBULE_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(Icons.get(Icons.LING), (Messages.get(vM0_5_X_Changes.class, "dev5")),
                Messages.get(vM0_5_X_Changes.class, "dev5logs")));

        changes.addButton(new ChangeButton(Icons.get(Icons.KOTLIN), (Messages.get(vM0_5_X_Changes.class, "kotlinsi")),
                Messages.get(vM0_5_X_Changes.class, "kotlinlogs")));

        changes.addButton(new ChangeButton(new RenSprite(), (Messages.get(vM0_5_X_Changes.class, "newnpc1")),
                Messages.get(vM0_5_X_Changes.class, "renlogs")));

        changes.addButton(new ChangeButton(new Image("Npcs/rt.png", 0, 0, 16, 16), (Messages.get(vM0_5_X_Changes.class, "newnpc2")),
                Messages.get(vM0_5_X_Changes.class, "obsirlogs")));

        changes.addButton(new ChangeButton(new SlylSprite(), (Messages.get(vM0_5_X_Changes.class, "newnpc3")),
                Messages.get(vM0_5_X_Changes.class, "slyllogs")));

        changes.addButton(new ChangeButton(new NxhySprite(), (Messages.get(vM0_5_X_Changes.class, "newnpc4")),
                Messages.get(vM0_5_X_Changes.class, "nxhylogs")));

        changes.addButton(new ChangeButton(new NyzSprites(), (Messages.get(vM0_5_X_Changes.class, "newnpc5")),
                Messages.get(vM0_5_X_Changes.class, "nyzlogs")));

        changes.addButton(new ChangeButton(new MimicSprite.Dimand(), (Messages.get(vM0_5_X_Changes.class, "newmimic")),
                Messages.get(vM0_5_X_Changes.class, "newmimiclogs")));

        changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.DG17), (Messages.get(vM0_5_X_Changes.class, "newwand")),
                Messages.get(vM0_5_X_Changes.class, "newwandlogs")));

        changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.DG10), (Messages.get(vM0_5_X_Changes.class, "newfood")),
                Messages.get(vM0_5_X_Changes.class, "newfoodlogs")));

        changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.DG20), (Messages.get(vM0_5_X_Changes.class, "newstory")),
                Messages.get(vM0_5_X_Changes.class, "newstorylogs")));

        changes.addButton(new ChangeButton(new ItemSprite(ItemSpriteSheet.DG21), (Messages.get(vM0_5_X_Changes.class, "newitem")),
                Messages.get(vM0_5_X_Changes.class, "newitem")));

        changes.addButton(new ChangeButton(Icons.get(Icons.TALENT), (Messages.get(vM0_5_X_Changes.class, "newsp")),
                Messages.get(vM0_5_X_Changes.class, "newsplogs")));

        changes.addButton(new ChangeButton(new FireGhostSprite(), (Messages.get(vM0_5_X_Changes.class, "jq2")),
                Messages.get(vM0_5_X_Changes.class, "jq2logs")));

        changes = new ChangeInfo(Messages.get(ChangesScene.class, "changes"), false, null);
        changes.hardlight(Window.GREEN_COLOR);
        changeInfos.add(changes);

        changes.addButton(new ChangeButton(new Image("sprites/spinner.png", 144, 0, 16, 16), (Messages.get(ChangesScene.class, "bugfixes")),
                Messages.get(vM0_5_X_Changes.class, "bug_0505")));

        changes.addButton(new ChangeButton(Icons.get(Icons.WARNING), (Messages.get(vM0_5_X_Changes.class, "newmisc")),
                Messages.get(vM0_5_X_Changes.class, "newmisclogs")));

        changes.addButton(new ChangeButton(Icons.get(Icons.CHALLENGE_ON), (Messages.get(vM0_5_X_Changes.class, "challengegd")),
                Messages.get(vM0_5_X_Changes.class, "challengegdlogs")));

        changes.addButton(new ChangeButton(new KagenoNusujinSprite(), (Messages.get(vM0_5_X_Changes.class, "jq1")),
                Messages.get(vM0_5_X_Changes.class, "jq1logs")));

        changes.addButton(new ChangeButton(new PylonSprite(), (Messages.get(vM0_5_X_Changes.class, "xr1")),
                Messages.get(vM0_5_X_Changes.class, "xr1logs")));


    }

}
