package com.shatteredpixel.shatteredpixeldungeon.items;

import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;

public class MainBooks extends Item {

    @Override
    public String info() {
        return desc()+"\n\n"+author;
    }

    public String author = Messages.get(this, "author");
}
