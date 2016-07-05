package com.mdground.hdenergy.activity.datareport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.databinding.ActivityBoilerEditOneBinding;
import com.mdground.hdenergy.databinding.ItemBoilerFlowBinding;
import com.mdground.hdenergy.models.ProjectWorkFlowrate;
import com.mdground.hdenergy.models.ProjectWorkFurnace;
import com.mdground.hdenergy.utils.StringUtil;
import com.mdground.hdenergy.views.BaoPickerDialog;

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

    private ArrayList<ProjectWorkFlowrate> mFlowArrayList = new ArrayList<>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_boiler_edit_one;
    }

    @Override
    protected void initData() {
        initPickerDialog();

        mProjectWorkFurnace = getIntent().getParcelableExtra(Constants.KEY_PROJECT_WORK_FURNACE);

        mFlowArrayList.add(createFlowrate());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(layoutManager);
        mDataBinding.recyclerView.setNestedScrollingEnabled(false);
        mDataBinding.recyclerView.setFocusable(false);

        mAdapter = new EditOneAdapter();
        mDataBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        mBaoPickerDialog.setOnWheelScrollListener(new OnWheelScrollListener() {
            @Override
            public void onScrollingStarted(WheelView wheel) {

            }

            @Override
            public void onScrollingFinished(WheelView wheel) {
                int currentPosition = wheel.getCurrentItem();

                int hours = currentPosition + 1;

                mDataBinding.tvHours.setText(String.valueOf(hours));
            }
        });

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

        mDataBinding.etuiElectricQuantityTwo.getIvIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.rltElectricQuantityTwo.setVisibility(View.GONE);
            }
        });

        mDataBinding.etuiElectricQuantityThree.getIvIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.rltElectricQuantityThree.setVisibility(View.GONE);
            }
        });

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

        mDataBinding.etuiWaterQuantityTwo.getIvIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.rltWaterQuantityTwo.setVisibility(View.GONE);
            }
        });

        mDataBinding.etuiWaterQuantityThree.getIvIcon().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDataBinding.rltWaterQuantityThree.setVisibility(View.GONE);
            }
        });
    }

    private void initPickerDialog() {
        mBaoPickerDialog = new BaoPickerDialog(this);

        ArrayList<String> hourArrayList = new ArrayList<>();
        for (int i = 1; i < 25; i++) {
            hourArrayList.add(String.valueOf(i));
        }
        mBaoPickerDialog.bindWheelViewData(hourArrayList);
    }

    private ProjectWorkFlowrate createFlowrate() {
        ProjectWorkFlowrate projectWorkFlowrate = new ProjectWorkFlowrate();
        projectWorkFlowrate.setWorkFurnaceID(mProjectWorkFurnace.getWorkFurnaceID());
        mFlowArrayList.add(projectWorkFlowrate);
        return projectWorkFlowrate;
    }

    public static void hideSoftKeyboard(Activity activity, View view) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
    }

    private boolean isHeader(int positon) {
        if (positon == 0) {
            return true;
        } else if (positon == mFlowArrayList.size() || positon == (mFlowArrayList.size() + 1)) {
            return true;
        }
        return false;
    }

    //region  ACTION
    public void selectBlowInDurationAction(View view) {
        mBaoPickerDialog.show();
    }

    public void nextStepAction(View view) {
        mDataBinding.lltRoot.requestFocus();

        // 开炉时长
        String hours = mDataBinding.tvHours.getText().toString();
        mProjectWorkFurnace.setWorkingHour(Integer.parseInt(hours));

        // 电量1
        {
            int electricity1 = 0;
            String electricity1String = mDataBinding.etuiElectricQuantityOne.getText();
            if (!StringUtil.isEmpty(electricity1String)) {
                electricity1 = Integer.parseInt(electricity1String);
            }
            mProjectWorkFurnace.setElectricity1(electricity1);
        }

        // 电量2
        {
            int electricity2 = 0;
            String electricity2String = mDataBinding.etuiElectricQuantityTwo.getText();
            if (!StringUtil.isEmpty(electricity2String)) {
                electricity2 = Integer.parseInt(electricity2String);
            }
            mProjectWorkFurnace.setElectricity2(electricity2);
        }

        // 电量3
        {
            int electricity3 = 0;
            String electricity3String = mDataBinding.etuiElectricQuantityThree.getText();
            if (!StringUtil.isEmpty(electricity3String)) {
                electricity3 = Integer.parseInt(electricity3String);
            }
            mProjectWorkFurnace.setElectricity3(electricity3);
        }

        // 水量1
        {
            int water1 = 0;
            String water1String = mDataBinding.etuiWaterQuantityOne.getText();
            if (!StringUtil.isEmpty(water1String)) {
                water1 = Integer.parseInt(water1String);
            }
            mProjectWorkFurnace.setWater1(water1);
        }

        // 水量2
        {
            int water2 = 0;
            String water2String = mDataBinding.etuiWaterQuantityTwo.getText();
            if (!StringUtil.isEmpty(water2String)) {
                water2 = Integer.parseInt(water2String);
            }
            mProjectWorkFurnace.setWater2(water2);
        }

        // 水量3
        {
            int water3 = 0;
            String water3String = mDataBinding.etuiWaterQuantityThree.getText();
            if (!StringUtil.isEmpty(water3String)) {
                water3 = Integer.parseInt(water3String);
            }
            mProjectWorkFurnace.setWater3(water3);
        }

        // 流量列表
        mProjectWorkFurnace.setProjectWorkFlowrateList(mFlowArrayList);

        Intent intent = new Intent(this, BoilerEditTwoActivity.class);
        intent.putExtra(Constants.KEY_PROJECT_WORK_FURNACE, mProjectWorkFurnace);
        startActivity(intent);
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
            final ProjectWorkFlowrate projectWorkFlowrate = mFlowArrayList.get(position);

            final ItemBoilerFlowBinding itemBoilerFlowBinding = ((ItemBoilerFlowBinding) holder.viewDataBinding);
            itemBoilerFlowBinding.setFlow(projectWorkFlowrate);
            itemBoilerFlowBinding.setPosition(position + 1);

            itemBoilerFlowBinding.etuiInitialFlow.getEtInput().setText(String.valueOf(projectWorkFlowrate.getBeginFlow()));
            itemBoilerFlowBinding.etuiCloseFlow.getEtInput().setText(String.valueOf(projectWorkFlowrate.getEndFlow()));
            itemBoilerFlowBinding.etuiAdjustFlow.getEtInput().setText(String.valueOf(projectWorkFlowrate.getAdjustFlow()));
            itemBoilerFlowBinding.etAjustDescription.setText(projectWorkFlowrate.getDescription());

            ImageView ivAddOrDelete = itemBoilerFlowBinding.ivAddOrDelete;
            if (isHeader(position)) {
                ivAddOrDelete.setImageResource(R.drawable.add);

                ivAddOrDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFlowArrayList.add(createFlowrate());
                        mAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                ivAddOrDelete.setImageResource(R.drawable.delete);

                ivAddOrDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mFlowArrayList.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }

            itemBoilerFlowBinding.etuiInitialFlow.getEtInput().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        int initialFlow = 0;
                        String initialFlowString = ((EditText) v).getText().toString();
                        if (!StringUtil.isEmpty(initialFlowString)) {
                            initialFlow = Integer.parseInt(initialFlowString);
                        }
                        projectWorkFlowrate.setBeginFlow(initialFlow);
                    }
                }
            });

            itemBoilerFlowBinding.etuiCloseFlow.getEtInput().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        int closeFlow = 0;
                        String closeFlowString = ((EditText) v).getText().toString();
                        if (!StringUtil.isEmpty(closeFlowString)) {
                            closeFlow = Integer.parseInt(closeFlowString);
                        }
                        projectWorkFlowrate.setEndFlow(closeFlow);
                    }
                }
            });

            itemBoilerFlowBinding.etuiAdjustFlow.getEtInput().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        int adjustFlow = 0;
                        String adjustFlowString = ((EditText) v).getText().toString();
                        if (!StringUtil.isEmpty(adjustFlowString)) {
                            adjustFlow = Integer.parseInt(adjustFlowString);
                        }
                        projectWorkFlowrate.setAdjustFlow(adjustFlow);
                    }
                }
            });

            itemBoilerFlowBinding.etAjustDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    projectWorkFlowrate.setDescription(((EditText) v).getText().toString());
                }
            });
        }

        @Override
        public int getItemCount() {
            return mFlowArrayList.size();
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
