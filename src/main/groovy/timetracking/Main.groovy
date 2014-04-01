package timetracking

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
        button(loginAction, text: "Login"){
          constraint(row: 4, column:0, columnSpan: 5)
        }
      }
    }
  }
}
