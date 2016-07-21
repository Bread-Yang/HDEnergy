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
import com.mdground.hdenergy.utils.StringUtils;
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

    private List<ProjectWorkFuel> mProjectWorkFuelArrayList;

    private ArrayList<FuelCategory> mFuelCategoryArrayList = new ArrayList<>();

    private int mClickResId;

    private int mClickFuelPosition, mClickWareHousePosition;

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

                ProjectWorkFuel projectWorkFuel = mProjectWorkFuelArrayList.get(mClickFuelPosition);

                switch (mClickResId) {
                    case R.id.rltFuelCategory: {
                        FuelCategory fuelCategory = mFuelCategoryArrayList.get(currentPosition);

                        projectWorkFuel.setFuelID(fuelCategory.getFuelID());
                        projectWorkFuel.setFuelName(fuelCategory.getFuelName());
                        projectWorkFuel.setPreviousInventory(fuelCategory.getInventory());

                        // 重新选择燃料种类后,该燃料下所有的进料量的供应商都重置
                        for (ProjectFuelWarehouse projectFuelWarehouse : projectWorkFuel.getProjectFuelWarehouseList()) {
                            projectFuelWarehouse.setFuelName(fuelCategory.getFuelName());
                            projectFuelWarehouse.setSupplier("");
                        }

                        mDataBinding.recyclerView.getAdapter().notifyItemChanged(mClickFuelPosition);
                        break;
                    }
                    case R.id.rltSupplier: {
                        FuelCategory fuelCategory = null;
                        for (FuelCategory item : mFuelCategoryArrayList) {
                            if (item.getFuelID() == projectWorkFuel.getFuelID()) {
                                fuelCategory = item;
                                break;
                            }
                        }

                        String[] suppliers = fuelCategory.getSuppliers().split(";");

                        String selectSupplier = suppliers[currentPosition];

                        // 一个供应商不能用于多个进料量
                        for (int i = 0 ;i < projectWorkFuel.getProjectFuelWarehouseList().size(); i++) {
                            ProjectFuelWarehouse item = projectWorkFuel.getProjectFuelWarehouseList().get(i);

                            if (i != mClickWareHousePosition && item.getSupplier().equals(selectSupplier)) {
                                ViewUtils.toast(R.string.not_same_supplier);
                                return;
                            }
                        }

                        ProjectFuelWarehouse projectFuelWarehouse = projectWorkFuel.getProjectFuelWarehouseList().get(mClickWareHousePosition);
                        projectFuelWarehouse.setSupplier(suppliers[currentPosition]);

                        mDataBinding.recyclerView.getAdapter().notifyItemChanged(mClickFuelPosition);
                        break;
                    }
                }
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

    private ProjectFuelWarehouse createFuelWarehouse(int fuelPosition) {
        ProjectWorkFuel projectWorkFuel = mProjectWorkFuelArrayList.get(fuelPosition);

        ProjectFuelWarehouse projectFuelWarehouse = new ProjectFuelWarehouse();
        projectFuelWarehouse.setFuelName(projectWorkFuel.getFuelName());
        projectFuelWarehouse.setProjectID(projectFuelWarehouse.getProjectID());

        return projectFuelWarehouse;
    }

    //region  ACTION
    public void submitAction(View view) {
        for (ProjectWorkFuel projectWorkFuel : mProjectWorkFuelArrayList) {
            if (projectWorkFuel.getFuelID() == 0) {
                ViewUtils.toast(R.string.fill_fuel_info);
                return;
            }

            List<ProjectFuelWarehouse> projectFuelWarehouseArrayList = projectWorkFuel.getProjectFuelWarehouseList();

            for (ProjectFuelWarehouse projectFuelWarehouse : projectFuelWarehouseArrayList) {
                if (StringUtils.isEmpty(projectFuelWarehouse.getSupplier())
                        || StringUtils.isEmpty(projectFuelWarehouse.getPlateNumber())) {
                    ViewUtils.toast(R.string.fill_feedstock_info);
                    return;
                }
            }
        }

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

                mProjectWorkFuelArrayList = mProjectWorkFurnace.getProjectWorkFuelList();
                if (mProjectWorkFuelArrayList == null) {
                    mProjectWorkFuelArrayList = new ArrayList<ProjectWorkFuel>();
                    createProjectWorkFuel();
                }

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

            WarehouseAdapter warehouseAdapter = new WarehouseAdapter(position, projectWorkFuel);
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
                    mClickResId = R.id.rltFuelCategory;
                    mClickFuelPosition = position;

                    ArrayList<String> fuelCategoryStringArrayList = new ArrayList<>();
                    for (FuelCategory item : mFuelCategoryArrayList) {
                        fuelCategoryStringArrayList.add(item.getFuelName());
                    }
                    mBaoPickerDialog.bindWheelViewData(fuelCategoryStringArrayList);

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
                        projectWorkFuel.setPreviousInventory(StringUtils.convertStringToInt(stringToConvert));
                    }
                }
            });

            holder.viewDataBinding.etCurrentPeriodStock.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (!hasFocus) {
                        String stringToConvert = ((EditText) view).getText().toString();
                        projectWorkFuel.setCurrentInventory(StringUtils.convertStringToInt(stringToConvert));
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


        ProjectWorkFuel projectWorkFuel;
        List<ProjectFuelWarehouse> projectFuelWarehouseArrayList = new ArrayList<>();
        int fuelPosition;

        public WarehouseAdapter(int fuelPosition, ProjectWorkFuel projectWorkFuel) {
            this.fuelPosition = fuelPosition;
            this.projectWorkFuel = projectWorkFuel;
            this.projectFuelWarehouseArrayList = projectWorkFuel.getProjectFuelWarehouseList();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_boiler_warehouse, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final ProjectFuelWarehouse projectFuelWarehouse = projectFuelWarehouseArrayList.get(position);

            holder.viewDataBinding.setProjectFuelWarehouse(projectFuelWarehouse);

            if (position == 0) {
                holder.viewDataBinding.ivAddOrDelete.setImageResource(R.drawable.add);

                holder.viewDataBinding.ivAddOrDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        projectFuelWarehouseArrayList.add(createFuelWarehouse(fuelPosition));

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

            holder.viewDataBinding.rltSupplier.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickResId = R.id.rltSupplier;
                    mClickFuelPosition = fuelPosition;
                    mClickWareHousePosition = position;

                    FuelCategory fuelCategory = null;
                    for (FuelCategory item : mFuelCategoryArrayList) {
                        if (item.getFuelID() == projectWorkFuel.getFuelID()) {
                            fuelCategory = item;
                            break;
                        }
                    }

                    if (fuelCategory == null) {
                        ViewUtils.toast(R.string.choose_fuel_category_first);
                        return;
                    }

                    String[] suppliers = fuelCategory.getSuppliers().split(";");
                    ArrayList<String> suppliersStringArrayList = new ArrayList<>();
                    for (String item : suppliers) {
                        suppliersStringArrayList.add(item);
                    }

                    mBaoPickerDialog.bindWheelViewData(suppliersStringArrayList);

                    String chooseSupplier = holder.viewDataBinding.tvSupplier.getText().toString();
                    for (int i = 0; i < suppliers.length; i++) {
                        if (chooseSupplier.equals(suppliers[i])) {
                            mBaoPickerDialog.setCurrentItem(i);
                            break;
                        }
                    }
                    mBaoPickerDialog.show();
                }
            });

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
                        projectFuelWarehouse.setAmount(StringUtils.convertStringToInt(stringToConvert));
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
