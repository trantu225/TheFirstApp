package tiwaco.thefirstapp;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TUTRAN on 15/03/2017.
 */

public class CustomListAdapter extends BaseAdapter {

    private List<Customer> customerList ;
    private LayoutInflater layoutInflater;
    private Context context;
    public CustomListAdapter(Context con, List<Customer> listcus){
        customerList = listcus ;
        context = con ;
        layoutInflater = LayoutInflater.from(con);
    }
    @Override
    public int getCount() {
        return customerList.size();
    }

    @Override
    public Object getItem(int position) {
        return customerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder ;
      //  if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.custom_listview_item,null);
            holder = new ViewHolder();
            holder.Ten = (TextView)convertView.findViewById(R.id.tv_hoten);

       /*     convertView.setTag(holder);
        }
        else{
            holder  = (ViewHolder)convertView.getTag();
        }
*/
        Customer cus = customerList.get(position);
        holder.Ten.setText(cus.getName());
        holder.DiaChi.setText(cus.getAddress());
        return convertView;
    }

    static class ViewHolder {
        TextView Ten;
        TextView DiaChi;
    }
}
