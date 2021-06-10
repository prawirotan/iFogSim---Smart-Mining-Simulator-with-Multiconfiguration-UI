package org.fog.test.resources;


import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.fog.placement.Controller;
import org.fog.test.perfeval.SmartMiningMain;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;

public class GraphicsController implements Initializable {
	
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
	    private Button yesCloud;
	    @FXML 
	    private Button noCloud;
	    @FXML
	    private Button startSimGUI;
	    @FXML
	    private Button exitProgram;
	    @FXML
	    private BarChart<String, Double> networkUsageChart;
	    @FXML
	    private BarChart<String, Double> appLoopDelayUsageChart;
	    @FXML
	    private BarChart<String, Double> energyConsumptionUsageChart;
	    @FXML
	    private BarChart<String, Double> tupleExecutionUsageChart;
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
	    
	    List<Double> appLoopData = SmartMiningMain.getAppLoopDelayOutput();
	    Map<String, Double> energyConsumptionMapData = SmartMiningMain.getEnergyConsumptionOutputMap();
	    Map<Long, String> cloudEnergyConsumptionMapData = SmartMiningMain.getCloudEnergyConsumptionOutputMap();
	    Map<String, Double> tupleExecutionMapData = SmartMiningMain.getTupleExecutionOutputMap();
	    
	    
	    
	    
	    @FXML
	    void changeFogDeviceAmount(ActionEvent event) {

	    	if (event.getSource() == fog1) {
	    		SmartMiningMain.setFogDeviceAmount(1);
	    	}
	    	else if (event.getSource() == fog2) {
	    		SmartMiningMain.setFogDeviceAmount(2);
	    	}
	    	else if (event.getSource() == fog3) {
	    		SmartMiningMain.setFogDeviceAmount(3);
	    	}
	    	else if (event.getSource() == fog4) {
	    		SmartMiningMain.setFogDeviceAmount(4);
	    	}
	    	else if (event.getSource() == fog5) {
	    		SmartMiningMain.setFogDeviceAmount(5);
	    	}
	    }
	    
	    @FXML
	    void changeGasSensorAmount(ActionEvent event) {
	    	if (event.getSource() == gas1) {
	    		SmartMiningMain.setGasSensorAmount(1);
	    	}
	    	else if (event.getSource() == gas2) {
	    		SmartMiningMain.setGasSensorAmount(2);
	    	}
	    	else if (event.getSource() == gas3) {
	    		SmartMiningMain.setGasSensorAmount(3);
	    	}
	    	else if (event.getSource() == gas4) {
	    		SmartMiningMain.setGasSensorAmount(4);
	    	}
	    	else if (event.getSource() == gas5) {
	    		SmartMiningMain.setGasSensorAmount(5);
	    	}
	    }
	    
	    @FXML
	    void changeChSensorAmount(ActionEvent event) {
	    	if (event.getSource() == ch1) {
	    		SmartMiningMain.setChSensorAmount(1);
	    	}
	    	else if (event.getSource() == ch2) {
	    		SmartMiningMain.setChSensorAmount(2);
	    	}
	    	else if (event.getSource() == ch3) {
	    		SmartMiningMain.setChSensorAmount(3);
	    	}
	    	else if (event.getSource() == ch4) {
	    		SmartMiningMain.setChSensorAmount(4);
	    	}
	    	else if (event.getSource() == ch5) {
	    		SmartMiningMain.setChSensorAmount(5);
	    	}
	    }
	    
	    @FXML
	    void changeSrSensorAmount(ActionEvent event) {
	    	if (event.getSource() == sr1) {
	    		SmartMiningMain.setSrSensorAmount(1);
	    	}
	    	else if (event.getSource() == sr2) {
	    		SmartMiningMain.setSrSensorAmount(2);
	    	}
	    	else if (event.getSource() == sr3) {
	    		SmartMiningMain.setSrSensorAmount(3);
	    	}
	    	else if (event.getSource() == sr4) {
	    		SmartMiningMain.setSrSensorAmount(4);
	    	}
	    	else if (event.getSource() == sr5) {
	    		SmartMiningMain.setSrSensorAmount(5);
	    	}
	    }
	    
	    @FXML
	    void exitOnClick(ActionEvent event) {
	    	if (event.getSource() == exitProgram) {
	    		System.exit(0);
	    	}
	    }
	    
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
				System.out.println("NEXT TASK!");
				//networkUsage and Cloud Usage
				//System.out.println(SmartMiningMain.getNetworkUsageOutput());
				//System.out.println(SmartMiningMain.getCloudUsageOutput()); 
				XYChart.Series<String, Double> series1 = new XYChart.Series<>();
				series1.getData().add(new XYChart.Data<String, Double>("Network Usage", SmartMiningMain.getNetworkUsageOutput()));
				series1.getData().add(new XYChart.Data<String, Double>("Cloud Usage", SmartMiningMain.getCloudUsageOutput()));
				networkUsageChart.getData().add(series1);	
				
