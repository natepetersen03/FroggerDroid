package com.badlogic.drop;

import static com.badlogic.gdx.scenes.scene2d.Touchable.enabled;

import com.badlogic.drop.GUI.DifficultyRadio;
import com.badlogic.drop.GUI.EnumAction;
import com.badlogic.drop.GUI.EnumHandler;
import com.badlogic.drop.GUI.EnumeratedClickListener;
import com.badlogic.drop.GUI.CharacterRadio;
import com.badlogic.drop.GUI.GameConfigViewModel;
import com.badlogic.drop.data.DataEnums;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MainMenuScreen extends ScreenAdapter {

    private final Drop game;
    private OrthographicCamera camera;
    private Viewport viewport;
    private Stage stage;
    private Skin skin;

    public MainMenuScreen(final Drop game) {
        this.game = game;
        this.camera = new OrthographicCamera();
        this.camera.setToOrtho(false, 800, 480);
        this.viewport = new ExtendViewport(800, 400, camera);
        this.stage = new Stage(viewport);

        this.skin = game.getSkinGUI();
        constructGUI();
    }

    private void switchToGameScreen() {
        game.setScreen(new GameScreen(game));
        this.dispose();
    }

    @Override
    public void show() {
        // load all assets before displaying ready
        game.getAssetManager().finishLoading();
    }

    @Override
    public void render(float delta) {
        // Clear the screen with a dark blue background
        // ScreenUtils.clear(0, 0, 0.2f, 1);
        Gdx.gl.glClearColor(0, 0, 0.2f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        // update stage and all its actors
        stage.act();

        // draw stage and all its actors
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
        // viewport.update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

    private void constructGUI() {
        stage = new Stage(new ScreenViewport());

        Gdx.input.setInputProcessor(stage);
        GameConfigViewModel gameConfigViewModel = new GameConfigViewModel();

        Table table = new Table();
        table.setTouchable(enabled);
        table.setFillParent(true);

        Label label = new Label("New Game", skin, "dark-bg");
        label.setAlignment(Align.center);
        table.add(label)
                .spaceBottom(8.0f)
                .fillX()
                .minWidth(300.0f)
                .minHeight(42.0f)
                .colspan(3);

        table.row();
        TextField textField = new TextField(null, skin);
        TextField.TextFieldStyle t = textField.getStyle();
        t.cursor = skin.getDrawable("cursor");
        t.cursor.setMinWidth(3f);
        textField.setMessageText("Name");
        table.add(textField)
                .spaceTop(8.0f)
                .spaceBottom(32.0f)
                .fillX()
                .minHeight(42.0f)
                .colspan(3);
        textField.setTextFieldListener(new TextField.TextFieldListener() {
            @Override
            public void keyTyped(TextField textField, char c) {
                GameConfigViewModel.setName(textField.getText());
            }
        });

        table.row();
        ImageButton steveButton = new ImageButton(skin, "steve");
        table.add(steveButton)
                .padTop(3.0f)
                .padBottom(3.0f)
                .minSize(96.0f);

        ImageButton alexButton = new ImageButton(skin, "alex");
        table.add(alexButton)
                .padTop(3.0f)
                .padBottom(3.0f)
                .minWidth(100.0f)
                .minHeight(96.0f);

        ImageButton endermanButton = new ImageButton(skin, "enderman");
        table.add(endermanButton)
                .padTop(3.0f)
                .padBottom(3.0f)
                .minSize(96.0f);

        // Radio button group
        ButtonGroup<ImageButton> characterButtons = new ButtonGroup(
                steveButton,
                alexButton,
                endermanButton
        );
        characterButtons.setMinCheckCount(1);
        characterButtons.setMaxCheckCount(1);
        characterButtons.setUncheckLast(true);

        final CharacterRadio characterRadio = new CharacterRadio();
        final EnumHandler<DataEnums.Character> cHandler = new EnumHandler<>(characterRadio);
        steveButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                cHandler.act(DataEnums.Character.STEVE);
            }
        });
        alexButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                cHandler.act(DataEnums.Character.ALEX);
            }
        });
        endermanButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                cHandler.act(DataEnums.Character.ENDERMAN);
            }
        });

        table.row();
        label = new Label("Difficulty", skin, "dark-bg");
        label.setAlignment(Align.center);
        table.add(label)
                .spaceTop(32.0f)
                .spaceBottom(8.0f)
                .fillX()
                .minWidth(250.0f)
                .minHeight(42.0f)
                .colspan(3);

        table.row();
        CheckBox easyBox = new CheckBox("Easy", skin);
        table.add(easyBox)
                .padRight(3.0f)
                .minHeight(42.0f)
                .prefWidth(128.0f);

        CheckBox normalBox = new CheckBox("Normal", skin);
        table.add(normalBox)
                .padLeft(3.0f)
                .padRight(3.0f)
                .minHeight(42.0f)
                .prefWidth(128.0f);

        CheckBox hardBox = new CheckBox("Hard", skin);
        table.add(hardBox)
                .padLeft(3.0f)
                .minHeight(42.0f)
                .prefWidth(128.0f);

        // Radio button group
        ButtonGroup<CheckBox> difficultyButtons = new ButtonGroup(
                easyBox,
                normalBox,
                hardBox
        );
        difficultyButtons.setMinCheckCount(1);
        difficultyButtons.setMaxCheckCount(1);
        difficultyButtons.setUncheckLast(true);

        final DifficultyRadio difficultyRadio = new DifficultyRadio();
        final EnumHandler<DataEnums.Difficulty> dHandler = new EnumHandler<>(difficultyRadio);

        easyBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dHandler.act(DataEnums.Difficulty.EASY);
            }
        });
        normalBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dHandler.act(DataEnums.Difficulty.NORMAL);
            }
        });
        hardBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                dHandler.act(DataEnums.Difficulty.HARD);
            }
        });

        table.row();
        TextButton textButton = new TextButton("Play", skin);
        table.add(textButton)
                .spaceTop(32.0f)
                .spaceBottom(4.0f)
                .fillX()
                .minHeight(44.0f)
                .colspan(3);
        textButton.setDisabled(true);
        textButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                switchToGameScreen();
            }
        });

        gameConfigViewModel.setGoButton(textButton);

        stage.addActor(table);
    }
}
