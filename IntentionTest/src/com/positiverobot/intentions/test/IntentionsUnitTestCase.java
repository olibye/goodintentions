package com.positiverobot.intentions.test;

import java.util.ArrayList;

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


}
