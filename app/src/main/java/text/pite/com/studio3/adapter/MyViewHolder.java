package text.pite.com.studio3.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @ company pite
 * @ name sy
 */
public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private SparseArray<View> views ;
    private ViewOnclick viewOnclick;

    /**
     *
     * @param itemView  无点击事件
     */
     MyViewHolder(View itemView) {
        super(itemView);
        views = new SparseArray<>();
    }

    /**
     *
     * @param viewOnclick  有点击事件
     */
    public MyViewHolder(View itemView,ViewOnclick viewOnclick) {
        super(itemView);
        this.viewOnclick=viewOnclick;
        views = new SparseArray<>();
    }
    /**
     * 绑定id
     */
    private <T extends View>T getView(int viewId){
        View view = views.get(viewId);
        if(view==null){
            view = itemView.findViewById(viewId);
            views.put(viewId,view);
        }
        return (T) view;
    }
    public void setText(int viewId,String str){
        TextView view =getView(viewId);
        view.setText(str);
    }
    public void setImagerView(int viewId,int Resources){
        ImageView imageView = getView(viewId);
        imageView.setImageResource(Resources);
    }
    public void setOnclick(int viewId,int position){
        View view = getView(viewId);
        view.setOnClickListener(this);
        view.setTag(position);
    }

    @Override
    public void onClick(View v) {
        if(viewOnclick!=null)
        viewOnclick.clickView(v,Integer.valueOf(v.getTag().toString()));
    }
    public interface ViewOnclick{
        void clickView(View v,int position);
    }
}
