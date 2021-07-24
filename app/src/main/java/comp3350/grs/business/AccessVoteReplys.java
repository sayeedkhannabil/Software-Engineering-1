package comp3350.grs.business;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import comp3350.grs.application.Main;
import comp3350.grs.application.Services;
import comp3350.grs.objects.Game;
import comp3350.grs.objects.VoteI;
import comp3350.grs.objects.VoteReply;
import comp3350.grs.persistence.DataAccessI;

public class AccessVoteReplys {
    private DataAccessI dataAccessI;
    private List<VoteReply> voteReplyList;
    private VoteReply currVoteReply;

    public AccessVoteReplys(){
        dataAccessI= Services.getDataAccess(Main.dbName);
        voteReplyList=null;
    }

    public boolean insertVoteReply(VoteReply voteReply){
        return dataAccessI.insertVoteReply(voteReply);
    }

    public boolean updateVoteReply(VoteReply voteReply){
        return dataAccessI.updateVoteReply(voteReply);
    }

    public boolean deleteVoteReply(VoteReply voteReply){
        return dataAccessI.deleteVoteReply(voteReply);
    }

    public List<VoteReply> getVoteReplysByReply(int replyID){
        voteReplyList=dataAccessI.getVoteReplysByReply(replyID);
        return voteReplyList;
    }

    public VoteReply getVoteReply(String userID, int replyID){
        currVoteReply= dataAccessI.getVoteReply(userID, replyID);
        return currVoteReply;
    }


}
