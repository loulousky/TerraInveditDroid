package MarcusD.TerraInvedit;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;

import android.app.AlertDialog;
import android.app.Application;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class ThrowableApplication extends Application
{
	Boolean running = true;
	@Override
	public void onCreate()
	{
		super.onCreate();
		while(running)
		{
			try
			{
				Looper.loop();
				Thread.setDefaultUncaughtExceptionHandler(new UncaughtExceptionHandler()
				{
					@Override
					public void uncaughtException(Thread thread, Throwable ex)
					{
						showex(ex);
					}
				});
			}
			catch(Throwable t)
			{
				showex(t);
			}
		}
	}
	
	public void showex(Throwable t)
	{
		t.printStackTrace();
		StringWriter ejjwri = new StringWriter();
		t.printStackTrace(new PrintWriter(ejjwri));
		final AlertDialog ad = new AlertDialog.Builder(this).setTitle("Well, Dalvik threw an error at me").create();
		ad.setButton(AlertDialog.BUTTON_POSITIVE, "Copy texte", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				((CopyText)ad.findViewById(0x7FFF)).performLongClick();
			}
		});
		ad.setButton(AlertDialog.BUTTON_NEGATIVE, "Nah, m8", new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialog, int which)
			{
				ThrowableApplication.this.running = false;
				System.runFinalization();
				System.gc();
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});
		ad.setCancelable(false);
		ScrollView scv = new ScrollView(ad.getContext());
		CopyText ct = new CopyText(ad.getContext());
		ct.setId(0x7FFF);
		ct.setText(ejjwri.toString());
		scv.addView(ct);
		ad.setView(scv);
		ad.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
		ad.getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		ad.show();
	}
	
	private class CopyText extends TextView
	{

		public CopyText(Context context)
		{
			super(context);
			setOnLongClickListener(new OnLongClickListener()
			{
				@Override
				public boolean onLongClick(View v)
				{
					Log.d("CLIPPY", "It worx");
					ClipboardManager clippy = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
					clippy.setText(((TextView)v).getText());
					Toast t = Toast.makeText(getContext(), "", Toast.LENGTH_LONG);
					t.setText("Text copied to the clipboard.\nPlease post this message on my Youtube video, or on the Github page");
					t.show();
					return true;
				}
			});
		}
	}
}
