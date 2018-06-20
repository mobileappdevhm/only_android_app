package felix.peither.de.cie_for_android.CourseData;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import felix.peither.de.cie_for_android.R;

public class ListViewAdapter extends BaseAdapter {

    List<Course> courses = new ArrayList<>();
    Context ctx;
    LayoutInflater inflater;

    public ListViewAdapter(List<Course> courses, Context ctx, LayoutInflater inflater) {
        this.courses = courses;
        this.ctx = ctx;
        this.inflater = inflater;
    }


    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Object getItem(int position) {
        return courses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.courses_layout, null);

        return convertView;
    }
}
