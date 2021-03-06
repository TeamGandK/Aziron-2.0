package Controllers;

import Main.Clock;
import Main.Profile;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static Main.BuildStage.*;

public class ControllerAutorization implements Initializable {
    @FXML
    private  Pane paneSignIn;
    @FXML
    private  Pane paneSignUp;
    @FXML
    private Pane luckSignIn;
    @FXML
    private  Pane luckSignUp;
    @FXML
    private  Text textErrorSignUp;
    @FXML
    private   Text textErrorSignIn;
    @FXML
    private  TextField textFieldSignIn;
    @FXML
    private  TextField textFieldNewName;
    @FXML
    private TextField textFieldNewNameRepeat;
    @FXML
    private PasswordField passwordFieldSignIn;
    @FXML
    private PasswordField passwordFieldNewPassword;
    @FXML
    private  PasswordField passwordFieldNewPasswordRepeat;
    @FXML
    private Button buttonSignIn;
    @FXML
    private  Button buttonSignUp;
    @FXML
    private  Button buttonSignUpToSignIn;
    @FXML
    private  Button buttonSignUpOK;
    @FXML
    private ImageView buttonOffBackToMenu;
    @FXML
    private ImageView buttonOnBackToMenu;
    @FXML
    private  ImageView panelSignIn;
    @FXML
    private Text time;

    private void exitToMenu(){
        try {
            azironStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxmlFiles/WindowMenu.fxml")), 1280, 720));
            Image cursor = new Image("file:src\\Picture\\Mouse\\Mouse.png");
            ImageCursor imageCursor = new ImageCursor(cursor);
            azironStage.getScene().setCursor(imageCursor);
        } catch (IOException e) {
            e.printStackTrace();
        }
        profileController = 0;
        azironStage.show();
    }

    private void avtoriz() {
        try {
            if ((textFieldSignIn.getText()).equals(profile.getName())) {
                throw new IllegalCallerException("Такой пользователь уже авторизован");
            }
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("src\\Profiles\\" + textFieldSignIn.getText() + ".hoa")));
            List<String> lines = bufferedReader.lines().collect(Collectors.toList());
            if (lines.size() != 8) {
                throw new IllegalArgumentException();
            }
            if (!textFieldSignIn.getText().equals(lines.get(0))) {
                throw new IllegalArgumentException();
            }
            if (!passwordFieldSignIn.getText().equals(lines.get(1))) {
                throw new IllegalArgumentException();
            }
            String[] parts = lines.get(2).split("/");
            if (Byte.parseByte(parts[0]) > 20 || Byte.parseByte(parts[0]) < 1 || Integer.parseInt(parts[1]) < 0 || Integer.parseInt(parts[1]) > 5000) {
                throw new IllegalArgumentException();
            }

            if (Integer.parseInt(lines.get(3)) < 0 || Integer.parseInt(lines.get(4)) < 0 ||
                    Integer.parseInt(lines.get(5)) < 0 || Integer.parseInt(lines.get(6)) < 0 ||
                    Integer.parseInt(lines.get(7)) < 0 ||
                    Integer.parseInt(lines.get(3)) != Integer.parseInt(lines.get(5)) + Integer.parseInt(lines.get(6)) + Integer.parseInt(lines.get(7))) {
                throw new IllegalArgumentException();
            }
            profile = new Profile(lines.get(0), Byte.parseByte(parts[0]), Integer.parseInt(parts[1]),
                    Integer.parseInt(lines.get(3)), Integer.parseInt(lines.get(4)), Integer.parseInt(lines.get(5)),
                    Integer.parseInt(lines.get(6)), Integer.parseInt(lines.get(7)));
            profileController++;

            paneSignIn.setVisible(false);
            luckSignIn.setVisible(true);

