package hdenergy.mdground.com.hdenergy.activity.announcement;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import hdenergy.mdground.com.hdenergy.R;
import hdenergy.mdground.com.hdenergy.activity.base.ToolbarActivity;
import hdenergy.mdground.com.hdenergy.databinding.ActivityAnnouncementListBinding;
import hdenergy.mdground.com.hdenergy.databinding.ItemAnnouncementBinding;
import hdenergy.mdground.com.hdenergy.models.Announcement;

/**
 * Created by yoghourt on 6/28/16.
 */
public class AnnouncementListActivity extends ToolbarActivity<ActivityAnnouncementListBinding> {

    private AnnouncementListAdapter mAdapter;

    private ArrayList<Announcement> mAnnouncementArrayList = new ArrayList<>();

    @Override
    protected int getContentLayout() {
        return R.layout.activity_announcement_list;
    }

    @Override
    protected void initData() {
        mAnnouncementArrayList.add(new Announcement());
        mAnnouncementArrayList.add(new Announcement());
        mAnnouncementArrayList.add(new Announcement());

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mDataBinding.recyclerView.setLayoutManager(layoutManager);

        mAdapter = new AnnouncementListAdapter();
        mDataBinding.recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {

    }

    //region ADAPTER
    class AnnouncementListAdapter extends RecyclerView.Adapter<AnnouncementListAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_announcement, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return mAnnouncementArrayList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ItemAnnouncementBinding viewDataBinding;

            public ViewHolder(View itemView) {
                super(itemView);
                viewDataBinding = DataBindingUtil.bind(itemView);
            }
        }
    }
    //endregion
}
