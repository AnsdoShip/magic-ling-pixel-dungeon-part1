package com.shatteredpixel.shatteredpixeldungeon.ui;

import com.shatteredpixel.shatteredpixeldungeon.Assets;
import com.shatteredpixel.shatteredpixeldungeon.Dungeon;
import com.shatteredpixel.shatteredpixeldungeon.SPDAction;
import com.shatteredpixel.shatteredpixeldungeon.SPDSettings;
import com.shatteredpixel.shatteredpixeldungeon.Statistics;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.Hunger;
import com.shatteredpixel.shatteredpixeldungeon.actors.buffs.LockedFloor;
import com.shatteredpixel.shatteredpixeldungeon.effects.Speck;
import com.shatteredpixel.shatteredpixeldungeon.items.Item;
import com.shatteredpixel.shatteredpixeldungeon.scenes.GameScene;
import com.shatteredpixel.shatteredpixeldungeon.scenes.PixelScene;
import com.shatteredpixel.shatteredpixeldungeon.sprites.HeroSprite;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndGame;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndHero;
import com.shatteredpixel.shatteredpixeldungeon.windows.WndJournal;
import com.watabou.gltextures.TextureCache;
import com.watabou.glwrap.Blending;
import com.watabou.input.GameAction;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.ui.Component;
import com.watabou.utils.ColorMath;

public class StatusPane extends Component {

	private NinePatch bg;
	private Image avatar;
	public static float talentBlink;
	private float warning;

	private int lastTier = 0;

	private Image rawShielding;
	private Image shieldedHP;
	private Image hp;
	private Image hg;
	private BitmapText hpText;
	private BitmapText hgText;

	private Image exp;

	private BossHealthBar bossHP;

	private int lastLvl = -1;

	private BitmapText level;
	private BitmapText depth;

	private DangerIndicator danger;
	private BuffIndicator buffs;
	private Compass compass;
	public PageIndicator page;
	public PageIndicatorB pageb;
	public MainHandIndicator mainhand;
	public BossSelectIndicator bossselect;
	public JoinIndicator joinxxx;
	private JournalButton btnJournal;
	private MenuButton btnMenu;

	private Toolbar.PickedUpItem pickedUp;

	private BitmapText version;

