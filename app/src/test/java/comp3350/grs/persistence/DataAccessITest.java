package comp3350.grs.persistence;

import org.junit.Before;
import org.junit.Test;

import java.util.List;


import comp3350.grs.objects.Game;
import comp3350.grs.objects.Guest;
import comp3350.grs.objects.RegisteredUser;
import comp3350.grs.objects.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class DataAccessITest {
    private DataAccessI dataAccessI;
    private User user1,user2,user3;
    private Game game1,game2,game3;
    private List<User> userList;
    private List<Game> gameList;
    private String userID,userID2,password,password2,gameName,developer,
            description;
    private double price;

    @Before
    public void initiateDB(){
        dataAccessI=new DataAccessObject("TestDB");
//       dataAccessI=new DataAccessStub("TestDB");

        dataAccessI.open("database/TestDB");
        dataAccessI.clearTable();
        user1=null;
        user2=null;
        user3=null;
        game1=null;
        game2=null;
        game3=null;
        userList=null;
        gameList=null;
        userID=null;
        userID2=null;
        password=null;
        password2=null;
        gameName=null;
        developer=null;
        description=null;
        price=0.0;
    }

    @Test
    public void testTypical(){
        boolean insertSuccess=false;
        try {
            user1=new Guest();
            insertSuccess=dataAccessI.insertUser(user1);
            assert (insertSuccess);
            userList=dataAccessI.getAllUsers();
            assert (userList.size()==1);
            user2=userList.get(0);
            assert (user2.equals(user1));
            user2=dataAccessI.getOneUser(user1);
            assert (user2.getUserID().equals("Guest"));

            userID="myUserID";
            password="myPassword";
            user1=new RegisteredUser(userID,password);
            insertSuccess=dataAccessI.insertUser(user1);
            assert (insertSuccess);
            userList=dataAccessI.getAllUsers();
            assert (userList.size()==2);
            user2=dataAccessI.getOneUser(user1);
            assert (user2.getUserID().equals(userID));
            user2=(RegisteredUser)user2;
            assert (((RegisteredUser) user2).getPassword().equals(password));

            password2="anotherPassword";
            user1=new RegisteredUser(userID,password2);
            dataAccessI.updateUser(user1);
            userList=dataAccessI.getAllUsers();
            assert (userList.size()==2);
            user2=(RegisteredUser) dataAccessI.getOneUser(user1);
            assert (user2.getUserID().equals(userID));
            assert (((RegisteredUser) user2).getPassword().equals(password2));
            assertFalse(((RegisteredUser) user2).getPassword().equals(password));

            dataAccessI.deleteUser(user1);
            userList=dataAccessI.getAllUsers();
            assert (userList.size()==1);
            user2= dataAccessI.getOneUser(user1);
            assertNull(user2);


            game3=new Game("otherGame1");
            dataAccessI.insertGame(game3);
            gameList=dataAccessI.getAllGames();
            assert (gameList.size()==1);


            gameName="myGame";
            developer="myDeveloper";
            description="myDescription";
            price=9.99;
            game1=new Game(gameName,developer,description,price);
            insertSuccess=dataAccessI.insertGame(game1);
            assert (insertSuccess) ;
            gameList=dataAccessI.getAllGames();
            assert (gameList.size()==2);
            game2=dataAccessI.getOneGame(game1);
            assert (game2.getName().equals(gameName));
            assert (game2.getDev().equals(developer));
            assert (game2.getDescription().equals(description));
            assertEquals(price,game2.getPrice(),0.01);

            game3=new Game("otherGame2");
            dataAccessI.insertGame(game3);
            gameList=dataAccessI.getAllGames();
            assert (gameList.size()==3);

            developer="anotherDeveloper";
            description="anotherDescription";
            price=99.99;
            game1=new Game(gameName,developer,description,price);
            dataAccessI.updateGame(game1);
            gameList=dataAccessI.getAllGames();
            assert (gameList.size()==3);
            game2=dataAccessI.getOneGame(game1);
            assert (game2.getName().equals(gameName));
            assert (game2.getDev().equals(developer));
            assert (game2.getDescription().equals(description));
            assertEquals(price,game2.getPrice(),0.01);

            dataAccessI.deleteGame(game1);
            gameList=dataAccessI.getAllGames();
            assert (gameList.size()==2);
            game2=dataAccessI.getOneGame(game1);
            assertNull(game2);

            dataAccessI.clearTable();
            userList=dataAccessI.getAllUsers();
            assert (userList.size()==0);
            gameList=dataAccessI.getAllGames();
            assert (gameList.size()==0);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}