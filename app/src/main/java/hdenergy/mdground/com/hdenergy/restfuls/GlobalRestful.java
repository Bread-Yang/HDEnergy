package hdenergy.mdground.com.hdenergy.restfuls;

import hdenergy.mdground.com.hdenergy.constants.Constants;
import hdenergy.mdground.com.hdenergy.enumobject.restfuls.BusinessType;

/**
 * Created by yoghourt on 5/6/16.
 */
public class GlobalRestful extends BaseRestful {

    private static GlobalRestful mIntance = new GlobalRestful();

    @Override
    protected BusinessType getBusinessType() {
        return BusinessType.Global;
    }

    @Override
    protected String getHost() {
        return Constants.HOST;
    }


    private GlobalRestful() {

    }

    public static GlobalRestful getInstance() {
        if (mIntance == null) {
            mIntance = new GlobalRestful();
        }
        return mIntance;
    }
}
