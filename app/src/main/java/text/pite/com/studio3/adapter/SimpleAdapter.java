package text.pite.com.studio3.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import text.pite.com.studio3.R;


/**
 * @ company pite
 * @ name sy
 */
public abstract class SimpleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<String> mlist;
    private LayoutInflater miInflater;
    private int TYPE_FOOTER = 0;
    private int TYPE_ITEM = 1;
    private int DATA_EMPTY = 2;//没有数据时
    private FootHolder FootHolder = null;
    private int layout;
    private boolean isFoot;
    private MyViewHolder.ViewOnclick viewOnclick=null;

    protected SimpleAdapter(Context context, int layout, List<String> mlist, boolean isFoot) {
        this.mlist = mlist;
        this.layout = layout;
        this.isFoot = isFoot;
        this.viewOnclick = (MyViewHolder.ViewOnclick) context;
        miInflater = LayoutInflater.from(context);
    }
    protected SimpleAdapter(Context context, int layout, List<String> mlist, boolean isFoot,MyViewHolder.ViewOnclick viewOnclick) {
        this.mlist = mlist;
        this.layout = layout;
        this.isFoot = isFoot;
        this.viewOnclick = viewOnclick;
        miInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == DATA_EMPTY) {
            view = miInflater.inflate(R.layout.detaempty, parent, false);
            return new DataEmptyHolder(view);
        }
        if (viewType == TYPE_FOOTER) {
            view = miInflater.inflate(R.layout.footitem, parent, false);
            return new FootHolder(view);
        } else {
                view = miInflater.inflate(layout, parent, false);
            if(null!=viewOnclick)
                return new MyViewHolder(view,viewOnclick);
            else return new MyViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (mlist.size() == 0) {
            return DATA_EMPTY;
        } else if (position + 1 == getItemCount() && isFoot) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            convert((MyViewHolder) holder, mlist.get(position), position);
        } else if (holder instanceof FootHolder) {
            FootHolder = (FootHolder) holder;
        } else {
            //((DataEmptyHolder) holder).data_empty.setText("");
        }

    }

    public void setFootText(String str) {
        if (FootHolder != null)
            FootHolder.footTv.setText(str);
    }

    public void setFootVisibility(int v) {
        if (FootHolder != null)
            FootHolder.footTv.setVisibility(v);
    }

    public int getVisiblityState() {
        if (FootHolder != null)
            return FootHolder.footTv.getVisibility();
        return View.GONE;
    }

    /**
     * 设置数据的方法
     */
    public abstract void convert(MyViewHolder holder, String data, int position);

    @Override
    public int getItemCount() {
        if (mlist == null)
            return 0;
        else if (isFoot)
            return mlist.size() + 1;
        else
            return mlist.size();
    }

    static class FootHolder extends RecyclerView.ViewHolder {
        TextView footTv;

        FootHolder(View itemView) {
            super(itemView);
            footTv = itemView.findViewById(R.id.footTv);
        }
    }

    /**
     * 数据为空时
     */
    static class DataEmptyHolder extends RecyclerView.ViewHolder {
        TextView data_empty;

        DataEmptyHolder(View itemView) {
            super(itemView);
            data_empty = itemView.findViewById(R.id.data_emptys);
        }
    }
}
