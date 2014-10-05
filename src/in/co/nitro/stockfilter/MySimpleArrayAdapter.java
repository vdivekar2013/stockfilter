package in.co.nitro.stockfilter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MySimpleArrayAdapter extends ArrayAdapter<Instrument> {
	private final Context context;

	public MySimpleArrayAdapter(Context context, List<Instrument> values) {
		super(context, R.layout.unitlayout, values);
		this.context = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.unitlayout, parent, false);
		if(position < getCount()) {
			TextView nameView = (TextView) rowView.findViewById(R.id.textView1);
			nameView.setText(this.getItem(position).getName());
			TextView lastPriceView = (TextView) rowView.findViewById(R.id.nameView);
			lastPriceView.setText(String.valueOf(getItem(position).getLastTrade()));
			TextView preClosePriceView = (TextView) rowView.findViewById(R.id.codeView);
			preClosePriceView.setText(String.valueOf(getItem(position).getPreviousclose()));
		}
		return rowView;
	}
} 
