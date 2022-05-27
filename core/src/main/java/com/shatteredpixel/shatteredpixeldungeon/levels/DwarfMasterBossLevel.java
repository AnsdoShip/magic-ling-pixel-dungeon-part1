package com.shatteredpixel.shatteredpixeldungeon.levels;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Bones;
import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.actors.Actor;
import com.shatteredpixel.shatteredpixeldungeon.actors.Char;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.Mob;
import com.shatteredpixel.shatteredpixeldungeon.actors.mobs.bosses.DwarfMaster;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.items.Generator;
import com.shatteredpixel.shatteredpixeldungeon.items.Heap;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.items.artifacts.LloydsBeacon;
import com.shatteredpixel.shatteredpixeldungeon.items.wands.Wand;
import com.shatteredpixel.shatteredpixeldungeon.items.weapon.melee.MeleeWeapon;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.watabou.utils.Random;
import com.watabou.utils.Rect;

import java.util.ArrayList;
import java.util.HashSet;

public class DwarfMasterBossLevel extends Level {
    {
        color1 = 0x48763c;
        color2 = 0x59994a;
    }

    private static final int WIDTH = 38;
    private static final int HEIGHT = 38;

    public static final int CENTER = 18 + WIDTH * 17;
    private static final int EXIT = 18 + WIDTH * 2;
    private static final int ENTRANCE = 18 + WIDTH * 31;

    @Override
    public String tilesTex() {
        return Assets.Environment.TILES_CITY;
    }

    @Override
    public String waterTex() {
        return Assets.Environment.WATER_CITY;
    }

    private Mob getKing(){
        for (Mob m : mobs){
            if (m instanceof DwarfMaster) return m;
        }
        return null;
    }
    private static final int[] pedestals = new int[4];

    public int getSummoningPos(){
        Mob king = getKing();
        HashSet<DwarfMaster.Summoning> summons = king.buffs(DwarfMaster.Summoning.class);
        ArrayList<Integer> positions = new ArrayList<>();
        for (int pedestal : pedestals) {
            boolean clear = true;
            for (DwarfMaster.Summoning s : summons) {
                if (s.getPos() == pedestal) {
                    clear = false;
                    break;
                }
            }
            if (clear) {
                positions.add(pedestal);
            }
        }
        if (positions.isEmpty()){
            return -1;
        } else {
            return Random.element(positions);
        }
    }

    protected boolean build() {
        setSize(WIDTH, HEIGHT);
        map = codedMap.clone();

        this.entrance = (this.width * 20) + 18;
        this.entrance = (this.width * 31) + 18;
        //map[exit] = (this.width * 35) + 21;;

        return true;
    }
    private static final Rect entry = new Rect(1, 37, 14, 48);
    private static final Rect arena = new Rect(1, 25, 14, 38);
    private static final Rect end = new Rect(0, 0, 15, 22);

    private static final int bottomDoor = 7 + (arena.bottom-1)*15;
    private static final int topDoor = 7 + arena.top*15;

    public static final int throne;

    static {
        throne = 15 + WIDTH * 20;
        pedestals[0] = 12 + WIDTH * 14;
        pedestals[1] = 22 + WIDTH * 14;
        pedestals[2] = 13 + WIDTH * 22;
        pedestals[3] = 23 + WIDTH * 22;
    }

    @Override
    protected void createMobs() {

    }

    public Actor addRespawner() {
        return null;
    }

    @Override
    protected void createItems() {
        Item item = Bones.get();
        if (item != null) {
            int pos;
            do {
                pos = randomRespawnCell(null);
            } while (pos % WIDTH == 18);
            drop(item, pos).setHauntedIfCursed().type = Heap.Type.REMAINS;
        }

        MeleeWeapon mw = Generator.randomWeapon(11);
        mw.level(2);
        mw.cursed = false;
        mw.identify();
        drop(mw, 16 + WIDTH * 34).type = Heap.Type.LOCKED_CHEST;
        drop(new LloydsBeacon(), 18 + WIDTH * 34).type = Heap.Type.LOCKED_CHEST;
        Wand w;
        do {
            w = (Wand) Generator.random(Generator.Category.WAND);
        }while(Challenges.isItemBlocked(w));
        w.level(2);
        w.cursed = false;
        w.identify();
        drop(w, 20 + WIDTH * 34).type = Heap.Type.LOCKED_CHEST;

    }

    @Override
    public void occupyCell( Char ch ) {
        super.occupyCell(ch);

        if (map[entrance] == Terrain.ENTRANCE && map[exit] != Terrain.EXIT
                && ch == Dungeon.hero && (Dungeon.level.distance(ch.pos, entrance) >= 2)) {
            seal();
        }
    }

