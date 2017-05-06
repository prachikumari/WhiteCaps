package com.example.android.whitecaps;

/**
 * Created by User on 30-04-2017.
 */
//RoutineMgr

public class RoutineMgr  {
    LoginMgr mr;
    private String useremail;
    DataMgr helper;
    private String stream , semester, section ,period,day;
    TakeAttendance ta;

    //Getter and Setter
    public String getString() {
        return useremail;
    }

    public void setString(String h) {
        useremail=h;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getSemester() {return semester;}

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }


    //RotineMgr Constructor
    public RoutineMgr(String useremail , TakeAttendance ta1,String stream1 , String semester1 , String period1,String section,String day1)
    {
        setString(useremail);
        ta=ta1;
        setDay(day1);
        setSection(section);
        chckEmptyFields(stream1.trim() ,semester1.trim() ,  period1.trim(),section.trim());

    }

    //Checking Empty fields if any
    public void chckEmptyFields(String stream1 , String semester1 , String period1,String section1)
    {
        if(stream1.equals("Stream") || stream1==null)
            ta.showalert("Stream");
       else if(semester1.equals("Semester") || semester1==null)
            ta.showalert("Semester");
       else if(section1.equals("Section") || section1==null)
            ta.showalert("Section");
       else if(period1.equals("Period") || period1==null)
            ta.showalert("Period");

      else
        {
            //Setting fields
            setStream(stream1);
            setPeriod(period1);
           // setSection(section1);
            setSemester(semester1);
            return;
        }
    }

}



