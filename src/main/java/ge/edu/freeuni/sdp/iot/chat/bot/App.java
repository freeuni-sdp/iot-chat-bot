package ge.edu.freeuni.sdp.iot.chat.bot;

import ge.edu.freeuni.sdp.iot.chat.bot.model.*;
import ge.edu.freeuni.sdp.iot.chat.bot.proxies.*;

import java.util.List;
import java.util.Scanner;

public class App {
	private static final String HOUSE_REGISTRY_URL = "http://iot-house-registry.herokuapp.com/houses";
	private static final String TEMPERATURE_SCHEDULER_URL = "http://private-anon-20273e8228-iottemperaturescheduler.apiary-mock.com/webapi/houses/";
	private static final String HEATING_SWITCH_URL = "https://iot-heating-switch.herokuapp.com/house/";
	private static final String AIR_CONDITIONING_SWITCH_URL = "http://private-d0abb-airconditioningswitch.apiary-mock.com/webapi/houses/";
	private static final String BATH_VENT_SWITCH_PROXY = "http://private-anon-1ca5b1d89-iotbathventswitch.apiary-mock.com/houses/";
	private static final String SPRINKLER_SWITCH_URL = "https://iot-sprinkler-switch.herokuapp.com/webapi/houses/";
	private static final String ROOM_THERMOMETER_SENSOR_URL = "https://iot-room-thermometer.herokuapp.com/webapi/houses/";
	private static final String BATH_HUMIDITY_SENSOR_URL = "https://iot-bath-humidity-sensor.herokuapp.com/webapi/houses/";
	private static final String SOIL_MOISTUER_SENSOR_URL = "http://private-anon-6aaf5a2d7-sdp2.apiary-mock.com/house/";
	private static final String BATH_LIGHT_SENSOR_URL = "https://iot-bath-light-sensor.herokuapp.com/webapi/status";
	private static final String ROUTER_URL = "http://iot-router.herokuapp.com/webapi/houses/";


	public static Scanner scanner;
	private static House house;
	private static HouseServiceProxy houseServiceProxy = new HouseServiceProxy(HOUSE_REGISTRY_URL);

	public static void main(String[] args) {

		scanner = new Scanner(System.in);


		while (true) {
			System.out.println("-------------------------------------------------------");
            int command = new OptionList<Integer>().title("Choose Action:")
                    .add("H", "Choose House", 2)
					.add("P", "Ping URLs", 1)
					.add("Q", "Quit the app", 0)
                    .read(scanner);

			switch (command) {
			case 1:
				printPings();
                break;
			case 2:
				chooseHouse();
				break;
			case 0:
				return;
            default:
                System.out.println("Please Enter Valid Action");
            }
		}
	}

	private static void printServerError() {
		System.out.println("Server error!");
	}

	private static void chooseHouse() {
		OptionList<Integer> options = new OptionList<Integer>();
		options.title("Choose House:");
		List<House> houses = houseServiceProxy.getAll();
		if (houses == null) {
			printServerError();
			return;
		}
		int houseIndex = printHouses(options, houses).read(scanner);
		house = houses.get(houseIndex);
		printOptions();
	}

	private static void printOptions() {
		while (true) {
			System.out.println("-------------------------------------------------------");
			int command = new OptionList<Integer>().title("Choose Action:")
					.add("1", "Sensors", 1)
					.add("2", "Switches", 2)
					.add("3", "Schedulers", 3)
					.add("B", "Go Back", 0)
					.read(scanner);

			switch (command) {
				case 1:
					sensorsOption();
					break;
				case 2:
					switchOption();
					break;
				case 3:
					schedulersOption();
				case 0:
					return;
				default:
					System.out.println("Please Enter Valid Action");
			}
		}
	}

	private static void schedulersOption() {
		while (true) {
			System.out.println("-------------------------------------------------------");
			int command = new OptionList<Integer>().title("Choose Action:")
					.add("1", "Temperature Scheduler", 1)
					.add("B", "Go Back", 0)
					.read(scanner);

			switch (command) {

				case 1:
					temperatureScheduler();
					break;
				case 0:
					return;
				default:
					System.out.println("Please Enter Valid Action");
			}
		}
	}

