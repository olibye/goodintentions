package com.positiverobot.intentions.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.content.ComponentName;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.AssetManager;
import android.content.res.XmlResourceParser;
import android.test.ActivityInstrumentationTestCase2;
import android.util.Log;

import com.positiverobot.intentions.IntentionActivity;
import com.positiverobot.intentions.IntentionActivity.PInfo;

public class IntentionsUnitTestCase extends
		ActivityInstrumentationTestCase2<IntentionActivity> {

	public IntentionsUnitTestCase() {
		super(IntentionActivity.class.getPackage().getName(),
				IntentionActivity.class);
	}

	public void testGetInstalledPackages() {

		ArrayList<PInfo> packages = getActivity().getPackages();
		assertNotNull(packages);
	}

	public void testQueryIntentActivities() {
		/*
		 * Possible? getPackageManager().queryIntentActivities Could be used to
		 * find resolvers
		 */

	}

	/**
	 * These may only be prefered not all those intents in the system
	 */
	public void testGetPreferedActivities() {
		List<IntentFilter> filterAccumulator = new ArrayList<IntentFilter>();
		List<ComponentName> nameAccumulator = new ArrayList<ComponentName>();

		// http://developer.android.com/reference/android/content/pm/PackageManager.html#getPreferredActivities(java.util.List<android.content.IntentFilter>,
		// java.util.List<android.content.ComponentName>, java.lang.String)
		// null for everything
		int preferredActivities = getActivity().getPackageManager()
				.getPreferredActivities(filterAccumulator, nameAccumulator,
						null);
		assertTrue(preferredActivities == 0);
	}

	public void testGetInstalledComponentList() throws NameNotFoundException,
			IOException, XmlPullParserException {
		PackageManager packageManager = getActivity().getPackageManager();
		List<PackageInfo> packs = packageManager
				.getInstalledPackages(PackageManager.GET_ACTIVITIES
						| PackageManager.GET_INTENT_FILTERS);

		for (PackageInfo packageInfo : packs) {
			AssetManager am = getActivity().createPackageContext(
					packageInfo.packageName, 0).getAssets();

			XmlResourceParser xpp = am
					.openXmlResourceParser("AndroidManifest.xml");

			assertNotNull(xpp);

			int eventType = xpp.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				if (eventType == XmlPullParser.START_DOCUMENT) {
				} else if (eventType == XmlPullParser.START_TAG) {
					if ("action".equals(xpp.getName())) {
						String name = xpp.getAttributeValue(0);
						Log.v(packageInfo.packageName, "Action Tag:" + name);
					}
					if ("category".equals(xpp.getName())) {
						String name = xpp.getAttributeValue(0);
						Log.v(packageInfo.packageName, "Category Tag:" + name);
					}
					if ("data".equals(xpp.getName())) {
						String name = xpp.getAttributeValue(0);
						Log.v(packageInfo.packageName, "Data Tag:" + name);
					}
				} else if (eventType == XmlPullParser.END_TAG) {
				} else if (eventType == XmlPullParser.TEXT) {
					Log.v(packageInfo.packageName, "Text " + xpp.getText());
				}
				eventType = xpp.next();
			}

			xpp.close();
		}
	}
}
