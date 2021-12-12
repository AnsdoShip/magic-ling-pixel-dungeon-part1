package com.shatteredpixel.shatteredpixeldungeon.scenes;

import com.shatteredpixel.shatteredpixeldungeon.Chrome;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.ui.Archs;
import com.shatteredpixel.shatteredpixeldungeon.ui.GOLDButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.StyledButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Camera;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.DeviceCompat;

public class ThanksScene extends PixelScene {

    private static final int BTN_HEIGHT = 22;
    private static final int GAP = 2;

    @Override
    public void create() {
        super.create();

        uiCamera.visible = false;

        int w = Camera.main.width;
        int h = Camera.main.height;

        int elementWidth = PixelScene.landscape() ? 202 : 120;

        Archs archs = new Archs();
        archs.setSize(w, h);
        add(archs);

        GOLDButton btnExit = new GOLDButton();
        btnExit.setPos(w - btnExit.width(), 0);
        add(btnExit);

        RenderedTextBlock title = PixelScene.renderTextBlock(Messages.get(this, "title"), 9);
        title.hardlight(Window.Pink_COLOR);
        title.setPos(
                (w - title.width()) / 2f,
                (20 - title.height()) / 2f
        );
        align(title);
        add(title);

        SupporterMessage msg = new SupporterMessage();
        msg.setSize(elementWidth, 0);
        add(msg);

        StyledButton link = new StyledButton(Chrome.Type.GREY_BUTTON_TR, "加入魔绫总群"){
            @Override
            protected void onClick() {
                super.onClick();
                String link = "https://jq.qq.com/?_wv=1027&k=l0xDPl0k";
                DeviceCompat.openURI(link);
            }
        };
        link.icon(Icons.get(Icons.LING));
        link.textColor(Window.ANSDO_COLOR);
        link.setSize(elementWidth, BTN_HEIGHT);
        add(link);

        float elementHeight = msg.height() + BTN_HEIGHT + GAP;

        float top = 16 + (h - 16 - elementHeight)/2f;
        float left = (w-elementWidth)/2f;

        msg.setPos(left, top);
        align(msg);

        link.setPos(left, msg.bottom()+GAP);
        align(link);

    }

    @Override
    protected void onBackPressed() {
        ShatteredPixelDungeon.switchNoFade(SPSLScene.class);
    }

    private static class SupporterMessage extends Component {

        NinePatch bg;
        RenderedTextBlock text;

        @Override
        protected void createChildren() {
            bg = Chrome.get(Chrome.Type.GREY_BUTTON_TR);
            add(bg);

              String message = "编码致谢：\n-REN\n-Alexstrasza\n-SmuJB\n-Tianscar\n-TrashBoxbodylev";
                message += "\n\n测试致谢：\n-迷茫\n-小狐狸\n-摘希\n-ZIOM-ObSir\n-坏猫猫\n-不是史神\n-月鸾";
                message += "\n\n美术致谢：\n-被子\n-冷群怪\n-REN\n-Daniel Clan\n-奈亚子\n-TianScar";
                message += "\n\n宣传致谢：\n-那些回忆\n-罗贝里";
                message += "\n\n捐赠致谢：\n-洛诗夜\n-风植居士\n-JOJO";

            text = PixelScene.renderTextBlock(message, 6);
            add(text);

            text.hardlight(Window.WHITE);

        }

        @Override
        protected void layout() {
            bg.x = x;
            bg.y = y;

            text.maxWidth((int)width - bg.marginHor());
            text.setPos(x + bg.marginLeft(), y + bg.marginTop() + 1);

            height = (text.bottom() + 3) - y;

            height += bg.marginBottom();

            bg.size(width, height);

        }

    }

}
