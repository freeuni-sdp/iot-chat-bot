package ge.edu.freeuni.sdp.iot.chat.bot;

import ge.edu.freeuni.sdp.iot.chat.bot.model.House;
import ge.edu.freeuni.sdp.iot.chat.bot.proxies.HouseServiceProxy;

import java.util.List;
import java.util.Scanner;

public class App {

	public static Scanner scanner;
	private static House house;
	private static HouseServiceProxy proxy = new HouseServiceProxy("http://iot-house-registry.herokuapp.com/houses");


	public static void main(String[] args) {

		scanner = new Scanner(System.in);


		while (true) {
			System.out.println("-------------------------------------------------------");
            int command = new OptionList<Integer>().title("Choose Action:")
                    .add("P", "Ping URLs", 1).add("Q", "Quit the app", 0)
					.add("H", "Choose House", 2)
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
		List<House> houses = proxy.getAll();
		int houseIndex = printHouses(options, houses).read(scanner);
		house = houses.get(houseIndex);
	}

	private static OptionList<Integer> printHouses(OptionList<Integer> output, List<House> houses) {
		for (int i = 0; i < houses.size(); i++) {
			output.add(String.valueOf(i), houses.get(i).getName(), i);
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
