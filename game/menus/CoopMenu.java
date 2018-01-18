package menus;

import static engine.Mouse.setMouseEnabled;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

import engine.Display;
import entities.Entity;
import entities.Light;
import entities.TimeShip;
import gamestates.GamePerspective;
import guis.ButtonComponent;
import guis.GUI;
import guis.ImageComponent;
import guis.QuadComponent;
import guis.TextComponent;
import menus.coop.HostMenu;
import menus.coop.JoinMenu;
import objects.Camera;
import objects.Model_3D;
import objects.Skybox;
import renderer.MasterRenderer;
import renderer.skyboxRenderer.SkyboxRenderer;
import utils.SourceFile;
import utils.maths.Maths;
import utils.models.ModelMaster;

public class CoopMenu extends GUI {
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private ArrayList<Light> lights = new ArrayList<Light>();

	private Skybox skybox;

	private Camera camera;

	private MasterRenderer master;
	private SkyboxRenderer skyboxRenderer;

	private Entity timeship_1;
	private Entity timeship_2;

	private JoinMenu joinMenu;
	private HostMenu hostMenu;

	@SuppressWarnings("unused")
	public CoopMenu(MasterRenderer master, SkyboxRenderer skyboxRenderer) {
		this.skyboxRenderer = skyboxRenderer;
		this.master = master;
		super.setPosition(0f, 0.0f);
		images();
		buttons();
		// texts();
		camera = new Camera();
		camera.setNewProj();

		camera.x = 0;
		camera.y = -5f;
		camera.z = 20;

		camera.pitch = 4;
		camera.yaw = 35;
		camera.updateMatrix();

		Light sun = new Light(new Vector3f(2000, 2000, 2000), new Vector3f(1, 1, 1));
		lights.add(sun);
		master.setProjectionMatrix(camera.getProjectionMatrix());

		joinMenu = new JoinMenu();
		hostMenu = new HostMenu();

		SourceFile ame_nebula = new SourceFile("/res/skybox/space_1");
		Skybox skybox = new Skybox(new SourceFile[] { new SourceFile(ame_nebula, "face_right.png"),
				new SourceFile(ame_nebula, "face_left.png"), new SourceFile(ame_nebula, "face_bottom.png"),
				new SourceFile(ame_nebula, "face_top.png"), new SourceFile(ame_nebula, "face_back.png"),
				new SourceFile(ame_nebula, "face_front.png") }, 512);
		this.skybox = skybox;

		Model_3D timemastersHQ = ModelMaster.getOBJModel("timemasters_hq_1");
		timeship_1 = new TimeShip(new Vector3f(0, -2f, 0f), new Vector3f(-35, -180 + 10, 10), 0.4f);
		timeship_2 = new TimeShip(new Vector3f(0, -10f, 0f), new Vector3f(-20, -180 + 10, 15), 0.4f);

		entities.add(timeship_1);
		entities.add(timeship_2);

		GamePerspective settingsMenu = new GamePerspective("coop_menu") {

			@Override
			public void render() {
				renderComponents();
			}

			@Override
			public void start() {
				setMouseEnabled(true);
			}

			@Override
			public void stop() {

			}
		};

		joinMenu.showAll();
		hostMenu.hideAll();

	}

	List<ButtonComponent> buttons = new ArrayList<ButtonComponent>();

	public ButtonComponent host_button, join_button;

	public void buttons() {
		float spaceBetw = 0.11f;
		float startFloat = 0.41f;

		float y = 0.25f;

		host_button = new ButtonComponent(this, startFloat, y, 0.1f, 0.06f);
		join_button = new ButtonComponent(this, startFloat + spaceBetw, y, 0.1f, 0.06f);
		buttons.add(host_button);
		buttons.add(join_button);

		for (ButtonComponent button : buttons) {
			button.setOutlineWidth(0.001f);
			button.setOutlineColor(new Vector4f(0.3f, 0.3f, 0.3f, 0.7f));
			button.setBackgroundColor(new Vector4f(0.7f, 0.7f, 0.7f, 0.7f));
			button.setTextPosition(new Vector2f(0, 0.0137f));
			button.setClickDelay(500);
			button.setHoverEvent(new Runnable() {

				@Override
				public void run() {
					button.playHover(1.05f);
				}

			});
		}
		host_button.setText("Host game", "candara", 1.3f);
		join_button.setText("Join game", "candara", 1.3f);

		host_button.setClickEvent(new Runnable() {

			@Override
			public void run() {
				resetGuis();
				hostMenu.showAll();
			}

		});

		join_button.setClickEvent(new Runnable() {

			@Override
			public void run() {
				resetGuis();
				joinMenu.showAll();
			}

		});
	}

	private void resetGuis() {
		joinMenu.hideAll();
		hostMenu.hideAll();
	}

	public void images() {
		ImageComponent background = new ImageComponent(this, new SourceFile("/res/guis/menus/menus_background_2.png"));
		background.setPosition(0.65f, 0.5f);
		background.setSize(Maths.getFrom720toCurrentDisplaySize(new Vector2f(1000, 720)));
		background.setRotation(180);
		QuadComponent quad = new QuadComponent(this, 0.41f, 0.2f + 0.15f, 0.541f, 0.5f);
		quad.setBackgroundColor(new Vector4f(0.078431f, 0.168627f, 0.29803f, 1f));

		ImageComponent text = new ImageComponent(this, new SourceFile("/res/guis/menus/titles/Coop.png"));
		text.setPosition(0.5f, 0.15f);
		text.setSize(Maths.getFrom720toCurrentDisplaySize(new Vector2f(510, 150)));
	}

	@SuppressWarnings("unused")
	private void texts() {
		TextComponent settings = new TextComponent(this, "Co-op (this will be an image)", "candara", 3.4f, 200, false);
		settings.setColor(0, 0, 0, 1);
		settings.setPosition(0.41f, 0.075f);
	}

	@Override
	public void renderComponents() {
		camera.yaw += 20 * Display.getFrameTime();
		skyboxRenderer.render(skybox, camera);
		master.render(camera, lights, entities);
		master.unprepare();
		super.renderComponents();
		joinMenu.renderComponents();
		hostMenu.renderComponents();
	}
}