				//appLoop
				XYChart.Series<String, Double> series2 = new XYChart.Series<>();
				appLoopData = SmartMiningMain.getAppLoopDelayOutput();
				
				for (int i=0; i<appLoopData.size(); i++) {
					//System.out.println("YO MAMA");
					series2.getData().add(new XYChart.Data<String, Double>("Loop "+(i+1), appLoopData.get(i)));
				}
				appLoopDelayUsageChart.getData().add(series2);
				
				//energy Consumption
				XYChart.Series<String, Double> series3 = new XYChart.Series<>();
				energyConsumptionMapData = SmartMiningMain.getEnergyConsumptionOutputMap();
				
				if (energyConsumptionMapData != null) {
			        energyConsumptionMapData.forEach((k, v) -> {
			            if (v != null && !k.contains("Router")) {
			            	series3.getData().add(new XYChart.Data<String, Double>(k, v));
			            }
			            else if (v != null && !k.contains("Proxy Server")) {
			            	series3.getData().add(new XYChart.Data<String, Double>(k, v));
			            }
			            else if (v != null && !k.contains("Master Module")) {
			            	series3.getData().add(new XYChart.Data<String, Double>(k, v));
			            }
			            else if (v != null && !k.contains("Gas Sensor")) {
			            	series3.getData().add(new XYChart.Data<String, Double>(k, v));
			            }
			            else if (v != null && !k.contains("Chemical Sensor")) {
			            	series3.getData().add(new XYChart.Data<String, Double>(k, v));
			            }
			            else if (v != null && !k.contains("Surrounding Sensor")) {
			            	series3.getData().add(new XYChart.Data<String, Double>(k, v));
			            }
			        });
			    }
				energyConsumptionUsageChart.getData().add(series3);
				
				//tuple Execution
				XYChart.Series<String, Double> series4 = new XYChart.Series<>();
				tupleExecutionMapData = SmartMiningMain.getTupleExecutionOutputMap();
				
				if (tupleExecutionMapData != null) {
					tupleExecutionMapData.forEach((k, v) -> {
			            if (v != null && !k.contains("SR")) {
			            	series4.getData().add(new XYChart.Data<String, Double>(k, v));
			            }
			            else if (v != null && !k.contains("SR_TASK")) {
			            	series4.getData().add(new XYChart.Data<String, Double>(k, v));
			            }
			            else if (v != null && !k.contains("SR_RESP")) {
			            	series4.getData().add(new XYChart.Data<String, Double>(k, v));
			            }
			            else if (v != null && !k.contains("GAS")) {
			            	series4.getData().add(new XYChart.Data<String, Double>(k, v));
			            }
			            else if (v != null && !k.contains("GAS_TASK")) {
			            	series4.getData().add(new XYChart.Data<String, Double>(k, v));
			            }
			            else if (v != null && !k.contains("GAS_RESP")) {
			            	series4.getData().add(new XYChart.Data<String, Double>(k, v));
			            }
			            else if (v != null && !k.contains("CH")) {
			            	series4.getData().add(new XYChart.Data<String, Double>(k, v));
			            }
			            else if (v != null && !k.contains("CH_RESP")) {
			            	series4.getData().add(new XYChart.Data<String, Double>(k, v));
			            }
			            else if (v != null && !k.contains("CH_RESP")) {
			            	series4.getData().add(new XYChart.Data<String, Double>(k, v));
			            }
			        });
				}
				tupleExecutionUsageChart.getData().add(series4);
	
			});
	    }
	    
	    @FXML
	    void cloudConfigure(ActionEvent event) {
	    	if (event.getSource() == yesCloud) {
	    		SmartMiningMain.setCloud(true);
	    	}
	    	else if (event.getSource() == noCloud) {
	    		SmartMiningMain.setCloud(false);
	    	}
	    }

		@Override
		public void initialize(URL url, ResourceBundle rb) {
			// TODO Auto-generated method stub
			xAxisNetwork.setLabel("Cost");
	        xAxisNetwork.setTickLabelRotation(90);
	        yAxisNetwork.setLabel("Value");
	        xAxisNetwork.setAnimated(false);
	        
	        xAxisAppDelay.setLabel("Cost");
	        xAxisAppDelay.setTickLabelRotation(90);
	        yAxisAppDelay.setLabel("Value");
	        xAxisAppDelay.setAnimated(false);
	        
	        xAxisEnergyCon.setLabel("Cost");
	        xAxisEnergyCon.setTickLabelRotation(90);
	        yAxisEnergyCon.setLabel("Value");
	        xAxisEnergyCon.setAnimated(false);
	        
	        xAxisTupleExec.setLabel("Cost");
	        xAxisTupleExec.setTickLabelRotation(90);
	        yAxisTupleExec.setLabel("Value");
	        xAxisTupleExec.setAnimated(false);
			
		}
		

}
