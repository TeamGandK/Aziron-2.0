package Skills;

import Heroes.Hero;
import Main.Sound;
import javafx.animation.FadeTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;


import java.io.File;

import static Match.Battle.turn;
import static Match.Battle.turns;
import static Controllers.ControllerChoiceHero.battle;
import static Controllers.ControllerChoiceHero.player1;
import static Controllers.ControllerChoiceHero.player2;


public class SkillsLV implements Skill {

    private boolean firstOpen = false;
    private boolean twoOpen = false;
    private boolean threeOpen = false;
    private ImageView[] imageViewsText = {
            new ImageView(new Image("file:src\\Picture\\Skills\\21.png")),
            new ImageView(new Image("file:src\\Picture\\Skills\\22.png")),
            new ImageView(new Image("file:src\\Picture\\Skills\\23.png"))
    };
    private ImageView[] imageViewList = {
            new ImageView(new Image("file:src\\Picture\\Skills\\SkillLV1.png")),
            new ImageView(new Image("file:src\\Picture\\Skills\\SkillLV2.png")),
            new ImageView(new Image("file:src\\Picture\\Skills\\SkillLV3.png"))};
    private Sound[] sounds = {
            new Sound(new File("src\\Sounds\\SoundLordVampire\\LVSkill1-1.wav")),
            new Sound(new File("src\\Sounds\\SoundLordVampire\\LVSkill2-1.wav")),
            new Sound(new File("src\\Sounds\\SoundLordVampire\\LVSkill3-1.wav"))
    };
    private int turnOpen2 = 0;
    private int turnOpen3 = 0;