            TranslateTransition transition2 = new TranslateTransition(Duration.millis(100), new Rectangle(-10, -10, 1, 1));
            transition2.setByX(1);
            transition2.setCycleCount(1);
            transition2.setOnFinished(event1 -> {
                try {
                    azironStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("../fxmlFiles/WindowProfile.fxml")), 1280, 720));
                    Image cursor = new Image("file:src\\Picture\\Mouse\\Mouse.png");
                    ImageCursor imageCursor = new ImageCursor(cursor);
                    azironStage.getScene().setCursor(imageCursor);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                azironStage.show();
            });
            transition2.play();
        } catch (IllegalCallerException e) {
            textErrorSignIn.setText("Такой пользователь уже авторизован");
        } catch (Exception e) {
            textErrorSignIn.setText("Неверное имя пользователя или пароль");
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Clock.launchTimer(time).start();

        //1. Фон:
        panelSignIn.setOnMouseEntered(event -> {
            buttonOnBackToMenu.setVisible(false);
            buttonOffBackToMenu.setVisible(true);
        });
        // Кнопка "Назад в меню"
        buttonOffBackToMenu.setOnMouseEntered(event -> {
            buttonOffBackToMenu.setVisible(false);
            buttonOnBackToMenu.setVisible(true);
        });
        buttonOnBackToMenu.setOnMouseClicked(event -> exitToMenu());
        //Кнопка "Регистрация"
        buttonSignUp.setOnMouseClicked(event -> {
            paneSignIn.setVisible(false);
            paneSignUp.setVisible(true);
        });
        buttonSignUpToSignIn.setOnMouseClicked(event -> {
            paneSignIn.setVisible(true);
            paneSignUp.setVisible(false);
        });
        //Кнопка "Вход":
        textFieldSignIn.setOnKeyPressed(event -> {
            if (event.getCode() == (KeyCode.ENTER))
                avtoriz();
            if (event.getCode() == (KeyCode.ESCAPE))
                exitToMenu();
        });
        passwordFieldSignIn.setOnKeyPressed(event -> {
            if (event.getCode() == (KeyCode.ENTER))
                avtoriz();
            if (event.getCode() == (KeyCode.ESCAPE))
                exitToMenu();
        });
        buttonSignIn.setOnMouseClicked(event -> avtoriz()
        );

        //Кнопка: "Зарегистрироваться"
        buttonSignUpOK.setOnMouseClicked(event -> {
            try {
                if (!textFieldNewName.getText().equals(textFieldNewNameRepeat.getText())) {
                    throw new IllegalArgumentException("Имя пользователя не совпадает с текущим");
                }
                if (textFieldNewName.getText().equals("")) {
                    throw new IllegalArgumentException("Заполните поля с именем пользователя");
                }
                System.out.println("Name+");
                if (!passwordFieldNewPassword.getText().equals(passwordFieldNewPasswordRepeat.getText())) {
                    throw new IllegalArgumentException("Пароль пользователя не совпадает с текущим");
                }
                if (new File("src\\Profiles\\" + textFieldNewName.getText() + ".hoa").canRead()) {
                    throw new IllegalArgumentException("Такой профиль уже существует");
                }
                File newDirectory = new File("src\\Profiles\\" + textFieldNewName.getText());
                newDirectory.mkdirs();
                File newProfile = new File("src\\Profiles\\" + textFieldNewName.getText() + "\\" + textFieldNewName.getText() + ".hoa");
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(newProfile));
                List<String> profileList = Arrays.asList(
                        textFieldNewName.getText(),
                        passwordFieldNewPassword.getText(),
                        "1/0",
                        "0",
                        "0",
                        "0",
                        "0",
                        "0"
                );
                int i = 0;
                for (String line : profileList) {
                    bufferedWriter.write(line);
                    i++;
                    if (i < profileList.size()) bufferedWriter.newLine();
                }
                bufferedWriter.close();

                paneSignUp.setVisible(false);
                luckSignUp.setVisible(true);

                luckSignUp.setOnMouseEntered(event1 -> {
                    luckSignUp.setVisible(false);
                    paneSignIn.setVisible(true);
                });
            } catch (Exception e) {
                textErrorSignUp.setText(e.getMessage());
            }
        });
    }
}
