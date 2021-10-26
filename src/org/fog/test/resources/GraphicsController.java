package org.fog.test.resources;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JFileChooser;

import org.fog.placement.Controller;
import org.fog.test.perfeval.SmartMiningMain;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

public class GraphicsController implements Initializable {
	
		@FXML
		private Label fogLabel;
	    @FXML
	    private Button fog1;
	    @FXML
	    private Button fog2;
	    @FXML
	    private Button fog3;
	    @FXML 
	    private Button fog4;
	    @FXML
	    private Button fog5;
	    @FXML
		private Label gasLabel;
	    @FXML
	    private Button gas1;
	    @FXML
	    private Button gas2;
	    @FXML
	    private Button gas3;
	    @FXML
	    private Button gas4;
	    @FXML
	    private Button gas5;
	    @FXML
		private Label chLabel;
	    @FXML
	    private Button ch1;
	    @FXML
	    private Button ch2;
	    @FXML
	    private Button ch3;
	    @FXML
	    private Button ch4;
	    @FXML
	    private Button ch5;
	    @FXML
		private Label srLabel;
	    @FXML
	    private Button sr1;
	    @FXML
	    private Button sr2;
	    @FXML
	    private Button sr3;
	    @FXML
	    private Button sr4;
	    @FXML
	    private Button sr5;
	    @FXML
		private Label cloudLabel;
	    @FXML
	    private Button yesCloud;
	    @FXML 
	    private Button noCloud;
	    @FXML
	    private Button startSimGUI;
	    @FXML
	    private Label fogConfig;
	    @FXML
	    private Label gasConfig;
	    @FXML
	    private Label chConfig;
	    @FXML
	    private Label srConfig;
	    @FXML
	    private Label cloudConfig;
	    @FXML
	    private Label configurations;
	    @FXML
	    private Label simExecutionTimeUpdate;
	    @FXML
	    private Label simExecConfig;
	    @FXML
	    private TextField customFogInput;
	    @FXML
	    private Button customFogOK;
	    @FXML
	    private Button saveButtonMasterNodeMetric;
	    @FXML
	    private Button saveButtonSensorMetric;
	    
	   
	    @FXML
	    private BarChart<String, Double> networkUsageChart;
	    @FXML
	    private BarChart<String, Double> appLoopDelayUsageChart;
	    @FXML
	    private BarChart<String, Double> energyConsumptionUsageChart;
	    @FXML
	    private BarChart<String, Double> tupleExecutionUsageChart;
	    @FXML
	    private BarChart<String, Long> cloudEnergyConsumptionUsageChart;
	    @FXML
	    private CategoryAxis xAxisNetwork;
	    @FXML
	    private NumberAxis yAxisNetwork;
	    @FXML
	    private CategoryAxis xAxisAppDelay;
	    @FXML
	    private NumberAxis yAxisAppDelay;
	    @FXML
	    private CategoryAxis xAxisEnergyCon;
	    @FXML
	    private NumberAxis yAxisEnergyCon;
	    @FXML
	    private CategoryAxis xAxisTupleExec;
	    @FXML
	    private NumberAxis yAxisTupleExec;
	    @FXML
	    private CategoryAxis xAxisCloudEnergyCon;
	    @FXML
	    private NumberAxis yAxisCloudEnergyCon;
	    
	    
	    List<Double> appLoopData = SmartMiningMain.getAppLoopDelayOutput();
	    
	    Map<String, Double> energyConsumptionMapData = SmartMiningMain.getEnergyConsumptionOutputMap();
	    Map<Long, String> cloudEnergyConsumptionMapData = SmartMiningMain.getCloudEnergyConsumptionOutputMap();
	    
	    Map<String, Double> tupleExecutionMapData = SmartMiningMain.getTupleExecutionOutputMap();
	    
	    String currentNetworkUsageKey;
	    Double currentNetworkUsageValue;
	    
	    String currentCloudUsageKey;
	    Double currentCloudUsageValue;
	    
	    String currentCloudEnergyConKey;
	    Long currentCloudEnergyConValue;
	    
