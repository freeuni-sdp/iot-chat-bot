package ge.edu.freeuni.sdp.iot.chat.bot;

import java.util.Scanner;

public class App {

	public static Scanner scanner;

	public static void main(String[] args) {

		scanner = new Scanner(System.in);


		while (true) {
			System.out.println("-------------------------------------------------------");
            int command = new OptionList<Integer>().title("Choose Action:")
                    .add("P", "Ping URLs", 1).add("Q", "Quit the app", 0)
                    .read(scanner);

			switch (command) {
			case 1:
				printPings();
                break;
			case 0:
				return;
            default:
                System.out.println("Please Enter Valid Action");
            }
		}
	}


	private static void printPings() {
		System.out.println("-----------------Pings--------------------------");
		PingURLs p = new PingURLs();
        p.printPings();
		System.out.print("Enter new action:");
	}

}
