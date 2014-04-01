package timetracking

// import javafx.application.Application;

// import javafx.event.ActionEvent;
// import javafx.event.EventHandler;

// import javafx.scene.Group;
// import javafx.scene.Scene;
// import javafx.scene.layout.StackPane;

// import javafx.scene.layout.GridPane;

// import javafx.scene.control.Label
// import javafx.scene.control.Button
// import javafx.scene.control.TextField
// import javafx.scene.control.PasswordField

// import javafx.scene.text.Text;
// import javafx.scene.text.Font;
// import javafx.scene.text.FontWeight;

// import javafx.geometry.Pos;
// import javafx.geometry.Insets;

// import javafx.stage.Stage;

/**
 * @author archer
 * Created: Tue Apr 01 11:04:04 EEST 2014
 */

import static groovyx.javafx.GroovyFX.start


start {
actions {
  fxaction(
  id: 'loginAction',
  icon: 'icons/login.png', 
  onAction: { evt -> println evt }
  )
}

  stage(title: 'TimeTracking', visible: true) {
    scene(fill: WHITE, width: 500, height: 250) {
      gridPane(hgap: 10, vgap: 5, padding: [25, 25, 25, 25], alignment: "center", gridLinesVisible: false){
        text(text: 'Please, login', font: '20pt Tahoma') {
          constraint(row: 0, column:0, columnSpan: 5, halignment: "left")
        }
        label(text: "User name"){
          constraint(row: 1, column:0, columnSpan: 1, halignment: "left")
        }
        textField(){
          constraint(row: 1, column:2, columnSpan: 1, halignment: "left")
        }
        label(text: "Password"){
          constraint(row: 2, column:0, columnSpan: 1, halignment: "left")
        }
        passwordField(){
          constraint(row: 2, column:2, columnSpan: 1, halignment: "left")
        }
        button(loginAction, id: "loginBtn", text: "Login"){
          constraint(row: 3, column:0, columnSpan: 5)
        }
      }
    }
  }
}

// class Main  extends Application {
 
//   public static void main(String[] args) {
//     Application.launch(Main.class, args);
//   }
    
//   @Override
//   public void start(Stage primaryStage) {

//     GridPane grid = new GridPane();
//     grid.setAlignment(Pos.CENTER);
//     grid.setHgap(10);
//     grid.setVgap(10);
//     grid.setPadding(new Insets(25, 25, 25, 25));

//     def buttonAction = { evt ->
//       println "Hello World" 
//     } as EventHandler<ActionEvent>

//     def btn = new Button(
//     text:'Login', 
//     onAction: buttonAction);

//     Text scenetitle = new Text("Please, login");
//     scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
//     grid.add(scenetitle, 0, 0, 3, 1);

//     Label userName = new Label("User Name:");
//     grid.add(userName, 0, 1);

//     TextField userTextField = new TextField();
//     grid.add(userTextField, 1, 1);

//     Label pw = new Label("Password:");
//     grid.add(pw, 0, 2);

//     PasswordField pwBox = new PasswordField();
//     grid.add(pwBox, 1, 2);
    
//     grid.add(btn, 1, 3);
//     Scene scene = new Scene(grid, 200, 200);

//     primaryStage.setTitle("TimeTracking")
//     primaryStage.setScene(scene);
//     primaryStage.sizeToScene();
//     primaryStage.show();
//   }
// }