package hdenergy.mdground.com.hdenergy.models;

import java.util.List;

/**
 * ProjectWork
 * Created by yoghourt on 7/3/16.
 */
public class ProjectWork {

    private String CreatedTime;
    private int DailyExpense;
    private String ExpenseDetail;
    private int ProjectID;
    private String ProjectName;
    private List<ProjectWorkFurnace> ProjectWorkFurnaceList;
    private String Remark;
    private String SaleType;
    private int UserID;
    private String UserName;
    private int WorkID;

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }

    public int getDailyExpense() {
        return DailyExpense;
    }

    public void setDailyExpense(int dailyExpense) {
        DailyExpense = dailyExpense;
    }

    public String getExpenseDetail() {
        return ExpenseDetail;
    }

    public void setExpenseDetail(String expenseDetail) {
        ExpenseDetail = expenseDetail;
    }

    public int getProjectID() {
        return ProjectID;
    }

    public void setProjectID(int projectID) {
        ProjectID = projectID;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public List<ProjectWorkFurnace> getProjectWorkFurnaceList() {
        return ProjectWorkFurnaceList;
    }

    public void setProjectWorkFurnaceList(List<ProjectWorkFurnace> projectWorkFurnaceList) {
        ProjectWorkFurnaceList = projectWorkFurnaceList;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getSaleType() {
        return SaleType;
    }

    public void setSaleType(String saleType) {
        SaleType = saleType;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getWorkID() {
        return WorkID;
    }

    public void setWorkID(int workID) {
        WorkID = workID;
    }
}
