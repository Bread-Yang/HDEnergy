package com.mdground.hdenergy.activity.datareport;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.databinding.ActivityBoilerEditOneBinding;
import com.mdground.hdenergy.databinding.ItemBoilerFlowBinding;
import com.mdground.hdenergy.models.ProjectWorkFlowrate;
import com.mdground.hdenergy.models.ProjectWorkFurnace;
import com.mdground.hdenergy.utils.HDUtils;
import com.mdground.hdenergy.utils.StringUtils;
import com.mdground.hdenergy.utils.ViewUtils;
import com.mdground.hdenergy.views.BaoPickerDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;

/**
 * Created by yoghourt on 2016-06-27.
 */
public class BoilerEditOneActivity extends ToolbarActivity<ActivityBoilerEditOneBinding> {

    private BaoPickerDialog mBaoPickerDialog;

    private EditOneAdapter mAdapter;

    private ProjectWorkFurnace mProjectWorkFurnace;

    private ArrayList<ProjectWorkFlowrate> mProjectWorkFlowrateArrayList = new ArrayList<>();

    private float mLastEndFlow;

    private boolean mIsHeatProduct; // 销售产品是否是"热力"

    private int mSelectHourIndex;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_boiler_edit_one;
    }

    @Override
    protected void initData() {
        mLastEndFlow = getIntent().getFloatExtra(Constants.KEY_LAST_END_FLOW, 0);
        mIsHeatProduct = getIntent().getBooleanExtra(Constants.KEY_IS_HEAT_SALE_PRODUCT, false);
        if (mIsHeatProduct) {
            // 若销售产品为热力，则水量整个layout是没有的
            mDataBinding.lltWater.setVisibility(View.GONE);
        }

        mProjectWorkFurnace = getIntent().getParcelableExtra(Constants.KEY_PROJECT_WORK_FURNACE);

        // 开炉时长
        int workingHour = mProjectWorkFurnace.getWorkingHour();
        if (workingHour == 0) {
            workingHour = 24;
            mProjectWorkFurnace.setWorkingHour(24);
        }
        mDataBinding.tvHours.setText(getString(R.string.with_hour_unit, workingHour));

        ArrayList<ProjectWorkFlowrate> projectWorkFlowrateList = (ArrayList<ProjectWorkFlowrate>) mProjectWorkFurnace.getProjectWorkFlowrateList();
        if (projectWorkFlowrateList != null) {
            mProjectWorkFlowrateArrayList.clear();
            mProjectWorkFlowrateArrayList.addAll(projectWorkFlowrateList);
            //锅炉增加一个流量
            if (mProjectWorkFlowrateArrayList.size() == 0) {
                mProjectWorkFlowrateArrayList.add(createFlowrate());
            }
        } else {
            mProjectWorkFlowrateArrayList.add(createFlowrate());
        }

        // 设置电量
        if (mProjectWorkFurnace.getElectricity1() != 0) {
            mDataBinding.etuiElectricQuantityOne.setText(
                    String.valueOf(mProjectWorkFurnace.getElectricity1()));
        }
        if (mProjectWorkFurnace.getElectricity2() != 0) {
            mDataBinding.rltElectricQuantityTwo.setVisibility(View.VISIBLE);

            mDataBinding.etuiElectricQuantityTwo.setText(
                    String.valueOf(mProjectWorkFurnace.getElectricity2()));
        }
        if (mProjectWorkFurnace.getElectricity3() != 0) {
            mDataBinding.rltElectricQuantityThree.setVisibility(View.VISIBLE);

            mDataBinding.etuiElectricQuantityThree.setText(
                    String.valueOf(mProjectWorkFurnace.getElectricity3()));
        }
        calculateElectricityUnitConsumption();

        // 设置水量
        if (mProjectWorkFurnace.getWater1() != 0) {
            mDataBinding.etuiWaterQuantityOne.setText(
                    String.valueOf(mProjectWorkFurnace.getWater1()));
        }
        if (mProjectWorkFurnace.getWater2() != 0) {
            mDataBinding.rltWaterQuantityTwo.setVisibility(View.VISIBLE);

            mDataBinding.etuiWaterQuantityTwo.setText(
                    String.valueOf(mProjectWorkFurnace.getWater2()));
        }
        if (mProjectWorkFurnace.getWater3() != 0) {
            mDataBinding.rltWaterQuantityThree.setVisibility(View.VISIBLE);

            mDataBinding.etuiWaterQuantityThree.setText(
                    String.valueOf(mProjectWorkFurnace.getWater3()));
        }
        calculateWaterUnitConsumption();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(layoutManager);
        mDataBinding.recyclerView.setNestedScrollingEnabled(false);
        mDataBinding.recyclerView.setFocusable(false);

        mAdapter = new EditOneAdapter();
        mDataBinding.recyclerView.setAdapter(mAdapter);

        initPickerDialog();
    }

    @Override
    protected void setListener() {
        mBaoPickerDialog.setOnWheelScrollListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                mSelectHourIndex = wheel.getCurrentItem();

                int hours = mSelectHourIndex;

                mDataBinding.tvHours.setText(getString(R.string.with_hour_unit, hours));
            }
        });

        // 电量增加
        mDataBinding.ivAddOrDeleteElectricQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.rltElectricQuantityTwo.getVisibility()
                        == View.GONE) {
                    mDataBinding.rltElectricQuantityTwo.setVisibility(View.VISIBLE);
                } else if (mDataBinding.rltElectricQuantityThree.getVisibility()
                        == View.GONE) {
                    mDataBinding.rltElectricQuantityThree.setVisibility(View.VISIBLE);
                }
            }
        });

        // 电量1
        mDataBinding.etuiElectricQuantityOne.getEtInput().setOnlyOneTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                float electricity1 = StringUtils.convertStringToFloat(s.toString());
                mProjectWorkFurnace.setElectricity1(electricity1);
                calculateElectricityUnitConsumption();
            }
        });
