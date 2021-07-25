package comp3350.grs.business;

import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.exceptions.Duplicate;
import comp3350.grs.objects.Request;
import comp3350.grs.persistence.DataAccessI;

public class AccessRequests {
    private DataAccessI dataAccessI;
    private List<Request> requestList;
    private Request currentRequest;
    private int currentRequestIndex;

    public AccessRequests(){
        dataAccessI = Services.getDataAccess(Main.dbName);
        requestList = null;
        currentRequest = null;
        currentRequestIndex = 0;
    }

    public void clear(){
        dataAccessI.clearRequests();
    }

    //get a list of all requests
    public List<Request> getAllRequests() {
        requestList = dataAccessI.getAllRequests();
        return requestList;
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
