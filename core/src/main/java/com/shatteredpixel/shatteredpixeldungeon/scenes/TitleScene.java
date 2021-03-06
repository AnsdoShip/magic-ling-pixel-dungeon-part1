package com.shatteredpixel.shatteredpixeldungeon.scenes;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Chrome;
import com.shatteredpixel.shatteredpixeldungeon.GamesInProgress;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.ShatteredPixelDungeon;
import com.shatteredpixel.shatteredpixeldungeon.effects.BannerSprites;
import com.shatteredpixel.shatteredpixeldungeon.effects.Fireball;
import com.shatteredpixel.shatteredpixeldungeon.effects.Flare;
import com.shatteredpixel.shatteredpixeldungeon.messages.Messages;
import com.shatteredpixel.shatteredpixeldungeon.services.news.News;
import com.shatteredpixel.shatteredpixeldungeon.sprites.FireMagicGirlSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSprite;
import com.shatteredpixel.shatteredpixeldungeon.sprites.ItemSpriteSheet;
import com.shatteredpixel.shatteredpixeldungeon.sprites.RedNecromancerSprite_EX;
import com.shatteredpixel.shatteredpixeldungeon.ui.Archs;
import com.shatteredpixel.shatteredpixeldungeon.ui.StyledButton;
import com.shatteredpixel.shatteredpixeldungeon.ui.Window;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndSettings;
import com.watabou.glwrap.Blending;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Music;
import com.watabou.utils.ColorMath;
import com.watabou.utils.DeviceCompat;
import com.watabou.utils.GameMath;

import java.util.Date;

public class TitleScene extends PixelScene {

