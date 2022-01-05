package com.shatteredpixel.shatteredpixeldungeon;

import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LockedFloor;
import com.watabou.noosa.audio.Music;

public class BGMPlayer {
    public static void playBGMWithDepth() {
        if (Dungeon.hero != null) {
            if (Dungeon.hero.buff(LockedFloor.class) != null) {
                BGMPlayer.playBoss();
                return;
            }
        }
        int d = Dungeon.depth;
        if (d == -1) {
            Music.INSTANCE.play(Assets.TOWN, true);
        }else if (d == 0) {
            Music.INSTANCE.play(Assets.TOWN, true);
        } else if (d > 0 && d <= 5) {
            Music.INSTANCE.play(Assets.BGM_1, true);
        } else if (d > 5 && d <= 10) {
            Music.INSTANCE.play(Assets.BGM_2, true);
        } else if (d > 10 && d <= 15) {
            Music.INSTANCE.play(Assets.BGM_3, true);
        } else if (d > 15 && d <= 20) {
            Music.INSTANCE.play(Assets.BGM_4, true);
        } else if (d > 20 && d <= 26) {
            Music.INSTANCE.play(Assets.BGM_5, true);
        } else
            //default
            Music.INSTANCE.play(Assets.Music.THEME, true);
    }

    public static void playBoss() {
        int t = Dungeon.depth;
        if (Dungeon.bossLevel() && t == 5) {
            Music.INSTANCE.play(Assets.BGM_BOSSA, true);
        } else if (Dungeon.bossLevel() && t == 10) {
            Music.INSTANCE.play(Assets.BGM_BOSSB, true);
        } else if (Dungeon.bossLevel() && t == 15 && Statistics.spawnersIce > 0) {
            Music.INSTANCE.play(Assets.BGM_BOSSC3, true);
        } else if (Dungeon.bossLevel() && t == 15) {
            Music.INSTANCE.play(Assets.BGM_BOSSC, true);
        } else if (Dungeon.bossLevel() && t == 20) {
            Music.INSTANCE.play(Assets.BGM_BOSSD, true);
        } else if (Dungeon.bossLevel() && t == 25 && (Statistics.spawnersAlive > 0)) {
            Music.INSTANCE.play(Assets.BGM_BOSSE3, true);
        }else if (Dungeon.bossLevel() && t == 25){
            Music.INSTANCE.play(Assets.BGM_BOSSE, true);
        }
    }
}
