import java.util.Scanner;


public class SinglePlayer
{	
	private char[][] gameTable = {{' ',' ',' '},{' ',' ',' '},{' ',' ',' '}};
	private int turn = 0;

	public SinglePlayer (int turn)
	{
		this.turn = turn;
		start();
	}
	private void start()
	{	
		int move = 0;
		while ( getResult(gameTable) == 4) //game's on going
		{	
			move++;
			if ( (move + 1)%2 + 1 == turn)
			{
				clearScreen(); //comment to see prob density
				preview();
				getNewMove((turn == 1)?'X':'O');
			}
			else
			{
				xoai((turn == 2)?'X':'O');
			}
		}
		clearScreen(); //comment to see prob density
		preview();
		if (getResult(gameTable) == 3)
		{
			System.out.println("It's a tie!");
		} 
		else if (getResult(gameTable) == turn)
		{
			System.out.println("WoW! You won the game!");
		}
		else
		{
			System.out.println("Oops! You lost the game!");
		}
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

   	private int getResult(char[][] gameTable) // 1: player 1 won 2: player 2 won 3: tie 4: ongoing
	{
		for (int i=0; i<3; i++)
		{	
			//check rows
			if ( (gameTable[i][0] == gameTable[i][1]) && (gameTable[i][0] == gameTable[i][2]) )
			{
				if (gameTable[i][0] == 'X')
					return 1;
				else if (gameTable[i][0] == 'O')
					return 2;			
			}
			//check columns
			if ( (gameTable[0][i] == gameTable[1][i]) && (gameTable[1][i] == gameTable[2][i]) )
			{
				if (gameTable[0][i] == 'X')
					return 1;
				else if (gameTable[0][i] == 'O')
					return 2;				
			}
		}
		//check / and \
		if ( ((gameTable[0][0] == gameTable[1][1]) && (gameTable[1][1] == gameTable[2][2])) ||
				((gameTable[0][2] == gameTable[1][1]) && (gameTable[1][1] == gameTable[2][0])) )
		{
			if (gameTable[1][1] == 'X')
				return 1;
			else if (gameTable[1][1] == 'O')
				return 2;
		}
		//if no winner
		for (int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
			{
				if (gameTable[i][j] == ' ' )
				{
					return 4;
				}
			}
		}
		//if no winner and nothing is empty
		return 3;
	}
	private void xoai(char letter)
	{
		double highestProbability = 0.0;
		int row = 0, column = 0;

		for (int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
			{
				if (gameTable[i][j] == ' ' )
				{
					//check for obvious move
					gameTable[i][j] = letter;
					int result = getResult(gameTable);
					gameTable[i][j] = ' ';

					if (result != 4) //won or forced tied
					{
						row = i;
						column = j;
						highestProbability = 2.0; //impossible to achieve
						//System.out.println(i + "  " + j + "  " + highestProbability); //uncomment to see prob density
					}
					else //no obvious moves
					{
						double probability = probability(gameTable,i,j,letter);
						if (probability > highestProbability) 
						{
							row = i;
							column = j;
							highestProbability = probability;
						}
						//System.out.println(i + "  " + j + "  " + probability); //uncomment to see prob density
					}
				}
			}
		}
		gameTable[row][column] = letter;
	}

	private double probability (char[][] table, int row, int column, char letter)
	{
		//copy the table
		char[][] newTable = {{' ', ' ', ' '},{' ', ' ', ' '},{' ', ' ', ' '}};
		for (int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
			{
				newTable[i][j]=table[i][j];
			}
		}
		newTable[row][column] = letter;
		char nextLetter = ((letter == 'X')?'O':'X');

		if (getResultNextMove(newTable, nextLetter) == 4) //on going
		{
			int remainingCellNum = 0;
			double probability = 0.0;
			for (int i=0; i<3; i++)
			{
				for(int j=0; j<3; j++)
				{
					if (newTable[i][j] == ' ')
					{
						probability += probability(newTable,i,j,nextLetter);
						remainingCellNum++;
					}
				}
			}

			if (remainingCellNum == 0)
				remainingCellNum++;
			return (probability/remainingCellNum);

		} 
		else if (getResultNextMove(newTable, nextLetter) == 3) //tie
		{
			return 0.5;
		}
		else if (getResultNextMove(newTable, nextLetter) == turn) //lost
		{
			return 0;
		}
		else //won
		{
			return 1;
		}



	}

	private int getResultNextMove (char[][] gameTable, char letter)
	{
		int result = 0;
		for (int i=0; i<3; i++)
		{
			for(int j=0; j<3; j++)
			{
				if (gameTable[i][j] == ' ')
				{
					gameTable[i][j] = letter;
					result = getResult(gameTable);
					gameTable[i][j] = ' ';
					if (result != 4) //won lost or tied
						return result;
				}
			}
		}
		//nothing has be returned so no next move ends the game
		return 4;
	}








}