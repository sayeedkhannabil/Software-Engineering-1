package comp3350.grs.business;

import java.util.List;

import comp3350.grs.application.Services;
import comp3350.grs.exceptions.Duplicate;
import comp3350.grs.objects.Request;
import comp3350.grs.persistence.DataAccessI;
//business object of request
public class AccessRequests {
    private DataAccessI dataAccessI;

    public AccessRequests(){
        dataAccessI = Services.getDataAccess();
    }

    public void clear(){
        dataAccessI.clearRequests();
    }

    //get a list of all requests
    public List<Request> getAllRequests() {
        return dataAccessI.getAllRequests();
    }

    public boolean checkGameExists(Request newRequest) throws Duplicate {
        String gameRequested = newRequest.getGameName();
        boolean gameAlreadyExists = dataAccessI.getAllGames().contains(dataAccessI.getGameByName(gameRequested));
        if(gameAlreadyExists){
            throw new Duplicate("The game already exists in the gallery. Request can't be made.");
        }
        return gameAlreadyExists;
    }

    public List<Request> getRequestsByUser(String userID) {
        return dataAccessI.getRequestsByUser(userID);
    }

    public List<Request> getRequestsByGame(String gameName){
        return dataAccessI.getRequestsByGame(gameName);
    }

    public Request getRequest(String gameName, String userID) {
        return dataAccessI.getRequest(gameName, userID);
    }

    public boolean insertRequest(Request newRequest) throws Duplicate{
        checkGameExists(newRequest);
        return dataAccessI.insertRequest(newRequest);
    }

    public boolean deleteRequest(Request deleteRequest) {
        return dataAccessI.deleteRequest(deleteRequest);
    }

    public List<String> getGamesByRequestNum(int limit){
        return dataAccessI.getGamesOrderByRequestNum(limit);
    }
}
