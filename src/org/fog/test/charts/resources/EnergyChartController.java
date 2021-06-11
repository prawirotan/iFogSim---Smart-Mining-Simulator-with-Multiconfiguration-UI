package org.fog.test.charts.resources;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EnergyChartController implements Initializable {

	
	@FXML
	private Button searchFile;
	@FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private ScatterChart<String, Double> energyConsumption;
    
    File file = null;
    List<Double> value = new ArrayList<Double>();
	List<String> key = new ArrayList<String>();
	List<String> configKey = new ArrayList<String>();
	List<Long> cloudEnergyValue = new ArrayList<Long>();
	List<String> cloudString = new ArrayList<String>();
	List<String> configCloudKey = new ArrayList<String>();
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		xAxis.setLabel("Configuration");
        yAxis.setLabel("Energy Consumption");
        xAxis.setAnimated(false);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	void searchFileOnClick(ActionEvent event) {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		file = fileChooser.showOpenDialog(new Stage());
		int counter = 0;

		try (Scanner scanner = new Scanner(file)) {
			while (scanner.hasNext()) {
				System.out.println(counter);
				if (counter%3==0) {
					String data = scanner.next();
					for (int i=0; i<data.length(); i++) {
						if (data.charAt(i) == '!') {
							System.out.println("ENERGY!");
							StringBuilder value_string = new StringBuilder();
							for (int j=i+1; j<data.length(); j++) {
								value_string.append(data.charAt(j));
							}
							value.add(Double.parseDouble(value_string.toString()));
							break;
						}
						else if (data.charAt(i) == '.') {
							System.out.println("CLOUD ENERGY!");
							StringBuilder cloudVal_string = new StringBuilder();
							for (int j=i+1; j<data.length(); j++) {
								cloudVal_string.append(data.charAt(j));
							}
							cloudEnergyValue.add(Long.parseLong(cloudVal_string.toString()));
							break;
						}
					}
				}
				else if (counter%3==1) {
					String data = scanner.next();
					for (int i=0; i<data.length(); i++) {
						if (data.charAt(i) == 'e') {
							System.out.println("DEVICE NAME!");
							StringBuilder key_string = new StringBuilder();
							for (int j=i+1; j<data.length(); j++) {
								key_string.append(data.charAt(j));
							}
							key.add(key_string.toString());
							break;
						}
						else if (data.charAt(i) == 'd') {
							System.out.println("CLOUD NAME!");
							StringBuilder cloud_string = new StringBuilder();
							for (int j=i+1; j<data.length(); j++) {
								cloud_string.append(data.charAt(j));
							}
							cloudString.add(cloud_string.toString());
							break;
						}
					}
				}
				else if (counter%3==2) {
					String data = scanner.next();
					for (int i=0; i<data.length(); i++) {
						if (data.charAt(i) == 'g') {
							System.out.println("DEVICE CONFIG!");
							StringBuilder configKey_string = new StringBuilder();
							for (int j=i+1; j<data.length(); j++) {
								configKey_string.append(data.charAt(j));
							}
							configKey.add(configKey_string.toString());
							break;
						}
						else if (data.charAt(i) == '?') {
							System.out.println("CLOUD DEVICE CONFIG!");
							StringBuilder configCloudKey_string = new StringBuilder();
							for (int j=i+1; j<data.length(); j++) {
								configCloudKey_string.append(data.charAt(j));
							}
							configCloudKey.add(configCloudKey_string.toString());
							System.out.println(configCloudKey.size());
							break;
							
						}
					}
				}
				counter++;
			}
				
			XYChart.Series series1 = new XYChart.Series<>();
			XYChart.Series series2 = new XYChart.Series<>();
				
			for (int i=0; i<value.size(); i++) {
					
				final XYChart.Data<String, Number> data1 = new XYChart.Data(key.get(i)+configKey.get(i), value.get(i));	
				data1.nodeProperty().addListener(new ChangeListener<Node>() {
				       @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
				         if (node != null) {      
				           displayLabelForData(data1);
				         } 
				       }
				});
				series1.getData().add(data1);
			}
				
			for (int i=0; i<cloudEnergyValue.size(); i++) {	
				System.out.println("CLOUD COME");
				System.out.println("CloudEnergy"+configKey.get(i)+" "+cloudEnergyValue.get(i));
				final XYChart.Data<String, Number> data2 = new XYChart.Data("CloudEnergy"+configCloudKey.get(i), cloudEnergyValue.get(i));
				data2.nodeProperty().addListener(new ChangeListener<Node>() {
				       public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
				         if (node != null) {      
				           displayLabelForData(data2);
				         } 
				       }
				});
				series2.getData().add(data2);
			}
			
			energyConsumption.getData().add(series1);
			energyConsumption.getData().add(series2);
				
				
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	private void displayLabelForData(XYChart.Data<String, Number> data) {
		  final Node node = data.getNode();
		  final Text dataText = new Text(round(data.getYValue().doubleValue(), 1) + "");
		  node.parentProperty().addListener(new ChangeListener<Parent>() {
		    @Override public void changed(ObservableValue<? extends Parent> ov, Parent oldParent, Parent parent) {
		      Group parentGroup = (Group) parent;
		      parentGroup.getChildren().add(dataText);
		    }
		  });

		  node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
		    @Override public void changed(ObservableValue<? extends Bounds> ov, Bounds oldBounds, Bounds bounds) {
		      dataText.setLayoutX(
		        Math.round(
		          bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2
		        )
		      );
		      dataText.setLayoutY(
		        Math.round(
		          bounds.getMinY() - dataText.prefHeight(-1) * 0.1
		        )
		      );
		    }
		  });
		}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();
	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
}