	private static void temperatureScheduler() {
		TemperatureScheduleProxy proxy = new TemperatureScheduleProxy
				(TEMPERATURE_SCHEDULER_URL + house.getID());
		while (true) {
			System.out.println("-------------------------------------------------------");
			int command = new OptionList<Integer>().title("Choose Action:")
					.add("1", "Get Scheduler", 1)
					.add("B", "Go Back", 0)
					.read(scanner);

			switch (command) {

				case 1:
					getTemperatureScheduler(proxy);
					break;
				case 0:
					return;
				default:
					System.out.println("Please Enter Valid Action");
			}
		}
	}

	private static void getTemperatureScheduler(TemperatureScheduleProxy proxy) {
		System.out.print("Enter Floor Number: ");
		Scanner in = new Scanner(System.in);
		int floor = in.nextInt();
		System.out.println("\nChecking Heating Switch Status...");
		List<TemperatureSchedule> schedules = proxy.getTemperatureSchedules(floor);
		if (schedules == null) {
			printServerError();
			return;
		}
		System.out.println();
		for (TemperatureSchedule schedule: schedules) {
			System.out.println(schedule);
		}
		System.out.println();
	}

	private static void switchOption() {
		while (true) {
			System.out.println("-------------------------------------------------------");
			int command = new OptionList<Integer>().title("Choose Action:")
					.add("1", "Sprinkler Switch", 1)
					.add("2", "Bath-vent Switch", 2)
					.add("3", "Air-conditioning Switch", 3)
					.add("4", "Heating Switch", 4)
					.add("B", "Go Back", 0)
					.read(scanner);

			switch (command) {

				case 1:
					sprinklerSwitch();
					break;
				case 2:
					bathVentSwitch();
					break;
				case 3:
					airConditioningSwitch();
					break;
				case 4:
					heatingSwitch();
					break;
				case 0:
					return;
				default:
					System.out.println("Please Enter Valid Action");
			}
		}
	}

	private static void heatingSwitch() {
		HeatingSwitchServiceProxy proxy = new HeatingSwitchServiceProxy
				(HEATING_SWITCH_URL + house.getID());
		while (true) {
			System.out.println("-------------------------------------------------------");
			int command = new OptionList<Integer>().title("Choose Action:")
					.add("1", "Get Switch Status", 1)
					.add("2", "Turn On Switch", 2)
					.add("3", "Turn Off Switch", 3)
					.add("B", "Go Back", 0)
					.read(scanner);

			switch (command) {
				case 1:
					getHeatingSwitchStatus(proxy);
					break;
				case 2:
					turnOnHeatingSwitch(proxy);
					break;
				case 3:
					turnOffHeatingSwitch(proxy);
					break;
				case 0:
					return;
				default:
					System.out.println("Please Enter Valid Action");
			}
		}
	}

	private static void getHeatingSwitchStatus(HeatingSwitchServiceProxy proxy) {
		System.out.print("Enter Floor Number: ");
		Scanner in = new Scanner(System.in);
		int floor = in.nextInt();
		System.out.println("\nChecking Heating Switch Status...");
		HeatingSwitch heatingSwitch = proxy.getSwitchStatusByFloor(String.valueOf(floor));
		if (heatingSwitch == null) {
			printServerError();
			return;
		}
		System.out.println("Floor: " + floor + ", " + heatingSwitch.toString());
	}

	private static void turnOnHeatingSwitch(HeatingSwitchServiceProxy proxy) {
		System.out.print("Enter Floor Number: ");
		Scanner in = new Scanner(System.in);
		int floor = in.nextInt();
		System.out.print("\nEnter Period: ");
		int period = in.nextInt();
		boolean bool = proxy.turnOnSwitch(String.valueOf(floor), period);
		if (bool) {
			System.out.println("Success");
		} else {
			System.out.println("Failed");
		}
	}

	private static void turnOffHeatingSwitch(HeatingSwitchServiceProxy proxy) {
		System.out.print("Enter Floor Number: ");
		Scanner in = new Scanner(System.in);
		int floor = in.nextInt();
		boolean bool = proxy.turnOffSwitch(String.valueOf(floor));
		if (bool) {
			System.out.println("Success");
		} else {
			System.out.println("Failed");
		}
	}

