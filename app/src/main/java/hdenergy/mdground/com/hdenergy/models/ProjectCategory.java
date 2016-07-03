package hdenergy.mdground.com.hdenergy.models;

/**
 * Created by yoghourt on 7/3/16.
 */

public class ProjectCategory {

    private int CategoryID;
    private int ParentID;       // 父类别标识
    private String CategoryName;
    private String CreatedTime;

    public int getCategoryID() {
        return CategoryID;
    }

    public void setCategoryID(int categoryID) {
        CategoryID = categoryID;
    }

    public int getParentID() {
        return ParentID;
    }

    public void setParentID(int parentID) {
        ParentID = parentID;
    }

    public String getCategoryName() {
        return CategoryName;
    }

    public void setCategoryName(String categoryName) {
        CategoryName = categoryName;
    }

    public String getCreatedTime() {
        return CreatedTime;
    }

    public void setCreatedTime(String createdTime) {
        CreatedTime = createdTime;
    }
}
