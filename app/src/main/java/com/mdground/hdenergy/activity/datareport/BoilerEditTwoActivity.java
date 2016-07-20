package com.mdground.hdenergy.activity.datareport;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.reflect.TypeToken;
import com.mdground.hdenergy.R;
import com.mdground.hdenergy.activity.base.ToolbarActivity;
import com.mdground.hdenergy.constants.Constants;
import com.mdground.hdenergy.databinding.ActivityBoilerEditTwoBinding;
import com.mdground.hdenergy.databinding.ItemBoilerFuelBinding;
import com.mdground.hdenergy.databinding.ItemBoilerWarehouseBinding;
import com.mdground.hdenergy.models.FuelCategory;
import com.mdground.hdenergy.models.ProjectFuelWarehouse;
import com.mdground.hdenergy.models.ProjectWorkFuel;
import com.mdground.hdenergy.models.ProjectWorkFurnace;
import com.mdground.hdenergy.restfuls.GlobalRestful;
import com.mdground.hdenergy.restfuls.bean.ResponseData;
import com.mdground.hdenergy.utils.StringUtil;
import com.mdground.hdenergy.utils.ViewUtils;
import com.mdground.hdenergy.views.BaoPickerDialog;

import java.util.ArrayList;
import java.util.List;

import kankan.wheel.widget.OnWheelScrollListener;
import kankan.wheel.widget.WheelView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yoghourt on 2016-06-27.
 */
public class BoilerEditTwoActivity extends ToolbarActivity<ActivityBoilerEditTwoBinding> {

    private FuelAdapter mAdapter;

    private BaoPickerDialog mBaoPickerDialog;

    private ProjectWorkFurnace mProjectWorkFurnace;

    private ArrayList<ProjectWorkFuel> mProjectWorkFuelArrayList = new ArrayList<>();

    private ArrayList<FuelCategory> mFuelCategoryArrayList = new ArrayList<>();

    private int mCurrentClickFuelPosition;

    @Override
    protected int getContentLayout() {
        return R.layout.activity_boiler_edit_two;
    }