	@Override
	protected void createChildren() {

		bg = new NinePatch( Assets.Interfaces.STATUS, 0, 0, 128, 36, 85, 0, 45, 0 );
		add( bg );

		add( new Button(){
			@Override
			protected void onClick () {
				Camera.main.panTo( Dungeon.hero.sprite.center(), 5f );
				GameScene.show( new WndHero() );
			}

			@Override
			public GameAction keyAction() {
				return SPDAction.HERO_INFO;
			}
		}.setRect( 0, 1, 30, 30 ));

		btnJournal = new JournalButton();
		add( btnJournal );

		btnMenu = new MenuButton();
		add( btnMenu );

		avatar = HeroSprite.avatar( Dungeon.hero.heroClass, lastTier );
		add( avatar );

		talentBlink = 0;

		compass = new Compass( Statistics.amuletObtained ? Dungeon.level.entrance : Dungeon.level.exit );
		add( compass );

		rawShielding = new Image( Assets.Interfaces.SHLD_BAR );
		rawShielding.alpha(0.5f);
		add(rawShielding);

		shieldedHP = new Image( Assets.Interfaces.SHLD_BAR );
		add(shieldedHP);

		hp = new Image( Assets.Interfaces.HP_BAR );
		add( hp );

		hg = new Image(Assets.Interfaces.HG_BAR);
		add(hg);

		hpText = new BitmapText(PixelScene.pixelFont);
		hpText.alpha(0.6f);
		add(hpText);

		hgText = new BitmapText(PixelScene.pixelFont);
		hgText.alpha(0.6f);
		add(hgText);

		page=new PageIndicator();
		add(page);

		pageb=new PageIndicatorB();
		add(pageb);

		mainhand=new MainHandIndicator();
		add(mainhand);

		bossselect=new BossSelectIndicator();
		add(bossselect);

		joinxxx=new JoinIndicator();
		add(joinxxx);

		exp = new Image( Assets.Interfaces.XP_BAR );
		add( exp );

		bossHP = new BossHealthBar();
		add( bossHP );

		level = new BitmapText( PixelScene.pixelFont);
		level.hardlight( 0xFFFFAA );
		add( level );

		depth = new BitmapText( Integer.toString( Dungeon.depth ), PixelScene.pixelFont)
		{
			private float time;

			@Override
			public void update() {
				super.update();
				am = 1f + 0.01f*Math.max(0f, (float)Math.sin( time += Game.elapsed/5 ));
				time += Game.elapsed / 3.5f;
				float r = 0.33f+0.57f*Math.max(0f, (float)Math.sin( time));
				float g = 0.53f+0.57f*Math.max(0f, (float)Math.sin( time + 2*Math.PI/3 ));
				float b = 0.63f+0.57f*Math.max(0f, (float)Math.sin( time + 4*Math.PI/3 ));

				if(Dungeon.bossLevel() && Dungeon.hero.buff(LockedFloor.class) != null) {
					depth.hardlight(r, g, b);
				} else {
					depth.hardlight(0xCACFC2);
				}
				if (time >= 2f * Math.PI) time = 0;
			}

			@Override
			public void draw() {
				Blending.setLightMode();
				super.draw();
				Blending.setNormalMode();
			}
		};
		depth.alpha(1f);
		depth.measure();
		add( depth );

		danger = new DangerIndicator();
		add( danger );

		buffs = new BuffIndicator( Dungeon.hero );
		add( buffs );

		add( pickedUp = new Toolbar.PickedUpItem());

		version = new BitmapText("v" + Game.version + "-JDSALing", PixelScene.pixelFont) {
			private float time;

			@Override
			public void update() {
				super.update();
				am = 1f + 0.01f*Math.max(0f, (float)Math.sin( time += Game.elapsed/5 ));
				time += Game.elapsed / 5f;
				float r = 0.43f+0.57f*Math.max(0f, (float)Math.sin( time));
				float g = 0.43f+0.57f*Math.max(0f, (float)Math.sin( time + 2*Math.PI/3 ));
				float b = 0.43f+0.57f*Math.max(0f, (float)Math.sin( time + 4*Math.PI/3 ));
				if(Dungeon.bossLevel() && Dungeon.hero.buff(LockedFloor.class) != null) {
					version.hardlight(r, g, b);
				} else {
					version.hardlight(0xCCCCCC);
				}
				if (time >= 2f * Math.PI) time = 0;
			}

			@Override
			public void draw() {
				Blending.setLightMode();
				super.draw();
				Blending.setNormalMode();
			}
		};
		version.alpha(1f);
		add(version);
	}

	@Override
	protected void layout() {

		height = 32;

		bg.size( width, bg.height );

		avatar.x = bg.x + 15 - avatar.width / 2f;
		avatar.y = bg.y + 16 - avatar.height / 2f;
		PixelScene.align(avatar);

		compass.x = avatar.x + avatar.width / 2f - compass.origin.x;
		compass.y = avatar.y + avatar.height / 2f - compass.origin.y;
		PixelScene.align(compass);

		hp.x = shieldedHP.x = rawShielding.x = 30;
		hp.y = shieldedHP.y = rawShielding.y = 3;

		hpText.scale.set(PixelScene.align(0.5f));
		hpText.x = hp.x + 1;
		hpText.y = hp.y + (hp.height - (hpText.baseLine()+hpText.scale.y))/2f;
		hpText.y -= 0.001f; //prefer to be slightly higher
		PixelScene.align(hpText);

		hg.x = 30.0f;
		hg.y = 8.0f;

		hgText.scale.set(PixelScene.align(0.5f));
		hgText.x = hg.x + 1;
		hgText.y = hg.y + (hp.height - (hgText.baseLine()+hgText.scale.y))/2f;
		hgText.y -= 0.001f; //prefer to be slightly higher
		PixelScene.align(hgText);

		bossHP.setPos( 6 + (width - bossHP.width())/2, 20);

		depth.x = width - 35.5f - depth.width() / 2f;
		depth.y = 8f - depth.baseLine() / 2f;
		PixelScene.align(depth);

		danger.setPos( width - danger.width(), 20 );

		buffs.setPos( 31, 12 );

		btnJournal.setPos( width - 42, 1 );

		btnMenu.setPos( width - btnMenu.width(), 1 );

		version.scale.set(PixelScene.align(0.5f));
		version.measure();
		version.x = width - version.width();
		version.y = btnMenu.bottom() + (4 - version.baseLine());
		PixelScene.align(version);
	}

