package comp3350.grs.persistence;

import java.util.ArrayList;
import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.exceptions.IncorrectFormat;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.Guest;
import comp3350.grs.objects.Rating;
import comp3350.grs.objects.Request;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.Review;
import comp3350.grs.objects.User;
import comp3350.grs.presentation.MainActivity;

//parent of sql database and stub database, mainly used to load default data
public abstract class DataAccess {
    protected String dbName;
    protected String dbType;
    protected List<User> users;//list of all users
    protected List<Game> games;//list of all games
    protected List<Rating> ratings;//list of all ratings
    protected List<Review> reviews;//list of all reviews
    protected List<Request> requests; //list of all game requests

    public DataAccess(String dbName){
        this.dbName=dbName;
    }

    public DataAccess(){
        this.dbName= Main.dbName;
    }

    //load database with default value
    public void open(String dbPath) {
        games=new ArrayList<>();
        ratings = new ArrayList<>();
        reviews = new ArrayList<>();
        List<String> genres;
        Game newGame=null;
        String gameName,gameDev,desc;
        double price;


        genres=new ArrayList<>();
        genres.add("Action");
        genres.add("Adventure");
        genres.add("Warhammer 40K");
        genres.add("Shooter");
        genres.add("First-Person");
        genres.add("Action-Adventure");
        genres.add("Violent");
        genres.add("Gore");
        genres.add("FPS");
        genres.add("Singleplayer");
        genres.add("Combat");
        genres.add("Games Workshop");
        genres.add("Dog");

        gameName="Necromunda: Hired Gun";
        gameDev="Streum On Studio";
        desc="Become a Hired Gun. The money�s good, the dog�s loyal, and the gun�s reliable. Embark on a fast-paced, violent, and thrilling FPS set in the darkest reaches of Warhammer 40,000�s most infamous hive city.";
        price=125.8;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Co-op");
        genres.add("Multiplayer");
        genres.add("Action");
        genres.add("Open World");
        genres.add("RPG");
        genres.add("Third Person");
        genres.add("Character Customization");
        genres.add("Adventure");
        genres.add("Fantasy");
        genres.add("Difficult");
        genres.add("Singleplayer");
        genres.add("Action RPG");
        genres.add("Exploration");
        genres.add("Great Soundtrack");
        genres.add("Replay Value");
        genres.add("Atmospheric");
        genres.add("Hack and Slash");
        genres.add("JRPG");
        genres.add("Souls-like");
        genres.add("MMORPG");

        gameName="Monster Hunter: World";
        gameDev="CAPCOM Co., Ltd.";
        desc="Welcome to a new world! In Monster Hunter: World, the latest installment in the series, you can enjoy the ultimate hunting experience, using everything at your disposal to hunt monsters in a new world teeming with surprises and excitement.";
        price=133.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Flight");
        genres.add("Space");
        genres.add("Multiplayer");
        genres.add("VR");
        genres.add("Sci-fi");
        genres.add("Singleplayer");
        genres.add("Action");
        genres.add("Simulation");
        genres.add("First-Person");
        genres.add("PvP");
        genres.add("Online Co-Op");
        genres.add("Spaceships");
        genres.add("Co-op");
        genres.add("Shooter");
        genres.add("Controller");
        genres.add("Space Sim");
        genres.add("Adventure");
        genres.add("Female Protagonist");
        genres.add("Strategy");
        genres.add("Open World");

        gameName="STAR WARS?: Squadrons";
        gameDev="MOTIVE";
        desc="Master the art of starfighter combat in the authentic piloting experience STAR WARS�: Squadrons. Feel the adrenaline of first-person multiplayer space dogfights alongside your squadron, and buckle up in a thrilling STAR WARS� story.";
        price=79.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Open World Survival Craft");
        genres.add("Survival");
        genres.add("Online Co-Op");
        genres.add("Open World");
        genres.add("Crafting");
        genres.add("Building");
        genres.add("Exploration");
        genres.add("Multiplayer");
        genres.add("Co-op");
        genres.add("Base Building");
        genres.add("Mythology");
        genres.add("Sandbox");
        genres.add("Adventure");
        genres.add("Sailing");
        genres.add("RPG");
        genres.add("Early Access");
        genres.add("Action");
        genres.add("Indie");
        genres.add("PvP");
        genres.add("Fishing");

        gameName="Valheim";
        gameDev="Iron Gate AB";
        desc="A brutal exploration and survival game for 1-10 players, set in a procedurally-generated purgatory inspired by viking culture. Battle, build, and conquer your way to a saga worthy of Odin�s patronage!";
        price=70.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Strategy");
        genres.add("Turn-Based Strategy");
        genres.add("Fantasy");
        genres.add("Turn-Based Tactics");
        genres.add("Top-Down");
        genres.add("PvP");
        genres.add("Games Workshop");
        genres.add("Multiplayer");
        genres.add("Singleplayer");
        genres.add("Roguelite");
        genres.add("Roguelike");
        genres.add("Warhammer 40K");

        gameName="Warhammer Age of Sigmar: Storm Ground";
        gameDev="Gasket Games";
        desc="Conquer the Mortal Realms! The first strategy videogame adaptation of Age of Sigmar�s dark-fantasy universe. Lead your highly-customisable force in challenging roguelike campaigns, unlocking new units, equipment and skills in dynamic, turn-based warfare.";
        price=125.8;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Simulation");
        genres.add("Driving");
        genres.add("Destruction");
        genres.add("Physics");
        genres.add("Automobile Sim");
        genres.add("Realistic");
        genres.add("Open World");
        genres.add("Racing");
        genres.add("Sandbox");
        genres.add("Moddable");
        genres.add("Singleplayer");
        genres.add("Early Access");
        genres.add("Exploration");
        genres.add("First-Person");
        genres.add("Funny");
        genres.add("Third Person");
        genres.add("Action");
        genres.add("Adventure");
        genres.add("Flight");
        genres.add("Arcade");

        gameName="BeamNG.drive";
        gameDev="BeamNG";
        desc="A dynamic soft-body physics vehicle simulator capable of doing just about anything.";
        price=64.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Open World");
        genres.add("Action");
        genres.add("Automobile Sim");
        genres.add("Multiplayer");
        genres.add("Third Person");
        genres.add("Crime");
        genres.add("Adventure");
        genres.add("Singleplayer");
        genres.add("Third-Person Shooter");
        genres.add("Sandbox");
        genres.add("Moddable");
        genres.add("Shooter");
        genres.add("Physics");
        genres.add("Atmospheric");
        genres.add("Funny");
        genres.add("Classic");
        genres.add("Dark Humor");
        genres.add("Gore");
        genres.add("Co-op");
        genres.add("Satire");

        gameName="Grand Theft Auto IV: The Complete Edition";
        gameDev="Rockstar North";
        desc="Niko Bellic, Johnny Klebitz and Luis Lopez all have one thing in common - they live in the worst city in America. Liberty City worships money and status, and is heaven for those who have them and a living nightmare for those who don''t.";
        price=29.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Co-op");
        genres.add("Adventure");
        genres.add("Split Screen");
        genres.add("Puzzle");
        genres.add("Puzzle Platformer");
        genres.add("3D Platformer");
        genres.add("Platformer");
        genres.add("Story Rich");
        genres.add("Action-Adventure");
        genres.add("Local Co-Op");
        genres.add("Female Protagonist");
        genres.add("Action");
        genres.add("Multiplayer");
        genres.add("Co-op Campaign");
        genres.add("Atmospheric");
        genres.add("Local Multiplayer");
        genres.add("Emotional");
        genres.add("Online Co-Op");
        genres.add("Exploration");
        genres.add("Minigames");

        gameName="It Takes Two";
        gameDev="Hazelight";
        desc="Embark on the craziest journey of your life in It Takes Two. Invite a friend to join for free with Friend�s Pass and work together across a huge variety of gleefully disruptive gameplay challenges.";
        price=0.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Early Access");
        genres.add("Survival");
        genres.add("Voxel");
        genres.add("Zombies");
        genres.add("Post-apocalyptic");
        genres.add("Open World");
        genres.add("Open World Survival Craft");
        genres.add("Simulation");
        genres.add("Exploration");
        genres.add("Base Building");
        genres.add("Strategy");
        genres.add("Multiplayer");
        genres.add("Procedural Generation");
        genres.add("Tower Defense");
        genres.add("Character Customization");
        genres.add("Online Co-Op");
        genres.add("Sandbox");
        genres.add("Building");
        genres.add("FPS");
        genres.add("Action");

        gameName="7 Days to Die";
        gameDev="The Fun Pimps";
        desc="7 Days to Die is an open-world game that is a unique combination of first person shooter, survival horror, tower defense, and role-playing games. Play the definitive zombie survival sandbox RPG that came first. Navezgane awaits!";
        price=23.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("RPG");
        genres.add("Dungeons & Dragons");
        genres.add("Choices Matter");
        genres.add("Early Access");
        genres.add("Tactical");
        genres.add("Character Customization");
        genres.add("Fantasy");
        genres.add("Party-Based RPG");
        genres.add("CRPG");
        genres.add("Tactical RPG");
        genres.add("Story Rich");
        genres.add("Turn-Based Tactics");
        genres.add("Top-Down");
        genres.add("3D");
        genres.add("Dragons");
        genres.add("Singleplayer");
        genres.add("Team-Based");
        genres.add("Strategy");
        genres.add("Turn-Based Strategy");
        genres.add("Turn-Based Combat");
        genres.add("RPG");
        genres.add("Singleplayer");
        genres.add("Story Rich");
        genres.add("Strategy");

        gameName="Solasta: Crown of the Magister";
        gameDev="Tactical Adventures";
        desc="Roll for initiative, take attacks of opportunity, manage player location and the verticality of the battle field in this upcoming Turn-Based Tactical RPG based on the SRD 5.1 Ruleset. In Solasta, you make the choices, dice decide your destiny.";
        price=103.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Hunting");
        genres.add("Open World");
        genres.add("Multiplayer");
        genres.add("Simulation");
        genres.add("First-Person");
        genres.add("Shooter");
        genres.add("Adventure");
        genres.add("Realistic");
        genres.add("Survival");
        genres.add("Co-op");
        genres.add("FPS");
        genres.add("Action");
        genres.add("Singleplayer");
        genres.add("Walking Simulator");
        genres.add("Atmospheric");
        genres.add("Online Co-Op");
        genres.add("Sports");
        genres.add("Combat");
        genres.add("Strategy");
        genres.add("Gore");

        gameName="theHunter: Call of the Wild?";
        gameDev="Expansive Worlds";
        desc="Experience an atmospheric hunting game like no other in this realistic and visually breathtaking open world. Immerse yourself in the atmospheric single player campaign, or share the ultimate hunting experience with friends.";
        price=21.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Horror");
        genres.add("Online Co-Op");
        genres.add("Multiplayer");
        genres.add("Psychological Horror");
        genres.add("Co-op");
        genres.add("Supernatural");
        genres.add("VR");
        genres.add("First-Person");
        genres.add("Investigation");
        genres.add("Dark");
        genres.add("Mystery");
        genres.add("Detective");
        genres.add("Demons");
        genres.add("Early Access");
        genres.add("Thriller");
        genres.add("Indie");
        genres.add("3D");
        genres.add("Action");
        genres.add("Tactical");
        genres.add("Lovecraftian");

        gameName="Phasmophobia";
        gameDev="Kinetic Games";
        desc="Phasmophobia is a 4 player online co-op psychological horror. Paranormal activity is on the rise and it�s up to you and your team to use all the ghost hunting equipment at your disposal in order to gather as much evidence as you can.";
        price=47.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Open World Survival Craft");
        genres.add("Survival");
        genres.add("Underwater");
        genres.add("Open World");
        genres.add("Singleplayer");
        genres.add("Exploration");
        genres.add("Adventure");
        genres.add("Base Building");
        genres.add("Horror");
        genres.add("Crafting");
        genres.add("First-Person");
        genres.add("Early Access");
        genres.add("Aliens");
        genres.add("Survival Horror");
        genres.add("Sci-fi");
        genres.add("Atmospheric");
        genres.add("Story Rich");
        genres.add("Sandbox");
        genres.add("Great Soundtrack");
        genres.add("Multiplayer");

        gameName="Subnautica: Below Zero";
        gameDev="Unknown Worlds Entertainment";
        desc="Dive into a freezing underwater adventure on an alien planet. Below Zero is set two years after the original Subnautica. Return to Planet 4546B to uncover the truth behind a deadly cover-up. Survive by building habitats, crafting tools, & diving deeper into the world of Subnautica.";
        price=99.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Open World Survival Craft");
        genres.add("Survival");
        genres.add("Open World");
        genres.add("Horror");
        genres.add("Crafting");
        genres.add("Adventure");
        genres.add("Building");
        genres.add("Survival Horror");
        genres.add("First-Person");
        genres.add("Action");
        genres.add("Exploration");
        genres.add("Sandbox");
        genres.add("Atmospheric");
        genres.add("Simulation");
        genres.add("Singleplayer");
        genres.add("Indie");
        genres.add("Realistic");
        genres.add("Gore");
        genres.add("Early Access");
        genres.add("Zombies");

        gameName="The Forest";
        gameDev="Endnight Games Ltd";
        desc="As the lone survivor of a passenger jet crash, you find yourself in a mysterious forest battling to stay alive against a society of cannibalistic mutants. Build, explore, survive in this terrifying first person survival horror simulator.";
        price=31.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Difficult");
        genres.add("Roguelike");
        genres.add("Dungeon Crawler");
        genres.add("Physics");
        genres.add("Action");
        genres.add("Indie");
        genres.add("RPG");
        genres.add("Action Roguelike");
        genres.add("Open World");
        genres.add("Roguelite");
        genres.add("Perma Death");
        genres.add("Sandbox");
        genres.add("2D");
        genres.add("2D Platformer");
        genres.add("Action-Adventure");
        genres.add("Gun Customization");
        genres.add("Dark Humor");
        genres.add("Mythology");
        genres.add("Crafting");
        genres.add("Pixel Graphics");

        gameName="Noita";
        gameDev="Nolla Games";
        desc="Noita is a magical action roguelite set in a world where every pixel is physically simulated. Fight, explore, melt, burn, freeze and evaporate your way through the procedurally generated world using spells you''ve created yourself.";
        price=49.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Cyberpunk");
        genres.add("Open World");
        genres.add("RPG");
        genres.add("Sci-fi");
        genres.add("Futuristic");
        genres.add("Singleplayer");
        genres.add("FPS");
        genres.add("First-Person");
        genres.add("Atmospheric");
        genres.add("Nudity");
        genres.add("Exploration");
        genres.add("Story Rich");
        genres.add("Mature");
        genres.add("Action");
        genres.add("Violent");
        genres.add("Great Soundtrack");
        genres.add("Action RPG");
        genres.add("Adventure");
        genres.add("Immersive Sim");
        genres.add("Character Customization");

        gameName="Cyberpunk 2077";
        gameDev="CD PROJEKT RED";
        desc="Cyberpunk 2077 is an open-world, action-adventure story set in Night City, a megalopolis obsessed with power, glamour and body modification. You play as V, a mercenary outlaw going after a one-of-a-kind implant that is the key to immortality.";
        price=238.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Survival");
        genres.add("Open World Survival Craft");
        genres.add("Multiplayer");
        genres.add("Open World");
        genres.add("Zombies");
        genres.add("Massively Multiplayer");
        genres.add("Action");
        genres.add("Early Access");
        genres.add("Adventure");
        genres.add("Crafting");
        genres.add("Realistic");
        genres.add("Nudity");
        genres.add("Violent");
        genres.add("FPS");
        genres.add("Singleplayer");
        genres.add("Indie");
        genres.add("PvP");
        genres.add("Third Person");
        genres.add("Gore");
        genres.add("RPG");

        gameName="SCUM";
        gameDev="Gamepires";
        desc="SCUM aims to evolve the multiplayer open world survival game with unprecedented levels of character customization, control and progression, where knowledge and skills are the ultimate weapons for long-term survival.";
        price=77.4;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Open World");
        genres.add("RPG");
        genres.add("Assassin");
        genres.add("Singleplayer");
        genres.add("Action");
        genres.add("Adventure");
        genres.add("Historical");
        genres.add("Stealth");
        genres.add("Story Rich");
        genres.add("Female Protagonist");
        genres.add("Parkour");
        genres.add("Third Person");
        genres.add("Sexual Content");
        genres.add("Choices Matter");
        genres.add("Violent");
        genres.add("Multiplayer");
        genres.add("Atmospheric");
        genres.add("Nudity");
        genres.add("Great Soundtrack");
        genres.add("Gore");

        gameName="Assassin''s Creed? Odyssey";
        gameDev="Ubisoft Quebec";
        desc="Choose your fate in Assassin''s Creed� Odyssey. From outcast to living legend, embark on an odyssey to uncover the secrets of your past and change the fate of Ancient Greece.";
        price=74.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Survival");
        genres.add("Crafting");
        genres.add("Multiplayer");
        genres.add("Open World");
        genres.add("Open World Survival Craft");
        genres.add("Building");
        genres.add("Sandbox");
        genres.add("PvP");
        genres.add("Adventure");
        genres.add("First-Person");
        genres.add("Action");
        genres.add("FPS");
        genres.add("Nudity");
        genres.add("Co-op");
        genres.add("Shooter");
        genres.add("Online Co-Op");
        genres.add("Indie");
        genres.add("Early Access");
        genres.add("Post-apocalyptic");
        genres.add("Simulation");

        gameName="Rust";
        gameDev="Facepunch Studios";
        desc="The only aim in Rust is to survive - Overcome struggles such as hunger, thirst and cold. Build a fire. Build a shelter. Kill animals. Protect yourself from other players.";
        price=77.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Racing");
        genres.add("Open World");
        genres.add("Driving");
        genres.add("Multiplayer");
        genres.add("Online Co-Op");
        genres.add("Singleplayer");
        genres.add("Automobile Sim");
        genres.add("Adventure");
        genres.add("Realistic");
        genres.add("PvP");
        genres.add("Action");
        genres.add("Arcade");
        genres.add("Exploration");
        genres.add("Simulation");
        genres.add("Sports");
        genres.add("Atmospheric");
        genres.add("First-Person");
        genres.add("Great Soundtrack");
        genres.add("Funny");
        genres.add("Controller");

        gameName="Forza Horizon 4";
        gameDev="Playground Games";
        desc="Dynamic seasons change everything at the world�s greatest automotive festival. Go it alone or team up with others to explore beautiful and historic Britain in a shared open world.";
        price=188.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Colony Sim");
        genres.add("Base Building");
        genres.add("Survival");
        genres.add("Resource Management");
        genres.add("Building");
        genres.add("Singleplayer");
        genres.add("Simulation");
        genres.add("Management");
        genres.add("2D");
        genres.add("Sandbox");
        genres.add("Strategy");
        genres.add("Space");
        genres.add("Indie");
        genres.add("Sci-fi");
        genres.add("Exploration");
        genres.add("Open World");
        genres.add("Difficult");
        genres.add("Early Access");
        genres.add("Adventure");
        genres.add("Space Sim");

        gameName="Oxygen Not Included";
        gameDev="Klei Entertainment";
        desc="Oxygen Not Included is a space-colony simulation game. Deep inside an alien space rock your industrious crew will need to master science, overcome strange new lifeforms, and harness incredible space tech to survive, and possibly, thrive.";
        price=23.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Action");
        genres.add("Multiplayer");
        genres.add("Open World");
        genres.add("Co-op");
        genres.add("Hunting");
        genres.add("Difficult");
        genres.add("Third Person");
        genres.add("Singleplayer");
        genres.add("Online Co-Op");
        genres.add("Adventure");
        genres.add("Character Customization");
        genres.add("RPG");
        genres.add("Exploration");
        genres.add("Dragons");
        genres.add("Hack and Slash");
        genres.add("JRPG");
        genres.add("Story Rich");
        genres.add("Souls-like");
        genres.add("MMORPG");
        genres.add("PvE");

        gameName="Monster Hunter World: Iceborne";
        gameDev="CAPCOM Co., Ltd.";
        desc="unknown";
        price=168.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Farming Sim");
        genres.add("Life Sim");
        genres.add("RPG");
        genres.add("Pixel Graphics");
        genres.add("Agriculture");
        genres.add("Simulation");
        genres.add("Multiplayer");
        genres.add("Relaxing");
        genres.add("Crafting");
        genres.add("Sandbox");
        genres.add("Indie");
        genres.add("Building");
        genres.add("Singleplayer");
        genres.add("Casual");
        genres.add("Open World");
        genres.add("2D");
        genres.add("Great Soundtrack");
        genres.add("Cute");
        genres.add("Dating Sim");
        genres.add("Fishing");

        gameName="Stardew Valley";
        gameDev="ConcernedApe";
        desc="You''ve inherited your grandfather''s old farm plot in Stardew Valley. Armed with hand-me-down tools and a few coins, you set out to begin your new life. Can you learn to live off the land and turn these overgrown fields into a thriving home?";
        price=48.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Open World Survival Craft");
        genres.add("Sandbox");
        genres.add("Survival");
        genres.add("2D");
        genres.add("Multiplayer");
        genres.add("Adventure");
        genres.add("Crafting");
        genres.add("Building");
        genres.add("Pixel Graphics");
        genres.add("Exploration");
        genres.add("Co-op");
        genres.add("Open World");
        genres.add("Indie");
        genres.add("Online Co-Op");
        genres.add("Action");
        genres.add("RPG");
        genres.add("Singleplayer");
        genres.add("Replay Value");
        genres.add("Platformer");
        genres.add("Atmospheric");

        gameName="Terraria";
        gameDev="Re-Logic";
        desc="Dig, fight, explore, build! Nothing is impossible in this action-packed adventure game. Four Pack also available!";
        price=18.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("RPG");
        genres.add("Story Rich");
        genres.add("Action");
        genres.add("Sci-fi");
        genres.add("Choices Matter");
        genres.add("Action RPG");
        genres.add("Third-Person Shooter");
        genres.add("Singleplayer");
        genres.add("Space");
        genres.add("Shooter");
        genres.add("Great Soundtrack");
        genres.add("Tactical RPG");
        genres.add("Military");
        genres.add("Sexual Content");
        genres.add("Character Customization");
        genres.add("Combat");
        genres.add("Atmospheric");
        genres.add("Classic");
        genres.add("Nudity");
        genres.add("Female Protagonist");

        gameName="Mass Effect? Legendary Edition";
        gameDev="BioWare";
        desc="The Mass Effect� Legendary Edition includes single-player base content and over 40 DLC from the highly acclaimed Mass Effect, Mass Effect 2, and Mass Effect 3 games, including promo weapons, armors, and packs � remastered and optimized for 4K Ultra HD.";
        price=248.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Open World");
        genres.add("Open World Survival Craft");
        genres.add("Space");
        genres.add("Exploration");
        genres.add("Sci-fi");
        genres.add("Survival");
        genres.add("Procedural Generation");
        genres.add("Adventure");
        genres.add("First-Person");
        genres.add("Singleplayer");
        genres.add("Sandbox");
        genres.add("Atmospheric");
        genres.add("Crafting");
        genres.add("Multiplayer");
        genres.add("Space Sim");
        genres.add("Indie");
        genres.add("Action");
        genres.add("Simulation");
        genres.add("FPS");
        genres.add("Great Soundtrack");

        gameName="No Man''s Sky";
        gameDev="Hello Games";
        desc="No Man''s Sky is a game about exploration and survival in an infinite procedurally generated universe.";
        price=69.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Base Building");
        genres.add("Open World");
        genres.add("Automation");
        genres.add("Multiplayer");
        genres.add("Crafting");
        genres.add("Co-op");
        genres.add("Resource Management");
        genres.add("Building");
        genres.add("Sandbox");
        genres.add("Exploration");
        genres.add("Adventure");
        genres.add("Simulation");
        genres.add("Strategy");
        genres.add("Early Access");
        genres.add("Indie");
        genres.add("Open World Survival Craft");
        genres.add("Survival");
        genres.add("First-Person");
        genres.add("Singleplayer");
        genres.add("Sci-fi");

        gameName="Satisfactory";
        gameDev="Coffee Stain Studios";
        desc="Satisfactory is a first-person open-world factory building game with a dash of exploration and combat. Play alone or with friends, explore an alien planet, create multi-story factories, and enter conveyor belt heaven!";
        price=79.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Multiplayer");
        genres.add("Funny");
        genres.add("Battle Royale");
        genres.add("Online Co-Op");
        genres.add("Family Friendly");
        genres.add("Party Game");
        genres.add("Comedy");
        genres.add("Cute");
        genres.add("Casual");
        genres.add("Physics");
        genres.add("Massively Multiplayer");
        genres.add("Colorful");
        genres.add("3D Platformer");
        genres.add("Indie");
        genres.add("Co-op");
        genres.add("Action");
        genres.add("Controller");
        genres.add("Great Soundtrack");
        genres.add("Difficult");
        genres.add("Local Co-Op");

        gameName="Fall Guys: Ultimate Knockout";
        gameDev="Mediatonic";
        desc="Fall Guys is a massively multiplayer party game with up to 60 players online in a free-for-all struggle through round after round of escalating chaos until one victor remains!";
        price=58.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Racing");
        genres.add("Open World");
        genres.add("Multiplayer");
        genres.add("Driving");
        genres.add("Action");
        genres.add("Adventure");
        genres.add("Singleplayer");
        genres.add("Co-op");
        genres.add("Atmospheric");
        genres.add("Combat Racing");
        genres.add("Third Person");
        genres.add("Sports");
        genres.add("Arcade");
        genres.add("First-Person");
        genres.add("Online Co-Op");
        genres.add("PvP");
        genres.add("Split Screen");
        genres.add("Character Customization");
        genres.add("Destruction");
        genres.add("Sexual Content");

        gameName="Need for Speed? Heat";
        gameDev="Ghost Games";
        desc="Hustle by day and risk it all at night in Need for Speed� Heat Deluxe Edition, a white-knuckle street racer, where the lines of the law fade as the sun starts to set.";
        price=57.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Open World");
        genres.add("Co-op");
        genres.add("Action");
        genres.add("FPS");
        genres.add("Multiplayer");
        genres.add("Adventure");
        genres.add("Shooter");
        genres.add("Singleplayer");
        genres.add("First-Person");
        genres.add("Survival");
        genres.add("Story Rich");
        genres.add("Stealth");
        genres.add("America");
        genres.add("Hunting");
        genres.add("Exploration");
        genres.add("Atmospheric");
        genres.add("Character Customization");
        genres.add("Great Soundtrack");
        genres.add("Sexual Content");
        genres.add("Nudity");

        gameName="Far Cry? 5";
        gameDev="Ubisoft Montreal";
        desc="Welcome to Hope County, Montana, home to a fanatical doomsday cult known as Eden�s Gate. Stand up to cult leader Joseph Seed & his siblings, the Heralds, to spark the fires of resistance & liberate the besieged community.";
        price=44.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Survival");
        genres.add("Open World Survival Craft");
        genres.add("Multiplayer");
        genres.add("Co-op");
        genres.add("Crafting");
        genres.add("Open World");
        genres.add("Building");
        genres.add("Base Building");
        genres.add("Adventure");
        genres.add("Online Co-Op");
        genres.add("Sandbox");
        genres.add("Early Access");
        genres.add("First-Person");
        genres.add("Singleplayer");
        genres.add("Underwater");
        genres.add("Simulation");
        genres.add("Action");
        genres.add("Indie");
        genres.add("Strategy");
        genres.add("Massively Multiplayer");

        gameName="Raft";
        gameDev="Redbeet Interactive";
        desc="Raft throws you and your friends into an epic oceanic adventure! Alone or together, players battle to survive a perilous voyage across a vast sea! Gather debris, scavenge reefs and build your own floating home, but be wary of the man-eating sharks!";
        price=45.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Dark Humor");
        genres.add("Nature");
        genres.add("Underwater");
        genres.add("Open World");
        genres.add("Action RPG");
        genres.add("Singleplayer");
        genres.add("Exploration");
        genres.add("Narration");
        genres.add("Third Person");
        genres.add("Violent");
        genres.add("Female Protagonist");
        genres.add("Action-Adventure");
        genres.add("RPG");
        genres.add("Action");
        genres.add("Simulation");
        genres.add("Gore");
        genres.add("Atmospheric");
        genres.add("Adventure");
        genres.add("Satire");
        genres.add("Indie");

        gameName="Maneater";
        gameDev="Tripwire Interactive";
        desc="Experience the ultimate power fantasy as the apex predator of the seas - a giant Shark! Terrorize the coastal waterways. Tear swimmers and divers limb from limb, give the humans a reason to fear you!";
        price=98.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Cute");
        genres.add("Exploration");
        genres.add("Adventure");
        genres.add("Singleplayer");
        genres.add("First-Person");
        genres.add("Open World");
        genres.add("Colorful");
        genres.add("Funny");
        genres.add("Sandbox");
        genres.add("Simulation");
        genres.add("Casual");
        genres.add("Fantasy");
        genres.add("Management");
        genres.add("Indie");
        genres.add("Great Soundtrack");
        genres.add("Family Friendly");
        genres.add("Early Access");
        genres.add("Female Protagonist");
        genres.add("Action");
        genres.add("FPS");

        gameName="Slime Rancher";
        gameDev="Monomi Park";
        desc="Slime Rancher is the tale of Beatrix LeBeau, a plucky, young rancher who sets out for a life a thousand light years away from Earth on the �Far, Far Range� where she tries her hand at making a living wrangling slimes.";
        price=20.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("RPG");
        genres.add("Open World");
        genres.add("Fantasy");
        genres.add("Singleplayer");
        genres.add("Story Rich");
        genres.add("Character Customization");
        genres.add("Action");
        genres.add("Choices Matter");
        genres.add("Adventure");
        genres.add("Strategy");
        genres.add("Sexual Content");
        genres.add("Female Protagonist");
        genres.add("Third Person");
        genres.add("Great Soundtrack");
        genres.add("Dragons");
        genres.add("Action RPG");
        genres.add("Atmospheric");
        genres.add("Nudity");
        genres.add("Combat");
        genres.add("CRPG");

        gameName="Dragon Age? Inquisition";
        gameDev="BioWare";
        desc="Winner of over 130 Game of the Year awards, discover the definitive Dragon Age: Inquisition experience. The Game of the Year Edition includes the critically acclaimed game, all three official add-ons - Jaws of Hakkon, The Descent, and Trespasser - and more.";
        price=29.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Souls-like");
        genres.add("Action");
        genres.add("RPG");
        genres.add("Adventure");
        genres.add("Co-op");
        genres.add("Third-Person Shooter");
        genres.add("Multiplayer");
        genres.add("Online Co-Op");
        genres.add("Shooter");
        genres.add("Post-apocalyptic");
        genres.add("Survival");
        genres.add("Difficult");
        genres.add("Singleplayer");
        genres.add("Lovecraftian");
        genres.add("Violent");
        genres.add("Dark Fantasy");
        genres.add("Replay Value");
        genres.add("Horror");
        genres.add("Procedural Generation");
        genres.add("Atmospheric");

        gameName="Remnant: From the Ashes";
        gameDev="Gunfire Games";
        desc="The world has been thrown into chaos by an ancient evil from another dimension. As one of the last remnants of humanity, you must set out alone or alongside up to two other survivors to face down hordes of deadly enemies to try to carve a foothold, rebuild, and retake what was lost.";
        price=56.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Open World Survival Craft");
        genres.add("Survival");
        genres.add("Open World");
        genres.add("Exploration");
        genres.add("Underwater");
        genres.add("Crafting");
        genres.add("Base Building");
        genres.add("Horror");
        genres.add("Singleplayer");
        genres.add("Adventure");
        genres.add("First-Person");
        genres.add("Sci-fi");
        genres.add("Sandbox");
        genres.add("Aliens");
        genres.add("Atmospheric");
        genres.add("Survival Horror");
        genres.add("Action");
        genres.add("Early Access");
        genres.add("Indie");
        genres.add("Multiplayer");

        gameName="Subnautica";
        gameDev="Unknown Worlds Entertainment";
        desc="Descend into the depths of an alien underwater world filled with wonder and peril. Craft equipment, pilot submarines and out-smart wildlife to explore lush coral reefs, volcanoes, cave systems, and more - all while trying to survive.";
        price=99.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Simulation");
        genres.add("Driving");
        genres.add("Automobile Sim");
        genres.add("Open World");
        genres.add("Realistic");
        genres.add("Relaxing");
        genres.add("Singleplayer");
        genres.add("Moddable");
        genres.add("Exploration");
        genres.add("First-Person");
        genres.add("Economy");
        genres.add("Atmospheric");
        genres.add("Adventure");
        genres.add("Indie");
        genres.add("Management");
        genres.add("Casual");
        genres.add("TrackIR");
        genres.add("Sandbox");
        genres.add("Racing");
        genres.add("RPG");

        gameName="Euro Truck Simulator 2";
        gameDev="SCS Software";
        desc="Travel across Europe as king of the road, a trucker who delivers important cargo across impressive distances! With dozens of cities to explore, your endurance, skill and speed will all be pushed to their limits.";
        price=24.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("RPG");
        genres.add("MMORPG");
        genres.add("Open World");
        genres.add("Massively Multiplayer");
        genres.add("Multiplayer");
        genres.add("Fantasy");
        genres.add("Adventure");
        genres.add("First-Person");
        genres.add("Exploration");
        genres.add("Story Rich");
        genres.add("Third Person");
        genres.add("Magic");
        genres.add("Action");
        genres.add("Co-op");
        genres.add("Atmospheric");
        genres.add("Great Soundtrack");
        genres.add("Action RPG");
        genres.add("Singleplayer");
        genres.add("Sandbox");
        genres.add("Character Customization");

        gameName="The Elder Scrolls? Online";
        gameDev="Zenimax Online Studios";
        desc="Join over 18 million players in the award-winning online multiplayer RPG and experience limitless adventure in a persistent Elder Scrolls world. Battle, craft, steal, or explore, and combine different types of equipment and abilities to create your own style of play. No game subscription required.";
        price=27.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Dark Fantasy");
        genres.add("Deckbuilding");
        genres.add("Story Rich");
        genres.add("Card Game");
        genres.add("Roguelike");
        genres.add("RPG");
        genres.add("Choices Matter");
        genres.add("Base Building");
        genres.add("Turn-Based");
        genres.add("Singleplayer");
        genres.add("Indie");
        genres.add("CRPG");
        genres.add("Board Game");
        genres.add("Replay Value");
        genres.add("Isometric");
        genres.add("Roguelite");
        genres.add("Card Battler");
        genres.add("Turn-Based Combat");
        genres.add("Action");
        genres.add("Survival");

        gameName="Tainted Grail: Conquest";
        gameDev="Awaken Realms Digital";
        desc="A unique, infinitely replayable, story-driven hybrid between a deck-building Roguelike and an RPG game. Explore the ever-changing maps, fight with deadly enemies, and learn what happened to the cursed island of Avalon.";
        price=63.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Dinosaurs");
        genres.add("Survival");
        genres.add("Open World");
        genres.add("Multiplayer");
        genres.add("Simulation");
        genres.add("Hunting");
        genres.add("Massively Multiplayer");
        genres.add("Adventure");
        genres.add("Early Access");
        genres.add("Realistic");
        genres.add("Action");
        genres.add("Third Person");
        genres.add("Co-op");
        genres.add("Sandbox");
        genres.add("Indie");
        genres.add("Walking Simulator");
        genres.add("Strategy");
        genres.add("Team-Based");
        genres.add("RPG");
        genres.add("Horror");

        gameName="The Isle";
        gameDev="Afterthought LLC";
        desc="Experience fierce open world survival gameplay as you attempt to stay alive on an unforgiving island inhabited by dinosaurs! Hunt. Grow. Survive.";
        price=51.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Survival");
        genres.add("Open World Survival Craft");
        genres.add("Multiplayer");
        genres.add("Co-op");
        genres.add("Open World");
        genres.add("Adventure");
        genres.add("Crafting");
        genres.add("Online Co-Op");
        genres.add("Sandbox");
        genres.add("Difficult");
        genres.add("Indie");
        genres.add("2D");
        genres.add("Funny");
        genres.add("Atmospheric");
        genres.add("Survival Horror");
        genres.add("Strategy");
        genres.add("Singleplayer");
        genres.add("Horror");
        genres.add("Action");
        genres.add("Simulation");

        gameName="Don''t Starve Together";
        gameDev="Klei Entertainment";
        desc="Fight, Farm, Build and Explore Together in the standalone multiplayer expansion to the uncompromising wilderness survival game, Don''t Starve.";
        price=12.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("JRPG");
        genres.add("RPG");
        genres.add("Turn-Based Combat");
        genres.add("Party-Based RPG");
        genres.add("Story Rich");
        genres.add("Dragons");
        genres.add("Funny");
        genres.add("Adventure");
        genres.add("3D");
        genres.add("2D");
        genres.add("Fantasy");
        genres.add("Colorful");
        genres.add("Singleplayer");
        genres.add("Action");
        genres.add("Third Person");
        genres.add("Crafting");
        genres.add("Character Customization");
        genres.add("Anime");
        genres.add("Open World");
        genres.add("Cartoony");
        genres.add("RPG");
        genres.add("Action");
        genres.add("Open World");
        genres.add("Singleplayer");
        genres.add("Adventure");
        genres.add("Story Rich");
        genres.add("Third Person");

        gameName="DRAGON QUEST? XI S: Echoes of an Elusive Age? - Definitive Edition";
        gameDev="Square Enix";
        desc="The Definitive Edition includes the critically acclaimed DRAGON QUEST XI, plus additional scenarios, orchestral soundtrack, 2D mode and more! Whether you are a longtime fan or a new adventurer, this is the ultimate DQXI experience.";
        price=184.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Mechs");
        genres.add("Simulation");
        genres.add("FPS");
        genres.add("Action");
        genres.add("Shooter");
        genres.add("Vehicular Combat");
        genres.add("First-Person");
        genres.add("Co-op");
        genres.add("Destruction");
        genres.add("Moddable");
        genres.add("3D");
        genres.add("Combat");
        genres.add("Online Co-Op");
        genres.add("Singleplayer");
        genres.add("Futuristic");
        genres.add("Tutorial");
        genres.add("Economy");
        genres.add("Management");
        genres.add("PvE");
        genres.add("Resource Management");

        gameName="MechWarrior 5: Mercenaries";
        gameDev="Piranha Games Inc.";
        desc="The year is 3015. The battlefields are dominated by war machines known as BattleMechs. Level entire cities and decimate the enemy in your BattleMech. Follow a quest for glory and revenge. Manage an expanding mercenary company. Fight alongside your friends with a four-player PvE co-op.";
        price=90.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Multiplayer");
        genres.add("Hunting");
        genres.add("Online Co-Op");
        genres.add("Horror");
        genres.add("Shooter");
        genres.add("FPS");
        genres.add("Survival");
        genres.add("PvP");
        genres.add("Zombies");
        genres.add("Western");
        genres.add("Survival Horror");
        genres.add("Action");
        genres.add("Open World");
        genres.add("Perma Death");
        genres.add("First-Person");
        genres.add("Team-Based");
        genres.add("Violent");
        genres.add("Gore");
        genres.add("Battle Royale");
        genres.add("Co-op");

        gameName="Hunt: Showdown";
        gameDev="Crytek";
        desc="Hunt: Showdown is a thrilling, high-stakes PvPvE first-person shooter. Take down nightmarish monsters, as you compete for the bounties that will bring you glory, gear, and gold in this unforgiving � and unforgettable - online multiplayer experience.";
        price=74.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Exploration");
        genres.add("Tactical RPG");
        genres.add("CRPG");
        genres.add("PvP");
        genres.add("Story Rich");
        genres.add("Fantasy");
        genres.add("Turn-Based Strategy");
        genres.add("Strategy RPG");
        genres.add("Party-Based RPG");
        genres.add("RPG");
        genres.add("Character Customization");
        genres.add("Female Protagonist");
        genres.add("Co-op Campaign");
        genres.add("Local Co-Op");
        genres.add("Romance");
        genres.add("Funny");
        genres.add("Moddable");
        genres.add("Isometric");
        genres.add("Strategy");
        genres.add("3D");

        gameName="Divinity: Original Sin 2 - Definitive Edition";
        gameDev="Larian Studios";
        desc="The critically acclaimed RPG that raised the bar, from the creators of Baldur''s Gate 3. Gather your party. Master deep, tactical combat. Venture as a party of up to four - but know that only one of you will have the chance to become a God.";
        price=53.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Action");
        genres.add("Shooter");
        genres.add("Third-Person Shooter");
        genres.add("3D");
        genres.add("Cute");
        genres.add("Voxel");
        genres.add("Sci-fi");
        genres.add("Co-op");
        genres.add("Online Co-Op");
        genres.add("Multiplayer");
        genres.add("Singleplayer");
        genres.add("Aliens");

        gameName="EARTH DEFENSE FORCE: WORLD BROTHERS";
        gameDev="YUKE''S";
        desc="A new EDF adventure begins! Only this time, the action unfolds in a parallel world where the Earth is made of digital blocks. This world has befallen into chaos, as the once peaceful square Earth has been shattered into pieces. Shape aside, it is an Earth nonetheless which needs to be defended.";
        price=239.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Action");
        genres.add("Adventure");
        genres.add("Female Protagonist");
        genres.add("Supernatural");
        genres.add("Third Person");
        genres.add("Sci-fi");
        genres.add("Story Rich");
        genres.add("Atmospheric");
        genres.add("Horror");
        genres.add("Third-Person Shooter");
        genres.add("Singleplayer");
        genres.add("Surreal");
        genres.add("Psychological Horror");
        genres.add("Metroidvania");
        genres.add("Shooter");
        genres.add("Open World");
        genres.add("Violent");
        genres.add("Epic");
        genres.add("Difficult");
        genres.add("Souls-like");

        gameName="Control Ultimate Edition";
        gameDev="Remedy Entertainment";
        desc="Winner of over 80 awards, Control is a visually stunning third-person action-adventure that will keep you on the edge of your seat.";
        price=79.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Survival");
        genres.add("Open World");
        genres.add("Building");
        genres.add("Nudity");
        genres.add("Crafting");
        genres.add("Open World Survival Craft");
        genres.add("Multiplayer");
        genres.add("RPG");
        genres.add("Sandbox");
        genres.add("Massively Multiplayer");
        genres.add("Action");
        genres.add("Adventure");
        genres.add("Online Co-Op");
        genres.add("Exploration");
        genres.add("Violent");
        genres.add("Singleplayer");
        genres.add("Co-op");
        genres.add("Fantasy");
        genres.add("PvP");
        genres.add("Gore");

        gameName="Conan Exiles";
        gameDev="Funcom";
        desc="An online multiplayer survival game, now with mounts and mounted combat, set in the lands of Conan the Barbarian. Survive in a vast open world sandbox, build a home and kingdom, dominate your enemies in single or multiplayer.";
        price=38.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Mature");
        genres.add("Utilities");
        genres.add("Software");
        genres.add("Anime");
        genres.add("Design & Illustration");
        genres.add("Animation & Modeling");
        genres.add("Casual");
        genres.add("Memes");
        genres.add("Indie");
        genres.add("Early Access");
        genres.add("Cute");
        genres.add("Funny");
        genres.add("Action");
        genres.add("Singleplayer");
        genres.add("Music");
        genres.add("NSFW");
        genres.add("Sandbox");
        genres.add("Photo Editing");
        genres.add("Game Development");
        genres.add("Horror");

        gameName="Wallpaper Engine";
        gameDev="Wallpaper Engine Team";
        desc="Use stunning live wallpapers on your desktop. Animate your own images to create new wallpapers or import videos/websites and share them on the Steam Workshop!";
        price=19.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Open World");
        genres.add("Post-apocalyptic");
        genres.add("Zombies");
        genres.add("Singleplayer");
        genres.add("Action");
        genres.add("Survival");
        genres.add("Action-Adventure");
        genres.add("Adventure");
        genres.add("Third Person");
        genres.add("Motorbike");
        genres.add("Horror");
        genres.add("Multiplayer");
        genres.add("Survival Horror");
        genres.add("Open World Survival Craft");
        genres.add("Crafting");
        genres.add("Cinematic");
        genres.add("Third-Person Shooter");
        genres.add("Gore");
        genres.add("Violent");
        genres.add("Story Rich");

        gameName="Days Gone";
        gameDev="Bend Studio";
        desc="Ride and fight into a deadly, post pandemic America. Play as Deacon St. John, a drifter and bounty hunter who rides the broken road, fighting to survive while searching for a reason to live in this open-world action-adventure game.";
        price=279.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Open World Survival Craft");
        genres.add("Survival");
        genres.add("Open World");
        genres.add("Multiplayer");
        genres.add("Dinosaurs");
        genres.add("Crafting");
        genres.add("Building");
        genres.add("Adventure");
        genres.add("Base Building");
        genres.add("Co-op");
        genres.add("Action");
        genres.add("First-Person");
        genres.add("Sandbox");
        genres.add("Massively Multiplayer");
        genres.add("Singleplayer");
        genres.add("Early Access");
        genres.add("RPG");
        genres.add("Dragons");
        genres.add("Sci-fi");
        genres.add("Indie");

        gameName="ARK: Survival Evolved";
        gameDev="Studio Wildcard";
        desc="Stranded on the shores of a mysterious island, you must learn to survive. Use your cunning to kill or tame the primeval creatures roaming the land, and encounter other players to survive, dominate... and escape!";
        price=27.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Anime");
        genres.add("Fighting");
        genres.add("Action");
        genres.add("Ninja");
        genres.add("Multiplayer");
        genres.add("Adventure");
        genres.add("Story Rich");
        genres.add("Singleplayer");
        genres.add("Local Multiplayer");
        genres.add("Third Person");
        genres.add("Great Soundtrack");
        genres.add("Fantasy");
        genres.add("Open World");
        genres.add("Atmospheric");
        genres.add("Online Co-Op");
        genres.add("Co-op");
        genres.add("Beat ''em up");
        genres.add("Local Co-Op");
        genres.add("Memes");
        genres.add("Heist");

        gameName="NARUTO SHIPPUDEN: Ultimate Ninja STORM 4";
        gameDev="CyberConnect2 Co. Ltd.";
        desc="The latest opus in the acclaimed STORM series is taking you on a colourful and breathtaking ride. Take advantage of the totally revamped battle system and prepare to dive into the most epic fights you�ve ever seen !";
        price=38.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Open World");
        genres.add("Adventure");
        genres.add("Story Rich");
        genres.add("Western");
        genres.add("Action");
        genres.add("Multiplayer");
        genres.add("Shooter");
        genres.add("Realistic");
        genres.add("Third-Person Shooter");
        genres.add("Singleplayer");
        genres.add("Atmospheric");
        genres.add("Horses");
        genres.add("Beautiful");
        genres.add("Great Soundtrack");
        genres.add("Third Person");
        genres.add("Sandbox");
        genres.add("Mature");
        genres.add("First-Person");
        genres.add("Gore");
        genres.add("FPS");

        gameName="Red Dead Redemption 2";
        gameDev="Rockstar Games";
        desc="Winner of over 175 Game of the Year Awards and recipient of over 250 perfect scores, RDR2 is the epic tale of outlaw Arthur Morgan and the infamous Van der Linde gang, on the run across America at the dawn of the modern age. Also includes access to the shared living world of Red Dead Online.";
        price=166.8;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Survival");
        genres.add("Shooter");
        genres.add("Multiplayer");
        genres.add("Battle Royale");
        genres.add("FPS");
        genres.add("PvP");
        genres.add("Third-Person Shooter");
        genres.add("Action");
        genres.add("Online Co-Op");
        genres.add("Tactical");
        genres.add("Co-op");
        genres.add("First-Person");
        genres.add("Strategy");
        genres.add("Early Access");
        genres.add("Competitive");
        genres.add("Third Person");
        genres.add("Team-Based");
        genres.add("Difficult");
        genres.add("Simulation");
        genres.add("Stealth");

        gameName="PLAYERUNKNOWN''S BATTLEGROUNDS";
        gameDev="KRAFTON, Inc.";
        desc="PLAYERUNKNOWN''S BATTLEGROUNDS is a battle royale shooter that pits 100 players against each other in a struggle for survival. Gather supplies and outwit your opponents to become the last person standing.";
        price=98.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Early Access");
        genres.add("RPG");
        genres.add("Dungeons & Dragons");
        genres.add("Choices Matter");
        genres.add("Turn-Based");
        genres.add("Story Rich");
        genres.add("Character Customization");
        genres.add("Party-Based RPG");
        genres.add("CRPG");
        genres.add("Co-op Campaign");
        genres.add("Fantasy");
        genres.add("Romance");
        genres.add("Class-Based");
        genres.add("Dark Fantasy");
        genres.add("PvE");
        genres.add("Narration");
        genres.add("Stealth");
        genres.add("Crafting");
        genres.add("Combat");
        genres.add("Dragons");

        gameName="Baldur''s Gate 3";
        gameDev="Larian Studios";
        desc="Gather your party, and return to the Forgotten Realms in a tale of fellowship and betrayal, sacrifice and survival, and the lure of absolute power.";
        price=298.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Action");
        genres.add("Multiplayer");
        genres.add("Military");
        genres.add("Simulation");
        genres.add("Shooter");
        genres.add("First-Person");
        genres.add("Realistic");
        genres.add("Open World");
        genres.add("Tactical");
        genres.add("FPS");
        genres.add("War");
        genres.add("Co-op");
        genres.add("Strategy");
        genres.add("Sandbox");
        genres.add("Moddable");
        genres.add("Combat");
        genres.add("Online Co-Op");
        genres.add("Singleplayer");
        genres.add("Team-Based");
        genres.add("Third-Person Shooter");

        gameName="Arma 3";
        gameDev="Bohemia Interactive";
        desc="Experience true combat gameplay in a massive military sandbox. Deploying a wide variety of single- and multiplayer content, over 20 vehicles and 40 weapons, and limitless opportunities for content creation, this is the PC�s premier military game. Authentic, diverse, open - Arma 3 sends you to war.";
        price=29.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Adventure");
        genres.add("Massively Multiplayer");
        genres.add("Action");
        genres.add("RPG");
        genres.add("Survival");
        genres.add("Indie");
        genres.add("Open World");
        genres.add("Dinosaurs");
        genres.add("Multiplayer");
        genres.add("Violent");
        genres.add("Base Building");
        genres.add("Crafting");
        genres.add("Singleplayer");
        genres.add("Sandbox");
        genres.add("Building");
        genres.add("Sci-fi");
        genres.add("Open World Survival Craft");
        genres.add("Dragons");
        genres.add("Co-op");

        gameName="ARK: Genesis Season Pass";
        gameDev="Studio Wildcard";
        desc="unknown";
        price=87.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Exploration");
        genres.add("Space");
        genres.add("Mystery");
        genres.add("Adventure");
        genres.add("Story Rich");
        genres.add("Puzzle");
        genres.add("Atmospheric");
        genres.add("Great Soundtrack");
        genres.add("Singleplayer");
        genres.add("Indie");
        genres.add("Open World");
        genres.add("Sci-fi");
        genres.add("First-Person");
        genres.add("Beautiful");
        genres.add("Simulation");
        genres.add("Action");
        genres.add("Epic");
        genres.add("Horror");
        genres.add("3D");
        genres.add("Psychological Horror");

        gameName="Outer Wilds";
        gameDev="Mobius Digital";
        desc="Named Game of the Year 2019 by Giant Bomb, Polygon, Eurogamer, and The Guardian, Outer Wilds is a critically-acclaimed and award-winning open world mystery about a solar system trapped in an endless time loop.";
        price=48.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Survival");
        genres.add("Zombies");
        genres.add("Open World");
        genres.add("Open World Survival Craft");
        genres.add("Sandbox");
        genres.add("Multiplayer");
        genres.add("Post-apocalyptic");
        genres.add("Indie");
        genres.add("Crafting");
        genres.add("Simulation");
        genres.add("Co-op");
        genres.add("RPG");
        genres.add("Building");
        genres.add("Survival Horror");
        genres.add("Early Access");
        genres.add("Isometric");
        genres.add("Realistic");
        genres.add("Singleplayer");
        genres.add("2D");
        genres.add("Adventure");

        gameName="Project Zomboid";
        gameDev="The Indie Stone";
        desc="Project Zomboid is the ultimate in zombie survival. Alone or in MP: you loot, build, craft, fight, farm and fish in a struggle to survive. A hardcore RPG skillset, a vast map, a massively customisable sandbox and a cute tutorial raccoon await the unwary. So how will you die? All it takes is a bite�";
        price=32.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Military");
        genres.add("FPS");
        genres.add("Realistic");
        genres.add("Multiplayer");
        genres.add("Tactical");
        genres.add("Shooter");
        genres.add("War");
        genres.add("Team-Based");
        genres.add("Action");
        genres.add("First-Person");
        genres.add("Strategy");
        genres.add("Simulation");
        genres.add("Open World");
        genres.add("Early Access");
        genres.add("Co-op");
        genres.add("Massively Multiplayer");
        genres.add("Atmospheric");
        genres.add("Walking Simulator");
        genres.add("Indie");
        genres.add("Singleplayer");

        gameName="Squad";
        gameDev="Offworld Industries";
        desc="Squad is a tactical FPS that provides authentic combat experiences through teamwork, constant communication, and gameplay. It bridges the large gap between arcade shooter and military simulation with 100 player battles, combined arms combat, base building, and a great integrated VoIP system";
        price=111.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Zombies");
        genres.add("Survival Horror");
        genres.add("Horror");
        genres.add("Online Co-Op");
        genres.add("Open World");
        genres.add("Parkour");
        genres.add("First-Person");
        genres.add("Open World Survival Craft");
        genres.add("Survival");
        genres.add("Action");
        genres.add("FPS");
        genres.add("Gore");
        genres.add("PvP");
        genres.add("PvE");
        genres.add("Stealth");
        genres.add("Post-apocalyptic");
        genres.add("Story Rich");
        genres.add("Hack and Slash");
        genres.add("Action RPG");
        genres.add("RPG");

        gameName="Dying Light";
        gameDev="Techland";
        desc="First-person action survival game set in a post-apocalyptic open world overrun by flesh-hungry zombies. Roam a city devastated by a mysterious virus epidemic. Scavenge for supplies, craft weapons, and face hordes of the infected.";
        price=43.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("RPG");
        genres.add("Action");
        genres.add("FPS");
        genres.add("Looter Shooter");
        genres.add("Online Co-Op");
        genres.add("Open World");
        genres.add("Multiplayer");
        genres.add("Shooter");
        genres.add("Co-op");
        genres.add("Loot");
        genres.add("Co-op Campaign");
        genres.add("Singleplayer");
        genres.add("Gore");
        genres.add("Violent");
        genres.add("First-Person");
        genres.add("Adventure");
        genres.add("Nudity");
        genres.add("Great Soundtrack");
        genres.add("Epic");
        genres.add("Comedy");

        gameName="Borderlands 3";
        gameDev="Gearbox Software";
        desc="The original shooter-looter returns, packing bazillions of guns and a mayhem-fueled adventure! Blast through new worlds and enemies as one of four new Vault Hunters.";
        price=65.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Multiplayer");
        genres.add("Sports");
        genres.add("Team-Based");
        genres.add("Competitive");
        genres.add("Action-Adventure");
        genres.add("Third Person");
        genres.add("Action");
        genres.add("PvP");
        genres.add("Physics");
        genres.add("Indie");
        genres.add("Fighting");
        genres.add("Adventure");
        genres.add("Fast-Paced");
        genres.add("Roguelite");
        genres.add("Free to Play");
        genres.add("Action Roguelike");

        gameName="Knockout City?";
        gameDev="Velan Studios";
        desc="Team up and duke it out with rival Crews in Knockout City�, where EPIC DODGEBALL BATTLES settle the score in team-based multiplayer matches. Throw, catch, pass, dodge, and tackle your way to dodgeball dominance!";
        price=118.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Medieval");
        genres.add("Strategy");
        genres.add("Open World");
        genres.add("RPG");
        genres.add("War");
        genres.add("Multiplayer");
        genres.add("Sandbox");
        genres.add("Action");
        genres.add("Singleplayer");
        genres.add("Simulation");
        genres.add("Moddable");
        genres.add("Character Customization");
        genres.add("Adventure");
        genres.add("Horses");
        genres.add("Third Person");
        genres.add("Realistic");
        genres.add("First-Person");
        genres.add("Historical");
        genres.add("Great Soundtrack");
        genres.add("Early Access");

        gameName="Mount & Blade II: Bannerlord";
        gameDev="TaleWorlds Entertainment";
        desc="The horns sound, the ravens gather. An empire is torn by civil war. Beyond its borders, new kingdoms rise. Gird on your sword, don your armour, summon your followers and ride forth to win glory on the battlefields of Calradia. Establish your hegemony and create a new world out of the ashes of the old.";
        price=223.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Survival");
        genres.add("Zombies");
        genres.add("Multiplayer");
        genres.add("Open World");
        genres.add("Action");
        genres.add("PvP");
        genres.add("Shooter");
        genres.add("Massively Multiplayer");
        genres.add("Horror");
        genres.add("Simulation");
        genres.add("Early Access");
        genres.add("Post-apocalyptic");
        genres.add("FPS");
        genres.add("First-Person");
        genres.add("Survival Horror");
        genres.add("Sandbox");
        genres.add("Adventure");
        genres.add("Co-op");
        genres.add("Indie");
        genres.add("Atmospheric");

        gameName="DayZ";
        gameDev="Bohemia Interactive";
        desc="How long can you survive a post-apocalyptic world? A land overrun with an infected zombie population, where you compete with other survivors for limited resources. Will you team up with strangers and stay strong together? Or play as a lone wolf to avoid betrayal? This is DayZ � this is your story.";
        price=119.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("Open World");
        genres.add("Adventure");
        genres.add("Female Protagonist");
        genres.add("Action");
        genres.add("RPG");
        genres.add("Singleplayer");
        genres.add("Story Rich");
        genres.add("Post-apocalyptic");
        genres.add("Robots");
        genres.add("Sci-fi");
        genres.add("Exploration");
        genres.add("Third Person");
        genres.add("Beautiful");
        genres.add("Atmospheric");
        genres.add("Great Soundtrack");
        genres.add("Action RPG");
        genres.add("Archery");
        genres.add("Hunting");
        genres.add("Dinosaurs");
        genres.add("Stealth");

        gameName="Horizon Zero Dawn? Complete Edition";
        gameDev="Guerrilla";
        desc="Experience Aloy�s legendary quest to unravel the mysteries of a future Earth ruled by Machines. Use devastating tactical attacks against your prey and explore a majestic open world in this award-winning action RPG!";
        price=279.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);
        genres=new ArrayList();
        genres.add("RPG");
        genres.add("Story Rich");
        genres.add("Choices Matter");
        genres.add("Detective");
        genres.add("Isometric");
        genres.add("Atmospheric");
        genres.add("CRPG");
        genres.add("Singleplayer");
        genres.add("Great Soundtrack");
        genres.add("Noir");
        genres.add("Character Customization");
        genres.add("Open World");
        genres.add("Surreal");
        genres.add("Adventure");
        genres.add("Indie");
        genres.add("Exploration");
        genres.add("Funny");
        genres.add("Point & Click");
        genres.add("Sexual Content");
        genres.add("Fantasy");

        gameName="Disco Elysium - The Final Cut";
        gameDev="ZA/UM";
        desc="Disco Elysium - The Final Cut is a groundbreaking role playing game. You�re a detective with a unique skill system at your disposal and a whole city to carve your path across. Interrogate unforgettable characters, crack murders or take bribes. Become a hero or an absolute disaster of a human being.";
        price=87.0;
        try {
            newGame=new Game(gameName,gameDev,desc,price,genres);
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        games.add(newGame);

        //add a default guest and registered user
        users=new ArrayList<User>();
        User guest = null;
        try {
            guest = new Guest();
        } catch (IncorrectFormat incorrectFormat) {
            incorrectFormat.printStackTrace();
        }
        users.add(guest);

        User user = null;
        try{
            user = new RegisteredUser("RegisteredUser","registerPass");
        }
        catch (IncorrectFormat incorrectFormat){
            incorrectFormat.printStackTrace();
        }
        users.add(user);
    }


}