//        mDataBinding.etuiElectricQuantityOne.getEtInput().setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (!b) {
//                    float electricity1 = StringUtils.convertStringToFloat(((EditText) view).getText().toString());
//                    mProjectWorkFurnace.setElectricity1(electricity1);
//                    calculateElectricityUnitConsumption();
//                }
//            }
//        });

        // 电量2
        mDataBinding.etuiElectricQuantityTwo.getEtInput().setOnlyOneTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                float electricity2 = StringUtils.convertStringToFloat(s.toString());
                mProjectWorkFurnace.setElectricity2(electricity2);
                calculateElectricityUnitConsumption();
            }
        });
//        mDataBinding.etuiElectricQuantityTwo.getEtInput().setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (!b) {
//                    float electricity2 = StringUtils.convertStringToFloat(((EditText) view).getText().toString());
//                    mProjectWorkFurnace.setElectricity2(electricity2);
//                    calculateElectricityUnitConsumption();
//                }
//            }
//        });

        // 电量3
        mDataBinding.etuiElectricQuantityThree.getEtInput().setOnlyOneTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                float electricity3 = StringUtils.convertStringToFloat(s.toString());
                mProjectWorkFurnace.setElectricity3(electricity3);
                calculateElectricityUnitConsumption();
            }
        });
//        mDataBinding.etuiElectricQuantityThree.getEtInput().setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (!b) {
//                    float electricity3 = StringUtils.convertStringToFloat(((EditText) view).getText().toString());
//                    mProjectWorkFurnace.setElectricity3(electricity3);
//                    calculateElectricityUnitConsumption();
//                }
//            }
//        });

        // 电量2删除
        mDataBinding.etuiElectricQuantityTwo.getIvIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.rltElectricQuantityTwo.setVisibility(View.GONE);
                mDataBinding.etuiElectricQuantityTwo.setText(String.valueOf(0));
                mProjectWorkFurnace.setElectricity2(0);
            }
        });

        // 电量3删除
        mDataBinding.etuiElectricQuantityThree.getIvIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.rltElectricQuantityThree.setVisibility(View.GONE);
                mDataBinding.etuiElectricQuantityThree.setText(String.valueOf(0));
                mProjectWorkFurnace.setElectricity3(0);
            }
        });

        // 水量增加
        mDataBinding.ivAddOrDeleteWaterQuantity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBinding.rltWaterQuantityTwo.getVisibility()
                        == View.GONE) {
                    mDataBinding.rltWaterQuantityTwo.setVisibility(View.VISIBLE);
                } else if (mDataBinding.rltWaterQuantityThree.getVisibility()
                        == View.GONE) {
                    mDataBinding.rltWaterQuantityThree.setVisibility(View.VISIBLE);
                }
            }
        });

        // 水量1
        mDataBinding.etuiWaterQuantityOne.getEtInput().setOnlyOneTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                float water1 = StringUtils.convertStringToFloat(s.toString());
                mProjectWorkFurnace.setWater1(water1);
                calculateWaterUnitConsumption();
            }
        });
