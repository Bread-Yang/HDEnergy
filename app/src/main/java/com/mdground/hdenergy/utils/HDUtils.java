package com.mdground.hdenergy.utils;

import com.mdground.hdenergy.models.ProjectWorkFlowrate;
import com.socks.library.KLog;

import java.util.List;

/**
 * Created by yoghourt on 7/22/16.
 */

public class HDUtils {

    public static float caculateSingleFlow(boolean isHeatProduct, float beginFlow, float endFlow) {
        float resultFlow = 0;

        KLog.e("beginFlow : " + beginFlow);
        KLog.e("endFlow : " + endFlow);

        resultFlow = endFlow - beginFlow;
        if (resultFlow < 0 ) {
            resultFlow = 0;
        }
        if (isHeatProduct) {   // 当销售产品选择热力时，流量位置 =（截止流量 - 起始流量）* 23.8845 / 60，单位用吨
            resultFlow = (float) resultFlow * 23.8845f / 60f;
        }

        KLog.e("resultFlow : " + resultFlow);

        return resultFlow;
    }

    // 流量总和
    public static float calculateFlowAmount(boolean isHeatProduct,
                                            List<ProjectWorkFlowrate> projectWorkFlowrateArrayList) {
        float flowAmount = 0;
        for (ProjectWorkFlowrate projectWorkFlowrate : projectWorkFlowrateArrayList) {
            flowAmount += caculateSingleFlow(isHeatProduct, projectWorkFlowrate.getBeginFlow(), projectWorkFlowrate.getEndFlow())
                    + projectWorkFlowrate.getAdjustFlow();
        }
        KLog.e("flowAmount : " + flowAmount);
        return flowAmount;
    }
}
