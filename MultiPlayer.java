import java.util.Scanner;


public class MultiPlayer
{	
	private char[][] gameTable = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};

	public MultiPlayer ()
	{
		start();
	}
	private void start()
	{
		int move = 0;
		while ( getWinner() == 'N' )
		{	
			move++;
			if (move > 9)
				break;
			clearScreen();
			preview();
			System.out.println("It's player " + ((move + 1)%2 + 1) + "'s turn!");
			getNewMove((move%2 == 1)?'X':'O');
		}
		clearScreen();
		preview();
		if (move <= 9)
			System.out.println("Player " + getWinner() + " has won the game!");
		else
			System.out.println("It's a tie!");
	}

	private void preview()
	{
		System.out.println("\n       1       2       3");
		System.out.println("   *************************");
		System.out.println("   *       *       *       *");
		System.out.println(" A *   " + gameTable[0][0] + "   *   " + gameTable[0][1] + "   *   " + gameTable[0][2] + "   *");
		System.out.println("   *       *       *       *");
		System.out.println("   *************************");
		System.out.println("   *       *       *       *");
		System.out.println(" B *   " + gameTable[1][0] + "   *   " + gameTable[1][1] + "   *   " + gameTable[1][2] + "   *");
		System.out.println("   *       *       *       *");
		System.out.println("   *************************");
		System.out.println("   *       *       *       *");
		System.out.println(" C *   " + gameTable[2][0] + "   *   " + gameTable[2][1] + "   *   " + gameTable[2][2] + "   *");
		System.out.println("   *       *       *       *");
		System.out.println("   *************************\n");
	}

	private void getNewMove(char letter) //get new move and uptade the table using the letter
	{
		Scanner userInput = new Scanner(System.in);
		System.out.print("Enter the row letter followed by column number (ex. B1, A3): ");
		String move = userInput.nextLine();
		boolean inputValid = false;
		while (!inputValid)
		{
			//input check
			while ( (move.length() != 2) || 
					!( move.substring(0,1).equalsIgnoreCase("A") || move.substring(0,1).equalsIgnoreCase("B") || move.substring(0,1).equalsIgnoreCase("C")) ||
					!( move.substring(1,2).equals("1") || move.substring(1,2).equals("2") || move.substring(1,2).equals("3")) )
			{
				System.out.println("Wrong input format.");
				System.out.print("Enter the row letter followed by column number (ex. B1, A3): ");
				move = userInput.nextLine();
			}
			//determine row and column
			int column = Integer.parseInt(move.substring(1,2)) - 1,
			row = switch (move.charAt(0))
				{	
					case 'a', 'A'-> 0;
					case 'b', 'B'-> 1;
					default -> 2;
				};
			if (gameTable[row][column] == ' ')
			{
				gameTable[row][column] = letter;
				inputValid = true;
			}
			else
			{
				System.out.println("The cell you have chosen is already taken!");
				System.out.print("Enter the row letter followed by column number (ex. B1, A3): ");
				move = userInput.nextLine();
			}
		}
	}
	private void clearScreen() 
	{  
    System.out.print("\033[H\033[2J");  
    System.out.flush();  
   	}

   	private char getWinner()
	{
		for (int i=0; i<3; i++)
		{	
			//check rows
			if ( (gameTable[i][0] == gameTable[i][1]) && (gameTable[i][0] == gameTable[i][2]) )
			{
				if (gameTable[i][0] == 'X')
					return '1';
				else if (gameTable[i][0] == 'O')
					return '2';			
			}
			//check columns
			if ( (gameTable[0][i] == gameTable[1][i]) && (gameTable[1][i] == gameTable[2][i]) )
			{
				if (gameTable[0][i] == 'X')
					return '1';
				else if (gameTable[0][i] == 'O')
					return '2';				
			}
		}
		//check / and \
		if ( ((gameTable[0][0] == gameTable[1][1]) && (gameTable[1][1] == gameTable[2][2])) ||
				((gameTable[0][2] == gameTable[1][1]) && (gameTable[1][1] == gameTable[2][0])) )
		{
			if (gameTable[1][1] == 'X')
				return '1';
			else if (gameTable[1][1] == 'O')
				return '2';
		}
		//if no winner
		return 'N';
	}
}