	private static void airConditioningSwitch() {
		AirConditioningSwitchProxy proxy = new AirConditioningSwitchProxy
				(AIR_CONDITIONING_SWITCH_URL + house.getID());
		while (true) {
			System.out.println("-------------------------------------------------------");
			int command = new OptionList<Integer>().title("Choose Action:")
					.add("1", "Check Air-Conditioning Status", 1)
					.add("2", "Change Air-Conditioning Status", 2)
					.add("B", "Go Back", 0)
					.read(scanner);

			switch (command) {
				case 1:
					checkAirConditioningStatus(proxy);
					break;
				case 2:
					changeAirConditioningStatus(proxy);
					break;
				case 0:
					return;
				default:
					System.out.println("Please Enter Valid Action");
			}
		}
	}

	private static void changeAirConditioningStatus(AirConditioningSwitchProxy proxy) {
		String status;

		while (true) {
			System.out.println("-------------------------------------------------------");
			System.out.print("Enter Status ( Off(off), Weak Cold(w), Strong Cold(s) or Ventilation(v) ): ");
			Scanner in = new Scanner(System.in);
			status = in.nextLine();
			if (status.toLowerCase().equals("w")) {
				System.out.print("Turning on Weak Cold... ");
				status = "*";
				break;
			}
			if (status.toLowerCase().equals("s")) {
				System.out.print("Turning on Strong Cold... ");
				status = "**";
				break;
			}
			if (status.toLowerCase().equals("v")) {
				System.out.print("Turning on Ventilation... ");
				status = "#";
				break;
			}

			if (status.toLowerCase().equals("off")) {
				System.out.print("Turning off... ");
				status = "0";
				break;
			}
			else {
				System.out.println("Please Enter Valid choice.");
			}
		}
		boolean airConditioningSwitch = proxy.changeAirConditioningStatus(status);
		String res;
		if (! airConditioningSwitch)
			res = "Failed!";
		else
			res = "Success!";
		System.out.print(res + "\n");
	}

	private static AirConditioningSwitch checkAirConditioningStatus(AirConditioningSwitchProxy proxy) {
		System.out.println("\nChecking Air Conditioning Switch Status...");
		AirConditioningSwitch airConditioningSwitch = proxy.getAirConditioningStatus();
		if (airConditioningSwitch == null) {
			printServerError();
			return null;
		}
		String status = airConditioningSwitch.getStatus();
		System.out.println("\n Air-ConditioningSwitch Switch Sta" +
				"tus: " + status + "\n");
		return airConditioningSwitch;
	}

	private static void bathVentSwitch(){
		BathVentSwitchProxy proxy = new BathVentSwitchProxy
				(BATH_VENT_SWITCH_PROXY + house.getID());
		while (true) {
			System.out.println("-------------------------------------------------------");
			int command = new OptionList<Integer>().title("Choose Action:")
					.add("1", "Check bath-vent Status", 1)
					.add("2", "Change bath-vent Status", 2)
					.add("B", "Go Back", 0)
					.read(scanner);

			switch (command) {
				case 1:
					checkBathVentStatus(proxy);
					break;
				case 2:
					changeBathVentStatus(proxy);
					break;
				case 0:
					return;
				default:
					System.out.println("Please Enter Valid Action");
			}
		}
	}

	private static void sprinklerSwitch() {
		SprinklerSwitchServiceProxy proxy = new SprinklerSwitchServiceProxy
				(SPRINKLER_SWITCH_URL + house.getID());
		while (true) {
			System.out.println("-------------------------------------------------------");
			int command = new OptionList<Integer>().title("Choose Action:")
					.add("1", "Check Status", 1)
					.add("2", "Change Status", 2)
					.add("B", "Go Back", 0)
					.read(scanner);

			switch (command) {
				case 1:
					checkSprinklerStatus(proxy);
					break;
				case 2:
					changeSprinklerStatus(proxy);
					break;
				case 0:
					return;
				default:
					System.out.println("Please Enter Valid Action");
			}
		}
	}

	private static BathVentSwitch checkBathVentStatus(BathVentSwitchProxy proxy) {
		System.out.println("\nChecking Bath Vent Switch Status...");
		BathVentSwitch bathVentSwitch = proxy.getBathVentStatus();
		if (bathVentSwitch == null) {
			printServerError();
			return null;
		}
		String status = bathVentSwitch.getStatus().substring(0, 1).toUpperCase() + bathVentSwitch.getStatus().substring(1);
		System.out.println("\n Bath-vent Switch Sta" +
				"tus: " + status + "\n");
		return bathVentSwitch;
	}

