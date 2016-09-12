package com.gank.io;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TestFragment extends Fragment {



    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        RecyclerView recyclerCategory = (RecyclerView) view.findViewById(R.id.recycler_gank_catgory);
        recyclerCategory.setLayoutManager(new GridLayoutManager(getContext(), 2));
        CategoryAdapter adapter = new CategoryAdapter(getContext());
        recyclerCategory.setAdapter(adapter);
        return view;
    }




    class CategoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private List<String> datas;
        private Context context;

        public CategoryAdapter(Context context){
            this.context = context;
            datas = new ArrayList<>();
            datas.add("福利");
            datas.add("Android");
            datas.add("IOS");
            datas.add("休息视频");
            datas.add("拓展资源");
            datas.add("前端");
            datas.add("瞎推荐");
            datas.add("App");
        }
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.recycler_category_item, parent, false);
            CategoryHolder holder = new CategoryHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((CategoryHolder)holder).tvCategoryTitle.setText(datas.get(position));
        }

        @Override
        public int getItemCount() {
            return datas.size();
        }

        class CategoryHolder extends RecyclerView.ViewHolder{

            TextView tvCategoryTitle;
            public CategoryHolder(View itemView) {
                super(itemView);
                tvCategoryTitle = (TextView) itemView.findViewById(R.id.tv_category_title);
            }
        }
    }
}
