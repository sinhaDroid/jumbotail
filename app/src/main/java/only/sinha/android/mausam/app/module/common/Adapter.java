package only.sinha.android.mausam.app.module.common;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class Adapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private List<T> mItems = new ArrayList<>();

    private LayoutInflater mLayoutInflater;

    public Adapter(Context context) {
        this.mLayoutInflater = LayoutInflater.from(context);
    }

    public void setInitialItems(List<T> initialItems) {
        mItems = initialItems;
    }

    public LayoutInflater getLayoutInflater() {
        return this.mLayoutInflater;
    }

    public T getItem(int position) {
        return mItems.get(position);
    }

    public void replaceItem(T item, int position) {
        mItems.set(position, item);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void addAll(Collection<T> items) {
        for (T item : items) {
            mItems.add(item);
        }
        notifyDataSetChanged();
    }

    public void add(T item) {
        mItems.add(item);
    }

    public void add(T item, int position) {
        mItems.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    public int indexOf(T item) {
        return mItems.indexOf(item);
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    public void destroy() {
        mItems.clear();
        mLayoutInflater = null;
    }
}