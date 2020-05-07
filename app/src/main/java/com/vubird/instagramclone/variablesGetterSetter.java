package com.vubird.instagramclone;


public class variablesGetterSetter
{
     private String PunchSpeed;
     private String PunchPower;
     private String kickSpeed;
     private String kickPower;
     private String name;
     private static variablesGetterSetter singleton;


    public variablesGetterSetter()
    {

    }

    public String getPunchSpeed()
    {
        return PunchSpeed;
    }

    public void setPunchSpeed(String punchSpeed)
    {
        PunchSpeed = punchSpeed;
    }

    public String getPunchPower()
    {
        return PunchPower;
    }

    public void setPunchPower(String punchPower)
    {
        PunchPower = punchPower;
    }

    public String getKickSpeed()
    {
        return kickSpeed;
    }

    public void setKickSpeed(String kickSpeed) {
        this.kickSpeed = kickSpeed;
    }

    public String getKickPower() {
        return kickPower;
    }

    public void setKickPower(String kickPower) {
        this.kickPower = kickPower;
    }

    public static variablesGetterSetter getInstance()
    {

        if(singleton == null)

        {
            singleton = new variablesGetterSetter();
        }
        return singleton;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
