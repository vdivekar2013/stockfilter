package in.co.nitro.stockfilter;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

public class FileReadTask extends AsyncTask<MainActivity,Void,List<Instrument>> {

	private MainActivity activity;
	protected void onPostExecute(List<Instrument> instrumentList) {
		if(instrumentList == null) {
			Context context = activity.getApplicationContext();
			CharSequence text = "Exception occurred while reading the file";
			int duration = Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			return; 
		}
		activity.setInstrumentMasterList(instrumentList); 
		final ListView listview = (ListView) activity.findViewById(R.id.listView1);
		Button filterButton = (Button) activity.findViewById(R.id.button1);
		if(filterButton != null)
			filterButton.setEnabled(true);
		// Look up the AdView as a resource and load a request.
		AdView adView = (AdView)activity.findViewById(R.id.adView);
//		adView.setAdSize(AdSize.BANNER);
//		adView.setAdUnitId("ca-app-pub-5650476482162625/4038884190");
		AdRequest adRequest = new AdRequest.Builder().build();
		adView.loadAd(adRequest);
		MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(activity, instrumentList);
		if(listview != null) {
			listview.setAdapter(adapter);
			listview.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> adapter, View view,      int pos, long id) {
					Instrument instrument = (Instrument) adapter.getItemAtPosition(pos);
					showInstrumentDetails(instrument);
				}
				@SuppressLint("NewApi")
				private void showInstrumentDetails(Instrument instrument) {
					FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
					Fragment prev = activity.getFragmentManager().findFragmentByTag("dialog");
					if (prev != null) {
						ft.remove(prev);
					}
					ft.addToBackStack(null);

					// Create and show the dialog.
					InstrumentDetailDialog newFragment = new InstrumentDetailDialog();
					newFragment.setInstrument(instrument);
					newFragment.show(ft, "dialog");
				}
			});
		}
		activity.onFilter(null);
	}

	@Override
	protected List<Instrument> doInBackground(MainActivity... activityArray) {
		this.activity = activityArray[0];
		if(activity.getInstrumentMasterList() != null)
			return activity.getInstrumentMasterList();
		List<Instrument> instrumentList = null;
		try {
			instrumentList = InstrumentReader.read(activity);
		} catch(Exception ex) {

		}
		return instrumentList;
	}
}