	    Double simExecutionTime;
	    
	    
	    List<String> currentEnergyConKey = new ArrayList<String>();
	    List<Double> currentEnergyConValue = new ArrayList<Double>();
	    
	    List<String> currentTupleExecKey = new ArrayList<String>();
	    List<Double> currentTupleExecValue = new ArrayList<Double>();
	    
	    List<Double> currentAppLoopDelayValue = new ArrayList<Double>();
   
	    @FXML
	    void changeFogDeviceAmount(ActionEvent event) {

	    	if (event.getSource() == fog1) {
	    		SmartMiningMain.setFogDeviceAmount(1);
	    		fogConfig.setText("1");
	    	}
	    	else if (event.getSource() == fog2) {
	    		SmartMiningMain.setFogDeviceAmount(2);
	    		fogConfig.setText("2");
	    	}
	    	else if (event.getSource() == fog3) {
	    		SmartMiningMain.setFogDeviceAmount(3);
	    		fogConfig.setText("3");
	    	}
	    	else if (event.getSource() == fog4) {
	    		SmartMiningMain.setFogDeviceAmount(4);
	    		fogConfig.setText("4");
	    	}
	    	else if (event.getSource() == fog5) {
	    		SmartMiningMain.setFogDeviceAmount(5);
	    		fogConfig.setText("5");
	    	}
	    	else if (event.getSource() == customFogOK) {
	    		SmartMiningMain.setFogDeviceAmount(Integer.valueOf(customFogInput.getText()));
	    		fogConfig.setText(customFogInput.getText());
	    	}
	    }
	    
	    @FXML
	    void changeGasSensorAmount(ActionEvent event) {
	    	if (event.getSource() == gas1) {
	    		SmartMiningMain.setGasSensorAmount(1);
	    		gasConfig.setText("1");
	    	}
	    	else if (event.getSource() == gas2) {
	    		SmartMiningMain.setGasSensorAmount(2);
	    		gasConfig.setText("2");
	    	}
	    	else if (event.getSource() == gas3) {
	    		SmartMiningMain.setGasSensorAmount(3);
	    		gasConfig.setText("3");
	    	}
	    	else if (event.getSource() == gas4) {
	    		SmartMiningMain.setGasSensorAmount(4);
	    		gasConfig.setText("4");
	    	}
	    	else if (event.getSource() == gas5) {
	    		SmartMiningMain.setGasSensorAmount(5);
	    		gasConfig.setText("5");
	    	}
	    }
	    
	    @FXML
	    void changeChSensorAmount(ActionEvent event) {
	    	if (event.getSource() == ch1) {
	    		SmartMiningMain.setChSensorAmount(1);
	    		chConfig.setText("1");
	    	}
	    	else if (event.getSource() == ch2) {
	    		SmartMiningMain.setChSensorAmount(2);
	    		chConfig.setText("2");
	    	}
	    	else if (event.getSource() == ch3) {
	    		SmartMiningMain.setChSensorAmount(3);
	    		chConfig.setText("3");
	    	}
	    	else if (event.getSource() == ch4) {
	    		SmartMiningMain.setChSensorAmount(4);
	    		chConfig.setText("4");
	    	}
	    	else if (event.getSource() == ch5) {
	    		SmartMiningMain.setChSensorAmount(5);
	    		chConfig.setText("5");
	    	}
	    }
	    