	private static void changeBathVentStatus(BathVentSwitchProxy proxy) {
		String status;
		int timeout = 0;

		while (true) {
			System.out.println("-------------------------------------------------------");
			System.out.print("Enter Status (On or Off): ");
			Scanner in = new Scanner(System.in);
			status = in.nextLine();
			if (status.toLowerCase().equals("on")) {
				System.out.print("Enter Number of Seconds for timeout: ");
				Scanner in1 = new Scanner(System.in);
				timeout = in1.nextInt();
				System.out.print("Turning on... ");
				break;
			}
			if (status.toLowerCase().equals("off")) {
				System.out.print("Turning off... ");
				break;
			}
			else {
				System.out.println("Please Enter Valid choice.");
			}
		}
		BathVentSwitch bathVentSwitch = proxy.changeBathVentStatus(status, timeout);
		String res;
		if (bathVentSwitch == null)
			res = "Failed!";
		else
			res = "Successful!";
		System.out.print(res + "\n");
	}

	private static void changeSprinklerStatus(SprinklerSwitchServiceProxy proxy) {
		SprinklerSwitch sprinklerSwitch = checkSprinklerStatus(proxy);
		if (sprinklerSwitch == null) {
			printServerError();
			return;
		}
		String status;
		int timeout;
		if (sprinklerSwitch.isOn()) {
			System.out.print("Turning off... ");
			status = "off";
			timeout = 0;
		} else {
			System.out.print("Enter Number of Seconds: ");
			Scanner in = new Scanner(System.in);
			timeout = in.nextInt();
			status = "on";
			System.out.print("Turning on... ");
		}
		SprinklerSwitch sprinklerSwitch1 = proxy.changeSprinklerStatus(status, timeout);
		String res;
		if (sprinklerSwitch1 == null)
			res = "Failed!";
		else
			res = "Successful!";
		System.out.print(res + "\n");
	}

	private static SprinklerSwitch checkSprinklerStatus(SprinklerSwitchServiceProxy proxy) {
		System.out.println("\nChecking Sprinkler Switch Status...");
		SprinklerSwitch sprinklerSwitch = proxy.getSprinklerStatus();
		if (sprinklerSwitch == null) {
			printServerError();
			return null;
		}
		String status = sprinklerSwitch.getStatus().substring(0, 1).toUpperCase() + sprinklerSwitch.getStatus().substring(1);
		System.out.println("\nSprinkler Switch Status: " + status + "\n");
		return sprinklerSwitch;
	}

	private static void sensorsOption() {
		while (true) {
			System.out.println("-------------------------------------------------------");
			int command = new OptionList<Integer>().title("Choose Action:")
					.add("1", "Bath Light Sensor", 1)
					.add("2", "Soil Moisture Sensor", 2)
					.add("3", "Bath Humidity Sensor", 3)
					.add("4", "Room Thermometer Sensor", 4)
					.add("5", "Router Info", 5)
					.add("B", "Go Back", 0)
					.read(scanner);

			switch (command) {
				case 1:
					bathLightSensor();
					break;
				case 2:
					soilMoistureSensor();
					break;
				case 3:
					bathHumiditySensor();
					break;
				case 4:
					roomThermometerSensor();
					break;
				case 5:
					routerInfo();
					break;
				case 0:
					return;
				default:
					System.out.println("Please Enter Valid Action");
			}
		}
	}

	private static void roomThermometerSensor() {
		RoomThermometerServiceProxy roomThermometerServiceProxy = new RoomThermometerServiceProxy
				(ROOM_THERMOMETER_SENSOR_URL + house.getID() + "/floors");
		while (true) {
			System.out.println("-------------------------------------------------------");
			int command = new OptionList<Integer>().title("Choose Action:")
					.add("1", "On All Floors", 1)
					.add("2", "Choose Floor", 2)
					.add("B", "Go Back", 0)
					.read(scanner);

			switch (command) {
				case 1:
					listAllThermometers(roomThermometerServiceProxy);
					break;
				case 2:
					chooseFloorForThermometer(roomThermometerServiceProxy);
					break;
				case 0:
					return;
				default:
					System.out.println("Please Enter Valid Action");
			}
		}
	}