    @Override
    protected void initData() {
        mProjectWorkFurnace = getIntent().getParcelableExtra(Constants.KEY_PROJECT_WORK_FURNACE);

        mBaoPickerDialog = new BaoPickerDialog(BoilerEditTwoActivity.this);

        getProjectFuelListRequest();
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

                FuelCategory fuelCategory = mFuelCategoryArrayList.get(currentPosition);

                ProjectWorkFuel projectWorkFuel = mProjectWorkFuelArrayList.get(mCurrentClickFuelPosition);
                projectWorkFuel.setFuelID(fuelCategory.getFuelID());
                projectWorkFuel.setFuelName(fuelCategory.getFuelName());
                projectWorkFuel.setPreviousInventory(fuelCategory.getInventory());

                mDataBinding.recyclerView.getAdapter().notifyItemChanged(mCurrentClickFuelPosition);
            }
        });
    }

    private void createProjectWorkFuel() {
        ProjectWorkFuel projectWorkFuel = new ProjectWorkFuel();
        projectWorkFuel.setWorkFurnaceID(mProjectWorkFurnace.getFurnaceID());

        // 燃料种类
        if (mFuelCategoryArrayList.size() > 0) {
            FuelCategory fuelCategory = mFuelCategoryArrayList.get(0);

            projectWorkFuel.setFuelID(fuelCategory.getFuelID());
            projectWorkFuel.setFuelName(fuelCategory.getFuelName());
            projectWorkFuel.setPreviousInventory(fuelCategory.getInventory());
        }

        ArrayList<ProjectFuelWarehouse> projectFuelWarehouseArrayList = new ArrayList<>();
        ProjectFuelWarehouse projectFuelWarehouse = new ProjectFuelWarehouse();
        projectFuelWarehouseArrayList.add(projectFuelWarehouse);

        projectWorkFuel.setProjectFuelWarehouseList(projectFuelWarehouseArrayList);

        mProjectWorkFuelArrayList.add(projectWorkFuel);
    }

    //region  ACTION
    public void submitAction(View view) {
        mProjectWorkFurnace.setProjectWorkFuelList(mProjectWorkFuelArrayList);
        Intent intent = new Intent();
        intent.putExtra(Constants.KEY_PROJECT_WORK_FURNACE, mProjectWorkFurnace);
        setResult(RESULT_OK, intent);
        finish();
    }
    //endregion

    //region SERVER
    private void getProjectFuelListRequest() {
        ViewUtils.loading(this);
        GlobalRestful.getInstance().GetProjectFuelList(mProjectWorkFurnace.getProjectID(), new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                ViewUtils.dismiss();
                mFuelCategoryArrayList = response.body().getContent(new TypeToken<ArrayList<FuelCategory>>() {
                });

                ArrayList<String> fuelCategoryStringArrayList = new ArrayList<>();
                for (FuelCategory item : mFuelCategoryArrayList) {
                    fuelCategoryStringArrayList.add(item.getFuelName());
                }
                mBaoPickerDialog.bindWheelViewData(fuelCategoryStringArrayList);

                createProjectWorkFuel();

                LinearLayoutManager layoutManager = new LinearLayoutManager(BoilerEditTwoActivity.this);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                mDataBinding.recyclerView.setLayoutManager(layoutManager);
                mDataBinding.recyclerView.setNestedScrollingEnabled(false);
                mDataBinding.recyclerView.setFocusable(false);

                mAdapter = new FuelAdapter();
                mDataBinding.recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {

            }
        });
    }
    //endregion

    //region ADAPTER
    class FuelAdapter extends RecyclerView.Adapter<FuelAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_boiler_fuel, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final ProjectWorkFuel projectWorkFuel = mProjectWorkFuelArrayList.get(position);
            holder.viewDataBinding.setFuel(projectWorkFuel);

            LinearLayoutManager layoutManager = new LinearLayoutManager(BoilerEditTwoActivity.this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            holder.viewDataBinding.recyclerView.setLayoutManager(layoutManager);
            holder.viewDataBinding.recyclerView.setNestedScrollingEnabled(false);

            WarehouseAdapter warehouseAdapter = new WarehouseAdapter(projectWorkFuel.getProjectFuelWarehouseList());
            holder.viewDataBinding.recyclerView.setAdapter(warehouseAdapter);

            if (position == 0) {
                holder.viewDataBinding.ivAddOrDelete.setImageResource(R.drawable.add);

                holder.viewDataBinding.ivAddOrDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createProjectWorkFuel();

                        mAdapter.notifyDataSetChanged();
                    }
                });
            } else {
                holder.viewDataBinding.ivAddOrDelete.setImageResource(R.drawable.delete);

                holder.viewDataBinding.ivAddOrDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mProjectWorkFuelArrayList.remove(position);

                        mAdapter.notifyDataSetChanged();
                    }
                });
            }

            holder.viewDataBinding.rltFuelCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mCurrentClickFuelPosition = position;

                    String chooseFuelCategory = holder.viewDataBinding.tvFuelCategory.getText().toString();
                    for (int i = 0; i < mFuelCategoryArrayList.size(); i++) {
                        if (chooseFuelCategory.equals(mFuelCategoryArrayList.get(i).getFuelName())) {
                            mBaoPickerDialog.setCurrentItem(i);
                            break;
                        }
                    }
                    mBaoPickerDialog.show();
                }
            });

            holder.viewDataBinding.etLastPeriodStock.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (!hasFocus) {
                        String stringToConvert = ((EditText) view).getText().toString();
                        projectWorkFuel.setPreviousInventory(StringUtil.convertStringToInt(stringToConvert));
                    }
                }
            });

            holder.viewDataBinding.etCurrentPeriodStock.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (!hasFocus) {
                        String stringToConvert = ((EditText) view).getText().toString();
                        projectWorkFuel.setCurrentInventory(StringUtil.convertStringToInt(stringToConvert));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mProjectWorkFuelArrayList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ItemBoilerFuelBinding viewDataBinding;

            public ViewHolder(View itemView) {
                super(itemView);
                viewDataBinding = DataBindingUtil.bind(itemView);
            }
        }
    }

    class WarehouseAdapter extends RecyclerView.Adapter<WarehouseAdapter.ViewHolder> {

        List<ProjectFuelWarehouse> projectFuelWarehouseArrayList = new ArrayList<>();

        public WarehouseAdapter(List<ProjectFuelWarehouse> projectFuelWarehouseArrayList) {
            this.projectFuelWarehouseArrayList = projectFuelWarehouseArrayList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_boiler_warehouse, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final ProjectFuelWarehouse projectFuelWarehouse = projectFuelWarehouseArrayList.get(position);

            if (position == 0) {
                holder.viewDataBinding.ivAddOrDelete.setImageResource(R.drawable.add);

                holder.viewDataBinding.ivAddOrDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        projectFuelWarehouseArrayList.add(new ProjectFuelWarehouse());

                        notifyDataSetChanged();
                    }
                });
            } else {
                holder.viewDataBinding.ivAddOrDelete.setImageResource(R.drawable.delete);

                holder.viewDataBinding.ivAddOrDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        projectFuelWarehouseArrayList.remove(position);

                        notifyDataSetChanged();
                    }
                });
            }

            holder.viewDataBinding.etLicensePlate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (!hasFocus) {
                        projectFuelWarehouse.setPlateNumber(((EditText) view).getText().toString());
                    }
                }
            });

            holder.viewDataBinding.etuiDeliveryCapacity.getEtInput().setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (!hasFocus) {
                        String stringToConvert = ((EditText) view).getText().toString();
                        projectFuelWarehouse.setAmount(StringUtil.convertStringToInt(stringToConvert));
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return projectFuelWarehouseArrayList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public ItemBoilerWarehouseBinding viewDataBinding;

            public ViewHolder(View itemView) {
                super(itemView);
                viewDataBinding = DataBindingUtil.bind(itemView);
            }
        }
    }
    //endregion
}
