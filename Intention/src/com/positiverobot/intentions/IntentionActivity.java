package com.positiverobot.intentions;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

/**
 * @author byeo
 * 
 */
public class IntentionActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	public class PInfo {
		private String appname = "";
		private String pname = "";
		private String versionName = "";
		private int versionCode = 0;
		private Drawable icon;

		private void prettyPrint() {
			Log.v("INTENTIONS", appname + "\t" + pname + "\t" + versionName
					+ "\t" + versionCode);
		}
	}

	public ArrayList<PInfo> getPackages() {
		ArrayList<PInfo> apps = getInstalledApps(false); /*
														 * false = no system
														 * packages
														 */
		final int max = apps.size();
		for (int i = 0; i < max; i++) {
			apps.get(i).prettyPrint();
		}
		return apps;
	}

	/**
	 * http://developer.android.com/reference/android/content/pm/PackageManager.
	 * html#GET_INTENT_FILTERS
	 * 
	 * @param getSysPackages
	 * @return
	 */
	private ArrayList<PInfo> getInstalledApps(boolean getSysPackages) {
		ArrayList<PInfo> res = new ArrayList<PInfo>();
		PackageManager packageManager = getPackageManager();
		List<PackageInfo> packs = packageManager
				.getInstalledPackages(PackageManager.GET_ACTIVITIES
						| PackageManager.GET_INTENT_FILTERS);
		for (int i = 0; i < packs.size(); i++) {
			PackageInfo p = packs.get(i);
			if ((!getSysPackages) && (p.versionName == null)) {
				continue;
			}

			PInfo newInfo = new PInfo();
			newInfo.appname = p.applicationInfo.loadLabel(packageManager)
					.toString();
			newInfo.pname = p.packageName;
			newInfo.versionName = p.versionName;
			newInfo.versionCode = p.versionCode;

			newInfo.icon = p.applicationInfo.loadIcon(packageManager);

			res.add(newInfo);
		}
		return res;
	}

}