	@Override
	public void create() {


		super.create();

		Music.INSTANCE.play( Assets.Music.THEME, true );

		uiCamera.visible = false;

		int w = Camera.main.width;
		int h = Camera.main.height;

		Image title = BannerSprites.get( BannerSprites.Type.PIXEL_DUNGEON );

		float topRegion = Math.max(title.height - 6, h * 0.45f);

		title.x = (w - title.width()) / 2f;
		title.y = 2 + (topRegion - title.height()) / 2f;

		align(title);

		placeTorch(title.x + 22, title.y + 46);
		placeTorch(title.x + title.width - 22, title.y + 46);

		placeTorch2(title.x + -5, title.y + 63);
		placeTorch2(title.x + title.width - 15, title.y + 63);

		placeTorch3(title.x + -10, title.y + 46);
		placeTorch3(title.x + title.width - 10, title.y + 46);

		Image swordLeft = new Image( BannerSprites.get( BannerSprites.Type.SWORD ) ) {
			private float preCurTime = 0;
			private float curTime = 0;

			@Override
			public void update() {
				super.update();
				this.origin.set( this.width / 2, this.height / 2 );
				float time = 0.8f;
				this.x = - this.width + curTime/ time * (Camera.main.width / 2f + this.width / 2f);
				this.angle = 90 - curTime/ time *225;
				am = curTime*curTime*curTime/(time * time * time);

				float preTime = 0.9f;
				if (preCurTime < preTime) {
					preCurTime += Game.elapsed;
					return;
				}
				if (curTime < time) {
					curTime += Game.elapsed;
					if (curTime >= time) Camera.main.shake( GameMath.gate( 1, 2, 5), 0.3f );
				}
			}
		};
		swordLeft.center(title.center());
		add( swordLeft );

		Image swordRight = new Image( BannerSprites.get( BannerSprites.Type.SWORD ) ) {
			private float preCurTime = 0;
			private float curTime = 0;

			@Override
			public void update() {
				super.update();
				this.origin.set( this.width / 2, this.height / 2 );
				float time = 0.8f;
				this.x = Camera.main.width - curTime/ time * (Camera.main.width / 2f + this.width / 2f);
				this.angle = 90 + curTime/ time * 225;
				am = curTime*curTime*curTime/(time * time * time);

				float preTime = 0.9f;
				if (preCurTime < preTime) {
					preCurTime += Game.elapsed;
					return;
				}
				if (curTime < time) curTime += Game.elapsed;
			}
		};
		swordRight.center(title.center());
		add( swordRight );

		add( title );

		Flare flare = new Flare( 7, 128 ) {
			private float time1 = 0;
			private float time2 = 0;
			private float time3 = 0;
			@Override
			public void update() {
				super.update();
				am = Math.max(0f, (float)Math.sin(time1 += Game.elapsed));
				if (time1 >= 1.5f * Math.PI) time1 = 0;
				rm = Math.max(0f, (float)Math.sin(time2 += Game.elapsed));
				if (time2 >= 1.0f * Math.E) time2 = 5;
				ra = Math.max(0f, (float)Math.sin(time3 += Game.elapsed));
				if (time3 >= 1.0f * Math.PI) time3 = 1;
			}
		};
		flare.color( Window.BLUE_COLOR, true ).show( title, 0 ).angularSpeed = +27;

		Archs archs = new Archs();
		archs.setSize( w, h );
		addToBack( archs );

		Image signs = new Image( BannerSprites.get( BannerSprites.Type.PIXEL_DUNGEON_SIGNS ) ) {
			private float time = 0;
			@Override
			public void update() {
				super.update();
				am = Math.max(0f, (float)Math.sin(time += Game.elapsed));
				if (time >= 1.5f * Math.PI) time = 0;
			}
			@Override
			public void draw() {
				Blending.setLightMode();
				super.draw();
				Blending.setNormalMode();
			}
		};
		signs.x = title.x + (title.width() - signs.width()) / 2f;
		signs.y = title.y;
		add(signs);

		final Chrome.Type GREY_TR = Chrome.Type.GREY_BUTTON_TR;

		StyledButton btnPlay = new StyledButton(GREY_TR, Messages.get(this, "enter")){
			@Override
			protected void onClick() {
				if (GamesInProgress.checkAll().size() == 0) {
					GamesInProgress.selectedClass = null;
					GamesInProgress.curSlot = 1;
				}
				ShatteredPixelDungeon.switchNoFade( StartScene.class );
			}

			@Override
			protected boolean onLongClick() {
				//making it easier to start runs quickly while debugging
				if (DeviceCompat.isDebug()) {
					GamesInProgress.selectedClass = null;
					GamesInProgress.curSlot = 1;
					ShatteredPixelDungeon.switchScene(StartScene.class);
					return true;
				}
				return super.onLongClick();
			}
		};
		btnPlay.icon(new ItemSprite(ItemSpriteSheet.ENDDIED, null));
		add(btnPlay);

		StyledButton btnRankings = new StyledButton(GREY_TR,Messages.get(this, "rankings")) {
			@Override
			protected void onClick() {
				ShatteredPixelDungeon.switchNoFade(RankingsScene.class);
			}
		};
		btnRankings.icon(new ItemSprite(ItemSpriteSheet.ANKH, null));
		add(btnRankings);

		StyledButton btnBadges = new StyledButton(GREY_TR, Messages.get(this, "badges")) {
			@Override
			protected void onClick() {
				ShatteredPixelDungeon.switchNoFade(BadgesScene.class);
			}
		};
		btnBadges.icon(new ItemSprite(ItemSpriteSheet.DG12, null));
		add(btnBadges);

		StyledButton btnSupport = new SupportButton(GREY_TR, Messages.get(this, "support"));
		add(btnSupport);

		StyledButton btnChanges = new ChangesButton(GREY_TR, Messages.get(this, "changes"));
		btnChanges.icon(new ItemSprite(ItemSpriteSheet.ICEBOOK, null));
		add(btnChanges);

		StyledButton btnSettings = new SettingsButton(GREY_TR, Messages.get(this, "settings"));
		add(btnSettings);

		StyledButton btnAbout = new StyledButton(GREY_TR, Messages.get(this, "about")){
			@Override
			protected void onClick() {
				ShatteredPixelDungeon.switchNoFade( AboutSelectScene.class );
			}
		};
		btnAbout.icon(new ItemSprite(ItemSpriteSheet.MAGICGIRLBOOKS, null));
		add(btnAbout);

		StyledButton btnNews = new NewsButton(GREY_TR, Messages.get(this, "news"));
		btnNews.icon(new ItemSprite(ItemSpriteSheet.YELLOWBOOKS, null));
		add(btnNews);

		final int BTN_HEIGHT = 20;
		int GAP = (int)(h - topRegion - (landscape() ? 3 : 4) * BTN_HEIGHT) / 3;
		GAP /= landscape() ? 3 : 5;
		GAP = Math.max(GAP, 2);

		if (landscape()) {
			btnPlay.setRect(title.x - 50, topRegion + GAP, title.width() + 100 - 1, BTN_HEIGHT);
			align(btnPlay);
			btnRankings.setRect(btnPlay.left(), btnPlay.bottom()+ GAP, (btnPlay.width() * 0.332f) - 1, BTN_HEIGHT);
			btnBadges.setRect(btnRankings.left(), btnRankings.bottom()+GAP, btnRankings.width(), BTN_HEIGHT);
			btnSupport.setRect(btnRankings.right() + 2, btnRankings.top(), btnRankings.width(), BTN_HEIGHT);
			btnChanges.setRect(btnSupport.left(), btnSupport.bottom() + GAP, btnRankings.width(), BTN_HEIGHT);
			btnSettings.setRect(btnSupport.right() + 2, btnSupport.top(), btnRankings.width(), BTN_HEIGHT);
			btnAbout.setRect(btnSettings.left(), btnSettings.bottom() + GAP, btnRankings.width(), BTN_HEIGHT);
			btnNews.setRect(btnPlay.left(), btnAbout.bottom() + GAP, btnAbout.width() + 157 - 1, BTN_HEIGHT);
			align(btnNews);
		}
		else {
			btnPlay.setRect(title.x, topRegion + GAP, title.width(), BTN_HEIGHT);
			align(btnPlay);
			btnRankings.setRect(btnPlay.left(), btnPlay.bottom()+ GAP, (btnPlay.width() / 2) - 1, BTN_HEIGHT);
			btnBadges.setRect(btnRankings.right() + 2, btnRankings.top(), btnRankings.width(), BTN_HEIGHT);
			btnSupport.setRect(btnRankings.left(), btnRankings.bottom()+ GAP, btnRankings.width(), BTN_HEIGHT);
			btnChanges.setRect(btnSupport.right() + 2, btnSupport.top(), btnSupport.width(), BTN_HEIGHT);
			btnSettings.setRect(btnSupport.left(), btnSupport.bottom()+GAP, btnRankings.width(), BTN_HEIGHT);
			btnAbout.setRect(btnSettings.right() + 2, btnSettings.top(), btnSettings.width(), BTN_HEIGHT);
			btnNews.setRect(btnPlay.left(), btnAbout.bottom() + GAP, btnAbout.width() + 68 - 1, BTN_HEIGHT);
			align(btnNews);
		}

		BitmapText version = new BitmapText( "v" + Game.version, pixelFont);
		version.measure();
		version.alpha( 0.4f);
		version.x = w - version.width() - 4;
		version.y = h - version.height() - 2;
		add( version );

		fadeIn();
	}

