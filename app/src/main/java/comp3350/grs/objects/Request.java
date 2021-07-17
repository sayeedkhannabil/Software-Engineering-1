package comp3350.grs.objects;

import comp3350.grs.exceptions.IncorrectFormat;

public class Request {
    private String gameName;
    private String userID;

    public Request(String gameName,String userID) throws IncorrectFormat {
        checkGameName(gameName);
        this.gameName=gameName;
        this.userID=userID;
    }

    public Request(){
        this.gameName=null;
        this.userID=null;
    }

    private void checkGameName(String gameName) throws IncorrectFormat {
        if (gameName.trim().equals("")){
            throw new IncorrectFormat("Game name cannot be blank/empty.");
        }
    }

    public boolean validRequest(){
        return this.gameName!=null&&this.userID!=null;
    }

    public String getGameName(){
        return this.gameName;
    }

    public String getUserID(){
        return this.userID;
    }

    @Override
    public boolean equals(Object object)
    {
        Request other = null;
        boolean isSame = false;

        if(object != null && validRequest() && object instanceof Request)
        {
            other = (Request) object;
            if(this.gameName.equals(other.gameName)&& this.userID.equals(other.userID))
            {
                isSame = true;
            }
        }
        return isSame;
    }

    @Override
    public String toString()
    {
        return "Game Name:"+this.gameName+",UserID:"+this.userID;
    }
}
