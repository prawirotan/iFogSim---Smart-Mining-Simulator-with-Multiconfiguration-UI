package org.fog.test.perfeval;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.core.CloudSim;
import org.cloudbus.cloudsim.power.PowerHost;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;
import org.cloudbus.cloudsim.sdn.overbooking.BwProvisionerOverbooking;
import org.cloudbus.cloudsim.sdn.overbooking.PeProvisionerOverbooking;
import org.fog.application.AppEdge;
import org.fog.application.AppLoop;
import org.fog.application.ApplicationFog;
import org.fog.application.selectivity.FractionalSelectivity;
import org.fog.entities.Actuator;
import org.fog.entities.FogBroker;
import org.fog.entities.FogDevice;
import org.fog.entities.FogDeviceCharacteristics;
import org.fog.entities.Sensor;
import org.fog.entities.Tuple;
import org.fog.placement.Controller;
import org.fog.placement.ModuleMapping;
import org.fog.placement.ModulePlacementEdgewards;
import org.fog.placement.ModulePlacementMapping;
import org.fog.policy.AppModuleAllocationPolicy;
import org.fog.scheduler.StreamOperatorScheduler;
import org.fog.test.resources.GraphicsController;
import org.fog.utils.FogLinearPowerModel;
import org.fog.utils.FogUtils;
import org.fog.utils.TimeKeeper;
import org.fog.utils.distribution.DeterministicDistribution;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.concurrent.*;


public class SmartMiningMain extends Application{
	
