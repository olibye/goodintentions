package com.positiverobot.intentions.test;

import java.util.ArrayList;
import java.util.List;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.positiverobot.intentions.IntentionActivity;

public class IntentionsUnitTestCase extends
		ActivityInstrumentationTestCase2<IntentionActivity> {

	public IntentionsUnitTestCase() {
		super(IntentionActivity.class.getPackage().getName(),
				IntentionActivity.class);
	}

	public void testGetInstalledPackages() {
		ArrayList<PInfo> packages = getPackages();
		assertNotNull(packages);
	}

	class PInfo {
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

	private ArrayList<PInfo> getPackages() {
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

	private ArrayList<PInfo> getInstalledApps(boolean getSysPackages) {
		ArrayList<PInfo> res = new ArrayList<PInfo>();
		PackageManager packageManager = getActivity().getPackageManager();
		List<PackageInfo> packs = packageManager.getInstalledPackages(0);
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