	private static final int[] warningColors = new int[]{0x660000, 0xCC0000, 0x660000};

	@Override
	public void update() {
		super.update();

		int health = Dungeon.hero.HP;
		int shield = Dungeon.hero.shielding();
		int maxHp = Dungeon.hero.HT;
		int maxHunger = (int) Hunger.STARVING;

		if (!Dungeon.hero.isAlive()) {
			avatar.tint(0x000000, 0.5f);
		} else if ((health/(float)maxHp) < 0.3f) {
			warning += Game.elapsed * 5f *(0.4f - (health/(float)maxHp));
			warning %= 1f;
			avatar.tint(ColorMath.interpolate(warning, warningColors), 0.5f );
		} else if (talentBlink > 0){
			talentBlink -= Game.elapsed;
			avatar.tint(1, 1, 0, (float)Math.abs(Math.sin(2*talentBlink)/2f));
		} else {
			avatar.resetColor();
		}
		if (SPDSettings.ClassUI()) {
			bg.texture = TextureCache.get(Assets.Interfaces.STATUS_DARK);
		} else {
			bg.texture = TextureCache.get(Assets.Interfaces.STATUS);
		}

		if (SPDSettings.ClassPage()) {
			page.setPos(0, 40);
			pageb.setPos(0, 9999);
			mainhand.setPos(0, 51);
			joinxxx.setPos(0, 78);
			bossselect.setPos(0, 104);
		} else {
			page.setPos(0, 9999);
			pageb.setPos(0, 40);
			mainhand.setPos(0, 9999);
			joinxxx.setPos(0, 9999);
			bossselect.setPos(0, 9999);
		}


		hp.scale.x = Math.max( 0, (health-shield)/(float)maxHp);
		shieldedHP.scale.x = health/(float)maxHp;
		rawShielding.scale.x = shield/(float)maxHp;

		if (shield <= 0){
			hpText.text(health + "/" + maxHp);
		}
		else {
			hpText.text(health + "+" + shield +  "/" + maxHp);
		}

		Hunger hungerBuff = Dungeon.hero.buff(Hunger.class);
		if (hungerBuff != null) {
			int hunger = Math.max(0, maxHunger - hungerBuff.hunger());
			hg.scale.x = (float) hunger / (float) maxHunger;
			hgText.text(hunger + "/" + maxHunger);
		}
		else if (Dungeon.hero.isAlive()) {
			hg.scale.x = 1.0f;
		}

		exp.scale.x = (width / exp.width) * Dungeon.hero.exp / Dungeon.hero.maxExp();

		if (Dungeon.hero.lvl != lastLvl) {

			if (lastLvl != -1) {
				Emitter emitter = (Emitter)recycle( Emitter.class );
				emitter.revive();
				emitter.pos( 27, 27 );
				emitter.burst( Speck.factory( Speck.STAR ), 12 );
			}

			lastLvl = Dungeon.hero.lvl;
			level.text( Integer.toString( lastLvl ) );
			level.measure();
			level.x = 27.5f - level.width() / 2f;
			level.y = 28.0f - level.baseLine() / 2f;
			PixelScene.align(level);
		}

		int tier = Dungeon.hero.tier();
		if (tier != lastTier) {
			lastTier = tier;
			avatar.copy( HeroSprite.avatar( Dungeon.hero.heroClass, tier ) );
		}
	}

