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
					.add("B", "Go Back", 0)
					.read(scanner);

			switch (command) {
				case 1:
					sprinklerSwitch();
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
