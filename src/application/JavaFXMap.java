package application;
import database.DatabaseHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import javafx.embed.swing.JFXPanel;
import javafx.scene.web.WebEvent;

/**
 *
 * @author Suhas S Pai
 */

public class JavaFXMap extends JFXPanel {
    public void createScene() { // create the scene
        browser=new Browser();
        scene = new Scene(browser, getParent().getWidth(), getParent().getHeight()-30, Color.web("#666970"));
        setScene(scene);
    }
    public static WebEngine getWebEngine() {
        return browser.getWebEngine();
    }
    
    private static Scene scene;
    private static Browser browser;
}

class Browser extends Region {
    public Browser() {
        //apply the styles
        getStyleClass().add("browser");
        // load the web page
        final String URL=JavaFXMap.class.getResource("map/map.html").toExternalForm();
        webEngine.load(URL);
        webEngine.setOnAlert((WebEvent<String> event)->{
            Integer nodeNo=Integer.parseInt(event.getData());
            DatabaseHandler.query(nodeNo);
        });
        proxyAuthentication();
        //add the web view to the scene
        getChildren().add(browser);
        
    }
    public WebEngine getWebEngine() {
        return webEngine;
    }

    @Override
    protected void layoutChildren() {
        double width=getWidth();
        double height=getHeight();
        layoutInArea(browser, 0, 0, width, height, 0, HPos.CENTER, VPos.CENTER);
    }
    @Override
    protected double computePrefWidth(double height) {
        return 350;
    }
    @Override
    protected double computePrefHeight(double width) {
        return 780;
    }

    private final String authUser="suhas_prj";
    private final String authPassword="q1A!w2S@";
    private final WebView browser=new WebView();
    private final WebEngine webEngine=browser.getEngine();
    private void proxyAuthentication() {
        System.setProperty("http.proxyHost", "10.1.1.10");
        System.setProperty("http.proxyPort", "3128");
        System.setProperty("http.proxyUser", authUser);
        System.setProperty("http.proxyPassword", authPassword);
        Authenticator.setDefault(new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(authUser, authPassword.toCharArray());
            }
        }); // proxy authentication
    }
}
