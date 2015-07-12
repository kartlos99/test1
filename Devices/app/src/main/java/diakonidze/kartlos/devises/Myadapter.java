package diakonidze.kartlos.devises;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

/**
 * Created by k.diakonidze on 6/22/2015.
 */
public class Myadapter extends BaseExpandableListAdapter {

    private List<Devises> header_text;
    private HashMap<String,List<Devises>> child_text;
    private Context context;

    public Myadapter (Context context, List<Devises> header_text, HashMap<String,List<Devises>> child_text ){
        this.context = context;
        this.header_text = header_text;
        this.child_text = child_text;

    }
    @Override
    public int getGroupCount() {
        return header_text.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child_text.get(header_text.get(groupPosition).getDeviceCategory()).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return header_text.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child_text.get(header_text.get(groupPosition).getDeviceCategory()).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {


        Devises title = (Devises) this.getGroup(groupPosition);

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item11,null);
        }

        TextView Tdevice = (TextView) convertView.findViewById(R.id.text_device_1);
        TextView Tcount = (TextView) convertView.findViewById(R.id.text_count_1);
        ImageView Image = (ImageView) convertView.findViewById(R.id.imageView_1);

        //headtext.setTypeface(null, Typeface.BOLD_ITALIC);
        Tdevice.setText(title.getDeviceCategory());
        Tcount.setText(title.getCount()+" ცალი");

        try {
            Picasso.with(context)
                    .load(title.getImig())
                    .resize(222,222)
                    .centerInside()
                    .into(Image);
        }catch (Exception e){}


       // if(isExpanded){headtext.setTextColor(Color.BLUE);}

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Devises child = (Devises) this.getChild(groupPosition, childPosition);
        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item12, null);
        }

        TextView Tdevice = (TextView) convertView.findViewById(R.id.text_device);
        TextView Tmodel = (TextView) convertView.findViewById(R.id.text_modeli);
        TextView Tbrand = (TextView) convertView.findViewById(R.id.text_brand);
        TextView Tcount = (TextView) convertView.findViewById(R.id.text_count_child);
        ImageView Image = (ImageView) convertView.findViewById(R.id.imageView3);

        Tdevice.setText(child.getDeviceCategory());
        Tmodel.setText("მოდელი: " + child.getModel());
        Tbrand.setText("მწარმოებელი: " + child.getBrand());
        Tcount.setText(child.getCount() + " ცალი");

        try {
            Picasso.with(context)
                    .load(child.getImig())
                    .resize(400 ,450)
                    .centerInside()
                    .into(Image);
        }catch (Exception e){}

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
