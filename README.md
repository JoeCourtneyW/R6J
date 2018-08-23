# R6J
A JVM based Rainbow Six Siege API Wrapper

Version 1.0.3

## Adding R6J to your project
```
<dependency>
  <groupId>com.github.joecourtneyw</groupId>
  <artifactId>R6J</artifactId>
  <version>1.0.3</version>
</dependency>
```
## Using the API
To begin, you must grab an instance of the R6J class, from there, you can query for players and grab all of their available stats

```Java
public class Example {

	    public static void main(String[] args){
	        R6J api = new R6J(new Auth("example@example.com", "password")); //Creates a new instance of the api with the given ubisoft credentials

	        if(api.playerExists("Kantoraketti.G2", Platform.UPLAY)) { //Checks if the player name exists on UPLAY
	        	  R6Player kanto = api.getPlayer("Kantoraketti.G2", Platform.UPLAY); //Grabs a player instance of Kantoraketti.G2 on UPLAY

	        	  System.out.println("Kantoraketti.G2's Total Kills: " + kanto.getKills()); //Prints out how many kills Kanto has
	        } else {
	        	  System.out.println("Player does not exist!"); //The user mentioned doesn't exist
	        }
	    }
}
```

## Development
R6J is still under development, if you have any issues or want to suggest something, submit a pull request

## License
R6J is released under the Unlicense, which means anyone can do anything they want with it, have at it!
