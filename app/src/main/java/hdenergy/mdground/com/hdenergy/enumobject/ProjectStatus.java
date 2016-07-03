package hdenergy.mdground.com.hdenergy.enumobject;

/**
 * 项目状态
 * Created by yoghourt on 7/3/16.
 */

public enum ProjectStatus {

    Normal(0),              // 正常
    UnderRepair(1),         // 维修
    Stoped(2);              // 停炉

    private int value;

    private ProjectStatus(int productStatus) {
        this.value = productStatus;
    }

    public int value() {
        return value;
    }

    public static ProjectStatus fromValue(int value) {
        for (ProjectStatus type : ProjectStatus.values()) {
            if (type.value() == value) {
                return type;
            }
        }
        return null;
    }
}
