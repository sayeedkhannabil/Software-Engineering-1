package comp3350.grs.business;

import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
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

    // get next review sequentially
    public Request getSequential() {
        if (requestList == null) {
            getAllRequests();
            currentRequestIndex = 0;
        }

        if (currentRequestIndex < requestList.size()) {
            currentRequest = requestList.get(currentRequestIndex);
            currentRequestIndex++;
        }

        else {
            requestList = null;
            currentRequest = null;
            currentRequestIndex = 0;
        }

        return currentRequest;
    }

    public List<Request> getRequestsByUser(String userId) {
        return dataAccessI.getRequestsByUser(userId);
    }

    public Request getRequest(String gameName, String userID) {
        return dataAccessI.getRequest(gameName, userID);
    }

    public boolean insertRequest(Request newRequest) {
        return dataAccessI.insertRequest(newRequest);
    }

    public boolean updateRequest(Request updateRequest) {
        return dataAccessI.updateReview(updateRequest);
    }

    public boolean deleteRequest(Request deleteRequest) {
        return dataAccessI.deleteReview(deleteRequest);
    }
}
