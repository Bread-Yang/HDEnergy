package hdenergy.mdground.com.hdenergy.models;

import java.util.List;

/**
 * 锅炉汇报实体
 * Created by yoghourt on 7/3/16.
 */
public class ProjectWorkFurnace {

    private int FurnaceID;      // 锅炉标识
    private int WorkFurnaceID;  // PrimaryKey，标识
    private int WorkID;         // 数据汇报标识
    private String FurnaceName; // 锅炉名称
    private int ProjectID;      // 项目名称
    private int WorkingHour;    // 开炉时长（以分作为单位）
    private int Electricity1;   // 电量1
    private int Electricity2;   // 电量2
    private int Electricity3;   // 电量3
    private int ElectricitySingleCost; // 电单耗
    private int Water1;         // 水量1
    private int Water2;         // 水量2
    private int Water3;         // 水量3
    private int WaterSingleCost;// 水单耗
    private String CreatedTime;
    private List<ProjectWorkFlowrate> ProjectWorkFlowrateList; // 流量列表
    private List<ProjectWorkFuel> ProjectWorkFuelList;  // 燃料列表

}

