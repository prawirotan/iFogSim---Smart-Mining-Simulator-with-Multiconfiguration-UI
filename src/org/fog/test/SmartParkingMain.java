package org.fog.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

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
import org.fog.utils.FogLinearPowerModel;
import org.fog.utils.FogUtils;
import org.fog.utils.TimeKeeper;
import org.fog.utils.distribution.DeterministicDistribution;

public class SmartParkingMain {

	//Here we are creating a list for fog devices.
	static ArrayList<FogDevice> fogDevices = new ArrayList<FogDevice>();
	static ArrayList<Sensor> sensors = new ArrayList<Sensor>();
	static ArrayList<Actuator> actuators = new ArrayList<Actuator>();
	static int numOfAreas = 7; //the number of fog nodes
	static int numOfCamerasPerArea1=10;
	// the number of cameras per fog node.
	static double CAM_TRANSMISSION_TIME = 5; //time interval
	private static boolean CLOUD = false;
	
	public static void main(String[] args) {

		Log.printLine("Starting DCNS...");

		try {
			Log.disable();
			int num_user = 1; // number of cloud users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false; // mean trace events

			CloudSim.init(num_user, calendar, trace_flag);

			String appId = "dcns"; // identifier of the application
			
			FogBroker broker = new FogBroker("broker");
			
			ApplicationFog application = createApplication(appId, broker.getId());
			application.setUserId(broker.getId());
			
			createFogDevices(broker.getId(), appId);
			
			Controller controller = null;
			
			ModuleMapping moduleMapping = ModuleMapping.createModuleMapping(); // initializing a module mapping
			for(FogDevice device : fogDevices){
				if(device.getName().startsWith("p")){ // names of all Smart Cameras start with 'm' 
					moduleMapping.addModuleToDevice("picture-capture", device.getName());  // fixing 1 instance of the Motion Detector module to each Smart Camera
				}
				if(device.getName().startsWith("s")){ // names of all Smart Cameras start with 'm' 
					moduleMapping.addModuleToDevice("slot-detector", device.getName());  // fixing 1 instance of the Motion Detector module to each Smart Camera
				}
			}
			
			if(CLOUD){
				// if the mode of deployment is cloud-based
				moduleMapping.addModuleToDevice("picture-capture", "cloud"); // placing all instances of Object Detector module in the Cloud
				moduleMapping.addModuleToDevice("slot-detector", "cloud"); // placing all instances of Object Tracker module in the Cloud
			}
			
			controller = new Controller("master-controller", fogDevices, sensors, 
					actuators);
			
			controller.submitApplication(application, 
					(CLOUD)?(new ModulePlacementMapping(fogDevices, application, moduleMapping))
							:(new ModulePlacementEdgewards(fogDevices, sensors, actuators, application, moduleMapping)));
			
			TimeKeeper.getInstance().setSimulationStartTime(Calendar.getInstance().getTimeInMillis());
			
			CloudSim.startSimulation();

			CloudSim.stopSimulation();

			Log.printLine("VRGame finished!");
		} catch (Exception e) {
			e.printStackTrace();
			Log.printLine("Unwanted errors happen");
		}
	}
	private static void createFogDevices(int userId, String appId) {
		
		FogDevice cloud = createFogDevice("cloud", 44800, 40000,
		100, 10000, 0, 0.01, 16*103, 16*83.25);
		cloud.setParentId(-1);
		fogDevices.add(cloud);
		FogDevice proxy = createFogDevice("proxy-server", 2800, 4000,
		10000, 10000, 1, 0.0, 107.339, 83.4333);
		proxy.setParentId(cloud.getId());
		double costPerStorage;
		proxy.setUplinkLatency(100);
		fogDevices.add(proxy);
		
		for(int i=0;i<numOfAreas;i++){
			addArea(i+"", userId, appId, proxy.getId());
		}
	}
	
	private static FogDevice addArea(String id, int userId, String appId, int parentId) {
		
		FogDevice router = createFogDevice("a-"+id, 2800, 4000,
		1000, 10000, 2, 0.0, 107.339,83.4333);
		
		fogDevices.add(router);
		
		router.setUplinkLatency(2);
		for(int i=0;i<numOfCamerasPerArea1;i++) {
			String mobileId = id+"-"+i;
			FogDevice camera = addCamera(mobileId, userId,
			appId, router.getId());
			camera.setUplinkLatency(2);
			fogDevices.add(camera);
		}
		router.setParentId(parentId);
		return router;
		
	}
	
	
	private static FogDevice addCamera(String id, int userId,
	String appId, int parentId){
	FogDevice camera = createFogDevice("c-"+id, 500, 1000, 10000,
	10000, 3, 0, 87.53, 82.44);
	camera.setParentId(parentId);
	Sensor sensor = new Sensor("s-"+id, "CAMERA", userId, appId, new
	DeterministicDistribution(CAM_TRANSMISSION_TIME));
	sensors.add(sensor);
	Actuator ptz = new Actuator("ptz-"+id, userId,
	appId, "PTZ_CONTROL");
	actuators.add(ptz);
	sensor.setGatewayDeviceId(camera.getId());
	sensor.setLatency(40.0);
	ptz.setGatewayDeviceId(parentId);
	ptz.setLatency(1.0);
	return camera;
	}
	
	private static ApplicationFog createApplication (String appId, int userId) {
		ApplicationFog application =
		ApplicationFog.createApplication(appId, userId);
		application.addAppModule("picture-capture", 10);
		application.addAppModule("slot-detector", 10);
		// adding edge from CAMERA (sensor) to picture-capture module carrying tuples of type CAMERA
		application.addAppEdge("CAMERA", "picture-capture", 1000, 500,
		"CAMERA", Tuple.UP, AppEdge.SENSOR);
		application.addAppEdge("picture-capture", "slot-detector",
				1000, 500, "slots",Tuple.UP, AppEdge.MODULE);
				// adding edge from Slot Detector to PTZ CONTROL (actuator)
				application.addAppEdge("slot-detector", "PTZ_CONTROL", 100,
				28, 100, "PTZ_PARAMS",
				Tuple.UP, AppEdge.ACTUATOR);
				application.addTupleMapping("picture-capture", "CAMERA", "slots",
				new FractionalSelectivity(1.0));
				application.addTupleMapping("slot-detector", "slots",
				"PTZ_PARAMS", new FractionalSelectivity(1.0));
				final AppLoop loop1 = new AppLoop(new ArrayList<String>()
				{{add("CAMERA");
				add("picture-capture");add("slot-detector");
				add("PTZ_CONTROL");}});
				ArrayList<AppLoop> loops = new ArrayList<AppLoop>(){{add(loop1);}};
				application.setLoops(loops);
				return application;
	}
	
	private static FogDevice createFogDevice(String nodeName, long mips,
			int ram, long upBw, long downBw, int level, double ratePerMips, double busyPower, double idlePower) {
		
		ArrayList<Pe> peList = new ArrayList<Pe>();

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

		ArrayList<Host> hostList = new ArrayList<Host>();
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
	
}
