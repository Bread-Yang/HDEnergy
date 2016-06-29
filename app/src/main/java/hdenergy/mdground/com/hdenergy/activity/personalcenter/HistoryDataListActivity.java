package hdenergy.mdground.com.hdenergy.activity.personalcenter;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.base.ToolbarActivity;
import hdenergy.mdground.com.hdenergy.databinding.ActivityHistoryDatastaticsBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemHistoryDatastaticsBinding;

/**
 * Created by PC on 2016-06-29.
 */

public class HistoryDataListActivity extends ToolbarActivity<ActivityHistoryDatastaticsBinding>{
    private HistoryDateAdapter mAdapter;
    private ArrayList<String> mArrayList=new ArrayList<>();
    @Override
    protected int getContentLayout() {
        return R.layout.activity_history_datastatics;
    }

    @Override
    protected void initData() {
        Intent intent=getIntent();
        String title=intent.getStringExtra("name");
       getDateList();
        setTitle(title);
        mAdapter=new HistoryDateAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HistoryDataListActivity.this);
        mDataBinding.recyclerView.setLayoutManager(linearLayoutManager);
        mDataBinding.recyclerView.setAdapter(mAdapter);

    }

    @Override
    protected void setListener() {

    }
    //region ADAPTER
    public class HistoryDateAdapter extends RecyclerView.Adapter<HistoryDateAdapter.MyViewHolder>{


        @Override
        public HistoryDateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(HistoryDataListActivity.this)
                    .inflate(R.layout.item_history_datastatics,parent,false);
            MyViewHolder holder=new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
//            holder.itemHistoryDatastaticsBinding.tvTitles.setText(mArrayList.get(position));
            holder.itemHistoryDatastaticsBinding.tvStandardUnit.setText(mArrayList.get(position));
            holder.itemHistoryDatastaticsBinding.tvElectircUnitConsumption.setText(mArrayList.get(position));
            holder.itemHistoryDatastaticsBinding.tvProfit.setText(mArrayList.get(position));
            holder.itemHistoryDatastaticsBinding.tvQuestion.setText(mArrayList.get(position));
            holder.itemHistoryDatastaticsBinding.tvWaterUnitConsumption.setText(mArrayList.get(position));
            holder.itemHistoryDatastaticsBinding.tvUnitIndivdual.setText(mArrayList.get(position));
        }

        @Override
        public int getItemCount() {
            return mArrayList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder{
            //           public ItemHistoryDatastaticsBinding itemHistoryDatastaticsBinding;
            public ItemHistoryDatastaticsBinding itemHistoryDatastaticsBinding;
            public MyViewHolder(View itemView) {
                super(itemView);
                itemHistoryDatastaticsBinding= DataBindingUtil.bind(itemView);
            }
        }

    }
    //endregion

    //region METHOD
    public void getDateList(){
        mArrayList.add(getString(R.string.yongxing));
        mArrayList.add(getString(R.string.app_name));
    }


    //endregion
}