	private void placeTorch( float x, float y ) {
		Fireball fb2 = new Fireball();
		fb2.setPos( x, y );
		add( fb2 );
	}

	private void placeTorch2( float x, float y ) {
		Image fb = (new RedNecromancerSprite_EX());
		fb.setPos( x, y );
		add( fb );
	}

	private void placeTorch3( float x, float y ) {
		Image fb = (new FireMagicGirlSprite());
		fb.setPos( x, y );
		add( fb );
	}

	private static class NewsButton extends StyledButton {

		public NewsButton(Chrome.Type type, String label ){
			super(type, label);
			if (SPDSettings.news()) News.checkForNews();
		}

		int unreadCount = -1;

		@Override
		public void update() {
			super.update();

			if (unreadCount == -1 && News.articlesAvailable()){
				long lastRead = SPDSettings.newsLastRead();
				if (lastRead == 0){
					if (News.articles().get(0) != null) {
						SPDSettings.newsLastRead(News.articles().get(0).date.getTime());
					}
				} else {
					unreadCount = News.unreadArticles(new Date(SPDSettings.newsLastRead()));
					if (unreadCount > 0) {
						unreadCount = Math.min(unreadCount, 9);
						text(text() + "(" + unreadCount + ")");
					}
				}
			}

			if (unreadCount > 0){
				textColor(ColorMath.interpolate( 0xFFFFFF, Window.TITLE_COLOR,
						0.5f + (float)Math.sin(Game.timeTotal*5)/2f));
			}
		}

		@Override
		protected void onClick() {
			super.onClick();
			ShatteredPixelDungeon.switchNoFade( NewsScene.class );
		}
	}

	private static class ChangesButton extends StyledButton {

		public ChangesButton( Chrome.Type type, String label ){
			super(type, label);
		}

		@Override
		protected void onClick() {
			super.onClick();
			ShatteredPixelDungeon.switchNoFade( ChangesScene.class );
		}
	}

	private static class SettingsButton extends StyledButton {

		public SettingsButton(Chrome.Type type, String label){
			super(type, label);
			icon(new ItemSprite(ItemSpriteSheet.BREDBOOK, null));
			textColor(Window.Pink_COLOR);
		}

		@Override
		public void update() {
			super.update();
		}

		@Override
		protected void onClick() {
			ShatteredPixelDungeon.scene().add(new WndSettings());
		}
	}

	private static class SupportButton extends StyledButton {

		public SupportButton( Chrome.Type type, String label ){
			super(type, label);
			icon(new ItemSprite(ItemSpriteSheet.NOKING, null));
			textColor(Window.TITLE_COLOR);
		}

		@Override
		protected void onClick() {
			ShatteredPixelDungeon.switchNoFade(ThanksScene.class);
		}
	}

}
