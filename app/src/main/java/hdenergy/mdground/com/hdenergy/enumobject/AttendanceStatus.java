package hdenergy.mdground.com.hdenergy.enumobject;

/**
 * 上班状态
 * Created by yoghourt on 7/3/16.
 */

public enum AttendanceStatus {

    Normal(0),              // 正常
    Business (1),           // 出差
    Leave (2),              // 请假
    Injury (3),             // 工伤
    Dispatch (4),           // 派遣
    Shift (5),              // 换休
    NotSubmitted  (6);      // 未提交

    private int value;

    private AttendanceStatus(int productStatus) {
        this.value = productStatus;
    }

    public int value() {
        return value;
    }

    public static AttendanceStatus fromValue(int value) {
        for (AttendanceStatus type : AttendanceStatus.values()) {
            if (type.value() == value) {
                return type;
            }
        }
        return null;
    }
}