	    @FXML
	    void changeSrSensorAmount(ActionEvent event) {
	    	if (event.getSource() == sr1) {
	    		SmartMiningMain.setSrSensorAmount(1);
	    		srConfig.setText("1");
	    	}
	    	else if (event.getSource() == sr2) {
	    		SmartMiningMain.setSrSensorAmount(2);
	    		srConfig.setText("2");
	    	}
	    	else if (event.getSource() == sr3) {
	    		SmartMiningMain.setSrSensorAmount(3);
	    		srConfig.setText("3");
	    	}
	    	else if (event.getSource() == sr4) {
	    		SmartMiningMain.setSrSensorAmount(4);
	    		srConfig.setText("4");
	    	}
	    	else if (event.getSource() == sr5) {
	    		SmartMiningMain.setSrSensorAmount(5);
	    		srConfig.setText("5");
	    	}
	    }
	    
	    
	    @SuppressWarnings({ "rawtypes", "unchecked" })
		@FXML
	    void startSimulatorOnClick(ActionEvent event) {
	    	Task<Void> task = new Task<Void>() {

				@Override
				protected Void call() throws Exception {
					// TODO Auto-generated method stub
					SmartMiningMain.startSmartMiningSimulator();
					return null;
				}
	    	};
	    	
	    	Thread th = new Thread(task);
			th.setDaemon(true);
			th.start();
			task.setOnSucceeded(e -> {
				
				simExecutionTime = SmartMiningMain.getSimulationExecutionTime();
				int simExecutionTimeInt = (int)Math.round(simExecutionTime);
				configurations.setText("Simulation Complete.");
				startSimGUI.setVisible(false);
				fogLabel.setVisible(false);
				fog1.setVisible(false);
				fog2.setVisible(false);
				fog3.setVisible(false);
				fog4.setVisible(false);
				fog5.setVisible(false);
				gasLabel.setVisible(false);
				gas1.setVisible(false);
				gas2.setVisible(false);
				gas3.setVisible(false);
				gas4.setVisible(false);
				gas5.setVisible(false);
				chLabel.setVisible(false);
				ch1.setVisible(false);
				ch2.setVisible(false);
				ch3.setVisible(false);
				ch4.setVisible(false);
				ch5.setVisible(false);
				srLabel.setVisible(false);
				sr1.setVisible(false);
				sr2.setVisible(false);
				sr3.setVisible(false);
				sr4.setVisible(false);
				sr5.setVisible(false);
				cloudLabel.setVisible(false);
				yesCloud.setVisible(false);
				noCloud.setVisible(false);
				simExecutionTimeUpdate.setText(simExecutionTimeInt+" MS");
				simExecConfig.setVisible(true);
				customFogInput.setVisible(false);
				customFogOK.setVisible(false);
				saveButtonMasterNodeMetric.setVisible(true);
				saveButtonSensorMetric.setVisible(true);
				
				System.out.println("NEXT TASK!");
				//networkUsage and Cloud Usage

				XYChart.Series series1 = new XYChart.Series<>();
				final XYChart.Data<String, Number> dataNetworkUsage = new XYChart.Data
						("Network Usage", SmartMiningMain.getNetworkUsageOutput());
				final XYChart.Data<String, Number> dataCloudUsage = new XYChart.Data
						("Cloud Usage", SmartMiningMain.getCloudUsageOutput());
				
				dataNetworkUsage.nodeProperty().addListener(new ChangeListener<Node>() {
			        @Override public void changed(ObservableValue<? extends Node> ov, 
			        		Node oldNode, final Node node) {
			          if (node != null) {      
			            displayLabelForData(dataNetworkUsage);
			          } 
			        }
				});
				
				dataCloudUsage.nodeProperty().addListener(new ChangeListener<Node>() {
			        @Override public void changed(ObservableValue<? extends Node> ov, 
			        		Node oldNode, final Node node) {
			          if (node != null) {      
			            displayLabelForData(dataCloudUsage);
			          } 
			        }
				});
				
				series1.getData().add(dataNetworkUsage);
				series1.getData().add(dataCloudUsage);
				
				networkUsageChart.getData().add(series1);
				
				currentNetworkUsageKey = "Network Usage";
				currentNetworkUsageValue = SmartMiningMain.getNetworkUsageOutput();
				currentCloudUsageKey = "Cloud Usage";
				currentCloudUsageValue = SmartMiningMain.getCloudUsageOutput();
				
				//###################################################################
				
				//appLoop
				XYChart.Series series2 = new XYChart.Series<>();
				appLoopData = SmartMiningMain.getAppLoopDelayOutput();
				
				for (int i=0; i<appLoopData.size(); i++) {
					//System.out.println("YO MAMA");
					final XYChart.Data<String, Number> dataAppLoop = new XYChart.Data("Loop "+(i+1), appLoopData.get(i));
					dataAppLoop.nodeProperty().addListener(new ChangeListener<Node>() {
				        @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
				          if (node != null) {      
				            displayLabelForData(dataAppLoop);
				          } 
				        }
					});
					//series2.getData().add(new XYChart.Data<String, Double>("Loop "+(i+1), appLoopData.get(i)));
					series2.getData().add(dataAppLoop);
				}
				appLoopDelayUsageChart.getData().add(series2);
				currentAppLoopDelayValue = SmartMiningMain.getAppLoopDelayOutput();
				//###################################################################################
				
				//energy Consumption
				XYChart.Series series3 = new XYChart.Series<>();
				energyConsumptionMapData = SmartMiningMain.getEnergyConsumptionOutputMap();
				
				if (energyConsumptionMapData != null) {
			        energyConsumptionMapData.forEach((k, v) -> {
			            if (v != null && !k.contains("Router")) {
			            	final XYChart.Data<String, Number> dataEnergyCon = new XYChart.Data(k, v);
			            	dataEnergyCon.nodeProperty().addListener(new ChangeListener<Node>() {
						        @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
						          if (node != null) {      
						            displayLabelForData(dataEnergyCon);
						          } 
						        }
							});
			            	series3.getData().add(dataEnergyCon);
			            	currentEnergyConKey.add(k);
			            	currentEnergyConValue.add(v);
			            }
			            else if (v != null && !k.contains("Proxy Server")) {
			            	//series3.getData().add(new XYChart.Data<String, Double>(k, v));
			            	final XYChart.Data<String, Number> dataEnergyCon = new XYChart.Data(k, v);
			            	dataEnergyCon.nodeProperty().addListener(new ChangeListener<Node>() {
						        @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
						          if (node != null) {      
						            displayLabelForData(dataEnergyCon);
						          } 
						        }
							});
			            	series3.getData().add(dataEnergyCon);
			            	currentEnergyConKey.add(k);
			            	currentEnergyConValue.add(v);
			            }
			            else if (v != null && !k.contains("Master Module")) {
			            	final XYChart.Data<String, Number> dataEnergyCon = new XYChart.Data(k, v);
			            	dataEnergyCon.nodeProperty().addListener(new ChangeListener<Node>() {
						        @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
						          if (node != null) {      
						            displayLabelForData(dataEnergyCon);
						          } 
						        }
							});
			            	series3.getData().add(dataEnergyCon);
			            	currentEnergyConKey.add(k);
			            	currentEnergyConValue.add(v);
			            }
			            else if (v != null && !k.contains("Gas Sensor")) {
			            	final XYChart.Data<String, Number> dataEnergyCon = new XYChart.Data(k, v);
			            	dataEnergyCon.nodeProperty().addListener(new ChangeListener<Node>() {
						        @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
						          if (node != null) {      
						            displayLabelForData(dataEnergyCon);
						          } 
						        }
							});
			            	series3.getData().add(dataEnergyCon);
			            	currentEnergyConKey.add(k);
			            	currentEnergyConValue.add(v);
			            }
			            else if (v != null && !k.contains("Chemical Sensor")) {
			            	final XYChart.Data<String, Number> dataEnergyCon = new XYChart.Data(k, v);
			            	dataEnergyCon.nodeProperty().addListener(new ChangeListener<Node>() {
						        @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
						          if (node != null) {      
						            displayLabelForData(dataEnergyCon);
						          } 
						        }
							});
			            	series3.getData().add(dataEnergyCon);
			            	currentEnergyConKey.add(k);
			            	currentEnergyConValue.add(v);
			            }
			            else if (v != null && !k.contains("Surrounding Sensor")) {
			            	final XYChart.Data<String, Number> dataEnergyCon = new XYChart.Data(k, v);
			            	dataEnergyCon.nodeProperty().addListener(new ChangeListener<Node>() {
						        @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
						          if (node != null) {      
						            displayLabelForData(dataEnergyCon);
						          } 
						        }
							});
			            	series3.getData().add(dataEnergyCon);
			            	currentEnergyConKey.add(k);
			            	currentEnergyConValue.add(v);
			            }
			        });
			    }
				energyConsumptionUsageChart.getData().add(series3);
				
				//##############################################################################
				
				//tuple Execution
				XYChart.Series series4 = new XYChart.Series<>();
				tupleExecutionMapData = SmartMiningMain.getTupleExecutionOutputMap();
				
				if (tupleExecutionMapData != null) {
					tupleExecutionMapData.forEach((k, v) -> {
			            if (v != null && !k.contains("SR")) {
			            	//series4.getData().add(new XYChart.Data<String, Double>(k, v));
			            	final XYChart.Data<String, Number> dataTupleExec = new XYChart.Data(k, v);
			            	dataTupleExec.nodeProperty().addListener(new ChangeListener<Node>() {
						        @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
						          if (node != null) {      
						            displayLabelForData(dataTupleExec);
						          } 
						        }
							});
			            	series4.getData().add(dataTupleExec);
			            	currentTupleExecKey.add(k);
			            	currentTupleExecValue.add(v);
			            }
			            else if (v != null && !k.contains("SR_TASK")) {
			            	final XYChart.Data<String, Number> dataTupleExec = new XYChart.Data(k, v);
			            	dataTupleExec.nodeProperty().addListener(new ChangeListener<Node>() {
						        @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
						          if (node != null) {      
						            displayLabelForData(dataTupleExec);
						          } 
						        }
							});
			            	series4.getData().add(dataTupleExec);
			            	currentTupleExecKey.add(k);
			            	currentTupleExecValue.add(v);
			            }
			            else if (v != null && !k.contains("SR_RESP")) {
			            	final XYChart.Data<String, Number> dataTupleExec = new XYChart.Data(k, v);
			            	dataTupleExec.nodeProperty().addListener(new ChangeListener<Node>() {
						        @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
						          if (node != null) {      
						            displayLabelForData(dataTupleExec);
						          } 
						        }
							});
			            	series4.getData().add(dataTupleExec);
			            	currentTupleExecKey.add(k);
			            	currentTupleExecValue.add(v);
			            }
			            else if (v != null && !k.contains("GAS")) {
			            	final XYChart.Data<String, Number> dataTupleExec = new XYChart.Data(k, v);
			            	dataTupleExec.nodeProperty().addListener(new ChangeListener<Node>() {
						        @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
						          if (node != null) {      
						            displayLabelForData(dataTupleExec);
						          } 
						        }
							});
			            	series4.getData().add(dataTupleExec);
			            	currentTupleExecKey.add(k);
			            	currentTupleExecValue.add(v);
			            }
			            else if (v != null && !k.contains("GAS_TASK")) {
			            	final XYChart.Data<String, Number> dataTupleExec = new XYChart.Data(k, v);
			            	dataTupleExec.nodeProperty().addListener(new ChangeListener<Node>() {
						        @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
						          if (node != null) {      
						            displayLabelForData(dataTupleExec);
						          } 
						        }
							});
			            	series4.getData().add(dataTupleExec);
			            	currentTupleExecKey.add(k);
			            	currentTupleExecValue.add(v);
			            }
			            else if (v != null && !k.contains("GAS_RESP")) {
			            	final XYChart.Data<String, Number> dataTupleExec = new XYChart.Data(k, v);
			            	dataTupleExec.nodeProperty().addListener(new ChangeListener<Node>() {
						        @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
						          if (node != null) {      
						            displayLabelForData(dataTupleExec);
						          } 
						        }
							});
			            	series4.getData().add(dataTupleExec);
			            	currentTupleExecKey.add(k);
			            	currentTupleExecValue.add(v);
			            }
			            else if (v != null && !k.contains("CH")) {
			            	final XYChart.Data<String, Number> dataTupleExec = new XYChart.Data(k, v);
			            	dataTupleExec.nodeProperty().addListener(new ChangeListener<Node>() {
						        @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
						          if (node != null) {      
						            displayLabelForData(dataTupleExec);
						          } 
						        }
							});
			            	series4.getData().add(dataTupleExec);
			            	currentTupleExecKey.add(k);
			            	currentTupleExecValue.add(v);
			            }
			            else if (v != null && !k.contains("CH_RESP")) {
			            	final XYChart.Data<String, Number> dataTupleExec = new XYChart.Data(k, v);
			            	dataTupleExec.nodeProperty().addListener(new ChangeListener<Node>() {
						        @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
						          if (node != null) {      
						            displayLabelForData(dataTupleExec);
						          } 
						        }
							});
			            	series4.getData().add(dataTupleExec);
			            	currentTupleExecKey.add(k);
			            	currentTupleExecValue.add(v);
			            }
			            else if (v != null && !k.contains("CH_RESP")) {
			            	final XYChart.Data<String, Number> dataTupleExec = new XYChart.Data(k, v);
			            	dataTupleExec.nodeProperty().addListener(new ChangeListener<Node>() {
						        @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
						          if (node != null) {      
						            displayLabelForData(dataTupleExec);
						          } 
						        }
							});
			            	series4.getData().add(dataTupleExec);
			            	currentTupleExecKey.add(k);
			            	currentTupleExecValue.add(v);
			            }
			        });
				}
				tupleExecutionUsageChart.getData().add(series4);
				
				//################################################
				
				//cloud Energy Consumption
				XYChart.Series series5 = new XYChart.Series<>();
				cloudEnergyConsumptionMapData = SmartMiningMain.getCloudEnergyConsumptionOutputMap();
				
				if (cloudEnergyConsumptionMapData != null) {
			        cloudEnergyConsumptionMapData.forEach((k, v) -> {
			            if (v != null) {
			            	final XYChart.Data<String, Number> dataCloudCon = new XYChart.Data(v, k);
			            	dataCloudCon.nodeProperty().addListener(new ChangeListener<Node>() {
						        @Override public void changed(ObservableValue<? extends Node> ov, Node oldNode, final Node node) {
						          if (node != null) {      
						            displayLabelForData(dataCloudCon);
						          } 
						        }
							});
			            	series5.getData().add(dataCloudCon);
			            	currentCloudEnergyConKey = v;
			            	currentCloudEnergyConValue = k;
			            }
			        });
			    }
				cloudEnergyConsumptionUsageChart.getData().add(series5);
			});
	    }
	    
	    
	    @FXML
	    void saveToFileMasterNodeMetric() {
	    	if (currentNetworkUsageKey == "Network Usage") {
	    		try (BufferedWriter bw = new BufferedWriter(new FileWriter("network-usage.txt", true))) {
	    			bw.newLine();
	    			bw.write("Network" + currentNetworkUsageValue);
	    	        bw.newLine();
	    	        bw.write("Config" + fogConfig.getText());
	    	        bw.newLine();
	    	        bw.write("Cloud" + currentCloudUsageValue);
	    	        bw.newLine();
	    		} catch (IOException e) {
	    	        e.printStackTrace();
	    	    }
	    	}
	    	if (currentEnergyConKey.isEmpty()==false) {
	    		try (BufferedWriter bw = new BufferedWriter(new FileWriter("energy-con.txt", true))) {
	    			bw.newLine();
	    			for (int i=0; i<currentEnergyConKey.size(); i++) {
	    	        	bw.write("Energy!" + currentEnergyConValue.get(i));
	    	        	bw.newLine();
	    	        	bw.write("Name" + currentEnergyConKey.get(i));
	    	        	bw.newLine();
	    	        	bw.write("Config" + fogConfig.getText());
	    	        	bw.newLine();
	    	        }
	    		} catch (IOException e) {
	    	        e.printStackTrace();
	    	    }
	    	}
	    	if (currentCloudEnergyConKey.isEmpty()==false) {
	    		try (BufferedWriter bw = new BufferedWriter(new FileWriter("cloud-energy-con.txt", true))) {
    				bw.newLine();
	    			for (int i=0; i<1; i++) {
		    	        bw.write("CloudE." + currentCloudEnergyConValue);
		    	        bw.newLine();
		    	        bw.write("Cloud"+ currentCloudEnergyConKey);
		    	        bw.newLine();
		    	        bw.write("Cloud?" + fogConfig.getText());
		    	        bw.newLine();
		    	    }
	    		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	}
	    	if (currentAppLoopDelayValue.isEmpty()==false) {
	    		try (BufferedWriter bw = new BufferedWriter(new FileWriter("app-delay.txt", true))) {
    	        	bw.newLine();
	    	        for (int i=0; i<currentAppLoopDelayValue.size(); i++) {
	    	        	bw.write("Loop" + (i+1) + "@" + currentAppLoopDelayValue.get(i));
		    	        bw.newLine();
		    	        bw.write("Config" + fogConfig.getText());
		    	        bw.newLine();
	    	        }
	    		} catch (IOException e) {
	    	        e.printStackTrace();
	    	    }
	    	}
	    	if (currentTupleExecKey.isEmpty()==false && currentTupleExecValue.isEmpty()==false) {
	    		try (BufferedWriter bw = new BufferedWriter(new FileWriter("tuple-exec.txt", true))) {
    	        	bw.newLine();
	    	        for (int i=0; i<currentTupleExecKey.size(); i++) {
	    	        	bw.write("Tuple@" + currentTupleExecValue.get(i));
	    	        	bw.newLine();
	    	        	bw.write("Name?" + currentTupleExecKey.get(i));
	    	        	bw.newLine();
	    	        	bw.write("Config" + fogConfig.getText());
	    	        	bw.newLine();
	    	        }
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
	    	}
	    }
	    
	    @FXML
	    void saveToFileSensorMetric() {
	    	if (currentNetworkUsageKey == "Network Usage") {
	    		try (BufferedWriter bw = new BufferedWriter(new FileWriter("network-usage-sensor.txt", true))) {
	    			bw.newLine();
	    			bw.write("Network" + currentNetworkUsageValue);
	    	        bw.newLine();
	    	        bw.write("Config" + gasConfig.getText() + chConfig.getText() + srConfig.getText() + cloudConfig.getText());
	    	        bw.newLine();
	    	        bw.write("Cloud" + currentCloudUsageValue);
	    	        bw.newLine();
	    		} catch (IOException e) {
	    	        e.printStackTrace();
	    	    }
	    	}
	    	if (currentEnergyConKey.isEmpty()==false) {
	    		try (BufferedWriter bw = new BufferedWriter(new FileWriter("energy-con-sensor.txt", true))) {
	    			bw.newLine();
	    			for (int i=0; i<currentEnergyConKey.size(); i++) {
	    	        	bw.write("Energy!" + currentEnergyConValue.get(i));
	    	        	bw.newLine();
	    	        	bw.write("Name" + currentEnergyConKey.get(i));
	    	        	bw.newLine();
	    	        	bw.write("Config" + gasConfig.getText() + chConfig.getText() + srConfig.getText() + cloudConfig.getText());
	    	        	bw.newLine();
	    	        }
	    	        for (int i=0; i<1; i++) {
	    	        	bw.write("CloudE." + currentCloudEnergyConValue);
	    	        	bw.newLine();
	    	        	bw.write("Cloud"+ currentCloudEnergyConKey);
	    	        	bw.newLine();
	    	        	bw.write("Cloud?Config" + gasConfig.getText() + chConfig.getText() + srConfig.getText() + cloudConfig.getText());
	    	        	bw.newLine();
	    	        }
	    		} catch (IOException e) {
	    	        e.printStackTrace();
	    	    }
	    	}
	    	if (currentCloudEnergyConKey.isEmpty()==false) {
	    		try (BufferedWriter bw = new BufferedWriter(new FileWriter("cloud-energy-con-sensor.txt", true))) {
	    			bw.newLine(); 
	    			for (int i=0; i<1; i++) {
		    	        bw.write("CloudE." + currentCloudEnergyConValue);
		    	        bw.newLine();
		    	        bw.write("Cloud"+ currentCloudEnergyConKey);
		    	        bw.newLine();
		    	        bw.write("Cloud?" + gasConfig.getText() + chConfig.getText() + srConfig.getText() + cloudConfig.getText());
		    	        bw.newLine();
		    	    }
	    		} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    	} 
	    	if (currentAppLoopDelayValue.isEmpty()==false) {
	    		try (BufferedWriter bw = new BufferedWriter(new FileWriter("app-delay-sensor.txt", true))) {
	    			bw.newLine();
	    			for (int i=0; i<currentAppLoopDelayValue.size(); i++) {
	    	        	bw.write("Loop" + (i+1) + "@" + currentAppLoopDelayValue.get(i));
		    	        bw.newLine();
		    	        bw.write("Config" + gasConfig.getText() + chConfig.getText() + srConfig.getText() + cloudConfig.getText());
		    	        bw.newLine();
	    	        }
	    		} catch (IOException e) {
	    	        e.printStackTrace();
	    	    }
	    	}
	    	if (currentTupleExecKey.isEmpty()==false && currentTupleExecValue.isEmpty()==false) {
	    		try (BufferedWriter bw = new BufferedWriter(new FileWriter("tuple-exec-sensor.txt", true))) {
	    			bw.newLine();
	    			for (int i=0; i<currentTupleExecKey.size(); i++) {
	    	        	bw.write("Tuple@" + currentTupleExecValue.get(i));
	    	        	bw.newLine();
	    	        	bw.write("Name?" + currentTupleExecKey.get(i));
	    	        	bw.newLine();
	    	        	bw.write("Config" + gasConfig.getText() + chConfig.getText() + srConfig.getText() + cloudConfig.getText());
	    	        	bw.newLine();
	    	        }
	    		} catch (IOException e) {
	    			e.printStackTrace();
	    		}
	    	}
	    }
	    
	    
	    
	    @FXML
	    void cloudConfigure(ActionEvent event) {
	    	if (event.getSource() == yesCloud) {
	    		SmartMiningMain.setCloud(true);
	    		cloudConfig.setText("CLOUD");
	    	}
	    	else if (event.getSource() == noCloud) {
	    		SmartMiningMain.setCloud(false);
	    		cloudConfig.setText("EDGE");
	    	}
	    }

		@Override
		public void initialize(URL url, ResourceBundle rb) {
			// TODO Auto-generated method stub
			xAxisNetwork.setLabel("Cost");
	        yAxisNetwork.setLabel("Value");
	        xAxisNetwork.setAnimated(false);
	        
	        xAxisAppDelay.setLabel("Cost");
	        yAxisAppDelay.setLabel("Value");
	        xAxisAppDelay.setAnimated(false);
	        
	        xAxisEnergyCon.setLabel("Cost");
	        yAxisEnergyCon.setLabel("Value");
	        //xAxisEnergyCon.setTickLabelRotation(720);
	        xAxisEnergyCon.setAnimated(false);
	        
	        xAxisTupleExec.setLabel("Cost");
	        yAxisTupleExec.setLabel("Value");
	        //xAxisTupleExec.setTickLabelRotation(720);
	        xAxisTupleExec.setAnimated(false);
	        
	        xAxisCloudEnergyCon.setLabel("Cost");
	        yAxisCloudEnergyCon.setLabel("Value");
	        xAxisCloudEnergyCon.setAnimated(false);
	        
	        fogConfig.setText("3");
	        gasConfig.setText("1");
	        chConfig.setText("1");
	        srConfig.setText("1");
	        cloudConfig.setText("EDGE");
	        simExecConfig.setVisible(false);
	        
	        saveButtonMasterNodeMetric.setVisible(false);
	        saveButtonSensorMetric.setVisible(false);
			
		}
		
		private void displayLabelForData(XYChart.Data<String, Number> data) {
			  final Node node = data.getNode();
			  final Text dataText = new Text(round(data.getYValue().doubleValue(), 2) + "");
			  node.parentProperty().addListener(new ChangeListener<Parent>() {
			    @Override public void changed(
			    		ObservableValue<? extends Parent> ov, 
			    		Parent oldParent, Parent parent) {
			      Group parentGroup = (Group) parent;
			      parentGroup.getChildren().add(dataText);
			    }
			  });

			  node.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
			    @Override public void changed(
			    		ObservableValue<? extends Bounds> ov, 
			    		Bounds oldBounds, Bounds bounds) {
			      dataText.setLayoutX(
			        Math.round(
			          bounds.getMinX() + bounds.getWidth() / 2 - dataText.prefWidth(-1) / 2
			        )
			      );
			      dataText.setLayoutY(
			        Math.round(
			          bounds.getMinY() - dataText.prefHeight(-1) * 0.00001
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
