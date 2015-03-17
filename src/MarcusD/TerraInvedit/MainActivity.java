package MarcusD.TerraInvedit;

import eu.chainfire.libsuperuser.Shell;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends Activity {

	public Boolean beroken = false;
	ProgressDialog pd;
    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
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
    
    public void bad(View w)
    {
    	//Toast t = Toast.makeText(getApplicationContext(), getString(R.string.javatrans_rootprogress), Toast.LENGTH_LONG);
        //t.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        //t.show();
        
        pd = ProgressDialog.show(this, "ROOT", getString(R.string.javatrans_rootprogress), true);
        
        pd.setCancelable(true);
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
					beroken = !Shell.SU.available();
					
					
					
					Log.d("roottest", "is root beroken? " + beroken);
					
					if(beroken)
					{
						runOnUiThread(new Runnable()
						{
							@Override
							public void run()
							{
								Toast tempast = Toast.makeText(MainActivity.this.getApplicationContext(), getString(R.string.javatrans_rootejj), Toast.LENGTH_LONG);
								//tempast.setGravity(android.view.Gravity.CENTER, 0, 0);
								tempast.show();
							}
						});
					}
					else
					{
						startActivity(new Intent(MainActivity.this, ActivityPlayers.class));
					}
					
				}
				catch (Exception e)
				{
					e.printStackTrace();
				}
				pd.dismiss();
			}
		}).start();
    }
    
    
}