    public SkillsLV(Hero hero) {
        imageViewList[0].addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (firstOpen && ((turn == 1 && hero.getLocation()) || (turn == -1 && !hero.getLocation()))) {
                turns++;
                turn *= -1;
                firstUlt();
            }
        });
        imageViewList[1].addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (twoOpen && ((turn == 1 && hero.getLocation()) || (turn == -1 && !hero.getLocation()))) {
                turns++;
                turn *= -1;
                twoUlt();
            }
        });
        imageViewList[2].addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> {
            if (threeOpen && ((turn == 1 && hero.getLocation()) || (turn == -1 && !hero.getLocation()))) {
                turns++;
                turn *= -1;
                threeUlt();
            }
        });

        imageViewList[0].setOpacity(0.3);
        imageViewList[1].setOpacity(0.3);
        imageViewList[2].setOpacity(0.3);


        for (int i = 0; i < 3; i++) {
            imageViewList[i].setFitHeight(112);
            imageViewList[i].setFitWidth(131);
        }
        imageViewList[0].setLayoutY(547);
        imageViewList[1].setLayoutY(547);
        imageViewList[2].setLayoutY(547);

        if (hero.getLocation()) {
            imageViewList[0].setLayoutX(27);
            imageViewList[1].setLayoutX(99);
            imageViewList[2].setLayoutX(174);
        } else {
            for (int i = 0; i < 3; i++) {
                imageViewList[i].setScaleX(-1);
            }
            imageViewList[0].setLayoutX(978);
            imageViewList[1].setLayoutX(1053);
            imageViewList[2].setLayoutX(1126);
        }
        for (int i = 0; i < 3; i++) {
            imageViewsText[i].setOpacity(0);
            imageViewsText[i].setFitHeight(75);
            imageViewsText[i].setFitWidth(100);
            imageViewsText[i].setLayoutY(472);
            imageViewsText[i].setLayoutX(imageViewList[i].getLayoutX());
            int finalI = i;
            imageViewsText[i].addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
                        FadeTransition fadeTransition = new FadeTransition(Duration.millis(1000), imageViewsText[finalI]);
                        fadeTransition.setToValue(0.8);
                        fadeTransition.setCycleCount(1);
                        FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(1000), imageViewsText[finalI]);
                        fadeTransition2.setToValue(0);
                        fadeTransition2.setCycleCount(1);
                        fadeTransition2.delayProperty().setValue(Duration.seconds(1));
                        fadeTransition.setOnFinished(event -> fadeTransition2.play());
                        fadeTransition.play();
                    }
            );
        }
    }

    @Override
    public ImageView[] getImageViewsText() {
        return imageViewsText;
    }

    @Override
    public void setImageViewsText(ImageView[] imageViewsText) {
        this.imageViewsText = imageViewsText;
    }

    @Override
    public ImageView[] getImageViewList() {
        return imageViewList;
    }

    @Override
    public void firstUlt() {
        sounds[0].play();
        firstOpen = false;
        if (turn == -1) {
            player2.getHero().setHitPoints(player2.getHero().getHitPoints() - player1.getHero().getAttack() * 2.5);
            player1.getHero().setHitPoints(player1.getHero().getHitPoints() + player1.getHero().getAttack() * 2.5);
            battle.damageOrHilForSkills(player1.getHero().getAttack() * 2.5, player1.getHero().getAttack() * 2.5, 21);
        } else {
            player1.getHero().setHitPoints(player1.getHero().getHitPoints() - player2.getHero().getAttack() * 2.5);
            player2.getHero().setHitPoints(player2.getHero().getHitPoints() + player2.getHero().getAttack() * 2.5);
            battle.damageOrHilForSkills(player2.getHero().getAttack() * 2.5, player2.getHero().getAttack() * 2.5, 21);
        }
    }

    @Override
    public void twoUlt() {
        sounds[1].play();
        twoOpen = false;
        if (turn == -1) {
            player1.getHero().setAttack(player1.getHero().getAttack() + player1.getHero().getAttack() * 0.1);
            battle.damageOrHilForSkills(null, player1.getHero().getAttack() * 0.1, 22);
        } else {
            player2.getHero().setAttack(player2.getHero().getAttack() + player2.getHero().getAttack() * 0.1);
            battle.damageOrHilForSkills(null, player2.getHero().getAttack() * 0.1, 22);
        }
    }

    @Override
    public void threeUlt() {
        sounds[2].play();
        threeOpen = false;
        if (turn == -1) if (player1.getHero().getHitPoints()<0){
            player1.getHero().setHitPoints(player1.getHero().getHitPoints() * -1);
            battle.damageOrHilForSkills(null, player1.getHero().getHitPoints() * 2 * -1, 23);
        } else if (player2.getHero().getHitPoints()<0){
            player2.getHero().setHitPoints(player2.getHero().getHitPoints() * -1);
            battle.damageOrHilForSkills(null, player2.getHero().getHitPoints() * 2 * -1, 23);
        }
    }

    @Override
    public boolean isFirstOpen() {
        return firstOpen;
    }

    @Override
    public void setFirstOpen(boolean firstOpen) {
        this.firstOpen = firstOpen;
    }

    @Override
    public boolean isTwoOpen() {
        return twoOpen;
    }

    @Override
    public void setTwoOpen(boolean twoOpen) {
        this.twoOpen = twoOpen;
    }

    @Override
    public boolean isThreeOpen() {
        return threeOpen;
    }

    @Override
    public void setThreeOpen(boolean threeOpen) {
        this.threeOpen = threeOpen;
    }

    public void updateSkills(Hero hero) {
        if (turnOpen2 == 0 && hero.getLevelHero() >= 3) turnOpen2 = turns;
        if (turnOpen3 == 0 && hero.getLevelHero() >= 5) turnOpen3 = turns;

        if (turns % 10 == 0 && turns > 0) {
            firstOpen = true;
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), imageViewList[0]);
            fadeTransition.setFromValue(0.3);
            fadeTransition.setToValue(1);
            fadeTransition.setCycleCount(1);
            fadeTransition.play();
        }

        if ((turns - turnOpen2) % 14 == 0 && hero.getLevelHero() >= 3) {
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), imageViewList[1]);
            fadeTransition.setFromValue(0.3);
            fadeTransition.setToValue(1);
            fadeTransition.setCycleCount(1);
            fadeTransition.play();
            twoOpen = true;
        }

        if ((turns - turnOpen3) % 18 == 0 && hero.getLevelHero() >= 5) {
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(200), imageViewList[2]);
            fadeTransition.setFromValue(0.3);
            fadeTransition.setToValue(1);
            fadeTransition.setCycleCount(1);
            fadeTransition.play();
            threeOpen = true;
        }

        if (!firstOpen) imageViewList[0].setOpacity(0.3);
        if (!twoOpen) imageViewList[1].setOpacity(0.3);
        if (!threeOpen) imageViewList[2].setOpacity(0.3);
    }
}