	public void pickup( Item item, int cell) {
		pickedUp.reset( item,
				cell,
				btnJournal.journalIcon.x + btnJournal.journalIcon.width()/2f,
				btnJournal.journalIcon.y + btnJournal.journalIcon.height()/2f);
	}

	public void flash(){
		btnJournal.flashing = true;
	}

	public void updateKeys(){
		btnJournal.updateKeyDisplay();
	}

	private static class JournalButton extends Button {

		private Image bg;
		private Image journalIcon;
		private KeyDisplay keyIcon;

		private boolean flashing;

		public JournalButton() {
			super();

			width = bg.width + 13; //includes the depth display to the left
			height = bg.height + 4;
		}

		@Override
		public GameAction keyAction() {
			return SPDAction.JOURNAL;
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			bg = new Image( Assets.Interfaces.MENU, 2, 2, 13, 11 );
			add( bg );

			journalIcon = new Image( Assets.Interfaces.MENU, 31, 0, 11, 7);
			add( journalIcon );

			keyIcon = new KeyDisplay();
			add(keyIcon);
			updateKeyDisplay();
		}

		@Override
		protected void layout() {
			super.layout();

			bg.x = x + 13;
			bg.y = y + 2;

			journalIcon.x = bg.x + (bg.width() - journalIcon.width())/2f;
			journalIcon.y = bg.y + (bg.height() - journalIcon.height())/2f;
			PixelScene.align(journalIcon);

			keyIcon.x = bg.x + 1;
			keyIcon.y = bg.y + 1;
			keyIcon.width = bg.width - 2;
			keyIcon.height = bg.height - 2;
			PixelScene.align(keyIcon);
		}

		private float time;

		@Override
		public void update() {
			super.update();

			if (flashing){
				journalIcon.am = (float)Math.abs(Math.cos( 3 * (time += Game.elapsed) ));
				keyIcon.am = journalIcon.am;
				if (time >= 0.333f*Math.PI) {
					time = 0;
				}
			}
		}

		public void updateKeyDisplay() {
			keyIcon.updateKeys();
			keyIcon.visible = keyIcon.keyCount() > 0;
			journalIcon.visible = !keyIcon.visible;
			if (keyIcon.keyCount() > 0) {
				bg.brightness(.8f - (Math.min(6, keyIcon.keyCount()) / 20f));
			} else {
				bg.resetColor();
			}
		}

		@Override
		protected void onPointerDown() {
			bg.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.Sounds.CLICK );
		}

		@Override
		protected void onPointerUp() {
			if (keyIcon.keyCount() > 0) {
				bg.brightness(.8f - (Math.min(6, keyIcon.keyCount()) / 20f));
			} else {
				bg.resetColor();
			}
		}

		@Override
		protected void onClick() {
			flashing = false;
			time = 0;
			keyIcon.am = journalIcon.am = 1;
			GameScene.show( new WndJournal() );
		}

	}

	private static class MenuButton extends Button {

		private Image image;

		public MenuButton() {
			super();

			width = image.width + 4;
			height = image.height + 4;
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			image = new Image( Assets.Interfaces.MENU, 17, 2, 12, 11 );
			add( image );
		}

		@Override
		protected void layout() {
			super.layout();

			image.x = x + 2;
			image.y = y + 2;
		}

		@Override
		protected void onPointerDown() {
			image.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.Sounds.CLICK );
		}

		@Override
		protected void onPointerUp() {
			image.resetColor();
		}

		@Override
		protected void onClick() {
			GameScene.show( new WndGame() );
		}
	}
}