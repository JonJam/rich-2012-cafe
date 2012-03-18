package com.googlecode.rich2012cafe.activities;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.preference.ListPreference;
import android.util.AttributeSet;


public class ListPreferenceMultiSelect extends ListPreference {
	private boolean[] mClickedDialogEntryIndices;
	//Need to make sure the SEPARATOR is unique and weird enough that it doesn't match one of the entries.
	//Not using any fancy symbols because this is interpreted as a regex for splitting strings.
	private static final String SEPARATOR = "OV=I=XseparatorX=I=VO";

	
	public ListPreferenceMultiSelect(Context context) {
		super(context);
	}

	public ListPreferenceMultiSelect(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	protected void onPrepareDialogBuilder(final Builder builder) {

		CharSequence[] entries = getEntries();
		CharSequence[] entryValues = getEntryValues();
		if (entries == null || entryValues == null
				|| entries.length != entryValues.length) {
			throw new IllegalStateException(
			"ListPreference requires an entries array and an entryValues array which are both the same length");
		}

		mClickedDialogEntryIndices = new boolean[entryValues.length] ;
		restoreCheckedEntries();

		builder.setMultiChoiceItems(entries, mClickedDialogEntryIndices,
				new DialogInterface.OnMultiChoiceClickListener() {
			public void onClick(DialogInterface dialog, int which,
					boolean val) {
				mClickedDialogEntryIndices[which] = val;
				boolean empty = true;
				CharSequence[] entryValues = getEntryValues();
				for (int i = 0; i < entryValues.length; i++) {
					if (mClickedDialogEntryIndices[i]) {
						empty = false;
					}
				}
				if(empty){
					AlertDialog dialognew= new AlertDialog.Builder(builder.getContext()).create();
					dialognew.setMessage("You Must Select Atleast One Product");
					dialognew.show();
					mClickedDialogEntryIndices[which] = true;
				}
			}
		});
		

	}

	public static String[] parseStoredValue(CharSequence val) {
		if ("".equals(val) || val == null )
			return null;
		else
			return ((String) val).split(SEPARATOR);
	}

	private void restoreCheckedEntries() {
		CharSequence[] entryValues = getEntryValues();
		String[] vals = parseStoredValue(getValue());
		if (vals != null){
			for (int j = 0; j < vals.length; j++) {
				String val = vals[j].trim();
				for (int i = 0; i < entryValues.length; i++) {
					CharSequence entry = entryValues[i];
					if (entry.equals(val)) {
						mClickedDialogEntryIndices[i] = true;
						break;
					}
				}
			}
		}
		if(vals==null){
			for(int i=0; i<entryValues.length;i++){
				mClickedDialogEntryIndices[i] = true;
			}
		}
	}

	@Override
	protected void onDialogClosed(boolean positiveResult) {
		// super.onDialogClosed(positiveResult);
		
		CharSequence[] entryValues = getEntryValues();
		if (positiveResult && entryValues != null) {
			StringBuffer value = new StringBuffer();
			for (int i = 0; i < entryValues.length; i++) {
				if (mClickedDialogEntryIndices[i]) {
					value.append(entryValues[i]).append(SEPARATOR);
				}
			}

			if (callChangeListener(value)) {
				String val = value.toString();
				if (val.length() > 0)
					val = val.substring(0, val.length() - SEPARATOR.length());
				setValue(val);
			}
		}
	}
}