//        mDataBinding.etuiWaterQuantityOne.getEtInput().setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (!b) {
//                    float water1 = StringUtils.convertStringToFloat(((EditText) view).getText().toString());
//                    mProjectWorkFurnace.setWater1(water1);
//                    calculateWaterUnitConsumption();
//                }
//            }
//        });

        // 水量2
        mDataBinding.etuiWaterQuantityTwo.getEtInput().setOnlyOneTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                float water2 = StringUtils.convertStringToFloat(s.toString());
                mProjectWorkFurnace.setWater2(water2);
                calculateWaterUnitConsumption();
            }
        });
//        mDataBinding.etuiWaterQuantityTwo.getEtInput().setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (!b) {
//                    float water2 = StringUtils.convertStringToFloat(((EditText) view).getText().toString());
//                    mProjectWorkFurnace.setWater2(water2);
//                    calculateWaterUnitConsumption();
//                }
//            }
//        });

        // 水量3
        mDataBinding.etuiWaterQuantityThree.getEtInput().setOnlyOneTextWatcher(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                float water3 = StringUtils.convertStringToFloat(s.toString());
                mProjectWorkFurnace.setWater3(water3);
                calculateWaterUnitConsumption();
            }
        });
//        mDataBinding.etuiWaterQuantityThree.getEtInput().setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (!b) {
//                    float water3 = StringUtils.convertStringToFloat(((EditText) view).getText().toString());
//                    mProjectWorkFurnace.setWater3(water3);
//                    calculateWaterUnitConsumption();
//                }
//            }
//        });

        // 水量2删除
        mDataBinding.etuiWaterQuantityTwo.getIvIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.rltWaterQuantityTwo.setVisibility(View.GONE);
                mDataBinding.etuiWaterQuantityTwo.setText(String.valueOf(0));
                mProjectWorkFurnace.setWater2(0);
            }
        });

        // 水量3删除
        mDataBinding.etuiWaterQuantityThree.getIvIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.rltWaterQuantityThree.setVisibility(View.GONE);
                mDataBinding.etuiWaterQuantityThree.setText(String.valueOf(0));
                mProjectWorkFurnace.setWater3(0);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            setResult(RESULT_OK, data);
            finish();
        }
    }

    private void initPickerDialog() {
        mBaoPickerDialog = new BaoPickerDialog(this);

        ArrayList<String> hourArrayList = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            hourArrayList.add(String.valueOf(i));
        }

        int currentIndex = mProjectWorkFurnace.getWorkingHour();
        if (currentIndex == 0) {
            currentIndex = 24;
        }
        mBaoPickerDialog.bindWheelViewData(hourArrayList, true, currentIndex);
    }

    private ProjectWorkFlowrate createFlowrate() {
        ProjectWorkFlowrate projectWorkFlowrate = new ProjectWorkFlowrate();
        projectWorkFlowrate.setBeginFlow(mLastEndFlow);
        projectWorkFlowrate.setWorkFurnaceID(mProjectWorkFurnace.getWorkFurnaceID());
        return projectWorkFlowrate;
    }

    // 计算电单耗, 电单耗 = 电量总和 / 流量
    private void calculateElectricityUnitConsumption() {
        // 电量总和
        float electricityAmount = mProjectWorkFurnace.getElectricity1()
                + mProjectWorkFurnace.getElectricity2() + mProjectWorkFurnace.getElectricity3();

        float flowAmount = HDUtils.calculateFlowAmount(mIsHeatProduct, mProjectWorkFlowrateArrayList);

        float electircityUnitConsumption = 0;
        if (flowAmount != 0) {
            electircityUnitConsumption = electricityAmount / flowAmount;
        }
        mProjectWorkFurnace.setElectricitySingleCost(electircityUnitConsumption);

        if (mIsHeatProduct) {
            mDataBinding.tvElectircUnitConsumption.setText(
                    getString(R.string.kw_per_ton, electircityUnitConsumption));
        } else {
            mDataBinding.tvElectircUnitConsumption.setText(
                    getString(R.string.kw_per_ton_steam, electircityUnitConsumption));
        }
    }

    // 计算水单耗, 若销售产品为热力，则没有该选项，水单耗等于水量总和 / 流量
    private void calculateWaterUnitConsumption() {
        // 水量总和
        float waterAmount = mProjectWorkFurnace.getWater1()
                + mProjectWorkFurnace.getWater2() + mProjectWorkFurnace.getWater3();

        float flowAmount = HDUtils.calculateFlowAmount(mIsHeatProduct, mProjectWorkFlowrateArrayList);

        float waterUnitConsumption = 0;
        if (flowAmount != 0) {
            waterUnitConsumption = waterAmount / flowAmount;
        }
        mProjectWorkFurnace.setWaterSingleCost(waterUnitConsumption);

        if (mIsHeatProduct) {
            mDataBinding.tvWaterUnitConsumption.setText(
                    getString(R.string.ton_per_ton, waterUnitConsumption));
        } else {
            mDataBinding.tvWaterUnitConsumption.setText(
                    getString(R.string.ton_per_ton_steam, waterUnitConsumption));
        }
    }

    private void refreshUnitConsumption() {
        calculateElectricityUnitConsumption();
        calculateWaterUnitConsumption();
    }

    private boolean isHeader(int positon) {
        if (positon == 0) {
            return true;
        } else if (positon == mProjectWorkFlowrateArrayList.size() || positon == (mProjectWorkFlowrateArrayList.size() + 1)) {
            return true;
        }
        return false;
    }

    //region  ACTION
    public void selectBlowInDurationAction(View view) {
        mBaoPickerDialog.show();
    }

    public void nextStepAction(View view) {
//        for (ProjectWorkFlowrate projectWorkFlowrate : mProjectWorkFlowrateArrayList) {
//            if (projectWorkFlowrate.getBeginFlow() == 0 || projectWorkFlowrate.getEndFlow() == 0) {
//                ViewUtils.toast(R.string.fill_flow_info);
//                return;
//            }
//        }

//        if (mProjectWorkFurnace.getElectricity1() == 0) {
//            ViewUtils.toast(R.string.fill_electric_info);
//            return;
//        }
//
//        if (mDataBinding.rltElectricQuantityTwo.getVisibility() == View.VISIBLE) {
//            if (mProjectWorkFurnace.getElectricity2() == 0) {
//                ViewUtils.toast(R.string.fill_electric_info);
//                return;
//            }
//        }
//
//        if (mDataBinding.rltElectricQuantityThree.getVisibility() == View.VISIBLE) {
//            if (mProjectWorkFurnace.getElectricity3() == 0) {
//                ViewUtils.toast(R.string.fill_electric_info);
//                return;
//            }
//        }

//        if (mProjectWorkFurnace.getWater1() == 0) {
//            ViewUtils.toast(R.string.fill_water_info);
//            return;
//        }
//
//        if (mDataBinding.rltWaterQuantityTwo.getVisibility() == View.VISIBLE) {
//            if (mProjectWorkFurnace.getWater2() == 0) {
//                ViewUtils.toast(R.string.fill_water_info);
//                return;
//            }
//        }
//
//        if (mDataBinding.rltWaterQuantityThree.getVisibility() == View.VISIBLE) {
//            if (mProjectWorkFurnace.getWater3() == 0) {
//                ViewUtils.toast(R.string.fill_water_info);
//                return;
//            }
//        }

        for (ProjectWorkFlowrate projectWorkFlowrate : mProjectWorkFlowrateArrayList) {
            float closeFlow = projectWorkFlowrate.getEndFlow();
            float startFlow = projectWorkFlowrate.getBeginFlow();

            if (closeFlow < startFlow) {
                closeFlow = startFlow;
                ViewUtils.toast(R.string.close_flow_must_bigger_than_initial_flow);
                return;
            }
        }

        // 开炉时长
        mProjectWorkFurnace.setWorkingHour(mSelectHourIndex);

        // 电量1,2,3,水量1,2,3已经在setListener()中赋值了

        // 流量列表
        mProjectWorkFurnace.setProjectWorkFlowrateList(mProjectWorkFlowrateArrayList);

        float flowAmount = HDUtils.calculateFlowAmount(mIsHeatProduct, mProjectWorkFlowrateArrayList);
        if (flowAmount == 0) {
            ViewUtils.toast(R.string.flow_amount_not_zero);
            return;
        }

        Intent intent = new Intent(this, BoilerEditTwoActivity.class);
        intent.putExtra(Constants.KEY_PROJECT_WORK_FURNACE, mProjectWorkFurnace);
        intent.putExtra(Constants.KEY_IS_HEAT_SALE_PRODUCT, mIsHeatProduct);
        intent.putExtra(Constants.KEY_FLOW_AMOUNT, flowAmount);
        startActivityForResult(intent, 0);
    }
    //endregion

    //region ADAPTER
    class EditOneAdapter extends RecyclerView.Adapter<EditOneAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_boiler_flow, parent, false);
            ViewHolder holder = new ViewHolder(itemView);
            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            final ProjectWorkFlowrate projectWorkFlowrate = mProjectWorkFlowrateArrayList.get(position);
            final ItemBoilerFlowBinding itemBoilerFlowBinding = ((ItemBoilerFlowBinding) holder.viewDataBinding);
            itemBoilerFlowBinding.setFlow(projectWorkFlowrate);
            itemBoilerFlowBinding.setPosition(position + 1);

            itemBoilerFlowBinding.etuiInitialFlow.getEtInput().setText(String.valueOf(projectWorkFlowrate.getBeginFlow()));

            if (projectWorkFlowrate.getEndFlow() != 0) {
                itemBoilerFlowBinding.etuiCloseFlow.getEtInput().setText(String.valueOf(projectWorkFlowrate.getEndFlow()));
            }

            if (projectWorkFlowrate.getAdjustFlow() != 0) {
                itemBoilerFlowBinding.etuiAdjustFlow.getEtInput().setText(String.valueOf(projectWorkFlowrate.getAdjustFlow()));
            }

            double flow;
            if (mIsHeatProduct) {
                flow = (projectWorkFlowrate.getEndFlow() - projectWorkFlowrate.getBeginFlow()) * 23.8845 / 60;
            } else {
                flow = (projectWorkFlowrate.getEndFlow() - projectWorkFlowrate.getBeginFlow());
            }
            DecimalFormat df = new DecimalFormat("#####0.00");
            //   mFlowAmount= (int) (flow+mFlowAmount);

            String s = df.format(flow);
            itemBoilerFlowBinding.tvResultFlow.setText(s + getString(R.string.ton));

            itemBoilerFlowBinding.etAjustDescription.setText(projectWorkFlowrate.getDescription());

            ViewUtils.setEditTextWithMinusAndPlusSignal(itemBoilerFlowBinding.etuiAdjustFlow.getEtInput());

