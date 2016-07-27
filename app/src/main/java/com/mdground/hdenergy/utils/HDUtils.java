package com.mdground.hdenergy.utils;

import com.mdground.hdenergy.models.ProjectWorkFlowrate;

import java.util.ArrayList;

/**
 * Created by yoghourt on 7/22/16.
 */

public class HDUtils {

    public static float caculateSingleFlow(boolean isHeatProduct, float beginFlow, float endFlow) {
        float resultFlow = 0;

        resultFlow = endFlow - beginFlow;
        if (isHeatProduct) {   // 当销售产品选择热力时，流量位置 =（截止流量 - 起始流量）* 23.8845 / 60，单位用吨
            resultFlow = (int) (resultFlow * 23.8845 / 60);
        }
        return resultFlow;
    }

    // 流量总和
    public static float calculateFlowAmount(boolean isHeatProduct,
                                            ArrayList<ProjectWorkFlowrate> projectWorkFlowrateArrayList) {
        float flowAmount = 0;
        for (ProjectWorkFlowrate projectWorkFlowrate : projectWorkFlowrateArrayList) {
            flowAmount = caculateSingleFlow(isHeatProduct, projectWorkFlowrate.getBeginFlow(), projectWorkFlowrate.getEndFlow())
                    + projectWorkFlowrate.getAdjustFlow();
        }
        return flowAmount;
    }
}