    @Override
    public void seal() {
        super.seal();

        //GooHard boss = new GooHard();
        DwarfMaster boss = new DwarfMaster();
        boss.pos = CENTER;
        GameScene.add(boss);

        Level.set(ENTRANCE, Terrain.EMPTY_SP);
        GameScene.updateMap(ENTRANCE);
        Dungeon.observe();
    }

    @Override
    public void unseal() {
        super.unseal();
        Level.set(ENTRANCE, Terrain.ENTRANCE);
        GameScene.updateMap(ENTRANCE);
        Level.set(EXIT, Terrain.UNLOCKED_EXIT);
        GameScene.updateMap(EXIT);
        Dungeon.observe();
    }

    @Override
    public String tileName(int tile) {
        switch (tile) {
            case Terrain.WATER:
                return M.L(SewerLevel.class, "water_name");
            case Terrain.GRASS:
                return M.L(SewerLevel.class, "grass_name");
            case Terrain.HIGH_GRASS:
                return M.L(SewerLevel.class, "high_grass_name");
            case Terrain.STATUE:
                return M.L(SewerLevel.class, "statue_name");
            default:
                return super.tileName(tile);
        }
    }

    @Override
    public String tileDesc(int tile) {
        switch (tile) {
            case Terrain.WATER:
                return M.L(HallsLevel.class, "water_desc");
            case Terrain.STATUE:
            case Terrain.STATUE_SP:
                return M.L(HallsLevel.class, "statue_desc");
            case Terrain.BOOKSHELF:
                return M.L(HallsLevel.class, "bookshelf_desc");
            default:
                return super.tileDesc(tile);
        }
    }


    private static final int A = Terrain.WALL;
    private static final int R = Terrain.BOOKSHELF;
    private static final int E = Terrain.UNLOCKED_EXIT;
    private static final int L = Terrain.EMPTY;
    private static final int S = Terrain.EMPTY_SP;
    private static final int F = Terrain.WATER;
    private static final int X = Terrain.LOCKED_DOOR;
    private static final int B = Terrain.DOOR;
    private static final int P = Terrain.STATUE_SP;
    private static final int J = Terrain.PEDESTAL;
    private static final int O = Terrain.STATUE;
    private static final int N = Terrain.ENTRANCE;

