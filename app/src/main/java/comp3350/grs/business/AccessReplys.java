package comp3350.grs.business;

import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.objects.Reply;
import comp3350.grs.persistence.DataAccessI;


public class AccessReplys {
    private DataAccessI dataAccessI;
    private List<Reply> replyList;
    private Reply currentReply;
    private int currentReplyIndex;

    public AccessReplys() {
        dataAccessI = Services.getDataAccess(Main.dbName);
        replyList = null;
        currentReply = null;
        currentReplyIndex = 0;
    }

    public void clear() {
        dataAccessI.clearReplys();
    }

    public List<Reply> getAllReplys() {
        replyList = dataAccessI.getAllReply();
        return replyList;
    }

    public Reply getSequential() {
        if (replyList == null) {
            getAllReplys();
            currentReplyIndex = 0;
        }

        if (currentReplyIndex < replyList.size()) {
            currentReply = replyList.get(currentReplyIndex);
            currentReplyIndex++;
        }

        else {
            replyList = null;
            currentReply = null;
            currentReplyIndex = 0;
        }

        return currentReply;
    }

    public List<Reply> getReplyByUser(String userID) {
        return dataAccessI.getReplyByUser(userID);
    }

    public Reply getReplyById(int replyID) {
        return dataAccessI.getReplyByID(replyID);
    }

    public boolean insertReply(Reply newReply) {
        return dataAccessI.insertReply(newReply);
    }

    public boolean updateReply(Reply uReply) {
        return dataAccessI.updateReply(uReply);
    }

    public boolean deleteReply(Reply dReply) {
        return dataAccessI.deleteReply(dReply);
    }
}