	private static void chooseFloorForThermometer(RoomThermometerServiceProxy roomThermometerServiceProxy) {
		System.out.print("\nChoose Floor: ");
		Scanner in = new Scanner(System.in);
		int num = in.nextInt();
		System.out.println("\nChecking Thermometer Sensor:\n");
		RoomThermometer thermometer = roomThermometerServiceProxy.getFromFloor(num);
		if (thermometer == null) {
			printServerError();
			return;
		}
		System.out.println("\n" + thermometer + "\n");
	}

	private static void listAllThermometers(RoomThermometerServiceProxy roomThermometerServiceProxy) {
		System.out.println("\nListing All Thermometers:\n");
		List<RoomThermometer> roomThermometers = roomThermometerServiceProxy.getAll();
		if (roomThermometers == null) {
			printServerError();
			return;
		}
		for (RoomThermometer thermometer: roomThermometers) {
			System.out.println(thermometer);
		}
		System.out.println();
	}

	private static void bathHumiditySensor() {
		BathHumidityServiceProxy proxy = new BathHumidityServiceProxy
				(BATH_HUMIDITY_SENSOR_URL + house.getID());
		System.out.print("\nHow Many Measurements? ");
		Scanner in = new Scanner(System.in);
		int num = in.nextInt();
		System.out.println("\nChecking Bath Humidity Sensor...");
		List<BathHumidity> humidities = proxy.getBathHumidities(num);
		if (humidities == null) {
			printServerError();
			return;
		}
		System.out.println();
		for (BathHumidity humid: humidities)
			System.out.println(humid);
		System.out.println();
	}

	private static void soilMoistureSensor() {
		SoilMoistureServiceProxy proxy = new SoilMoistureServiceProxy
				(SOIL_MOISTUER_SENSOR_URL + house.getID());
		System.out.println("\nChecking Soil Moisture Sensor...");
		SoilMoisture soilMoisture = proxy.getSoilMoisture();
		if (soilMoisture == null) {
			printServerError();
			return;
		}
		System.out.println("\n" + soilMoisture + "\n");
	}

	private static void bathLightSensor() {
		BathLightServiceProxy proxy = new BathLightServiceProxy
				(BATH_LIGHT_SENSOR_URL);
		System.out.println("\nChecking Bath Light Sensor...");
		BathLight bathLight = proxy.getBathLight(house.getID());
		if (bathLight == null) {
			printServerError();
			return;
		}
		System.out.println("\n" + bathLight + "\n");
	}

	private static void routerInfo() {
		RouterServiceProxy routerServiceProxy = new RouterServiceProxy
				(ROUTER_URL + house.getID());
		while (true) {
			System.out.println("-------------------------------------------------------");
			int command = new OptionList<Integer>().title("Choose Action:")
					.add("1", "List All Addresses", 1)
					.add("2", "Is Anyone At Home?", 2)
					.add("B", "Go Back", 0)
					.read(scanner);

			switch (command) {
				case 1:
					listAllAddresses(routerServiceProxy);
					break;
				case 2:
					isAnyoneAtHome(routerServiceProxy);
					break;
				case 0:
					return;
				default:
					System.out.println("Please Enter Valid Action");
			}
		}
	}

	private static void isAnyoneAtHome(RouterServiceProxy proxy) {
		String str = proxy.isAnyoneAtHome() ? "Yes" : "No";
		System.out.println("\n" + str + "\n");
	}

	private static void listAllAddresses(RouterServiceProxy proxy) {
		System.out.println("\nListing mac addresses that are connected to the router:\n");
		List<Router> routers = proxy.getAll();
		if (routers == null) {
			printServerError();
			return;
		}
		for (Router router: routers) {
			System.out.println(router);
		}
		System.out.println();
	}

	private static OptionList<Integer> printHouses(OptionList<Integer> output, List<House> houses) {
		for (int i = 0; i < houses.size(); i++) {
			output.add(String.valueOf(i+1), houses.get(i).getName(), i);
		}
		return output;
	}

	private static void printPings() {
		System.out.println("-----------------Pings--------------------------");
		PingURLs p = new PingURLs();
        p.printPings();
		System.out.print("Enter new action:");
	}

}
