package ge.edu.freeuni.sdp.iot.chat.bot;

import ge.edu.freeuni.sdp.iot.chat.bot.model.House;
import ge.edu.freeuni.sdp.iot.chat.bot.model.Router;
import ge.edu.freeuni.sdp.iot.chat.bot.proxies.BathLightServiceProxy;
import ge.edu.freeuni.sdp.iot.chat.bot.proxies.HouseServiceProxy;
import ge.edu.freeuni.sdp.iot.chat.bot.proxies.RouterServiceProxy;
import ge.edu.freeuni.sdp.iot.chat.bot.proxies.SoilMoistureServiceProxy;

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
		getRequests();
	}

	private static void getRequests() {
		while (true) {
			System.out.println("-------------------------------------------------------");
			int command = new OptionList<Integer>().title("Choose Action:")
					.add("1", "Bath Light Sensor", 1)
					.add("2", "Soil Moisture Sensor", 2)
					.add("3", "Router Info", 3)
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
					routerInfo();
					break;
				case 0:
					return;
				default:
					System.out.println("Please Enter Valid Action");
			}
		}
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
