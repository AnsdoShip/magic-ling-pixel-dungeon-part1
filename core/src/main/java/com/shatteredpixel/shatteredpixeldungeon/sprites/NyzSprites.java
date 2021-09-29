//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.shatteredpixel.shatteredpixeldungeon.sprites;

import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.particles.PixelParticle;

public class NyzSprites extends MobSprite {
    private PixelParticle coin;

    public NyzSprites() {
        this.texture("Npcs/nyz.png");
        TextureFilm var1 = new TextureFilm(this.texture, 16, 16);
        Integer var2 = 1;
        this.idle = new Animation(10, true);
        Animation var3 = this.idle;
        Integer var4 = 0;
        var3.frames(var1, new Object[]{var2, var2, var2, var2, var2, var4, var4, var4, var4});
        this.die = new Animation(20, false);
        this.die.frames(var1, new Object[]{var4});
        this.run = this.idle.clone();
        this.attack = this.idle.clone();
        this.idle();
    }

    public void onComplete(Animation var1) {
        super.onComplete(var1);
        if (this.visible && var1 == this.idle) {
            if (this.coin == null) {
                this.coin = new PixelParticle();
                this.parent.add(this.coin);
            }}
    }
}