//            itemBoilerFlowBinding.etuiAdjustFlow.getEtInput().setInputType(InputType.TYPE_CLASS_TEXT);
//            itemBoilerFlowBinding.etuiAdjustFlow.getEtInput().setFilters(new InputFilter[]{new PlusAndMinusSignInputFilter()});

            ImageView ivAddOrDelete = itemBoilerFlowBinding.ivAddOrDelete;
            if (isHeader(position)) {
                ivAddOrDelete.setImageResource(R.drawable.add);

                ivAddOrDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProjectWorkFlowrateArrayList.add(createFlowrate());
                        mAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                ivAddOrDelete.setImageResource(R.drawable.delete);

                ivAddOrDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProjectWorkFlowrateArrayList.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }

            itemBoilerFlowBinding.etuiInitialFlow.getEtInput().setOnlyOneTextWatcher(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String initialFlowString = s.toString();
                    float startFlow = StringUtils.convertStringToFloat(initialFlowString);
                    projectWorkFlowrate.setBeginFlow(startFlow);

                    if (startFlow > projectWorkFlowrate.getEndFlow()) {
                        projectWorkFlowrate.setEndFlow(startFlow);
                        itemBoilerFlowBinding.etuiCloseFlow.setText(String.valueOf(startFlow));
                    }

                    float resultFlow = HDUtils.caculateSingleFlow(mIsHeatProduct,
                            projectWorkFlowrate.getBeginFlow(),
                            projectWorkFlowrate.getEndFlow());
                    itemBoilerFlowBinding.tvResultFlow.setText(getString(R.string.how_many_ton, resultFlow));

                    refreshUnitConsumption();
                }
            });
