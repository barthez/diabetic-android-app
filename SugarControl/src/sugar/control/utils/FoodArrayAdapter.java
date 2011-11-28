package sugar.control.utils;

import java.util.List;

import sugar.control.R;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class FoodArrayAdapter extends ArrayAdapter<Food> {

	private final List<Food> list;
	private final Activity context;

	public FoodArrayAdapter(Activity context, List<Food> list) {
		super(context, R.layout.rowbuttonlayout, list);
		this.context = context;
		this.list = list;
	}

	static class ViewHolder {
		protected TextView text;
		protected EditText weight;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		if (convertView == null) {
			LayoutInflater inflator = context.getLayoutInflater();
			view = inflator.inflate(R.layout.rowbuttonlayout, null);
			final ViewHolder viewHolder = new ViewHolder();
			viewHolder.text = (TextView) view.findViewById(R.id.label);
			viewHolder.weight = (EditText) view.findViewById(R.id.weight);
			viewHolder.weight
					.setOnEditorActionListener(new OnEditorActionListener() {
						
						@Override
						public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
							Food element = (Food) viewHolder.weight
							.getTag();
							try{
							element.setWeight(Double.parseDouble(v.getText().toString()));
							}catch (Exception e) {
								viewHolder.weight.setText("0.0");
								element.setWeight(0);
							}
							return true;
						}
					});
			
			view.setTag(viewHolder);
			viewHolder.weight.setTag(list.get(position));
		} else {
			view = convertView;
			((ViewHolder) view.getTag()).weight.setTag(list.get(position));
		}
		ViewHolder holder = (ViewHolder) view.getTag();
		holder.text.setText(list.get(position).getFoodName());
		holder.weight.setText(String.valueOf(list.get(position).getWeight()));
		return view;
	}
}

