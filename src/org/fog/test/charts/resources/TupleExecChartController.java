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
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TupleExecChartController implements Initializable {

	@FXML
	private Button searchFile;
	@FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private LineChart<String, Double> tupleExecution;
    
    int fogComparisonBreak = 54;
    int sensorComparisonBreak = 72;
    int gasSensorBreak = 36;
    int chSensorBreak = 63;
    int srSensorBreak = 81;
    
    File file = null;
    List<Double> value = new ArrayList<Double>();
	List<String> key = new ArrayList<String>();
	List<String> configKey = new ArrayList<String>();
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		xAxis.setLabel("Configuration");
        yAxis.setLabel("Tuple Execution Delay Time");
        xAxis.setAnimated(false);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@FXML
	void searchFileOnClick(ActionEvent event) {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Resource File");
		file = fileChooser.showOpenDialog(new Stage());
		int counter = 0;
		
		searchFile.setVisible(false);
		
		if (file.toString().endsWith("tuple-exec.txt")) {
			try (Scanner scanner = new Scanner(file)) {
				while (scanner.hasNext()) {
					System.out.println(counter);
					if (counter%3==0) {
						String data = scanner.next();
						for (int i=0; i<data.length(); i++) {
							if (data.charAt(i) == '@') {
								StringBuilder value_string = new StringBuilder();
								for (int j=i+1; j<data.length(); j++) {
									value_string.append(data.charAt(j));
								}
								value.add(Double.parseDouble(value_string.toString()));
								break;
							}
						}
					}
					else if (counter%3==1) {
						String data = scanner.next();
						for (int i=0; i<data.length(); i++) {
							if (data.charAt(i) == '?') {
								StringBuilder key_string = new StringBuilder();
								for (int j=i+1; j<data.length(); j++) {
									key_string.append(data.charAt(j));
								}
								key.add(key_string.toString());
								break;
							}
						}
					}
					else if (counter%3==2) {
						String data = scanner.next();
						for (int i=0; i<data.length(); i++) {
							if (data.charAt(i) == 'g') {
								StringBuilder configKey_string = new StringBuilder();
								for (int j=i+1; j<data.length(); j++) {
									configKey_string.append(data.charAt(j));
								}
								configKey.add(configKey_string.toString());
								break;
							}
						}
					}
					counter++;
				}
				
				XYChart.Series series1 = new XYChart.Series<>();
				XYChart.Series series2 = new XYChart.Series<>();
				series1.setName("Tuple Executions, Cloudward Mapping");
				series2.setName("Tuple Executions, Edgeward Mapping");
					
				int configCount = 0;
				for (int i=0; i<value.size(); i++) {
						
					final XYChart.Data<String, Number> data1 = new XYChart.Data(key.get(i)+" "+configKey.get(i), value.get(i));	
					data1.nodeProperty().addListener(new ChangeListener<Node>() {
					       @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
					         if (node != null) {      
					           displayLabelForData(data1);
					         } 
					       }
					});
					if (configCount < fogComparisonBreak) {
						series1.getData().add(data1);
					}
					else if (configCount >= fogComparisonBreak) {
						series2.getData().add(data1);
					}

					configCount++;
				}
				
				tupleExecution.getData().add(series1);
				tupleExecution.getData().add(series2);			
			
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
		
		else if (file.toString().endsWith("tuple-exec-sensor.txt")) {
			try (Scanner scanner = new Scanner(file)) {
				while (scanner.hasNext()) {
					System.out.println(counter);
					if (counter%3==0) {
						String data = scanner.next();
						for (int i=0; i<data.length(); i++) {
							if (data.charAt(i) == '@') {
								StringBuilder value_string = new StringBuilder();
								for (int j=i+1; j<data.length(); j++) {
									value_string.append(data.charAt(j));
								}
								value.add(Double.parseDouble(value_string.toString()));
								break;
							}
						}
					}
					else if (counter%3==1) {
						String data = scanner.next();
						for (int i=0; i<data.length(); i++) {
							if (data.charAt(i) == '?') {
								StringBuilder key_string = new StringBuilder();
								for (int j=i+1; j<data.length(); j++) {
									key_string.append(data.charAt(j));
								}
								key.add(key_string.toString());
								break;
							}
						}
					}
					else if (counter%3==2) {
						String data = scanner.next();
						for (int i=0; i<data.length(); i++) {
							if (data.charAt(i) == 'g') {
								StringBuilder configKey_string = new StringBuilder();
								for (int j=i+1; j<data.length(); j++) {
									configKey_string.append(data.charAt(j));
								}
								configKey.add(configKey_string.toString());
								break;
							}
						}
					}
					counter++;
				}
				
				XYChart.Series series1 = new XYChart.Series<>();
				XYChart.Series series2 = new XYChart.Series<>();
				XYChart.Series series3 = new XYChart.Series<>();
				series1.setName("Gas Sensor Variance");
				series2.setName("Chemical Sensor Variance");
				series3.setName("Surrounding Sensor Variance");
					
				int configCount = 0;
				for (int i=0; i<value.size(); i++) {
						
					final XYChart.Data<String, Number> data1 = new XYChart.Data(key.get(i)+" "+configKey.get(i), value.get(i));	
					data1.nodeProperty().addListener(new ChangeListener<Node>() {
					       @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
					         if (node != null) {      
					           displayLabelForData(data1);
					         } 
					       }
					});
					if (configCount < gasSensorBreak) {
						series1.getData().add(data1);
					}
					else if (configCount < chSensorBreak) {
						series2.getData().add(data1);
					}
					else if (configCount < srSensorBreak) {
						series3.getData().add(data1);
					}
					configCount++;
				}
				
				tupleExecution.getData().add(series1);
				tupleExecution.getData().add(series2);
				tupleExecution.getData().add(series3);

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	private void displayLabelForData(XYChart.Data<String, Number> data) {
		  final Node node = data.getNode();
		  final Text dataText = new Text(round(data.getYValue().doubleValue(), 2) + "");
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
		          bounds.getMinY() - dataText.prefHeight(-1) * 0.0001
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