//            itemBoilerFlowBinding.etuiInitialFlow.getEtInput().setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if (!hasFocus) {
//                        String initialFlowString = ((EditText) v).getText().toString();
//                        float startFlow = StringUtils.convertStringToFloat(initialFlowString);
//                        projectWorkFlowrate.setBeginFlow(startFlow);
//
//                        if (startFlow > projectWorkFlowrate.getEndFlow()) {
//                            projectWorkFlowrate.setEndFlow(startFlow);
//                            itemBoilerFlowBinding.etuiCloseFlow.setText(String.valueOf(startFlow));
//                        }
//
//                        float resultFlow = HDUtils.caculateSingleFlow(mIsHeatProduct,
//                                projectWorkFlowrate.getBeginFlow(),
//                                projectWorkFlowrate.getEndFlow());
//                        itemBoilerFlowBinding.tvResultFlow.setText(getString(R.string.how_many_ton, resultFlow));
//
//                        refreshUnitConsumption();
//                    }
//                }
//            });

            itemBoilerFlowBinding.etuiCloseFlow.getEtInput().setOnlyOneTextWatcher(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String closeFlowString = s.toString();
                    float closeFlow = StringUtils.convertStringToFloat(closeFlowString);
                    float startFlow = projectWorkFlowrate.getBeginFlow();

//                    if (closeFlow < startFlow) {
//                        closeFlow = startFlow;
//                        itemBoilerFlowBinding.etuiCloseFlow.setText(String.valueOf(startFlow));
//                        ViewUtils.toast(R.string.close_flow_must_bigger_than_initial_flow);
//                        return;
//                    }
                    projectWorkFlowrate.setEndFlow(StringUtils.convertStringToFloat(closeFlowString));

                    float resultFlow = HDUtils.caculateSingleFlow(mIsHeatProduct,
                            projectWorkFlowrate.getBeginFlow(),
                            projectWorkFlowrate.getEndFlow());
                    itemBoilerFlowBinding.tvResultFlow.setText(getString(R.string.how_many_ton, resultFlow));

                    refreshUnitConsumption();
                }
            });
