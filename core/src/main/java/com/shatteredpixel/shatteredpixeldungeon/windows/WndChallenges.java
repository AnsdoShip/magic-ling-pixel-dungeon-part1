/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * Shattered Pixel Dungeon
 * Copyright (C) 2014-2021 Evan Debenham
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package com.shatteredpixel.shatteredpixeldungeon.windows;

import com.shatteredpixel.shatteredpixeldungeon.Challenges;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.custom.messages.M;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.ui.CheckBox;
import com.shatteredpixel.shatteredpixeldungeon.ui.IconButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Icons;
import com.shatteredpixel.shatteredpixeldungeon.ui.RenderedTextBlock;
import com.shatteredpixel.shatteredpixeldungeon.ui.ScrollPane;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.watabou.noosa.Image;
import com.watabou.noosa.ui.Component;

import java.util.ArrayList;

public class WndChallenges extends Window {

	private static final int WIDTH		= 120;
	private static final int HEIGHT		= 170;
	private static final int TTL_HEIGHT = 16;
	private static final int BTN_HEIGHT = 16;
	private static final int GAP        = 1;

	private boolean editable;
	private ArrayList<CanScrollCheckBox> boxes;
	private ArrayList<CanScrollInfo> infos;

	public WndChallenges( long checked, boolean editable ) {

		super();

		resize(WIDTH, HEIGHT);

		this.editable = editable;

		ScrollPane pane = new ScrollPane(new Component()) {
			@Override
			public void onClick(float x, float y) {
				int max_size = boxes.size();
				for (int i = 0; i < max_size; ++i) {
					if (boxes.get(i).onClick(x, y))
						break;
				}
				max_size = infos.size();
				for(int i = 0; i<max_size;++i){
					if(infos.get(i).onClick(x,y)){
						break;
					}
				}
			}
		};
		add(pane);
		pane.setRect(0, GAP, WIDTH, HEIGHT - 2 * GAP);
		Component content = pane.content();

		boxes = new ArrayList<>();
		infos = new ArrayList<>();

		float pos = 0;

		final int normal_mode = 0;
		final int hard_mode = 7;
		final int warning_mode = 10;
		final int Test_Debug = 12;


		for (int i = 0; i < Challenges.NAME_IDS.length; i++) {

			final String challenge = Challenges.NAME_IDS[i];

			if(i==normal_mode || i==hard_mode || i==warning_mode || i==Test_Debug){
				RenderedTextBlock block = PixelScene.renderTextBlock(10);
				switch (i){
					case normal_mode:
						block.text(M.L(Challenges.class, "traditional"));
						block.hardlight(0x00FF00);
						break;
					case hard_mode:
						block.text(M.L(Challenges.class, "hard"));
						block.hardlight(0xFF00FF);
						break;
					case warning_mode:
						block.text(M.L(Challenges.class, "warning"));
						block.hardlight(0xFF0000);
						break;
					case Test_Debug:
						block.text(M.L(Challenges.class, "test"));
						block.hardlight(0xFFFF00);
						break;
				}
				block.setPos((WIDTH - block.width()) / 2,
						pos + GAP *4);
				PixelScene.align(block);
				content.add(block);
				pos += block.height() + 11*GAP;
			}

			CanScrollCheckBox cb = new CanScrollCheckBox(M.TL(Challenges.class, challenge));
			cb.checked((checked & Challenges.MASKS[i]) != 0);
			cb.active = editable;

			if (i > 0) {
				pos += GAP;
			}
			cb.setRect(0, pos, WIDTH - 16, BTN_HEIGHT);

			content.add(cb);
			boxes.add(cb);

			CanScrollInfo info = new CanScrollInfo(Icons.get(Icons.INFO)) {
				@Override
				protected void onClick() {
					super.onClick();
					ShatteredPixelDungeon.scene().add(
							new WndMessage(M.L(Challenges.class, challenge + "_desc"))
					);
				}
			};
			info.setRect(cb.right(), pos, 16, BTN_HEIGHT);
			infos.add(info);
			content.add(info);

			pos = cb.bottom();
		}
		content.setSize(WIDTH, (int) pos);
		pane.scrollTo(0, 0);


	}

	@Override
	public void onBackPressed() {

		if (editable) {
			long value = 0;
			for (int i=0; i < boxes.size(); i++) {
				if (boxes.get( i ).checked()) {
					value |= Challenges.MASKS[i];
				}
			}
			SPDSettings.challenges((int) value);
		}

		super.onBackPressed();
	}

	public static class CanScrollCheckBox extends CheckBox{

		public CanScrollCheckBox(String label) {
			super(label);
		}

		protected boolean onClick(float x, float y){
			if(!inside(x,y)) return false;
			if(active) onClick();

			return true;
		}

		@Override
		protected void layout(){
			super.layout();
			hotArea.width = hotArea.height = 0;
		}
	}

	public static class CanScrollInfo extends IconButton{
		public CanScrollInfo(Image Icon){super(Icon);}

		protected boolean onClick(float x, float y){
			if(!inside(x,y)) return false;
			if(active) onClick();
			return true;
		}

		@Override
		protected void layout(){
			super.layout();
			hotArea.width = hotArea.height = 0;
		}
	}
}