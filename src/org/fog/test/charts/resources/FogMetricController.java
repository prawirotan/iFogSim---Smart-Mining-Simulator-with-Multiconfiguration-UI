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

public class FogMetricController implements Initializable {
	
	@FXML
	private Button searchFile;
	@FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;
    @FXML
    private ScatterChart<String, Double> fogDeviceMetricChart;
	
	File file = null;
	List<Double> networkUsageOutput = new ArrayList<Double>();
	List<Double> cloudNetworkUsageOutput = new ArrayList<Double>();
	List<Double> energyConsumptionOutput = new ArrayList<Double>();
	List<String> energyConsumptionOutputName = new ArrayList<String>();
	List<Double> appDelayLoopOutput = new ArrayList<Double>();
	List<Double> tupleExecutionOutput = new ArrayList<Double>();
	List<String> tupleExecutionOutputName = new ArrayList<String>();
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		xAxis.setLabel("Fog Device");
        yAxis.setLabel("Values");
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
				if (counter%5==0) { //networkUsage
					String data = scanner.next();
					for (int i=0; i<data.length(); i++) {
						if (data.charAt(i) == '~') {
							StringBuilder value_string = new StringBuilder();
							for (int j=i+1; j<data.length(); j++) {
								value_string.append(data.charAt(j));
							}
							networkUsageOutput.add(Double.parseDouble(value_string.toString()));
							break;
						}
					}
				}
				else if (counter%5==1) { //cloudNetworkUsage
					String data = scanner.next();
					for (int i=0; i<data.length(); i++) {
						if (data.charAt(i) == '~') {
							StringBuilder value_string = new StringBuilder();
							for (int j=i+1; j<data.length(); j++) {
								value_string.append(data.charAt(j));
							}
							cloudNetworkUsageOutput.add(Double.parseDouble(value_string.toString()));
							break;
						}
					}
				}
				else if (counter%5==2) {
					int sub_counter = 0;
					while (sub_counter<14) {
						String data = scanner.next();
						for (int i=0; i<data.length(); i++) {
							if (data.charAt(i) == '~') {
								StringBuilder value_string = new StringBuilder();
								for (int j=i+1; j<data.length(); j++) {
									value_string.append(data.charAt(j));
								}
								energyConsumptionOutput.add(Double.parseDouble(value_string.toString()));
								break;
							}
							else if (data.charAt(i) == ':') {
								StringBuilder value_string = new StringBuilder();
								for (int j=i+1; j<data.length(); j++) {
									value_string.append(data.charAt(j));
								}
								energyConsumptionOutputName.add(value_string.toString());
								break;
							}
						}
						sub_counter++;
					}	
				}
				else if (counter%5==3) {
					int sub_counter = 0;
					while (sub_counter<3) {
						String data = scanner.next();
						for (int i=0; i<data.length(); i++) {
							if (data.charAt(i) == 'p') {
								StringBuilder value_string = new StringBuilder();
								for (int j=i+1; j<data.length(); j++) {
									value_string.append(data.charAt(j));
								}
								appDelayLoopOutput.add(Double.parseDouble(value_string.toString()));
								break;
							}
						}
						sub_counter++;
					}	
				}
				else if (counter%5==4) {
					int sub_counter = 0;
					while (sub_counter<27) {
						String data = scanner.next();
						for (int i=0; i<data.length(); i++) {
							if (data.charAt(i) == '~') {
								StringBuilder value_string = new StringBuilder();
								for (int j=i+1; j<data.length(); j++) {
									value_string.append(data.charAt(j));
								}
								tupleExecutionOutput.add(Double.parseDouble(value_string.toString()));
								break;
							}
							else if (data.charAt(i) == ':' ) {
								StringBuilder value_string = new StringBuilder();
								for (int j=i+1; j<data.length(); j++) {
									value_string.append(data.charAt(j));
								}
								tupleExecutionOutputName.add(value_string.toString());
								break;
							}
						}
						sub_counter++;
					}	
				}
				counter++;
			}
			
			XYChart.Series series1 = new XYChart.Series<>();
			XYChart.Series series2 = new XYChart.Series<>();
			XYChart.Series series3 = new XYChart.Series<>();
			XYChart.Series series4 = new XYChart.Series<>();
			XYChart.Series series5 = new XYChart.Series<>();
			List<XYChart.Series> seriesX = new ArrayList<XYChart.Series>();
			
			for (int i=0; i<networkUsageOutput.size(); i++) {
				
				final XYChart.Data<String, Number> data1 = new XYChart.Data("Network Usage" + i, networkUsageOutput.get(i));	
				data1.nodeProperty().addListener(new ChangeListener<Node>() {
				       @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
				         if (node != null) {      
				           displayLabelForData(data1);
				         } 
				       }
				});
				series1.getData().add(data1);
			}
			
			fogDeviceMetricChart.getData().add(series1);
			
			for (int i=0; i<cloudNetworkUsageOutput.size(); i++) {
				
				final XYChart.Data<String, Number> data2 = new XYChart.Data("Cloud Network Usage" + i, cloudNetworkUsageOutput.get(i));	
				data2.nodeProperty().addListener(new ChangeListener<Node>() {
				       @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
				         if (node != null) {      
				           displayLabelForData(data2);
				         } 
				       }
				});
				series2.getData().add(data2);
			}
			
			fogDeviceMetricChart.getData().add(series2);
			
			System.out.println(energyConsumptionOutput.size());
			System.out.println(energyConsumptionOutputName.size());
			
			for (int i=0; i<energyConsumptionOutput.size(); i++) {
				
				if (i%11==0) {
					final XYChart.Data<String, Number> data3 = new XYChart.Data("Cloud Energy" + i, energyConsumptionOutput.get(i));	
					data3.nodeProperty().addListener(new ChangeListener<Node>() {
					       @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
					         if (node != null) {      
					           displayLabelForData(data3);
					         } 
					       }
					});
					series3.getData().add(data3);
				}
				else {
					final XYChart.Data<String, Number> data3 = new XYChart.Data(energyConsumptionOutputName.get(i) + i, energyConsumptionOutput.get(i));	
					data3.nodeProperty().addListener(new ChangeListener<Node>() {
					       @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
					         if (node != null) {      
					           displayLabelForData(data3);
					         } 
					       }
					});
					series3.getData().add(data3);
				}
			}
			
			fogDeviceMetricChart.getData().add(series3);
			
			for (int i=0; i<appDelayLoopOutput.size(); i++) {
				
				final XYChart.Data<String, Number> data4 = new XYChart.Data("Loop "+ (i%3+1) + "." + i, appDelayLoopOutput.get(i));	
				data4.nodeProperty().addListener(new ChangeListener<Node>() {
				       @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
				         if (node != null) {      
				           displayLabelForData(data4);
				         } 
				       }
				});
				series4.getData().add(data4);
			}
			
			fogDeviceMetricChart.getData().add(series4);
			
			for (int i=0; i<tupleExecutionOutput.size(); i++) {
		
					
				final XYChart.Data<String, Number> data5 = new XYChart.Data(tupleExecutionOutputName.get(i) + i, tupleExecutionOutput.get(i));
				data5.nodeProperty().addListener(new ChangeListener<Node>() {
					      @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
					        if (node != null) {      
					          displayLabelForData(data5);
					        } 
					      }
				});
				series5.getData().add(data5);
			}
			
			fogDeviceMetricChart.getData().add(series5);
			
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
