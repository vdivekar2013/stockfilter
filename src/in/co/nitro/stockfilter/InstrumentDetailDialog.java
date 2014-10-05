package in.co.nitro.stockfilter;

import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

@SuppressLint("NewApi")
public class InstrumentDetailDialog extends DialogFragment {

	private Instrument instrument;
	
	@SuppressLint("NewApi")
	public InstrumentDetailDialog() {
		// Empty constructor required
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.instrument_detail, container);
		getDialog().setTitle("Instrument Detail");
		TextView name = (TextView) view.findViewById(R.id.nameView);
		name.setText(instrument.getName());
		TextView code = (TextView) view.findViewById(R.id.codeView);
		code.setText(instrument.getCode());
		TextView type = (TextView) view.findViewById(R.id.typeView);
		type.setText(String.valueOf(instrument.getType()));
		TextView group = (TextView) view.findViewById(R.id.groupView);
		group.setText(String.valueOf(instrument.getGroup()));
		TextView open = (TextView) view.findViewById(R.id.openView);
		open.setText(String.valueOf(instrument.getOpen()));
		TextView close = (TextView) view.findViewById(R.id.closeView);
		close.setText(String.valueOf(instrument.getClose()));
		TextView low = (TextView) view.findViewById(R.id.lowView);
		low.setText(String.valueOf(instrument.getLow()));
		TextView high = (TextView) view.findViewById(R.id.highView);
		high.setText(String.valueOf(instrument.getHigh()));
		TextView previous = (TextView) view.findViewById(R.id.previousView);
		previous.setText(String.valueOf(instrument.getPreviousclose()));
		TextView ltp = (TextView) view.findViewById(R.id.ltpView);
		ltp.setText(String.valueOf(instrument.getLastTrade()));
		TextView totalTrades = (TextView) view.findViewById(R.id.totalTradesView);
		totalTrades.setText(String.valueOf(instrument.getNumberTrades()));
		TextView totalShares = (TextView) view.findViewById(R.id.totalSharesView);
		totalShares.setText(String.valueOf(instrument.getNumberShares()));
		Button b = (Button)view.findViewById(R.id.button1);
		if(b != null) {
			b.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					getDialog().dismiss();
				}
			});
		}

		return view;
	}

	public Instrument getInstrument() {
		return instrument;
	}

	public void setInstrument(Instrument instrument) {
		this.instrument = instrument;
	}
}