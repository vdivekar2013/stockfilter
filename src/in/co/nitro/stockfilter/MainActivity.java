package in.co.nitro.stockfilter;

import java.util.List;
import android.annotation.SuppressLint;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	private List<Instrument> instrumentMasterList = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceHolderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	public List<Instrument> getInstrumentMasterList() {
		return instrumentMasterList;
	}

	public void setInstrumentMasterList(List<Instrument> instrumentMasterList) {
		this.instrumentMasterList = instrumentMasterList;
	}

	public void onFilter(View view) {
		final EditText nameFilterText = (EditText) findViewById(R.id.editText1);
		final EditText ltpGreaterFilterText = (EditText) findViewById(R.id.editText2);
		final EditText ltpLesserFilterText = (EditText) findViewById(R.id.editText3);
		String nameFilterString = nameFilterText.getText().toString();
		String ltpGreaterFilterString = ltpGreaterFilterText.getText().toString();
		String ltpLesserFilterString = ltpLesserFilterText.getText().toString();
		List<Instrument> filteredList = null;
		if(!TextUtils.isEmpty(nameFilterString)) {
			filteredList = InstrumentFilter.filterOnName(instrumentMasterList, nameFilterString);
		} else {
			filteredList = instrumentMasterList;
		}
		if(!TextUtils.isEmpty(ltpGreaterFilterString)) {
			float ltpGreater = Float.parseFloat(ltpGreaterFilterText.getText().toString());
			filteredList = InstrumentFilter.filterOnLastTrade(filteredList, '>', ltpGreater);
		}
		if(!TextUtils.isEmpty(ltpLesserFilterString)) {
			float ltpLesser = Float.parseFloat(ltpLesserFilterText.getText().toString());
			filteredList = InstrumentFilter.filterOnLastTrade(filteredList, '<', ltpLesser);
		}
		final ListView listView = (ListView) findViewById(R.id.listView1);
		MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, filteredList);
		listView.setAdapter(adapter);


	}

}
