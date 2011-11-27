/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package sugar.control.database;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

/**
 *
 * @author rial
 */
public class FoodSimpleCursorAdapter extends SimpleCursorAdapter {
    
    private final Context context;

    public FoodSimpleCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to) {
        super(context, layout, c, from, to);
        this.context = context;
    }

    static class ViewHolder {
            protected TextView text;
            protected CheckBox checkbox;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
		View view = null;
//		if (convertView == null) {
//			LayoutInflater inflator = context.getLayoutInflater();
//			view = inflator.inflate(R.layout.rowbuttonlayout, null);
//			final ViewHolder viewHolder = new ViewHolder();
//			viewHolder.text = (TextView) view.findViewById(R.id.label);
//			viewHolder.checkbox = (CheckBox) view.findViewById(R.id.check);
//			viewHolder.checkbox
//					.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//
//						@Override
//						public void onCheckedChanged(CompoundButton buttonView,
//								boolean isChecked) {
//							Model element = (Model) viewHolder.checkbox
//									.getTag();
//							element.setSelected(buttonView.isChecked());
//
//						}
//					});
//			view.setTag(viewHolder);
//			viewHolder.checkbox.setTag(list.get(position));
//		} else {
//			view = convertView;
//			((ViewHolder) view.getTag()).checkbox.setTag(list.get(position));
//		}
//		ViewHolder holder = (ViewHolder) view.getTag();
//		holder.text.setText(list.get(position).getName());
//		holder.checkbox.setChecked(list.get(position).isSelected());
		return view;
	}

}
