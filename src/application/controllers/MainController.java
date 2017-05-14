package application.controllers;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

import application.handlers.Constants;
import application.models.enums.Difficulty;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MainController implements Initializable {
	
	private static final String GAME_VIEW_FXML = "/views/GameView.fxml";
	private static final String START_GAME_BUTTON = "/images/startGameButton.jpg";
	private static final String START_GAME_BUTTON_HOVER = "/images/startGameButtonHover.jpg";
	private static final String EXIT_BUTTON = "/images/exitButton.jpg";
	private static final String EXIT_BUTTON_HOVER = "/images/exitButtonHover.jpg";
	private static final String BGG_URL = "https://boardgamegeek.com/boardgame/199242/mini-rogue";
	private static final int FADE_IN_DURATION_BACKGROUND = 1300;
	private static final int FADE_IN_DURATION_BUTTONS_LABELS = 1300;

	@FXML
	private ImageView backgroundMiniRogue;
	@FXML
	private ImageView startGameButton;
	@FXML
	private ImageView exitButton;
	@FXML
	private Image startGameButtonImage = new Image(START_GAME_BUTTON);
	@FXML
	private Image startGameButtonImageHover = new Image(START_GAME_BUTTON_HOVER);
	@FXML
	private Image exitButtonImage = new Image(EXIT_BUTTON);
	@FXML
	private Image exitButtonImageHover = new Image(EXIT_BUTTON_HOVER);
	@FXML
	private Label appVersion;
	@FXML
	private Label boardGameLabelOne;
	@FXML
	private Label boardGameLabelTwo;
	@FXML
	private Label appLabel;
	@FXML
	private Label selectDifficulty;
	@FXML
	private Label casual;
	@FXML
	private Label normal;
	@FXML
	private Label hard;
	@FXML
	private Label impossible;
	@FXML
	private Rectangle rectangle;
	@FXML
	private Hyperlink hyperLink;
	private static Difficulty difficulty;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		appVersion.setText(Constants.APP_VERSION);
		
		hyperLink.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
			        try {
			            Desktop.getDesktop().browse(new URI(BGG_URL));
			        } catch (IOException e1) {
			            e1.printStackTrace();
			        } catch (URISyntaxException e1) {
			            e1.printStackTrace();
			        }
			        }
			    }
			);
		
		startGameButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				startGameButton.setImage(startGameButtonImageHover);
			}
		});

		startGameButton.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				startGameButton.setImage(startGameButtonImage);
			}
		});
		
		startGameButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				activateDifficultyOptions();
			}
		});

		exitButton.setOnMouseEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				exitButton.setImage(exitButtonImageHover);
			}
		});

		exitButton.setOnMouseExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				exitButton.setImage(exitButtonImage);
			}
		});

		exitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				Platform.exit();
			}
		});

		FadeTransition fadeTransitionBackground = new FadeTransition(Duration.millis(FADE_IN_DURATION_BACKGROUND), backgroundMiniRogue);
		fadeTransitionBackground.setFromValue(0.0f);
		fadeTransitionBackground.setToValue(1.0f);

		FadeTransition fadeTransitionStart = new FadeTransition(Duration.millis(FADE_IN_DURATION_BUTTONS_LABELS), startGameButton);
		fadeTransitionStart.setFromValue(0.0f);
		fadeTransitionStart.setToValue(1.0f);

		FadeTransition fadeTransitionExit = new FadeTransition(Duration.millis(FADE_IN_DURATION_BUTTONS_LABELS), exitButton);
		fadeTransitionExit.setFromValue(0.0f);
		fadeTransitionExit.setToValue(1.0f);

		FadeTransition fadeTransitionBoardGameLabelOne = new FadeTransition(Duration.millis(FADE_IN_DURATION_BUTTONS_LABELS), boardGameLabelOne);
		fadeTransitionBoardGameLabelOne.setFromValue(0.0f);
		fadeTransitionBoardGameLabelOne.setToValue(1.0f);
		
		FadeTransition fadeTransitionBoardGameLabelTwo = new FadeTransition(Duration.millis(FADE_IN_DURATION_BUTTONS_LABELS), boardGameLabelTwo);
		fadeTransitionBoardGameLabelTwo.setFromValue(0.0f);
		fadeTransitionBoardGameLabelTwo.setToValue(1.0f);

		FadeTransition fadeTransitionAppVersion = new FadeTransition(Duration.millis(FADE_IN_DURATION_BUTTONS_LABELS), appVersion);
		fadeTransitionAppVersion.setFromValue(0.0f);
		fadeTransitionAppVersion.setToValue(1.0f);
		
		FadeTransition fadeTransitionAppLabel = new FadeTransition(Duration.millis(FADE_IN_DURATION_BUTTONS_LABELS), appLabel);
		fadeTransitionAppLabel.setFromValue(0.0f);
		fadeTransitionAppLabel.setToValue(1.0f);
		
		FadeTransition fadeTransitionUrl = new FadeTransition(Duration.millis(FADE_IN_DURATION_BUTTONS_LABELS), hyperLink);
		fadeTransitionUrl.setFromValue(0.0f);
		fadeTransitionUrl.setToValue(1.0f);
		
		ParallelTransition parallelTransition = new ParallelTransition();
		parallelTransition.getChildren().addAll(
				fadeTransitionStart,
				fadeTransitionExit,
				fadeTransitionAppVersion,
				fadeTransitionAppLabel,
				fadeTransitionBoardGameLabelOne,
				fadeTransitionBoardGameLabelTwo,
				fadeTransitionUrl
				);
		parallelTransition.setCycleCount(1);
		//parallelTransition.play();

		SequentialTransition sequentialTransition = new SequentialTransition();
		sequentialTransition.getChildren().addAll(fadeTransitionBackground, parallelTransition

		);
		sequentialTransition.setCycleCount(1);
		sequentialTransition.play();
	}
	
    private void startGame() throws IOException {
        Parent newgame_parent = FXMLLoader.load(getClass().getResource(GAME_VIEW_FXML));
        Scene newgame_scene = new Scene(newgame_parent);
        Stage app_stage = (Stage) backgroundMiniRogue.getScene().getWindow();
        app_stage.setScene(newgame_scene);
        app_stage.setX(200.0);
        app_stage.setY(20.0);
        app_stage.show();
    }
	
	private void activateDifficultyOptions() {
		selectDifficulty.setVisible(true);
		casual.setVisible(true);
		normal.setVisible(true);
		hard.setVisible(true);
		impossible.setVisible(true);
		rectangle.setVisible(true);
		
		casual.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				difficulty = Difficulty.CASUAL;
				try {
					startGame();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}
		});
		
		normal.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				difficulty = Difficulty.NORMAL;
				try {
					startGame();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}
		});
		
		hard.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				difficulty = Difficulty.HARD;
				try {
					startGame();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}
		});
		
		impossible.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent evt) {
				difficulty = Difficulty.IMPOSSIBLE;
				try {
					startGame();
				} catch (IOException e) {					
					e.printStackTrace();
				}
			}
		});
	}

	public static Difficulty getDifficulty() {
		return difficulty;
	}

}