//            itemBoilerFlowBinding.etuiCloseFlow.getEtInput().setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if (!hasFocus) {
//                        String closeFlowString = ((EditText) v).getText().toString();
//                        float closeFlow = StringUtils.convertStringToFloat(closeFlowString);
//                        float startFlow = projectWorkFlowrate.getBeginFlow();
//
//                        if (closeFlow < startFlow) {
//                            closeFlow = startFlow;
//                            itemBoilerFlowBinding.etuiCloseFlow.setText(String.valueOf(startFlow));
//                            ViewUtils.toast(R.string.close_flow_must_bigger_than_initial_flow);
//                            return;
//                        }
//                        projectWorkFlowrate.setEndFlow(StringUtils.convertStringToFloat(closeFlowString));
//
//                        float resultFlow = HDUtils.caculateSingleFlow(mIsHeatProduct,
//                                projectWorkFlowrate.getBeginFlow(),
//                                projectWorkFlowrate.getEndFlow());
//                        itemBoilerFlowBinding.tvResultFlow.setText(getString(R.string.how_many_ton, resultFlow));
//
//                        refreshUnitConsumption();
//                    }
//                }
//            });

            itemBoilerFlowBinding.etuiAdjustFlow.getEtInput().setOnlyOneTextWatcher(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String adjustFlowString = s.toString();
                    projectWorkFlowrate.setAdjustFlow(StringUtils.convertStringToFloat(adjustFlowString));

                    refreshUnitConsumption();
                }
            });
//            itemBoilerFlowBinding.etuiAdjustFlow.getEtInput().setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if (!hasFocus) {
//                        String adjustFlowString = ((EditText) v).getText().toString();
//                        projectWorkFlowrate.setAdjustFlow(StringUtils.convertStringToFloat(adjustFlowString));
//
//                        refreshUnitConsumption();
//                    }
//                }
//            });

            itemBoilerFlowBinding.etAjustDescription.setOnlyOneTextWatcher(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    projectWorkFlowrate.setDescription(s.toString());
                }
            });
//            itemBoilerFlowBinding.etAjustDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                @Override
//                public void onFocusChange(View v, boolean hasFocus) {
//                    if (!hasFocus) {
//                        projectWorkFlowrate.setDescription(((EditText) v).getText().toString());
//                    }
//                }
//            });
        }

        @Override
        public int getItemCount() {
            return mProjectWorkFlowrateArrayList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ItemBoilerFlowBinding viewDataBinding;

            public ViewHolder(View itemView) {
                super(itemView);
                viewDataBinding = DataBindingUtil.bind(itemView);
            }
        }
    }
    //endregion
}
