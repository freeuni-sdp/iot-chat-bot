package ge.edu.freeuni.sdp.iot.chat.bot;

import ge.edu.freeuni.sdp.iot.chat.bot.model.*;
import ge.edu.freeuni.sdp.iot.chat.bot.proxies.*;

import java.util.List;
import java.util.Scanner;

public class App {

	public static Scanner scanner;
	private static House house;
	private static HouseServiceProxy houseServiceProxy = new HouseServiceProxy("http://iot-house-registry.herokuapp.com/houses");


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

	private static void chooseHouse() {
		OptionList<Integer> options = new OptionList<Integer>();
		options.title("Choose House:");
		List<House> houses = houseServiceProxy.getAll();
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
					.add("B", "Go Back", 0)
					.read(scanner);

			switch (command) {
				case 1:
					sensorsOption();
					break;
				case 2:
					switchOption();
					break;
				case 0:
					return;
				default:
					System.out.println("Please Enter Valid Action");
			}
		}
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
				("https://iot-heating-switch.herokuapp.com/house/" + house.getID());
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
		HeatingSwitch heatingSwitch = proxy.getSwitchStatusByFloor(String.valueOf(floor));
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
				("http://private-d0abb-airconditioningswitch.apiary-mock.com/webapi/houses/" + house.getID());
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
			res = "Successful!";
		System.out.print(res + "\n");
	}

	private static AirConditioningSwitch checkAirConditioningStatus(AirConditioningSwitchProxy proxy) {
		AirConditioningSwitch airConditioningSwitch = proxy.getAirConditioningStatus();
		String status = airConditioningSwitch.getStatus();
		System.out.println("\n Air-ConditioningSwitch Switch Sta" +
				"tus: " + status + "\n");
		return airConditioningSwitch;
	}

	private static void bathVentSwitch(){
		BathVentSwitchProxy proxy = new BathVentSwitchProxy
				("http://private-anon-1ca5b1d89-iotbathventswitch.apiary-mock.com/houses/" + house.getID());
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
				("https://iot-sprinkler-switch.herokuapp.com/webapi/houses/" + house.getID());
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
		BathVentSwitch bathVentSwitch = proxy.getBathVentStatus();
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
		SprinklerSwitch sprinklerSwitch = proxy.getSprinklerStatus();
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
				("https://iot-room-thermometer.herokuapp.com/webapi/houses/" + house.getID() + "/floors");
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
		RoomThermometer thermometer = roomThermometerServiceProxy.getFromFloor(num);
		System.out.println("\n" + thermometer + "\n");
	}

	private static void listAllThermometers(RoomThermometerServiceProxy roomThermometerServiceProxy) {
		List<RoomThermometer> roomThermometers = roomThermometerServiceProxy.getAll();
		System.out.println("Listing All Thermometers:\n");
		for (RoomThermometer thermometer: roomThermometers) {
			System.out.println(thermometer);
		}
		System.out.println();
	}

	private static void bathHumiditySensor() {
		BathHumidityServiceProxy proxy = new BathHumidityServiceProxy
				("https://iot-bath-humidity-sensor.herokuapp.com/webapi/houses/" + house.getID());
		System.out.print("\nHow Many Measurements? ");
		Scanner in = new Scanner(System.in);
		int num = in.nextInt();
		List<BathHumidity> humidities = proxy.getBathHumidities(num);
		System.out.println();
		for (BathHumidity humid: humidities)
			System.out.println(humid);
		System.out.println();
	}

	private static void soilMoistureSensor() {
		SoilMoistureServiceProxy proxy = new SoilMoistureServiceProxy
				("http://private-anon-6aaf5a2d7-sdp2.apiary-mock.com/house/" + house.getID());
		System.out.println("\n" + proxy.getSoilMoisture() + "\n");
	}

	private static void bathLightSensor() {
		BathLightServiceProxy proxy = new BathLightServiceProxy
				("https://iot-bath-light-sensor.herokuapp.com/webapi/status");
		System.out.println("\n" + proxy.getBathLight(house.getID()) + "\n");
	}

	private static void routerInfo() {
		RouterServiceProxy routerServiceProxy = new RouterServiceProxy
				("http://iot-router.herokuapp.com/webapi/houses/" + house.getID());
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
		List<Router> routers = proxy.getAll();
		System.out.println("Listing mac addresses that are connected to the router:\n");
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
