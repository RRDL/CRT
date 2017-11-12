package rrdl.crt;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by maher on 11/11/2017.
 */

public class msgAdapter extends BaseAdapter{

    private List<String> msgList;

    private msgAdapter(List<String> msgList){
        this.msgList = msgList ;
    }

    @Override
    public int getCount() {
        return msgList==null ? 0 : msgList.size();
    }

    @Override
    public Object getItem(int position) {
        return msgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
