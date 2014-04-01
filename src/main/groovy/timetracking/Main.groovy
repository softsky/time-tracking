package timetracking

import groovyx.javafx.SceneGraphBuilder

import timetracking.security.AuthenticationService

/**
 * @author archer
 * Created: Tue Apr 01 11:04:04 EEST 2014
 */

import static groovyx.javafx.GroovyFX.start
import groovyx.javafx.beans.FXBindable

@FXBindable
class Person {
    String userName
    String password
}

start {
  def authenticationService = new AuthenticationService()
  def person = new Person(userName:'archer');

  def sg = new SceneGraphBuilder(true);
  def popup = sg.popup(autoHide: true) {
    stackPane() {
      rectangle(width: 200, height: 200, fill: blue,
      stroke: cyan, strokeWidth: 5, arcHeight: 20, arcWidth: 20)
      button( text: "OK", onAction: {popup.hide()})
    }
  }

  def theStage;

  actions {
    fxaction(
    id: 'loginAction',
    icon: 'icons/login.png', 
    onAction: { evt ->  println authenticationService.login(person.userName, person.password) }
    )
    fxaction(
    id: 'popupAction',
    icon: 'icons/login.png', 
    onAction: { evt ->  popup.show(theStage) }
    )

  }

  theStage = stage(title: 'TimeTracking', visible: true) {
    scene(fill: WHITE, width: 500, height: 250, stylesheets: [ this.class.getResource('/style.css').toString() ]) {
      gridPane(id: 'pane', hgap: 10, vgap: 5, padding: [25, 25, 25, 25], alignment: "center", gridLinesVisible: false){
        text(text: 'Please, login', font: '20pt Tahoma') {
          constraint(row: 0, column:0, columnSpan: 5, halignment: "left")
        }
        label(text: "User name"){
          constraint(row: 1, column:0, columnSpan: 1, halignment: "left")
        }
        textField(text: bind(person, 'userName')){
          constraint(row: 1, column:2, columnSpan: 1, halignment: "left")
        }
        label(text: "Password"){
          constraint(row: 2, column:0, columnSpan: 1, halignment: "left")
        }
        passwordField(text: bind(person, 'password')){
          constraint(row: 2, column:2, columnSpan: 1, halignment: "left")
        }
        button(loginAction, text: "Login"){
          constraint(row: 4, column:0, columnSpan: 5)
        }
        button(popupAction, text: "Popup"){
          constraint(row: 4, column:3, columnSpan: 5)
        }
      }
    }
  }
}