	//Create the list of fog devices
	static List<FogDevice> fogDevices = new ArrayList<FogDevice>();
	//Create the list of sensors
	static List<Sensor> sensors = new ArrayList<Sensor>();
	//Create the list of actuators
	static List<Actuator> actuators = new ArrayList<Actuator>();
	//Define the number of fog nodes will be deployed
	static int numOfFogDevices = 3;
	//Define the number of gas sensors with each fog nodes
	static int numOfGasSensorsPerMasterNode=1;
	//Define the number of chemical sensors with each fog nodes
	static int numOfChSensorsPerMasterNode=1;
	//Define the number of surrounding sensors with each fog nodes
	static int numOfSrSensorsPerMasterNode=1;
	//We are using the fog nodes to perform the operations.
	//cloud is set to false
	private static boolean CLOUD = false;
	//networkUsage Output
	private static double networkUsageOutput;
	//List of appLoopDelays
	private static List<Double> appLoopDelayOutput = new ArrayList<Double>();
	//cloudUsage Output
	private static double cloudUsageOutput;
	//Map of each energyConsumed for each fog Device except cloud
	private static Map<String, Double> energyConsumptionOutputMap = new HashMap<>();
	//Map of Cloud energyConsumed
	private static Map<Long, String> cloudEnergyConsumptionOutputMap = new HashMap<>();
	//Map of Tuple Execution Times
	private static Map<String, Double> tupleExecutionOutputMap = new HashMap<>();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("../resources/graphics_1.fxml"));
		Parent root = loader.load();
		root.getStylesheets().add(getClass().getResource("../resources/styles.css").toString());
		primaryStage.setTitle("iFogSim Smart Mining Simulator");
		primaryStage.setScene(new Scene(root, 1300, 880));
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public static void startSmartMiningSimulator() {
		

				System.out.println(numOfFogDevices);
				System.out.println(numOfGasSensorsPerMasterNode);
				Log.printLine("Smart Mining System!");
				try {
					Log.disable();
					int num_user = 1; // number of cloud users
					
					
					Calendar calendar = Calendar.getInstance();
					
					boolean trace_flag = false; // mean trace events
					
					CloudSim.init(num_user, calendar, trace_flag);
					
					String appId = "mins"; // identifier of the application
					
					FogBroker broker = new FogBroker("broker");
					
					
					createFogDevices(broker.getId(), appId);
					ApplicationFog application = createApplication(appId, broker.getId());
					
					application.setUserId(broker.getId());
					
					Controller controller = null;
					// initializing a module mapping
					ModuleMapping moduleMapping = ModuleMapping.createModuleMapping();
					
					// if the mode of deployment is cloud-based
					if(CLOUD){
					// placing all instances of master-module in Cloud
					moduleMapping.addModuleToDevice("master-module", "cloud");
					moduleMapping.addModuleToDevice("gasinfo-module", "cloud");
					moduleMapping.addModuleToDevice("chinfo-module", "cloud");
					moduleMapping.addModuleToDevice("srinfo-module", "cloud");
					}
					else {
						for(FogDevice device : fogDevices){
							
							if(device.getName().startsWith("a")){
								moduleMapping.addModuleToDevice("master-module", device.getName());
								moduleMapping.addModuleToDevice("gasinfo-module", device.getName());
								moduleMapping.addModuleToDevice("chinfo-module", device.getName()); 
								moduleMapping.addModuleToDevice("srinfo-module", device.getName());
							}
						}
					}
					
					controller = new Controller("master-controller", fogDevices, sensors, actuators);
					
					
					controller.submitApplication(application,
					(CLOUD)?(new ModulePlacementMapping(fogDevices, application, moduleMapping))
							:(new ModulePlacementEdgewards(fogDevices, sensors, actuators, application, moduleMapping)));
					
					TimeKeeper.getInstance().setSimulationStartTime(Calendar.getInstance().getTimeInMillis());
					
					
					CloudSim.startSimulation();
					CloudSim.stopSimulation();
					
					networkUsageOutput = controller.getNetworkUsage();
					appLoopDelayOutput = controller.getAppLoopDelay();
					cloudUsageOutput = controller.getCloudUsage();
					energyConsumptionOutputMap = controller.getEnergyConsumptionMap();
					cloudEnergyConsumptionOutputMap = controller.getCloudEnergyConsumptionMap();
					tupleExecutionOutputMap = controller.getTupleExecutionMap();
					
					Log.printLine("Mining industry simulation finished!");
					System.out.println("Simulation finished YOOO.");
					} catch (Exception e) {
					e.printStackTrace();
					Log.printLine("Unwanted errors happen");
					}	

	}
	
	private static void createFogDevices(int userId, String appId) {
		FogDevice cloud = createFogDevice("cloud", 44800, 40000, 100,
		10000, 0, 0.01, 16*103,
		16*83.25);
		cloud.setParentId(-1);
		fogDevices.add(cloud);
		FogDevice proxy = createFogDevice("proxy-server", 7000, 4000,
		10000, 10000, 1, 0.0, 107.339, 83.4333);
		proxy.setParentId(cloud.getId());
		proxy.setUplinkLatency(100.0);
		fogDevices.add(proxy);
		FogDevice router = createFogDevice("router", 2800, 4000, 10000, 10000, 2, 0.0, 107.339, 83.4333);
		router.setParentId(proxy.getId());
		router.setUplinkLatency(200.0);
		fogDevices.add(router);
		for(int i=0;i<numOfFogDevices;i++){
			addMasterNode(i+"", userId, appId, router.getId());
		}
	}
	
	private static FogDevice addMasterNode(String id, int userId, String appId, int parentId){ //Master Module
		FogDevice fognode = createFogDevice("a-"+id, 5000, 4000, 10000, 10000, 3, 0.0, 107.339, 83.4333);
		fogDevices.add(fognode);
		fognode.setUplinkLatency(1.0);
		
			for(int i=0;i<numOfGasSensorsPerMasterNode;i++){
				String mobileId = id+"-"+i;
				FogDevice gasSensor = addGasSensors(mobileId, userId, appId, fognode.getId());
				gasSensor.setUplinkLatency(2);
				fogDevices.add(gasSensor);
				/*String mobileId = id+"-"+i;
				addGasSensors(mobileId, userId, appId, fognode.getId());*/
			}
			for(int i=0;i<numOfChSensorsPerMasterNode;i++){
				String mobileId = id+"-"+i;
				FogDevice chSensor = addChSensors(mobileId, userId, appId, fognode.getId());
				chSensor.setUplinkLatency(2);
				fogDevices.add(chSensor);
				/*String mobileId = id+"-"+i;
				addChSensors(mobileId, userId, appId, fognode.getId());*/
			}
			for(int i=0;i<numOfSrSensorsPerMasterNode;i++){	
				String mobileId = id+"-"+i;
				FogDevice srSensor = addSrSensors(mobileId, userId, appId, fognode.getId());
				srSensor.setUplinkLatency(2);
				fogDevices.add(srSensor);
				/*String mobileId = id+"-"+i;
				addSrSensors(mobileId, userId, appId, fognode.getId());*/
			}
			fognode.setParentId(parentId);
			return fognode;
	}
	
	private static FogDevice addGasSensors(String id, int userId,
			String appId, int parentId){
			FogDevice gasSensor = createFogDevice("g-"+id, 5000, 1000, 10000,
			10000, 4, 0, 87.53, 82.44);
			gasSensor.setParentId(parentId);
			Sensor sensor = new Sensor("s-"+id, "GAS", userId, appId, new
			DeterministicDistribution(5));
			sensors.add(sensor);
			Actuator ptz = new Actuator("act-"+id, userId,
			appId, "ACT_CONTROL");
			actuators.add(ptz);
			sensor.setGatewayDeviceId(gasSensor.getId());
			sensor.setLatency(1.0);
			ptz.setGatewayDeviceId(parentId);
			ptz.setLatency(1.0);
			return gasSensor;
	}
	
	private static FogDevice addChSensors(String id, int userId,
			String appId, int parentId){
			FogDevice chSensor = createFogDevice("d-"+id, 5000, 1000, 10000,
			10000, 4, 0, 87.53, 82.44);
			chSensor.setParentId(parentId);
			Sensor sensor = new Sensor("sch-"+id, "CH", userId, appId,
			new DeterministicDistribution(5));
			sensors.add(sensor);
			Actuator ptzch = new Actuator("actch-"+id, userId,
			appId, "ACT_CONTROLCH");
			actuators.add(ptzch);
			sensor.setGatewayDeviceId(chSensor.getId());
			sensor.setLatency(1.0);
			ptzch.setGatewayDeviceId(parentId);
			ptzch.setLatency(1.0);
			return chSensor;
	}
	
	private static FogDevice addSrSensors(String id, int userId,
			String appId, int parentId){
			FogDevice srSensor = createFogDevice("s-"+id, 5000, 1000, 10000,
			10000, 4, 0, 87.53, 82.44);
			srSensor.setParentId(parentId);
			Sensor sensor = new Sensor("ssr-"+id, "SR", userId, appId,
			new DeterministicDistribution(5));
			sensors.add(sensor);
			Actuator ptzch = new Actuator("actsr-"+id, userId,
			appId, "ACT_CONTROLSR");
			actuators.add(ptzch);
			sensor.setGatewayDeviceId(srSensor.getId());
			sensor.setLatency(1.0);
			ptzch.setGatewayDeviceId(parentId);
			ptzch.setLatency(1.0);
			return srSensor;
	}
	
	private static ApplicationFog createApplication(String appId,
			int userId){
			ApplicationFog application = ApplicationFog.createApplication(appId, userId);
			
			application.addAppModule("master-module", 10);
			application.addAppModule("gasinfo-module", 10);
			application.addAppModule("chinfo-module", 10);
			application.addAppModule("srinfo-module", 10);
			
			application.addAppEdge("GAS", "master-module", 1000, 2000, "GAS", Tuple.UP, AppEdge.SENSOR); //adding edge from GAS (sensor) to Master Module carrying tuples of type GAS
			application.addAppEdge("CH", "master-module", 1000, 2000, "CH", Tuple.UP, AppEdge.SENSOR); //adding edge from CH (sensor) to Master Module carrying tuples of type CH
			application.addAppEdge("SR", "master-module", 1000, 2000,"SR", Tuple.UP, AppEdge.SENSOR); //adding edge from SR (sensor) to Master Module carrying tuples of type SR
			application.addAppEdge("master-module", "gasinfo-module", 1000, 2000, "GAS_TASK", Tuple.UP, AppEdge.MODULE); //adding edge from Master Module to GasInfo Module carrying tuples of type GAS_TASK
			application.addAppEdge("master-module", "chinfo-module", 1000, 2000, "CH_TASK", Tuple.UP, AppEdge.MODULE); //adding edge from Master Module to ChInfo Module carrying tuples of type CH_TASK
			application.addAppEdge("master-module", "srinfo-module", 1000, 2000, "SR_TASK", Tuple.UP, AppEdge.MODULE); //adding edge from Master Module to SrInfo Module carrying tuples of type SR_TASK
			//Response
			application.addAppEdge("gasinfo-module", "master-module", 1000, 2000, "GAS_RESP", Tuple.DOWN, AppEdge.MODULE); //adding edge from GasInfo Module to Master Module carrying tuples of GAS_RESP
			application.addAppEdge("chinfo-module", "master-module", 1000, 2000, "CH_RESP", Tuple.DOWN, AppEdge.MODULE); //adding edge from ChInfo Module to Master Module carrying tuples of CH_RESP
			application.addAppEdge("srinfo-module", "master-module", 1000, 2000,"SR_RESP", Tuple.DOWN, AppEdge.MODULE); //adding edge from SrInfo Module to Master Module carrying tuples of SR_RESP
			application.addAppEdge("master-module", "ACT_CONTROL", 1000, 2000, "GAS_PARAMS", Tuple.DOWN, AppEdge.ACTUATOR); //adding edge from Master Module to ACT_CONTROL (gas) carrying tuples of GAS_PARAMS
			application.addAppEdge("master-module", "ACT_CONTROLCH", 1000, 2000, "CH_PARAMS", Tuple.DOWN, AppEdge.ACTUATOR); //adding edge from Master Module to ACT_CONTROLCH (chemical) carrying tuples of CH_PARAMS
			application.addAppEdge("master-module", "ACT_CONTROLSR", 1000, 2000, "SR_PARAMS", Tuple.DOWN, AppEdge.ACTUATOR); //adding edge from Master Module to ACT_CONTROLSR (surrounding) carrying tuples of SR_PARAMS
			
			//Adding Tuple Mapping
			application.addTupleMapping("master-module", "GAS", "GAS_TASK", new FractionalSelectivity(1.0));
			application.addTupleMapping("master-module", "CH", "CH_TASK", new FractionalSelectivity(1.0));
			application.addTupleMapping("master-module", "SR", "SR_TASK", new FractionalSelectivity(1.0));
			
			application.addTupleMapping("gasinfo-module", "GAS_TASK", "GAS_RESP", new FractionalSelectivity(1.0));
			application.addTupleMapping("chinfo-module", "CH_TASK", "CH_RESP", new FractionalSelectivity(1.0));
			application.addTupleMapping("srinfo-module", "SR_TASK", "SR_RESP", new FractionalSelectivity(1.0));
			
			application.addTupleMapping("master-module", "GAS_RESP", "GAS_PARAMS", new FractionalSelectivity(1.0));
			application.addTupleMapping("master-module", "CH_RESP", "CH_PARAMS", new FractionalSelectivity(1.0));
			application.addTupleMapping("master-module", "SR_RESP", "SR_PARAMS", new FractionalSelectivity(1.0));
			
			
			final AppLoop loop1 = new AppLoop(new ArrayList<String>(){
				{
					add("GAS");
					add("master-module");
					add("gasinfo-module");
					add("master-module");
					add("ACT_CONTROL");
				}
				
			});
					
			final AppLoop loop2 = new AppLoop(new ArrayList<String>(){
				{
					add("CH");
					add("master-module");
					add("chinfo-module");
					add("master-module");
					add("ACT_CONTROLCH");
				}
				
			});
			
			final AppLoop loop3 = new AppLoop(new ArrayList<String>(){
				{
					add("SR");
					add("master-module");
					add("srinfo-module");
					add("master-module");
					add("ACT_CONTROLSR");
				}
			});
			
					
			List<AppLoop> loops = new ArrayList<AppLoop>(){
				{
					add(loop1);
					add(loop2);
					add(loop3);
				}
			};
			application.setLoops(loops);
			
			return application;
	}
	
	private static FogDevice createFogDevice(String nodeName, long mips,
			int ram, long upBw, long downBw, int level, double ratePerMips, double busyPower, double idlePower) {
		
		List<Pe> peList = new ArrayList<Pe>();

		// 3. Create PEs and add these into a list.
		peList.add(new Pe(0, new PeProvisionerOverbooking(mips))); // need to store Pe id and MIPS Rating

		int hostId = FogUtils.generateEntityId();
		long storage = 1000000; // host storage
		int bw = 10000;

		PowerHost host = new PowerHost(
				hostId,
				new RamProvisionerSimple(ram),
				new BwProvisionerOverbooking(bw),
				storage,
				peList,
				new StreamOperatorScheduler(peList),
				new FogLinearPowerModel(busyPower, idlePower)
			);

		List<Host> hostList = new ArrayList<Host>();
		hostList.add(host);

		String arch = "x86"; // system architecture
		String os = "Linux"; // operating system
		String vmm = "Xen";
		double time_zone = 10.0; // time zone this resource located
		double cost = 3.0; // the cost of using processing in this resource
		double costPerMem = 0.05; // the cost of using memory in this resource
		double costPerStorage = 0.001; // the cost of using storage in this
										// resource
		double costPerBw = 0.0; // the cost of using bw in this resource
		LinkedList<Storage> storageList = new LinkedList<Storage>(); // we are not adding SAN
													// devices by now

		FogDeviceCharacteristics characteristics = new FogDeviceCharacteristics(
				arch, os, vmm, host, time_zone, cost, costPerMem,
				costPerStorage, costPerBw);

		FogDevice fogdevice = null;
		try {
			fogdevice = new FogDevice(nodeName, characteristics, 
					new AppModuleAllocationPolicy(hostList), storageList, 10, upBw, downBw, 0, ratePerMips);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		fogdevice.setLevel(level);
		return fogdevice;
	}
	
	public static void setFogDeviceAmount(int amount) { System.out.println("FOG"); numOfFogDevices = amount; }
	public static void setGasSensorAmount(int amount) { System.out.println("GAS"); numOfGasSensorsPerMasterNode = amount; }
	public static void setChSensorAmount(int amount) { System.out.println("CH"); numOfChSensorsPerMasterNode = amount; }
	public static void setSrSensorAmount(int amount) { System.out.println("SR"); numOfSrSensorsPerMasterNode = amount; }
	public static void setCloud(boolean flag) { System.out.println("CLOUD"); CLOUD = flag; }
	
	public static double getNetworkUsageOutput() { return networkUsageOutput; }
	public static double getCloudUsageOutput() { return cloudUsageOutput; }
	public static List<Double> getAppLoopDelayOutput() { return appLoopDelayOutput; }
	public static Map<String, Double> getEnergyConsumptionOutputMap() { return energyConsumptionOutputMap; }
	public static Map<Long, String> getCloudEnergyConsumptionOutputMap() { return cloudEnergyConsumptionOutputMap; }
	public static Map<String, Double> getTupleExecutionOutputMap() { return tupleExecutionOutputMap; }
	
}
