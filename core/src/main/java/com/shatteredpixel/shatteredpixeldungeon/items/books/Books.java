package com.shatteredpixel.shatteredpixeldungeon.items.books;

import com.shatteredpixel.shatteredpixeldungeon.items.MainBooks;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.watabou.noosa.particles.Emitter;

public class Books extends MainBooks {

    public String title = Messages.get(this, "title");
    public String author = Messages.get(this, "author");
    public Integer age;
    public String desc = Messages.get(this, "desc");
    public Integer icon = null;

    public Emitter emitter() { return null; }

    public ItemSprite.Glowing glowing() {
        return null;
    }
    @Override
    public int value() {
        return quantity * 12;
    }
}