    private static final int[] codedMap = new int[]{
            A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,E,E,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,
            A,R,R,R,R,R,R,R,R,R,R,R,R,R,R,R,R,R,L,L,R,R,R,R,R,R,R,R,R,R,R,R,R,R,R,R,R,A,
            A,R,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,X,X,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,R,A,
            A,R,A,R,R,R,R,R,R,R,R,R,R,R,R,R,R,X,L,L,X,R,R,R,R,R,R,R,R,R,R,R,R,R,R,A,R,A,
            A,R,A,R,L,L,L,L,L,L,L,R,R,R,R,A,A,A,B,B,A,A,A,R,R,R,R,L,L,L,L,L,L,L,R,A,R,A,
            A,R,A,R,L,L,L,L,L,L,R,R,A,A,A,A,L,L,L,L,L,L,A,A,A,A,R,R,L,L,L,L,L,L,R,A,R,A,
            A,R,A,R,L,L,L,L,R,R,R,A,A,L,L,L,L,L,L,L,L,L,L,L,L,A,A,R,R,R,L,L,L,L,R,A,R,A,
            A,R,A,R,L,L,L,R,R,A,A,A,L,P,L,L,L,L,L,L,L,L,L,L,P,L,A,A,A,R,R,L,L,L,R,A,R,A,
            A,R,A,R,L,L,R,R,A,A,L,L,L,L,S,S,S,S,S,S,S,S,S,S,L,L,L,L,A,A,R,L,L,L,R,A,R,A,
            A,R,A,R,L,L,R,A,A,L,L,L,L,L,S,F,F,F,F,F,F,F,F,S,L,L,L,L,L,A,R,R,L,L,R,A,R,A,
            A,R,A,R,L,R,R,A,A,L,L,L,L,L,S,F,F,F,F,F,F,F,F,S,L,L,L,L,L,A,A,R,R,L,R,A,R,A,
            A,R,A,R,L,R,A,A,L,L,S,S,L,L,S,F,F,F,F,F,F,F,F,S,L,L,S,S,L,L,A,A,R,L,R,A,R,A,
            A,R,A,R,L,R,A,L,L,S,L,L,S,L,S,F,F,F,F,F,F,F,F,S,L,S,L,L,S,L,L,A,R,L,R,A,R,A,
            A,R,A,R,R,R,A,L,L,S,L,L,S,L,S,F,F,F,F,F,F,F,F,S,L,S,L,L,S,L,L,A,R,R,R,A,R,A,
            A,R,A,R,R,A,A,L,L,L,S,S,L,L,J,S,F,F,F,F,F,F,S,J,L,L,S,S,L,L,L,A,A,R,R,A,R,A,
            A,R,A,R,R,A,L,L,L,L,L,L,L,L,L,L,S,F,F,F,F,S,L,L,L,L,L,L,L,L,L,L,A,R,R,A,R,A,
            A,R,A,R,R,A,L,L,L,L,L,L,L,L,L,L,L,S,F,F,S,L,L,L,L,L,L,L,L,L,L,L,A,R,R,A,R,A,
            A,R,A,R,A,A,L,L,L,L,L,L,L,L,L,L,L,L,S,S,L,L,L,L,L,L,L,L,L,L,L,L,A,A,R,A,R,A,
            A,R,A,L,L,B,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,S,B,L,L,A,R,A,
            A,R,A,A,A,A,O,L,L,L,L,L,L,L,L,L,L,L,S,S,L,L,L,L,L,L,L,L,L,L,L,O,A,A,A,A,R,A,
            A,R,A,L,L,A,L,L,L,L,L,L,L,L,L,L,L,S,L,L,S,L,L,L,L,L,L,L,L,L,L,L,A,L,L,A,R,A,
            A,R,A,L,L,A,L,L,L,L,L,L,L,L,L,L,S,L,L,L,L,S,L,L,L,L,L,L,L,L,L,L,A,L,L,A,R,A,
            A,R,A,L,L,A,A,A,A,A,A,A,A,A,J,S,L,L,L,L,L,L,S,J,A,A,A,A,A,A,A,A,A,L,L,A,R,A,
            A,R,A,L,L,L,L,L,L,L,L,L,L,A,S,L,L,L,S,S,L,L,L,S,A,L,L,L,L,L,L,L,L,L,L,A,R,A,
            A,R,A,L,L,L,L,L,L,L,L,L,L,A,S,L,S,L,S,S,L,S,L,S,A,L,L,L,L,L,L,L,L,L,L,A,R,A,
            A,R,A,L,L,L,L,L,L,L,L,L,L,A,S,L,S,L,S,S,L,S,L,S,A,L,L,L,L,L,L,L,L,L,L,A,R,A,
            A,R,A,L,L,L,L,L,L,L,L,L,L,A,S,L,S,L,S,S,L,S,L,S,A,L,L,L,L,L,L,L,L,L,L,A,R,A,
            A,R,A,L,L,L,L,L,L,L,L,L,L,A,S,L,S,L,S,S,L,S,L,S,A,L,L,L,L,L,L,L,L,L,L,A,R,A,
            A,R,A,L,L,L,L,L,L,L,L,L,L,A,S,S,S,S,S,S,S,S,S,S,A,L,L,L,L,L,L,L,L,L,L,A,R,A,
            A,R,A,L,L,L,L,L,L,L,L,L,L,A,L,L,L,L,L,L,L,L,L,L,A,L,L,L,L,L,L,L,L,L,L,A,R,A,
            A,R,A,L,L,L,L,L,L,L,L,L,L,A,L,L,L,L,L,L,L,L,L,L,A,L,L,L,L,L,L,L,L,L,L,A,R,A,
            A,R,A,L,L,L,L,L,L,L,L,L,L,A,A,A,L,L,N,L,L,L,A,A,A,L,L,L,L,L,L,L,L,L,L,A,R,A,
            A,R,A,L,L,L,L,L,L,L,L,L,L,L,L,A,A,A,X,X,A,A,A,L,L,L,L,L,L,L,L,L,L,L,L,A,R,A,
            A,R,A,L,L,L,L,L,L,L,L,L,L,L,L,A,S,S,S,S,S,S,A,L,L,L,L,L,L,L,L,L,L,L,L,A,R,A,
            A,R,A,L,L,L,L,L,L,L,L,L,L,L,L,A,S,S,S,S,S,S,A,L,L,L,L,L,L,L,L,L,L,L,L,A,R,A,
            A,R,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,R,A,
            A,R,R,R,R,R,R,R,R,R,R,R,R,R,R,R,L,L,L,L,L,L,R,R,R,R,R,R,R,R,R,R,R,R,R,R,R,A,
            A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,A,
    };
}
