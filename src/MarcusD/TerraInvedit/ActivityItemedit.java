package MarcusD.TerraInvedit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import MarcusD.TerraInvedit.ItemRegistry.Buff;
import MarcusD.TerraInvedit.ItemRegistry.ItemEntry;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;

public class ActivityItemedit extends Activity
{
	short iid;
	short cnt;
	byte buf;
	List<Map.Entry<Short, ItemEntry>> s;
	
	@Override
	public void onCreate(Bundle b)
	{
		super.onCreate(b);
		setContentView(R.layout.layout_itemedit);
		
		Intent it = getIntent();
		this.iid = it.getShortExtra("ID", (short)0);
		this.cnt = it.getShortExtra("CNT", (short)0);
		this.buf = it.getByteExtra("BUF", (byte)0);
		
		//CNT
		NumberPicker et = (NumberPicker)findViewById(R.id.numberPicker2);
		et.setMinValue(0);
		et.setMaxValue(999);
		et.setOrientation(NumberPicker.VERTICAL);
		et.setOnValueChangedListener(new OnValueChangeListener()
		{
			@Override
			public void onValueChange(NumberPicker picker, int oldVal, int newVal)
			{
				ActivityItemedit.this.cnt = (short)newVal;
			}
		});
		et.setValue(this.cnt);
		
		//ITEM
		s = new ArrayList<Map.Entry<Short, ItemEntry>>();
		s.addAll(ItemRegistry.instance.basemap.entrySet());
		et = (NumberPicker)findViewById(R.id.numberPicker1);
		et.setMinValue(0);
		et.setMaxValue(s.size() - 1);
		String[] caps = new String[s.size()];
		for(int i = 0; i != s.size(); i++)
		{
			Map.Entry<Short, ItemEntry> va = s.get(i);
			caps[i] = va.getValue().iname + "(" + va.getKey() + ")";
			if(va.getValue().iid == iid) et.setValue(i);
		}
		et.setDisplayedValues(caps);
		et.setOnValueChangedListener(new OnValueChangeListener()
		{
			@Override
			public void onValueChange(NumberPicker arg0, int arg1, int arg2)
			{
				iid = s.get(arg2).getValue().iid;
			}
		});
		
		//BUFF
		Buff[] bufs = Buff.values();
		caps = new String[Buff.values().length];
		et = (NumberPicker)findViewById(R.id.numberPicker3);
		et.setMinValue(0);
		et.setMaxValue(caps.length - 1);
		et.setValue(buf);
		for(int i = 0; i != bufs.length; i++)
		{
			caps[i] = bufs[i].toString();
		}
		et.setDisplayedValues(caps);
		et.setOnValueChangedListener(new OnValueChangeListener()
		{
			@Override
			public void onValueChange(NumberPicker arg0, int arg1, int arg2)
			{
				buf = (byte)arg2;
			}
		});
	}
	
	public void fak(View v)
	{
		setResult(-1);
		finish();
	}
    
    public void saev(View v)
    {
    	Intent i = new Intent();
    	i.putExtra("POS", getIntent().getIntExtra("POS", -1));
    	if(iid == 0 || cnt == 0)
    	{
    		i.putExtra("ID", (short)0);
    		i.putExtra("CNT", (short)0);
    		i.putExtra("BUF", (byte)0);
    	}
    	else
    	{
    		i.putExtra("ID", iid);
    		i.putExtra("CNT", cnt);
    		i.putExtra("BUF", buf);
    	}
    	setResult(420, i);
    	finish();
    }
}
