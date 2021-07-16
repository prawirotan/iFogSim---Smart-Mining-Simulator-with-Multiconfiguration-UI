package org.fog.test.charts;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FogDeviceMetricScatterChart extends Application{

	@Override
	public void start(final Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../charts/resources/fogmetric_scatterchart.fxml"));
		Parent root = loader.load();
		primaryStage.setTitle("Fog Device Metric Scatter Chart");
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
