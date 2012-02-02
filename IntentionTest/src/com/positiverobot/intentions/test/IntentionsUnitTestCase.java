package com.positiverobot.intentions.test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ComponentName;
import android.content.IntentFilter;
import android.test.ActivityInstrumentationTestCase2;

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
		 * Possible? getPackageManager().queryIntentActivities
		 * Could be used to find resolvers
		 */

	}
	
	/**
	 * These may only be prefered not all those intents in the system
	 */
	public void testGetPreferedActivities() {
		List<IntentFilter> filterAccumulator = new ArrayList<IntentFilter>();
		List<ComponentName> nameAccumulator = new ArrayList<ComponentName>();
		
		// http://developer.android.com/reference/android/content/pm/PackageManager.html#getPreferredActivities(java.util.List<android.content.IntentFilter>, java.util.List<android.content.ComponentName>, java.lang.String)
		// null for everything
		int preferredActivities = getActivity().getPackageManager().getPreferredActivities(filterAccumulator, nameAccumulator, null);
		assertTrue(preferredActivities == 0);
	}

}
