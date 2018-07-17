# R6J
A JVM based Rainbow Six Siege API Wrapper

Version 1.0

## Adding R6J to your project
TODO: Add the project to the maven central repo

## Using the API
To begin, you must grab an instance of the R6J class, from there, you can query for players and grab their stats

```Java
public class Example {

    public static void main(String[] args){
        R6J api = new R6J(new Auth("example@example.com", "password")); //Creates a new instance of the api with the given ubisoft credentials

        R6Player steve = api.getPlayer("steve", Platform.UPLAY); //Grabs a player instance of steve on UPLAY

        steve.loadGeneralStats(); //You must load in which main.stats you want to read before attempting to read them

        System.out.println("Steve's Total Kills: " + steve.getKills()); //Prints out how many kills steve has

    }
}
```

## Development
R6J is still very much under development, if you have any issues with the code or want to suggest something, submit a pull request and I'll see it

## License
R6J is released under the Unlicense, which means anyone can do anything they want with it, have at it!
