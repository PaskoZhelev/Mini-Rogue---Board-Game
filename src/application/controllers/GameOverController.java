package application.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import application.core.GameEngine;
import application.exceptions.GameException;
import application.exceptions.UnknownBossMonsterException;
import application.exceptions.UnknownNormalMonsterException;
import application.factories.BossMonsterFactory;
import application.factories.NormalMonsterFactory;
import application.handlers.RollingDiceHandler;
import application.interfaces.Card;
import application.interfaces.Character;
import application.interfaces.Dungeon;
import application.interfaces.Monster;
import application.interfaces.Spell;
import application.models.Die;
import application.models.cards.BossMonsterCard;
import application.models.enums.SpellType;
import application.models.spells.FireballSpell;
import application.models.spells.HealingSpell;
import application.models.spells.IceSpell;
import application.models.spells.PoisonSpell;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;

public class GameOverController implements Initializable {	

	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
			
	}
		
	
}
