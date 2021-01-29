import java.util.Scanner;

public class XO
{
	public static void main(String[] args)
	{
		Scanner userInput = new Scanner(System.in);
		boolean running = true, runningSP = true;
		System.out.println("Welcome to XO!");
		while (running)
		{	
			System.out.print("Enter S for Singleplayer and M for Multiplayer mode: ");
			String input = userInput.nextLine();
			if (input.equalsIgnoreCase("S"))
			{	
				runningSP = true;
				while (runningSP)
				{
					System.out.print("You wanna be first or second player? Enter 1 or 2: ");
					if (userInput.hasNextInt())
					{
						int inputNum = Integer.parseInt(userInput.nextLine());
						if (inputNum == 1 || inputNum == 2) 
						{
							SinglePlayer game1 = new SinglePlayer(inputNum);
							System.out.print("Do you want to play again(Y/N)? ");
							if (userInput.nextLine().equalsIgnoreCase("N"))
							{
								running = false;
								runningSP = false;
							}	
							else
							{
								runningSP = false;
								System.out.println("Alright! Get ready for the next game!");
							}	
						}
						else
						{
							System.out.print("Invalid input! ");
						}
					}
					else
					{
						System.out.print("Invalid input! ");
						userInput.nextLine();
					}

				}
			}
			else if (input.equalsIgnoreCase("M"))
			{	
				MultiPlayer game = new MultiPlayer();
				System.out.print("Do you want to play again(Y/N)? ");
				if (userInput.nextLine().equalsIgnoreCase("N"))
					running = false;
				else
					System.out.println("Alright! Get ready for the next game!");
			}
			else
			{	
				System.out.print("Invalid input! ");
			}
		}
	}
}