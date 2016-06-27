package hdenergy.mdground.com.hdenergy.enumobject;

/**
 * Created by yoghourt on 5/16/16.
 */
public enum BoilerType {

    Flow(1),            // 流量
    Electricity(2),     // 电量
    Water(4);           // 水量

    private int value;

    private BoilerType(int product) {
        this.value = product;
    }

    public int value() {
        return value;
    }

    public static BoilerType fromValue(int value) {
        for (BoilerType type : BoilerType.values()) {
            if (type.value() == value) {
                return type;
            }
        }
        return null;
    }
}
