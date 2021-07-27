package comp3350.grs.business;

import java.util.List;

import comp3350.grs.application.Services;
import comp3350.grs.objects.VoteReply;
import comp3350.grs.persistence.DataAccessI;
//business object of VoteReply
public class AccessVoteReplys {
    private DataAccessI dataAccessI;

    public AccessVoteReplys(){
        dataAccessI= Services.getDataAccess();
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

    //clear data of all vote replys
    public void clear(){
        dataAccessI.clearVoteReplys();
    }

    //get a list of voteReply by its reply id
    public List<VoteReply> getVoteReplysByReply(int replyID){
        return dataAccessI.getVoteReplysByReply(replyID);
    }

    //get a voteReply by its userID and replyID
    public VoteReply getVoteReply(String userID, int replyID){
        return dataAccessI.getVoteReply(userID, replyID);
    }

}
